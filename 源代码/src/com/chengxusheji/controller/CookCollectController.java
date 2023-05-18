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
import com.chengxusheji.service.CookCollectService;
import com.chengxusheji.po.CookCollect;
import com.chengxusheji.service.CookingService;
import com.chengxusheji.po.Cooking;
import com.chengxusheji.service.UserInfoService;
import com.chengxusheji.po.UserInfo;

//CookCollect管理控制层
@Controller
@RequestMapping("/CookCollect")
public class CookCollectController extends BaseController {

    /*业务层对象*/
    @Resource CookCollectService cookCollectService;

    @Resource CookingService cookingService;
    @Resource UserInfoService userInfoService;
	@InitBinder("cookingObj")
	public void initBindercookingObj(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("cookingObj.");
	}
	@InitBinder("userObj")
	public void initBinderuserObj(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("userObj.");
	}
	@InitBinder("cookCollect")
	public void initBinderCookCollect(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("cookCollect.");
	}
	/*跳转到添加CookCollect视图*/
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model,HttpServletRequest request) throws Exception {
		model.addAttribute(new CookCollect());
		/*查询所有的Cooking信息*/
		List<Cooking> cookingList = cookingService.queryAllCooking();
		request.setAttribute("cookingList", cookingList);
		/*查询所有的UserInfo信息*/
		List<UserInfo> userInfoList = userInfoService.queryAllUserInfo();
		request.setAttribute("userInfoList", userInfoList);
		return "CookCollect_add";
	}

	/*客户端ajax方式提交添加菜谱收藏信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(@Validated CookCollect cookCollect, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
		boolean success = false;
		if (br.hasErrors()) {
			message = "输入信息不符合要求！";
			writeJsonResponse(response, success, message);
			return ;
		}
        cookCollectService.addCookCollect(cookCollect);
        message = "菜谱收藏添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	
	
	/*客户端ajax方式提交添加菜谱收藏信息*/
	@RequestMapping(value = "/userAdd", method = RequestMethod.POST)
	public void userAdd(CookCollect cookCollect, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response,HttpSession session) throws Exception {
		String message = "";
		boolean success = false;
		
		String user_name = (String) session.getAttribute("user_name");
		if(null == user_name) {
			message = "请先登录网站！";
			writeJsonResponse(response, success, message);
			return ;
		}
		UserInfo userObj = new UserInfo();
		userObj.setUser_name(user_name);
		cookCollect.setUserObj(userObj);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		cookCollect.setCollectionTime(sdf.format(new java.util.Date()));
		
		if(cookCollectService.queryCookCollect(cookCollect.getCookingObj(), userObj, "").size() > 0) {
			message = "你已经收藏过此菜谱！";
			writeJsonResponse(response, success, message);
			return ;
		}
		
        cookCollectService.addCookCollect(cookCollect);
        message = "菜谱收藏添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	
	
	
	/*ajax方式按照查询条件分页查询菜谱收藏信息*/
	@RequestMapping(value = { "/list" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void list(@ModelAttribute("cookingObj") Cooking cookingObj,@ModelAttribute("userObj") UserInfo userObj,String collectionTime,Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		if (page==null || page == 0) page = 1;
		if (collectionTime == null) collectionTime = "";
		if(rows != 0)cookCollectService.setRows(rows);
		List<CookCollect> cookCollectList = cookCollectService.queryCookCollect(cookingObj, userObj, collectionTime, page);
	    /*计算总的页数和总的记录数*/
	    cookCollectService.queryTotalPageAndRecordNumber(cookingObj, userObj, collectionTime);
	    /*获取到总的页码数目*/
	    int totalPage = cookCollectService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = cookCollectService.getRecordNumber();
        response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象
		JSONObject jsonObj=new JSONObject();
		jsonObj.accumulate("total", recordNumber);
		JSONArray jsonArray = new JSONArray();
		for(CookCollect cookCollect:cookCollectList) {
			JSONObject jsonCookCollect = cookCollect.getJsonObject();
			jsonArray.put(jsonCookCollect);
		}
		jsonObj.accumulate("rows", jsonArray);
		out.println(jsonObj.toString());
		out.flush();
		out.close();
	}

	/*ajax方式按照查询条件分页查询菜谱收藏信息*/
	@RequestMapping(value = { "/listAll" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void listAll(HttpServletResponse response) throws Exception {
		List<CookCollect> cookCollectList = cookCollectService.queryAllCookCollect();
        response.setContentType("text/json;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		JSONArray jsonArray = new JSONArray();
		for(CookCollect cookCollect:cookCollectList) {
			JSONObject jsonCookCollect = new JSONObject();
			jsonCookCollect.accumulate("collectId", cookCollect.getCollectId());
			jsonArray.put(jsonCookCollect);
		}
		out.println(jsonArray.toString());
		out.flush();
		out.close();
	}

	/*前台按照查询条件分页查询菜谱收藏信息*/
	@RequestMapping(value = { "/frontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String frontlist(@ModelAttribute("cookingObj") Cooking cookingObj,@ModelAttribute("userObj") UserInfo userObj,String collectionTime,Integer currentPage, Model model, HttpServletRequest request) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (collectionTime == null) collectionTime = "";
		List<CookCollect> cookCollectList = cookCollectService.queryCookCollect(cookingObj, userObj, collectionTime, currentPage);
	    /*计算总的页数和总的记录数*/
	    cookCollectService.queryTotalPageAndRecordNumber(cookingObj, userObj, collectionTime);
	    /*获取到总的页码数目*/
	    int totalPage = cookCollectService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = cookCollectService.getRecordNumber();
	    request.setAttribute("cookCollectList",  cookCollectList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("cookingObj", cookingObj);
	    request.setAttribute("userObj", userObj);
	    request.setAttribute("collectionTime", collectionTime);
	    List<Cooking> cookingList = cookingService.queryAllCooking();
	    request.setAttribute("cookingList", cookingList);
	    List<UserInfo> userInfoList = userInfoService.queryAllUserInfo();
	    request.setAttribute("userInfoList", userInfoList);
		return "CookCollect/cookCollect_frontquery_result"; 
	}
	
	
	
	/*前台按照查询条件分页查询菜谱收藏信息*/
	@RequestMapping(value = { "/userFrontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String userFrontlist(@ModelAttribute("cookingObj") Cooking cookingObj,@ModelAttribute("userObj") UserInfo userObj,String collectionTime,Integer currentPage, Model model, HttpServletRequest request,HttpSession session) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (collectionTime == null) collectionTime = "";
		userObj = new UserInfo();
		userObj.setUser_name(session.getAttribute("user_name").toString());
		List<CookCollect> cookCollectList = cookCollectService.queryCookCollect(cookingObj, userObj, collectionTime, currentPage);
	    /*计算总的页数和总的记录数*/
	    cookCollectService.queryTotalPageAndRecordNumber(cookingObj, userObj, collectionTime);
	    /*获取到总的页码数目*/
	    int totalPage = cookCollectService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = cookCollectService.getRecordNumber();
	    request.setAttribute("cookCollectList",  cookCollectList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("cookingObj", cookingObj);
	    request.setAttribute("userObj", userObj);
	    request.setAttribute("collectionTime", collectionTime);
	    List<Cooking> cookingList = cookingService.queryAllCooking();
	    request.setAttribute("cookingList", cookingList);
	    List<UserInfo> userInfoList = userInfoService.queryAllUserInfo();
	    request.setAttribute("userInfoList", userInfoList);
		return "CookCollect/cookCollect_userFrontquery_result"; 
	}

     /*前台查询CookCollect信息*/
	@RequestMapping(value="/{collectId}/frontshow",method=RequestMethod.GET)
	public String frontshow(@PathVariable Integer collectId,Model model,HttpServletRequest request) throws Exception {
		/*根据主键collectId获取CookCollect对象*/
        CookCollect cookCollect = cookCollectService.getCookCollect(collectId);

        List<Cooking> cookingList = cookingService.queryAllCooking();
        request.setAttribute("cookingList", cookingList);
        List<UserInfo> userInfoList = userInfoService.queryAllUserInfo();
        request.setAttribute("userInfoList", userInfoList);
        request.setAttribute("cookCollect",  cookCollect);
        return "CookCollect/cookCollect_frontshow";
	}

	/*ajax方式显示菜谱收藏修改jsp视图页*/
	@RequestMapping(value="/{collectId}/update",method=RequestMethod.GET)
	public void update(@PathVariable Integer collectId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键collectId获取CookCollect对象*/
        CookCollect cookCollect = cookCollectService.getCookCollect(collectId);

        response.setContentType("text/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象 
		JSONObject jsonCookCollect = cookCollect.getJsonObject();
		out.println(jsonCookCollect.toString());
		out.flush();
		out.close();
	}

	/*ajax方式更新菜谱收藏信息*/
	@RequestMapping(value = "/{collectId}/update", method = RequestMethod.POST)
	public void update(@Validated CookCollect cookCollect, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
    	boolean success = false;
		if (br.hasErrors()) { 
			message = "输入的信息有错误！";
			writeJsonResponse(response, success, message);
			return;
		}
		try {
			cookCollectService.updateCookCollect(cookCollect);
			message = "菜谱收藏更新成功!";
			success = true;
			writeJsonResponse(response, success, message);
		} catch (Exception e) {
			e.printStackTrace();
			message = "菜谱收藏更新失败!";
			writeJsonResponse(response, success, message); 
		}
	}
    /*删除菜谱收藏信息*/
	@RequestMapping(value="/{collectId}/delete",method=RequestMethod.GET)
	public String delete(@PathVariable Integer collectId,HttpServletRequest request) throws UnsupportedEncodingException {
		  try {
			  cookCollectService.deleteCookCollect(collectId);
	            request.setAttribute("message", "菜谱收藏删除成功!");
	            return "message";
	        } catch (Exception e) { 
	            e.printStackTrace();
	            request.setAttribute("error", "菜谱收藏删除失败!");
				return "error";

	        }

	}

	/*ajax方式删除多条菜谱收藏记录*/
	@RequestMapping(value="/deletes",method=RequestMethod.POST)
	public void delete(String collectIds,HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		String message = "";
    	boolean success = false;
        try { 
        	int count = cookCollectService.deleteCookCollects(collectIds);
        	success = true;
        	message = count + "条记录删除成功";
        	writeJsonResponse(response, success, message);
        } catch (Exception e) { 
            //e.printStackTrace();
            message = "有记录存在外键约束,删除失败";
            writeJsonResponse(response, success, message);
        }
	}

	/*按照查询条件导出菜谱收藏信息到Excel*/
	@RequestMapping(value = { "/OutToExcel" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void OutToExcel(@ModelAttribute("cookingObj") Cooking cookingObj,@ModelAttribute("userObj") UserInfo userObj,String collectionTime, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
        if(collectionTime == null) collectionTime = "";
        List<CookCollect> cookCollectList = cookCollectService.queryCookCollect(cookingObj,userObj,collectionTime);
        ExportExcelUtil ex = new ExportExcelUtil();
        String _title = "CookCollect信息记录"; 
        String[] headers = { "收藏id","收藏菜谱","收藏用户","收藏时间"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<cookCollectList.size();i++) {
        	CookCollect cookCollect = cookCollectList.get(i); 
        	dataset.add(new String[]{cookCollect.getCollectId() + "",cookCollect.getCookingObj().getCookingName(),cookCollect.getUserObj().getName(),cookCollect.getCollectionTime()});
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
			response.setHeader("Content-disposition","attachment; filename="+"CookCollect.xls");//filename是下载的xls的名，建议最好用英文 
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
