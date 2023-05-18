<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%> 
<%@ page import="com.chengxusheji.po.UserInfo" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1 , user-scalable=no">
<title>用户消息添加</title>
<link href="<%=basePath %>plugins/bootstrap.css" rel="stylesheet">
<link href="<%=basePath %>plugins/bootstrap-dashen.css" rel="stylesheet">
<link href="<%=basePath %>plugins/font-awesome.css" rel="stylesheet">
<link href="<%=basePath %>plugins/animate.css" rel="stylesheet">
<link href="<%=basePath %>plugins/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
</head>
<body style="margin-top:70px;">
<jsp:include page="../header.jsp"></jsp:include>
<div class="container">
	<div class="row">
		<div class="col-md-12 wow fadeInUp" data-wow-duration="0.5s">
			<div>
				<!-- Nav tabs -->
				<ul class="nav nav-tabs" role="tablist">
			    	<li role="presentation" ><a href="<%=basePath %>MessageInfo/frontlist">用户消息列表</a></li>
			    	<li role="presentation" class="active"><a href="#messageInfoAdd" aria-controls="messageInfoAdd" role="tab" data-toggle="tab">添加用户消息</a></li>
				</ul>
				<!-- Tab panes -->
				<div class="tab-content">
				    <div role="tabpanel" class="tab-pane" id="messageInfoList">
				    </div>
				    <div role="tabpanel" class="tab-pane active" id="messageInfoAdd"> 
				      	<form class="form-horizontal" name="messageInfoAddForm" id="messageInfoAddForm" enctype="multipart/form-data" method="post"  class="mar_t15">
						  <div class="form-group">
						  	 <label for="messageInfo_content" class="col-md-2 text-right">消息内容:</label>
						  	 <div class="col-md-8">
							    <textarea id="messageInfo_content" name="messageInfo.content" rows="8" class="form-control" placeholder="请输入消息内容"></textarea>
							 </div>
						  </div>
						  <div class="form-group">
						  	 <label for="messageInfo_sender_user_name" class="col-md-2 text-right">发送人:</label>
						  	 <div class="col-md-8">
							    <select id="messageInfo_sender_user_name" name="messageInfo.sender.user_name" class="form-control">
							    </select>
						  	 </div>
						  </div>
						  <div class="form-group">
						  	 <label for="messageInfo_reciever_user_name" class="col-md-2 text-right">接收人:</label>
						  	 <div class="col-md-8">
							    <select id="messageInfo_reciever_user_name" name="messageInfo.reciever.user_name" class="form-control">
							    </select>
						  	 </div>
						  </div>
						  <div class="form-group">
						  	 <label for="messageInfo_sendTimeDiv" class="col-md-2 text-right">发送时间:</label>
						  	 <div class="col-md-8">
				                <div id="messageInfo_sendTimeDiv" class="input-group date messageInfo_sendTime col-md-12" data-link-field="messageInfo_sendTime">
				                    <input class="form-control" id="messageInfo_sendTime" name="messageInfo.sendTime" size="16" type="text" value="" placeholder="请选择发送时间" readonly>
				                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
				                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
				                </div>
						  	 </div>
						  </div>
						  <div class="form-group">
						  	 <label for="messageInfo_readState" class="col-md-2 text-right">阅读状态:</label>
						  	 <div class="col-md-8">
							    <input type="text" id="messageInfo_readState" name="messageInfo.readState" class="form-control" placeholder="请输入阅读状态">
							 </div>
						  </div>
				          <div class="form-group">
				             <span class="col-md-2""></span>
				             <span onclick="ajaxMessageInfoAdd();" class="btn btn-primary bottom5 top5">添加</span>
				          </div>
						</form> 
				        <style>#messageInfoAddForm .form-group {margin:10px;}  </style>
					</div>
				</div>
			</div>
		</div>
	</div> 
</div>

<jsp:include page="../footer.jsp"></jsp:include> 
<script src="<%=basePath %>plugins/jquery.min.js"></script>
<script src="<%=basePath %>plugins/bootstrap.js"></script>
<script src="<%=basePath %>plugins/wow.min.js"></script>
<script src="<%=basePath %>plugins/bootstrapvalidator/js/bootstrapValidator.min.js"></script>
<script type="text/javascript" src="<%=basePath %>plugins/bootstrap-datetimepicker.min.js" charset="UTF-8"></script>
<script type="text/javascript" src="<%=basePath %>plugins/locales/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
<script>
var basePath = "<%=basePath%>";
	//提交添加用户消息信息
	function ajaxMessageInfoAdd() { 
		//提交之前先验证表单
		$("#messageInfoAddForm").data('bootstrapValidator').validate();
		if(!$("#messageInfoAddForm").data('bootstrapValidator').isValid()){
			return;
		}
		jQuery.ajax({
			type : "post",
			url : basePath + "MessageInfo/add",
			dataType : "json" , 
			data: new FormData($("#messageInfoAddForm")[0]),
			success : function(obj) {
				if(obj.success){ 
					alert("保存成功！");
					$("#messageInfoAddForm").find("input").val("");
					$("#messageInfoAddForm").find("textarea").val("");
				} else {
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
	//验证用户消息添加表单字段
	$('#messageInfoAddForm').bootstrapValidator({
		feedbackIcons: {
			valid: 'glyphicon glyphicon-ok',
			invalid: 'glyphicon glyphicon-remove',
			validating: 'glyphicon glyphicon-refresh'
		},
		fields: {
			"messageInfo.content": {
				validators: {
					notEmpty: {
						message: "消息内容不能为空",
					}
				}
			},
			"messageInfo.sendTime": {
				validators: {
					notEmpty: {
						message: "发送时间不能为空",
					}
				}
			},
			"messageInfo.readState": {
				validators: {
					notEmpty: {
						message: "阅读状态不能为空",
					}
				}
			},
		}
	}); 
	//初始化发送人下拉框值 
	$.ajax({
		url: basePath + "UserInfo/listAll",
		type: "get",
		success: function(userInfos,response,status) { 
			$("#messageInfo_sender_user_name").empty();
			var html="";
    		$(userInfos).each(function(i,userInfo){
    			html += "<option value='" + userInfo.user_name + "'>" + userInfo.name + "</option>";
    		});
    		$("#messageInfo_sender_user_name").html(html);
    	}
	});
	//初始化接收人下拉框值 
	$.ajax({
		url: basePath + "UserInfo/listAll",
		type: "get",
		success: function(userInfos,response,status) { 
			$("#messageInfo_reciever_user_name").empty();
			var html="";
    		$(userInfos).each(function(i,userInfo){
    			html += "<option value='" + userInfo.user_name + "'>" + userInfo.name + "</option>";
    		});
    		$("#messageInfo_reciever_user_name").html(html);
    	}
	});
	//发送时间组件
	$('#messageInfo_sendTimeDiv').datetimepicker({
		language:  'zh-CN',  //显示语言
		format: 'yyyy-mm-dd hh:ii:ss',
		weekStart: 1,
		todayBtn:  1,
		autoclose: 1,
		minuteStep: 1,
		todayHighlight: 1,
		startView: 2,
		forceParse: 0
	}).on('hide',function(e) {
		//下面这行代码解决日期组件改变日期后不验证的问题
		$('#messageInfoAddForm').data('bootstrapValidator').updateStatus('messageInfo.sendTime', 'NOT_VALIDATED',null).validateField('messageInfo.sendTime');
	});
})
</script>
</body>
</html>
