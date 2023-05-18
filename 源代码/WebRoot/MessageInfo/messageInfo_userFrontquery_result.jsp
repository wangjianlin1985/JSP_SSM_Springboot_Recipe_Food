<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%> 
<%@ page import="com.chengxusheji.po.MessageInfo" %>
<%@ page import="com.chengxusheji.po.UserInfo" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    List<MessageInfo> messageInfoList = (List<MessageInfo>)request.getAttribute("messageInfoList");
    //获取所有的sender信息
    List<UserInfo> userInfoList = (List<UserInfo>)request.getAttribute("userInfoList");
    int currentPage =  (Integer)request.getAttribute("currentPage"); //当前页
    int totalPage =   (Integer)request.getAttribute("totalPage");  //一共多少页
    int recordNumber =   (Integer)request.getAttribute("recordNumber");  //一共多少记录
    String content = (String)request.getAttribute("content"); //消息内容查询关键字
    UserInfo sender = (UserInfo)request.getAttribute("sender");
    UserInfo reciever = (UserInfo)request.getAttribute("reciever");
    String sendTime = (String)request.getAttribute("sendTime"); //发送时间查询关键字
    String readState = (String)request.getAttribute("readState"); //阅读状态查询关键字
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1 , user-scalable=no">
<title>用户消息查询</title>
<link href="<%=basePath %>plugins/bootstrap.css" rel="stylesheet">
<link href="<%=basePath %>plugins/bootstrap-dashen.css" rel="stylesheet">
<link href="<%=basePath %>plugins/font-awesome.css" rel="stylesheet">
<link href="<%=basePath %>plugins/animate.css" rel="stylesheet">
<link href="<%=basePath %>plugins/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
</head>
<body style="margin-top:70px;">
<div class="container">
<jsp:include page="../header.jsp"></jsp:include>
	<div class="row"> 
		<div class="col-md-9 wow fadeInDown" data-wow-duration="0.5s">
			<div>
				<!-- Nav tabs -->
				<ul class="nav nav-tabs" role="tablist">
			    	<li><a href="<%=basePath %>index.jsp">首页</a></li>
			    	<li role="presentation" class="active"><a href="#messageInfoListPanel" aria-controls="messageInfoListPanel" role="tab" data-toggle="tab">用户消息列表</a></li>
			    	<li role="presentation" ><a href="<%=basePath %>MessageInfo/messageInfo_frontAdd.jsp" style="display:none;">添加用户消息</a></li>
				</ul>
			  	<!-- Tab panes -->
			  	<div class="tab-content">
				    <div role="tabpanel" class="tab-pane active" id="messageInfoListPanel">
				    		<div class="row">
				    			<div class="col-md-12 top5">
				    				<div class="table-responsive">
				    				<table class="table table-condensed table-hover">
				    					<tr class="success bold"><td>序号</td><td>消息id</td><td>消息内容</td><td>发送人</td><td>发送时间</td><td>阅读状态</td><td>操作</td></tr>
				    					<% 
				    						/*计算起始序号*/
				    	            		int startIndex = (currentPage -1) * 5;
				    	            		/*遍历记录*/
				    	            		for(int i=0;i<messageInfoList.size();i++) {
					    	            		int currentIndex = startIndex + i + 1; //当前记录的序号
					    	            		MessageInfo messageInfo = messageInfoList.get(i); //获取到用户消息对象
 										%>
 										<tr>
 											<td><%=currentIndex %></td>
 											<td><%=messageInfo.getMessageId() %></td>
 											<td><%=messageInfo.getContent() %></td>
 											<td><%=messageInfo.getSender().getName() %></td>
 											<td><%=messageInfo.getSendTime() %></td>
 											<td><%=messageInfo.getReadState() %></td>
 											<td>
 												<a href="<%=basePath  %>MessageInfo/<%=messageInfo.getMessageId() %>/frontshow"><i class="fa fa-info"></i>&nbsp;查看</a>&nbsp;
 												<a href="#" onclick="messageInfoEdit('<%=messageInfo.getMessageId() %>');" style="display:none;"><i class="fa fa-pencil fa-fw"></i>编辑</a>&nbsp;
 												<a href="#" onclick="messageInfoDelete('<%=messageInfo.getMessageId() %>');"><i class="fa fa-trash-o fa-fw"></i>删除</a>
 											</td> 
 										</tr>
 										<%}%>
				    				</table>
				    				</div>
				    			</div>
				    		</div>

				    		<div class="row">
					            <div class="col-md-12">
						            <nav class="pull-left">
						                <ul class="pagination">
						                    <li><a href="#" onclick="GoToPage(<%=currentPage-1 %>,<%=totalPage %>);" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>
						                     <%
						                    	int startPage = currentPage - 5;
						                    	int endPage = currentPage + 5;
						                    	if(startPage < 1) startPage=1;
						                    	if(endPage > totalPage) endPage = totalPage;
						                    	for(int i=startPage;i<=endPage;i++) {
						                    %>
						                    <li class="<%= currentPage==i?"active":"" %>"><a href="#"  onclick="GoToPage(<%=i %>,<%=totalPage %>);"><%=i %></a></li>
						                    <%  } %> 
						                    <li><a href="#" onclick="GoToPage(<%=currentPage+1 %>,<%=totalPage %>);"><span aria-hidden="true">&raquo;</span></a></li>
						                </ul>
						            </nav>
						            <div class="pull-right" style="line-height:75px;" >共有<%=recordNumber %>条记录，当前第 <%=currentPage %>/<%=totalPage %> 页</div>
					            </div>
				            </div> 
				    </div>
				</div>
			</div>
		</div>
	<div class="col-md-3 wow fadeInRight">
		<div class="page-header">
    		<h1>用户消息查询</h1>
		</div>
		<form name="messageInfoQueryForm" id="messageInfoQueryForm" action="<%=basePath %>MessageInfo/userFrontlist" class="mar_t15" method="post">
			<div class="form-group">
				<label for="content">消息内容:</label>
				<input type="text" id="content" name="content" value="<%=content %>" class="form-control" placeholder="请输入消息内容">
			</div>






            <div class="form-group">
            	<label for="sender_user_name">发送人：</label>
                <select id="sender_user_name" name="sender.user_name" class="form-control">
                	<option value="">不限制</option>
	 				<%
	 				for(UserInfo userInfoTemp:userInfoList) {
	 					String selected = "";
 					if(sender!=null && sender.getUser_name()!=null && sender.getUser_name().equals(userInfoTemp.getUser_name()))
 						selected = "selected";
	 				%>
 				 <option value="<%=userInfoTemp.getUser_name() %>" <%=selected %>><%=userInfoTemp.getName() %></option>
	 				<%
	 				}
	 				%>
 			</select>
            </div>
            <div class="form-group" style="display:none;">
            	<label for="reciever_user_name">接收人：</label>
                <select id="reciever_user_name" name="reciever.user_name" class="form-control">
                	<option value="">不限制</option>
	 				<%
	 				for(UserInfo userInfoTemp:userInfoList) {
	 					String selected = "";
 					if(reciever!=null && reciever.getUser_name()!=null && reciever.getUser_name().equals(userInfoTemp.getUser_name()))
 						selected = "selected";
	 				%>
 				 <option value="<%=userInfoTemp.getUser_name() %>" <%=selected %>><%=userInfoTemp.getName() %></option>
	 				<%
	 				}
	 				%>
 			</select>
            </div>
			<div class="form-group">
				<label for="sendTime">发送时间:</label>
				<input type="text" id="sendTime" name="sendTime" class="form-control"  placeholder="请选择发送时间" value="<%=sendTime %>" onclick="SelectDate(this,'yyyy-MM-dd')" />
			</div>
			<div class="form-group">
				<label for="readState">阅读状态:</label>
				<input type="text" id="readState" name="readState" value="<%=readState %>" class="form-control" placeholder="请输入阅读状态">
			</div>






            <input type=hidden name=currentPage value="<%=currentPage %>" />
            <button type="submit" class="btn btn-primary">查询</button>
        </form>
	</div>

		</div>
	</div> 
<div id="messageInfoEditDialog" class="modal fade" tabindex="-1" role="dialog">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title"><i class="fa fa-edit"></i>&nbsp;用户消息信息编辑</h4>
      </div>
      <div class="modal-body" style="height:450px; overflow: scroll;">
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
		</form> 
	    <style>#messageInfoEditForm .form-group {margin-bottom:5px;}  </style>
      </div>
      <div class="modal-footer"> 
      	<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
      	<button type="button" class="btn btn-primary" onclick="ajaxMessageInfoModify();">提交</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<jsp:include page="../footer.jsp"></jsp:include> 
<script src="<%=basePath %>plugins/jquery.min.js"></script>
<script src="<%=basePath %>plugins/bootstrap.js"></script>
<script src="<%=basePath %>plugins/wow.min.js"></script>
<script src="<%=basePath %>plugins/bootstrap-datetimepicker.min.js"></script>
<script src="<%=basePath %>plugins/locales/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="<%=basePath %>js/jsdate.js"></script>
<script>
var basePath = "<%=basePath%>";
/*跳转到查询结果的某页*/
function GoToPage(currentPage,totalPage) {
    if(currentPage==0) return;
    if(currentPage>totalPage) return;
    document.messageInfoQueryForm.currentPage.value = currentPage;
    document.messageInfoQueryForm.submit();
}

/*可以直接跳转到某页*/
function changepage(totalPage)
{
    var pageValue=document.messageInfoQueryForm.pageValue.value;
    if(pageValue>totalPage) {
        alert('你输入的页码超出了总页数!');
        return ;
    }
    document.messageInfoQueryForm.currentPage.value = pageValue;
    documentmessageInfoQueryForm.submit();
}

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
				$('#messageInfoEditDialog').modal('show');
			} else {
				alert("获取信息失败！");
			}
		}
	});
}

/*删除用户消息信息*/
function messageInfoDelete(messageId) {
	if(confirm("确认删除这个记录")) {
		$.ajax({
			type : "POST",
			url : basePath + "MessageInfo/deletes",
			data : {
				messageIds : messageId,
			},
			success : function (obj) {
				if (obj.success) {
					alert("删除成功");
					$("#messageInfoQueryForm").submit();
					//location.href= basePath + "MessageInfo/frontlist";
				}
				else 
					alert(obj.message);
			},
		});
	}
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
})
</script>
</body>
</html>

