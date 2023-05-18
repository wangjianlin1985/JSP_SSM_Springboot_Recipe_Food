package com.chengxusheji.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.chengxusheji.utils.ExportExcelUtil;
import com.chengxusheji.utils.UserException;
import com.chengxusheji.service.CookCommentService;
import com.chengxusheji.service.CookingService;
import com.chengxusheji.po.CookComment;
import com.chengxusheji.po.Cooking;
import com.chengxusheji.service.CookClassService;
import com.chengxusheji.po.CookClass;

//Cooking管理控制层
@Controller
@RequestMapping("/Cooking")
public class CookingController extends BaseController {

    /*业务层对象*/
    @Resource CookingService cookingService;
    @Resource CookCommentService commentService;

    @Resource CookClassService cookClassService;
	@InitBinder("cookClassObj")
	public void initBindercookClassObj(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("cookClassObj.");
	}
	@InitBinder("cooking")
	public void initBinderCooking(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("cooking.");
	}
	/*跳转到添加Cooking视图*/
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model,HttpServletRequest request) throws Exception {
		model.addAttribute(new Cooking());
		/*查询所有的CookClass信息*/
		List<CookClass> cookClassList = cookClassService.queryAllCookClass();
		request.setAttribute("cookClassList", cookClassList);
		return "Cooking_add";
	}

	/*客户端ajax方式提交添加烹饪菜谱信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(@Validated Cooking cooking, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
		boolean success = false;
		if (br.hasErrors()) {
			message = "输入信息不符合要求！";
			writeJsonResponse(response, success, message);
			return ;
		}
		try {
			cooking.setCookingPhoto(this.handlePhotoUpload(request, "cookingPhotoFile"));
		} catch(UserException ex) {
			message = "图片格式不正确！";
			writeJsonResponse(response, success, message);
			return ;
		}
		cooking.setVideoFile(this.handleFileUpload(request, "videoFileFile"));
        cookingService.addCooking(cooking);
        message = "烹饪菜谱添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	/*ajax方式按照查询条件分页查询烹饪菜谱信息*/
	@RequestMapping(value = { "/list" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void list(@ModelAttribute("cookClassObj") CookClass cookClassObj,String cookingName,String gongxiao,String yongliao,String addTime,Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		if (page==null || page == 0) page = 1;
		if (cookingName == null) cookingName = "";
		if (gongxiao == null) gongxiao = "";
		if (yongliao == null) yongliao = "";
		if (addTime == null) addTime = "";
		if(rows != 0)cookingService.setRows(rows);
		List<Cooking> cookingList = cookingService.queryCooking(cookClassObj, cookingName, gongxiao, yongliao, addTime, page);
	    /*计算总的页数和总的记录数*/
	    cookingService.queryTotalPageAndRecordNumber(cookClassObj, cookingName, gongxiao, yongliao, addTime);
	    /*获取到总的页码数目*/
	    int totalPage = cookingService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = cookingService.getRecordNumber();
        response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象
		JSONObject jsonObj=new JSONObject();
		jsonObj.accumulate("total", recordNumber);
		JSONArray jsonArray = new JSONArray();
		for(Cooking cooking:cookingList) {
			JSONObject jsonCooking = cooking.getJsonObject();
			jsonArray.put(jsonCooking);
		}
		jsonObj.accumulate("rows", jsonArray);
		out.println(jsonObj.toString());
		out.flush();
		out.close();
	}

	/*ajax方式按照查询条件分页查询烹饪菜谱信息*/
	@RequestMapping(value = { "/listAll" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void listAll(HttpServletResponse response) throws Exception {
		List<Cooking> cookingList = cookingService.queryAllCooking();
        response.setContentType("text/json;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		JSONArray jsonArray = new JSONArray();
		for(Cooking cooking:cookingList) {
			JSONObject jsonCooking = new JSONObject();
			jsonCooking.accumulate("cookingId", cooking.getCookingId());
			jsonCooking.accumulate("cookingName", cooking.getCookingName());
			jsonArray.put(jsonCooking);
		}
		out.println(jsonArray.toString());
		out.flush();
		out.close();
	}

	/*前台按照查询条件分页查询烹饪菜谱信息*/
	@RequestMapping(value = { "/frontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String frontlist(@ModelAttribute("cookClassObj") CookClass cookClassObj,String cookingName,String gongxiao,String yongliao,String addTime,Integer currentPage, Model model, HttpServletRequest request) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (cookingName == null) cookingName = "";
		if (gongxiao == null) gongxiao = "";
		if (yongliao == null) yongliao = "";
		if (addTime == null) addTime = "";
		List<Cooking> cookingList = cookingService.queryCooking(cookClassObj, cookingName, gongxiao, yongliao, addTime, currentPage);
	    /*计算总的页数和总的记录数*/
	    cookingService.queryTotalPageAndRecordNumber(cookClassObj, cookingName, gongxiao, yongliao, addTime);
	    /*获取到总的页码数目*/
	    int totalPage = cookingService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = cookingService.getRecordNumber();
	    request.setAttribute("cookingList",  cookingList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("cookClassObj", cookClassObj);
	    request.setAttribute("cookingName", cookingName);
	    request.setAttribute("gongxiao", gongxiao);
	    request.setAttribute("yongliao", yongliao);
	    request.setAttribute("addTime", addTime);
	    List<CookClass> cookClassList = cookClassService.queryAllCookClass();
	    request.setAttribute("cookClassList", cookClassList);
		return "Cooking/cooking_frontquery_result"; 
	}

     /*前台查询Cooking信息*/
	@RequestMapping(value="/{cookingId}/frontshow",method=RequestMethod.GET)
	public String frontshow(@PathVariable Integer cookingId,Model model,HttpServletRequest request) throws Exception {
		/*根据主键cookingId获取Cooking对象*/
        Cooking cooking = cookingService.getCooking(cookingId);
        //查询此菜谱的所有评论信息
        ArrayList<CookComment> commentList = commentService.queryCookComment(cooking, null, "");
        List<CookClass> cookClassList = cookClassService.queryAllCookClass();
        request.setAttribute("cookClassList", cookClassList);
        request.setAttribute("cooking",  cooking);
        request.setAttribute("commentList", commentList);
        return "Cooking/cooking_frontshow";
	}

	/*ajax方式显示烹饪菜谱修改jsp视图页*/
	@RequestMapping(value="/{cookingId}/update",method=RequestMethod.GET)
	public void update(@PathVariable Integer cookingId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键cookingId获取Cooking对象*/
        Cooking cooking = cookingService.getCooking(cookingId);

        response.setContentType("text/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象 
		JSONObject jsonCooking = cooking.getJsonObject();
		out.println(jsonCooking.toString());
		out.flush();
		out.close();
	}

	/*ajax方式更新烹饪菜谱信息*/
	@RequestMapping(value = "/{cookingId}/update", method = RequestMethod.POST)
	public void update(@Validated Cooking cooking, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
    	boolean success = false;
		if (br.hasErrors()) { 
			message = "输入的信息有错误！";
			writeJsonResponse(response, success, message);
			return;
		}
		String cookingPhotoFileName = this.handlePhotoUpload(request, "cookingPhotoFile");
		if(!cookingPhotoFileName.equals("upload/NoImage.jpg"))cooking.setCookingPhoto(cookingPhotoFileName); 


		String videoFileFileName = this.handleFileUpload(request, "videoFileFile");
		if(!videoFileFileName.equals(""))cooking.setVideoFile(videoFileFileName);
		try {
			cookingService.updateCooking(cooking);
			message = "烹饪菜谱更新成功!";
			success = true;
			writeJsonResponse(response, success, message);
		} catch (Exception e) {
			e.printStackTrace();
			message = "烹饪菜谱更新失败!";
			writeJsonResponse(response, success, message); 
		}
	}
    /*删除烹饪菜谱信息*/
	@RequestMapping(value="/{cookingId}/delete",method=RequestMethod.GET)
	public String delete(@PathVariable Integer cookingId,HttpServletRequest request) throws UnsupportedEncodingException {
		  try {
			  cookingService.deleteCooking(cookingId);
	            request.setAttribute("message", "烹饪菜谱删除成功!");
	            return "message";
	        } catch (Exception e) { 
	            e.printStackTrace();
	            request.setAttribute("error", "烹饪菜谱删除失败!");
				return "error";

	        }

	}

	/*ajax方式删除多条烹饪菜谱记录*/
	@RequestMapping(value="/deletes",method=RequestMethod.POST)
	public void delete(String cookingIds,HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		String message = "";
    	boolean success = false;
        try { 
        	int count = cookingService.deleteCookings(cookingIds);
        	success = true;
        	message = count + "条记录删除成功";
        	writeJsonResponse(response, success, message);
        } catch (Exception e) { 
            //e.printStackTrace();
            message = "有记录存在外键约束,删除失败";
            writeJsonResponse(response, success, message);
        }
	}

	/*按照查询条件导出烹饪菜谱信息到Excel*/
	@RequestMapping(value = { "/OutToExcel" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void OutToExcel(@ModelAttribute("cookClassObj") CookClass cookClassObj,String cookingName,String gongxiao,String yongliao,String addTime, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
        if(cookingName == null) cookingName = "";
        if(gongxiao == null) gongxiao = "";
        if(yongliao == null) yongliao = "";
        if(addTime == null) addTime = "";
        List<Cooking> cookingList = cookingService.queryCooking(cookClassObj,cookingName,gongxiao,yongliao,addTime);
        ExportExcelUtil ex = new ExportExcelUtil();
        String _title = "Cooking信息记录"; 
        String[] headers = { "记录id","菜谱分类","菜谱名称","菜谱图片","菜谱功效","菜谱用料","发布时间"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<cookingList.size();i++) {
        	Cooking cooking = cookingList.get(i); 
        	dataset.add(new String[]{cooking.getCookingId() + "",cooking.getCookClassObj().getClassName(),cooking.getCookingName(),cooking.getCookingPhoto(),cooking.getGongxiao(),cooking.getYongliao(),cooking.getAddTime()});
        }
        /*
        OutputStream out = null;
		try {
			out = new FileOutputStream("C://output.xls");
			ex.exportExcel(title,headers, dataset, out);
		    out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		OutputStream out = null;//创建一个输出流对象 
		try { 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"Cooking.xls");//filename是下载的xls的名，建议最好用英文 
			response.setContentType("application/msexcel;charset=UTF-8");//设置类型 
			response.setHeader("Pragma","No-cache");//设置头 
			response.setHeader("Cache-Control","no-cache");//设置头 
			response.setDateHeader("Expires", 0);//设置日期头  
			String rootPath = request.getSession().getServletContext().getRealPath("/");
			ex.exportExcel(rootPath,_title,headers, dataset, out);
			out.flush();
		} catch (IOException e) { 
			e.printStackTrace(); 
		}finally{
			try{
				if(out!=null){ 
					out.close(); 
				}
			}catch(IOException e){ 
				e.printStackTrace(); 
			} 
		}
    }
}
