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
import com.chengxusheji.service.LearnRecService;
import com.chengxusheji.po.LearnRec;
import com.chengxusheji.service.CookingService;
import com.chengxusheji.po.Cooking;
import com.chengxusheji.service.UserInfoService;
import com.chengxusheji.po.UserInfo;

//LearnRec管理控制层
@Controller
@RequestMapping("/LearnRec")
public class LearnRecController extends BaseController {

    /*业务层对象*/
    @Resource LearnRecService learnRecService;

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
	@InitBinder("learnRec")
	public void initBinderLearnRec(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("learnRec.");
	}
	/*跳转到添加LearnRec视图*/
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model,HttpServletRequest request) throws Exception {
		model.addAttribute(new LearnRec());
		/*查询所有的Cooking信息*/
		List<Cooking> cookingList = cookingService.queryAllCooking();
		request.setAttribute("cookingList", cookingList);
		/*查询所有的UserInfo信息*/
		List<UserInfo> userInfoList = userInfoService.queryAllUserInfo();
		request.setAttribute("userInfoList", userInfoList);
		return "LearnRec_add";
	}

	/*客户端ajax方式提交添加学习记录信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(@Validated LearnRec learnRec, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
		boolean success = false;
		if (br.hasErrors()) {
			message = "输入信息不符合要求！";
			writeJsonResponse(response, success, message);
			return ;
		}
        learnRecService.addLearnRec(learnRec);
        message = "学习记录添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	
	
	/*客户端ajax方式提交添加学习记录信息*/
	@RequestMapping(value = "/userAdd", method = RequestMethod.POST)
	public void userAdd(LearnRec learnRec, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response, HttpSession session) throws Exception {
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
		learnRec.setUserObj(userObj);
		
		learnRec.setJifen(5.0f);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		learnRec.setLearnTime(sdf.format(new java.util.Date()));
		
		if(learnRecService.queryLearnRec(learnRec.getCookingObj(), userObj, "").size() > 0) {
			message = "你已经学习过此菜谱了！";
			writeJsonResponse(response, success, message);
			return ;
		}
		
        learnRecService.addLearnRec(learnRec);
        
        UserInfo db_user = userInfoService.getUserInfo(user_name);
        db_user.setJifen(db_user.getJifen() + 5.0f);
        userInfoService.updateUserInfo(db_user);
        
        message = "学习记录添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	
	
	/*ajax方式按照查询条件分页查询学习记录信息*/
	@RequestMapping(value = { "/list" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void list(@ModelAttribute("cookingObj") Cooking cookingObj,@ModelAttribute("userObj") UserInfo userObj,String learnTime,Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		if (page==null || page == 0) page = 1;
		if (learnTime == null) learnTime = "";
		if(rows != 0)learnRecService.setRows(rows);
		List<LearnRec> learnRecList = learnRecService.queryLearnRec(cookingObj, userObj, learnTime, page);
	    /*计算总的页数和总的记录数*/
	    learnRecService.queryTotalPageAndRecordNumber(cookingObj, userObj, learnTime);
	    /*获取到总的页码数目*/
	    int totalPage = learnRecService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = learnRecService.getRecordNumber();
        response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象
		JSONObject jsonObj=new JSONObject();
		jsonObj.accumulate("total", recordNumber);
		JSONArray jsonArray = new JSONArray();
		for(LearnRec learnRec:learnRecList) {
			JSONObject jsonLearnRec = learnRec.getJsonObject();
			jsonArray.put(jsonLearnRec);
		}
		jsonObj.accumulate("rows", jsonArray);
		out.println(jsonObj.toString());
		out.flush();
		out.close();
	}

	/*ajax方式按照查询条件分页查询学习记录信息*/
	@RequestMapping(value = { "/listAll" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void listAll(HttpServletResponse response) throws Exception {
		List<LearnRec> learnRecList = learnRecService.queryAllLearnRec();
        response.setContentType("text/json;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		JSONArray jsonArray = new JSONArray();
		for(LearnRec learnRec:learnRecList) {
			JSONObject jsonLearnRec = new JSONObject();
			jsonLearnRec.accumulate("recId", learnRec.getRecId());
			jsonArray.put(jsonLearnRec);
		}
		out.println(jsonArray.toString());
		out.flush();
		out.close();
	}

	/*前台按照查询条件分页查询学习记录信息*/
	@RequestMapping(value = { "/frontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String frontlist(@ModelAttribute("cookingObj") Cooking cookingObj,@ModelAttribute("userObj") UserInfo userObj,String learnTime,Integer currentPage, Model model, HttpServletRequest request) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (learnTime == null) learnTime = "";
		List<LearnRec> learnRecList = learnRecService.queryLearnRec(cookingObj, userObj, learnTime, currentPage);
	    /*计算总的页数和总的记录数*/
	    learnRecService.queryTotalPageAndRecordNumber(cookingObj, userObj, learnTime);
	    /*获取到总的页码数目*/
	    int totalPage = learnRecService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = learnRecService.getRecordNumber();
	    request.setAttribute("learnRecList",  learnRecList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("cookingObj", cookingObj);
	    request.setAttribute("userObj", userObj);
	    request.setAttribute("learnTime", learnTime);
	    List<Cooking> cookingList = cookingService.queryAllCooking();
	    request.setAttribute("cookingList", cookingList);
	    List<UserInfo> userInfoList = userInfoService.queryAllUserInfo();
	    request.setAttribute("userInfoList", userInfoList);
		return "LearnRec/learnRec_frontquery_result"; 
	}
	
	
	/*前台按照查询条件分页查询学习记录信息*/
	@RequestMapping(value = { "/userFrontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String userFrontlist(@ModelAttribute("cookingObj") Cooking cookingObj,@ModelAttribute("userObj") UserInfo userObj,String learnTime,Integer currentPage, Model model, HttpServletRequest request,HttpSession session) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (learnTime == null) learnTime = "";
		userObj = new UserInfo();
		userObj.setUser_name(session.getAttribute("user_name").toString());
		List<LearnRec> learnRecList = learnRecService.queryLearnRec(cookingObj, userObj, learnTime, currentPage);
	    /*计算总的页数和总的记录数*/
	    learnRecService.queryTotalPageAndRecordNumber(cookingObj, userObj, learnTime);
	    /*获取到总的页码数目*/
	    int totalPage = learnRecService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = learnRecService.getRecordNumber();
	    request.setAttribute("learnRecList",  learnRecList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("cookingObj", cookingObj);
	    request.setAttribute("userObj", userObj);
	    request.setAttribute("learnTime", learnTime);
	    List<Cooking> cookingList = cookingService.queryAllCooking();
	    request.setAttribute("cookingList", cookingList);
	    List<UserInfo> userInfoList = userInfoService.queryAllUserInfo();
	    request.setAttribute("userInfoList", userInfoList);
		return "LearnRec/learnRec_userFrontquery_result"; 
	}
	

     /*前台查询LearnRec信息*/
	@RequestMapping(value="/{recId}/frontshow",method=RequestMethod.GET)
	public String frontshow(@PathVariable Integer recId,Model model,HttpServletRequest request) throws Exception {
		/*根据主键recId获取LearnRec对象*/
        LearnRec learnRec = learnRecService.getLearnRec(recId);

        List<Cooking> cookingList = cookingService.queryAllCooking();
        request.setAttribute("cookingList", cookingList);
        List<UserInfo> userInfoList = userInfoService.queryAllUserInfo();
        request.setAttribute("userInfoList", userInfoList);
        request.setAttribute("learnRec",  learnRec);
        return "LearnRec/learnRec_frontshow";
	}

	/*ajax方式显示学习记录修改jsp视图页*/
	@RequestMapping(value="/{recId}/update",method=RequestMethod.GET)
	public void update(@PathVariable Integer recId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键recId获取LearnRec对象*/
        LearnRec learnRec = learnRecService.getLearnRec(recId);

        response.setContentType("text/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象 
		JSONObject jsonLearnRec = learnRec.getJsonObject();
		out.println(jsonLearnRec.toString());
		out.flush();
		out.close();
	}

	/*ajax方式更新学习记录信息*/
	@RequestMapping(value = "/{recId}/update", method = RequestMethod.POST)
	public void update(@Validated LearnRec learnRec, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
    	boolean success = false;
		if (br.hasErrors()) { 
			message = "输入的信息有错误！";
			writeJsonResponse(response, success, message);
			return;
		}
		try {
			learnRecService.updateLearnRec(learnRec);
			message = "学习记录更新成功!";
			success = true;
			writeJsonResponse(response, success, message);
		} catch (Exception e) {
			e.printStackTrace();
			message = "学习记录更新失败!";
			writeJsonResponse(response, success, message); 
		}
	}
    /*删除学习记录信息*/
	@RequestMapping(value="/{recId}/delete",method=RequestMethod.GET)
	public String delete(@PathVariable Integer recId,HttpServletRequest request) throws UnsupportedEncodingException {
		  try {
			  learnRecService.deleteLearnRec(recId);
	            request.setAttribute("message", "学习记录删除成功!");
	            return "message";
	        } catch (Exception e) { 
	            e.printStackTrace();
	            request.setAttribute("error", "学习记录删除失败!");
				return "error";

	        }

	}

	/*ajax方式删除多条学习记录记录*/
	@RequestMapping(value="/deletes",method=RequestMethod.POST)
	public void delete(String recIds,HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		String message = "";
    	boolean success = false;
        try { 
        	int count = learnRecService.deleteLearnRecs(recIds);
        	success = true;
        	message = count + "条记录删除成功";
        	writeJsonResponse(response, success, message);
        } catch (Exception e) { 
            //e.printStackTrace();
            message = "有记录存在外键约束,删除失败";
            writeJsonResponse(response, success, message);
        }
	}

	/*按照查询条件导出学习记录信息到Excel*/
	@RequestMapping(value = { "/OutToExcel" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void OutToExcel(@ModelAttribute("cookingObj") Cooking cookingObj,@ModelAttribute("userObj") UserInfo userObj,String learnTime, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
        if(learnTime == null) learnTime = "";
        List<LearnRec> learnRecList = learnRecService.queryLearnRec(cookingObj,userObj,learnTime);
        ExportExcelUtil ex = new ExportExcelUtil();
        String _title = "LearnRec信息记录"; 
        String[] headers = { "记录id","学会菜谱","学习用户","获得积分","学会时间"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<learnRecList.size();i++) {
        	LearnRec learnRec = learnRecList.get(i); 
        	dataset.add(new String[]{learnRec.getRecId() + "",learnRec.getCookingObj().getCookingName(),learnRec.getUserObj().getName(),learnRec.getJifen() + "",learnRec.getLearnTime()});
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
			response.setHeader("Content-disposition","attachment; filename="+"LearnRec.xls");//filename是下载的xls的名，建议最好用英文 
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
