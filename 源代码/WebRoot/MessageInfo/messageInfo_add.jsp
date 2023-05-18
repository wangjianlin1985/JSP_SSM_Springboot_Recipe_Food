<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%>
<jsp:include page="../check_logstate.jsp"/>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/messageInfo.css" />
<div id="messageInfoAddDiv">
	<form id="messageInfoAddForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">消息内容:</span>
			<span class="inputControl">
				<textarea id="messageInfo_content" name="messageInfo.content" rows="6" cols="80"></textarea>

			</span>

		</div>
		<div>
			<span class="label">发送人:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="messageInfo_sender_user_name" name="messageInfo.sender.user_name" style="width: auto"/>
			</span>
		</div>
		<div>
			<span class="label">接收人:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="messageInfo_reciever_user_name" name="messageInfo.reciever.user_name" style="width: auto"/>
			</span>
		</div>
		<div>
			<span class="label">发送时间:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="messageInfo_sendTime" name="messageInfo.sendTime" />

			</span>

		</div>
		<div>
			<span class="label">阅读状态:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="messageInfo_readState" name="messageInfo.readState" style="width:200px" />

			</span>

		</div>
		<div class="operation">
			<a id="messageInfoAddButton" class="easyui-linkbutton">添加</a>
			<a id="messageInfoClearButton" class="easyui-linkbutton">重填</a>
		</div> 
	</form>
</div>
<script src="${pageContext.request.contextPath}/MessageInfo/js/messageInfo_add.js"></script> 
