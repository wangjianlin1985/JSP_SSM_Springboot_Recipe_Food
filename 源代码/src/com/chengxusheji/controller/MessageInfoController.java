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
import com.chengxusheji.service.MessageInfoService;
import com.chengxusheji.po.MessageInfo;
import com.chengxusheji.service.UserInfoService;
import com.chengxusheji.po.UserInfo;

//MessageInfo管理控制层
@Controller
@RequestMapping("/MessageInfo")
public class MessageInfoController extends BaseController {

    /*业务层对象*/
    @Resource MessageInfoService messageInfoService;

    @Resource UserInfoService userInfoService;
	@InitBinder("sender")
	public void initBindersender(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("sender.");
	}
	@InitBinder("reciever")
	public void initBinderreciever(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("reciever.");
	}
	@InitBinder("messageInfo")
	public void initBinderMessageInfo(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("messageInfo.");
	}
	/*跳转到添加MessageInfo视图*/
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model,HttpServletRequest request) throws Exception {
		model.addAttribute(new MessageInfo());
		/*查询所有的UserInfo信息*/
		List<UserInfo> userInfoList = userInfoService.queryAllUserInfo();
		request.setAttribute("userInfoList", userInfoList);
		return "MessageInfo_add";
	}

	/*客户端ajax方式提交添加用户消息信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(@Validated MessageInfo messageInfo, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
		boolean success = false;
		if (br.hasErrors()) {
			message = "输入信息不符合要求！";
			writeJsonResponse(response, success, message);
			return ;
		}
        messageInfoService.addMessageInfo(messageInfo);
        message = "用户消息添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	
	
	/*客户端ajax方式提交添加用户消息信息*/
	@RequestMapping(value = "/userAdd", method = RequestMethod.POST)
	public void userAdd(MessageInfo messageInfo, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response,HttpSession session) throws Exception {
		String message = "";
		boolean success = false;
		String user_name = (String) session.getAttribute("user_name");
		UserInfo userObj = new UserInfo();
		userObj.setUser_name(user_name);
		
		if(user_name.equals(messageInfo.getReciever().getUser_name())) {
			message = "不能给自己发信息！";
			writeJsonResponse(response, success, message);
			return ;
		}
		
		messageInfo.setSender(userObj);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		messageInfo.setSendTime(sdf.format(new java.util.Date()));
		
		messageInfo.setReadState("未读");
        messageInfoService.addMessageInfo(messageInfo);
        message = "用户消息添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	
	
	
	/*ajax方式按照查询条件分页查询用户消息信息*/
	@RequestMapping(value = { "/list" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void list(String content,@ModelAttribute("sender") UserInfo sender,@ModelAttribute("reciever") UserInfo reciever,String sendTime,String readState,Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		if (page==null || page == 0) page = 1;
		if (content == null) content = "";
		if (sendTime == null) sendTime = "";
		if (readState == null) readState = "";
		if(rows != 0)messageInfoService.setRows(rows);
		List<MessageInfo> messageInfoList = messageInfoService.queryMessageInfo(content, sender, reciever, sendTime, readState, page);
	    /*计算总的页数和总的记录数*/
	    messageInfoService.queryTotalPageAndRecordNumber(content, sender, reciever, sendTime, readState);
	    /*获取到总的页码数目*/
	    int totalPage = messageInfoService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = messageInfoService.getRecordNumber();
        response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象
		JSONObject jsonObj=new JSONObject();
		jsonObj.accumulate("total", recordNumber);
		JSONArray jsonArray = new JSONArray();
		for(MessageInfo messageInfo:messageInfoList) {
			JSONObject jsonMessageInfo = messageInfo.getJsonObject();
			jsonArray.put(jsonMessageInfo);
		}
		jsonObj.accumulate("rows", jsonArray);
		out.println(jsonObj.toString());
		out.flush();
		out.close();
	}

	/*ajax方式按照查询条件分页查询用户消息信息*/
	@RequestMapping(value = { "/listAll" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void listAll(HttpServletResponse response) throws Exception {
		List<MessageInfo> messageInfoList = messageInfoService.queryAllMessageInfo();
        response.setContentType("text/json;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		JSONArray jsonArray = new JSONArray();
		for(MessageInfo messageInfo:messageInfoList) {
			JSONObject jsonMessageInfo = new JSONObject();
			jsonMessageInfo.accumulate("messageId", messageInfo.getMessageId());
			jsonMessageInfo.accumulate("content", messageInfo.getContent());
			jsonArray.put(jsonMessageInfo);
		}
		out.println(jsonArray.toString());
		out.flush();
		out.close();
	}

	/*前台按照查询条件分页查询用户消息信息*/
	@RequestMapping(value = { "/frontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String frontlist(String content,@ModelAttribute("sender") UserInfo sender,@ModelAttribute("reciever") UserInfo reciever,String sendTime,String readState,Integer currentPage, Model model, HttpServletRequest request) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (content == null) content = "";
		if (sendTime == null) sendTime = "";
		if (readState == null) readState = "";
		List<MessageInfo> messageInfoList = messageInfoService.queryMessageInfo(content, sender, reciever, sendTime, readState, currentPage);
	    /*计算总的页数和总的记录数*/
	    messageInfoService.queryTotalPageAndRecordNumber(content, sender, reciever, sendTime, readState);
	    /*获取到总的页码数目*/
	    int totalPage = messageInfoService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = messageInfoService.getRecordNumber();
	    request.setAttribute("messageInfoList",  messageInfoList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("content", content);
	    request.setAttribute("sender", sender);
	    request.setAttribute("reciever", reciever);
	    request.setAttribute("sendTime", sendTime);
	    request.setAttribute("readState", readState);
	    List<UserInfo> userInfoList = userInfoService.queryAllUserInfo();
	    request.setAttribute("userInfoList", userInfoList);
		return "MessageInfo/messageInfo_frontquery_result"; 
	}
	
	
	/*前台按照查询条件分页查询用户消息信息*/
	@RequestMapping(value = { "/userFrontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String userFrontlist(String content,@ModelAttribute("sender") UserInfo sender,@ModelAttribute("reciever") UserInfo reciever,String sendTime,String readState,Integer currentPage, Model model, HttpServletRequest request,HttpSession session) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (content == null) content = "";
		if (sendTime == null) sendTime = "";
		if (readState == null) readState = "";
		reciever = new UserInfo();
		reciever.setUser_name(session.getAttribute("user_name").toString());
		List<MessageInfo> messageInfoList = messageInfoService.queryMessageInfo(content, sender, reciever, sendTime, readState, currentPage);
	    /*计算总的页数和总的记录数*/
	    messageInfoService.queryTotalPageAndRecordNumber(content, sender, reciever, sendTime, readState);
	    /*获取到总的页码数目*/
	    int totalPage = messageInfoService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = messageInfoService.getRecordNumber();
	    request.setAttribute("messageInfoList",  messageInfoList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("content", content);
	    request.setAttribute("sender", sender);
	    request.setAttribute("reciever", reciever);
	    request.setAttribute("sendTime", sendTime);
	    request.setAttribute("readState", readState);
	    List<UserInfo> userInfoList = userInfoService.queryAllUserInfo();
	    request.setAttribute("userInfoList", userInfoList);
		return "MessageInfo/messageInfo_userFrontquery_result"; 
	}
	

     /*前台查询MessageInfo信息*/
	@RequestMapping(value="/{messageId}/frontshow",method=RequestMethod.GET)
	public String frontshow(@PathVariable Integer messageId,Model model,HttpServletRequest request) throws Exception {
		/*根据主键messageId获取MessageInfo对象*/
        MessageInfo messageInfo = messageInfoService.getMessageInfo(messageId);
        messageInfo.setReadState("已读");
        messageInfoService.updateMessageInfo(messageInfo);
        List<UserInfo> userInfoList = userInfoService.queryAllUserInfo();
        request.setAttribute("userInfoList", userInfoList);
        request.setAttribute("messageInfo",  messageInfo);
        return "MessageInfo/messageInfo_frontshow";
	}

	/*ajax方式显示用户消息修改jsp视图页*/
	@RequestMapping(value="/{messageId}/update",method=RequestMethod.GET)
	public void update(@PathVariable Integer messageId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键messageId获取MessageInfo对象*/
        MessageInfo messageInfo = messageInfoService.getMessageInfo(messageId);

        response.setContentType("text/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象 
		JSONObject jsonMessageInfo = messageInfo.getJsonObject();
		out.println(jsonMessageInfo.toString());
		out.flush();
		out.close();
	}

	/*ajax方式更新用户消息信息*/
	@RequestMapping(value = "/{messageId}/update", method = RequestMethod.POST)
	public void update(@Validated MessageInfo messageInfo, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
    	boolean success = false;
		if (br.hasErrors()) { 
			message = "输入的信息有错误！";
			writeJsonResponse(response, success, message);
			return;
		}
		try {
			messageInfoService.updateMessageInfo(messageInfo);
			message = "用户消息更新成功!";
			success = true;
			writeJsonResponse(response, success, message);
		} catch (Exception e) {
			e.printStackTrace();
			message = "用户消息更新失败!";
			writeJsonResponse(response, success, message); 
		}
	}
    /*删除用户消息信息*/
	@RequestMapping(value="/{messageId}/delete",method=RequestMethod.GET)
	public String delete(@PathVariable Integer messageId,HttpServletRequest request) throws UnsupportedEncodingException {
		  try {
			  messageInfoService.deleteMessageInfo(messageId);
	            request.setAttribute("message", "用户消息删除成功!");
	            return "message";
	        } catch (Exception e) { 
	            e.printStackTrace();
	            request.setAttribute("error", "用户消息删除失败!");
				return "error";

	        }

	}

	/*ajax方式删除多条用户消息记录*/
	@RequestMapping(value="/deletes",method=RequestMethod.POST)
	public void delete(String messageIds,HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		String message = "";
    	boolean success = false;
        try { 
        	int count = messageInfoService.deleteMessageInfos(messageIds);
        	success = true;
        	message = count + "条记录删除成功";
        	writeJsonResponse(response, success, message);
        } catch (Exception e) { 
            //e.printStackTrace();
            message = "有记录存在外键约束,删除失败";
            writeJsonResponse(response, success, message);
        }
	}

	/*按照查询条件导出用户消息信息到Excel*/
	@RequestMapping(value = { "/OutToExcel" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void OutToExcel(String content,@ModelAttribute("sender") UserInfo sender,@ModelAttribute("reciever") UserInfo reciever,String sendTime,String readState, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
        if(content == null) content = "";
        if(sendTime == null) sendTime = "";
        if(readState == null) readState = "";
        List<MessageInfo> messageInfoList = messageInfoService.queryMessageInfo(content,sender,reciever,sendTime,readState);
        ExportExcelUtil ex = new ExportExcelUtil();
        String _title = "MessageInfo信息记录"; 
        String[] headers = { "消息id","消息内容","发送人","接收人","发送时间","阅读状态"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<messageInfoList.size();i++) {
        	MessageInfo messageInfo = messageInfoList.get(i); 
        	dataset.add(new String[]{messageInfo.getMessageId() + "",messageInfo.getContent(),messageInfo.getSender().getName(),messageInfo.getReciever().getName(),messageInfo.getSendTime(),messageInfo.getReadState()});
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
			response.setHeader("Content-disposition","attachment; filename="+"MessageInfo.xls");//filename是下载的xls的名，建议最好用英文 
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
