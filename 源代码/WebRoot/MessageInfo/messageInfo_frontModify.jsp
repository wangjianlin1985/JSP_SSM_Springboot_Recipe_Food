<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%> 
<%@ page import="com.chengxusheji.po.MessageInfo" %>
<%@ page import="com.chengxusheji.po.UserInfo" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的sender信息
    List<UserInfo> userInfoList = (List<UserInfo>)request.getAttribute("userInfoList");
    MessageInfo messageInfo = (MessageInfo)request.getAttribute("messageInfo");

%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1 , user-scalable=no">
  <TITLE>修改用户消息信息</TITLE>
  <link href="<%=basePath %>plugins/bootstrap.css" rel="stylesheet">
  <link href="<%=basePath %>plugins/bootstrap-dashen.css" rel="stylesheet">
  <link href="<%=basePath %>plugins/font-awesome.css" rel="stylesheet">
  <link href="<%=basePath %>plugins/animate.css" rel="stylesheet"> 
</head>
<body style="margin-top:70px;"> 
<div class="container">
<jsp:include page="../header.jsp"></jsp:include>
	<div class="col-md-9 wow fadeInLeft">
	<ul class="breadcrumb">
  		<li><a href="<%=basePath %>index.jsp">首页</a></li>
  		<li class="active">用户消息信息修改</li>
	</ul>
		<div class="row"> 
      	<form class="form-horizontal" name="messageInfoEditForm" id="messageInfoEditForm" enctype="multipart/form-data" method="post"  class="mar_t15">
		  <div class="form-group">
			 <label for="messageInfo_messageId_edit" class="col-md-3 text-right">消息id:</label>
			 <div class="col-md-9"> 
			 	<input type="text" id="messageInfo_messageId_edit" name="messageInfo.messageId" class="form-control" placeholder="请输入消息id" readOnly>
			 </div>
		  </div> 
		  <div class="form-group">
		  	 <label for="messageInfo_content_edit" class="col-md-3 text-right">消息内容:</label>
		  	 <div class="col-md-9">
			    <textarea id="messageInfo_content_edit" name="messageInfo.content" rows="8" class="form-control" placeholder="请输入消息内容"></textarea>
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="messageInfo_sender_user_name_edit" class="col-md-3 text-right">发送人:</label>
		  	 <div class="col-md-9">
			    <select id="messageInfo_sender_user_name_edit" name="messageInfo.sender.user_name" class="form-control">
			    </select>
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="messageInfo_reciever_user_name_edit" class="col-md-3 text-right">接收人:</label>
		  	 <div class="col-md-9">
			    <select id="messageInfo_reciever_user_name_edit" name="messageInfo.reciever.user_name" class="form-control">
			    </select>
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="messageInfo_sendTime_edit" class="col-md-3 text-right">发送时间:</label>
		  	 <div class="col-md-9">
                <div class="input-group date messageInfo_sendTime_edit col-md-12" data-link-field="messageInfo_sendTime_edit">
                    <input class="form-control" id="messageInfo_sendTime_edit" name="messageInfo.sendTime" size="16" type="text" value="" placeholder="请选择发送时间" readonly>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                </div>
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="messageInfo_readState_edit" class="col-md-3 text-right">阅读状态:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="messageInfo_readState_edit" name="messageInfo.readState" class="form-control" placeholder="请输入阅读状态">
			 </div>
		  </div>
			  <div class="form-group">
			  	<span class="col-md-3""></span>
			  	<span onclick="ajaxMessageInfoModify();" class="btn btn-primary bottom5 top5">修改</span>
			  </div>
		</form> 
	    <style>#messageInfoEditForm .form-group {margin-bottom:5px;}  </style>
      </div>
   </div>
</div>


<jsp:include page="../footer.jsp"></jsp:include>
<script src="<%=basePath %>plugins/jquery.min.js"></script>
<script src="<%=basePath %>plugins/bootstrap.js"></script>
<script src="<%=basePath %>plugins/wow.min.js"></script>
<script src="<%=basePath %>plugins/bootstrap-datetimepicker.min.js"></script>
<script src="<%=basePath %>plugins/locales/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="<%=basePath %>js/jsdate.js"></script>
<script>
var basePath = "<%=basePath%>";
/*弹出修改用户消息界面并初始化数据*/
function messageInfoEdit(messageId) {
	$.ajax({
		url :  basePath + "MessageInfo/" + messageId + "/update",
		type : "get",
		dataType: "json",
		success : function (messageInfo, response, status) {
			if (messageInfo) {
				$("#messageInfo_messageId_edit").val(messageInfo.messageId);
				$("#messageInfo_content_edit").val(messageInfo.content);
				$.ajax({
					url: basePath + "UserInfo/listAll",
					type: "get",
					success: function(userInfos,response,status) { 
						$("#messageInfo_sender_user_name_edit").empty();
						var html="";
		        		$(userInfos).each(function(i,userInfo){
		        			html += "<option value='" + userInfo.user_name + "'>" + userInfo.name + "</option>";
		        		});
		        		$("#messageInfo_sender_user_name_edit").html(html);
		        		$("#messageInfo_sender_user_name_edit").val(messageInfo.senderPri);
					}
				});
				$.ajax({
					url: basePath + "UserInfo/listAll",
					type: "get",
					success: function(userInfos,response,status) { 
						$("#messageInfo_reciever_user_name_edit").empty();
						var html="";
		        		$(userInfos).each(function(i,userInfo){
		        			html += "<option value='" + userInfo.user_name + "'>" + userInfo.name + "</option>";
		        		});
		        		$("#messageInfo_reciever_user_name_edit").html(html);
		        		$("#messageInfo_reciever_user_name_edit").val(messageInfo.recieverPri);
					}
				});
				$("#messageInfo_sendTime_edit").val(messageInfo.sendTime);
				$("#messageInfo_readState_edit").val(messageInfo.readState);
			} else {
				alert("获取信息失败！");
			}
		}
	});
}

/*ajax方式提交用户消息信息表单给服务器端修改*/
function ajaxMessageInfoModify() {
	$.ajax({
		url :  basePath + "MessageInfo/" + $("#messageInfo_messageId_edit").val() + "/update",
		type : "post",
		dataType: "json",
		data: new FormData($("#messageInfoEditForm")[0]),
		success : function (obj, response, status) {
            if(obj.success){
                alert("信息修改成功！");
                location.reload(true);
                $("#messageInfoQueryForm").submit();
            }else{
                alert(obj.message);
            } 
		},
		processData: false,
		contentType: false,
	});
}

$(function(){
        /*小屏幕导航点击关闭菜单*/
        $('.navbar-collapse a').click(function(){
            $('.navbar-collapse').collapse('hide');
        });
        new WOW().init();
    /*发送时间组件*/
    $('.messageInfo_sendTime_edit').datetimepicker({
    	language:  'zh-CN',  //语言
    	format: 'yyyy-mm-dd hh:ii:ss',
    	weekStart: 1,
    	todayBtn:  1,
    	autoclose: 1,
    	minuteStep: 1,
    	todayHighlight: 1,
    	startView: 2,
    	forceParse: 0
    });
    messageInfoEdit("<%=request.getParameter("messageId")%>");
 })
 </script> 
</body>
</html>

