<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<jsp:include page="../check_logstate.jsp"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/messageInfo.css" />
<div id="messageInfo_editDiv">
	<form id="messageInfoEditForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">消息id:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="messageInfo_messageId_edit" name="messageInfo.messageId" value="<%=request.getParameter("messageId") %>" style="width:200px" />
			</span>
		</div>

		<div>
			<span class="label">消息内容:</span>
			<span class="inputControl">
				<textarea id="messageInfo_content_edit" name="messageInfo.content" rows="8" cols="60"></textarea>

			</span>

		</div>
		<div>
			<span class="label">发送人:</span>
			<span class="inputControl">
				<input class="textbox"  id="messageInfo_sender_user_name_edit" name="messageInfo.sender.user_name" style="width: auto"/>
			</span>
		</div>
		<div>
			<span class="label">接收人:</span>
			<span class="inputControl">
				<input class="textbox"  id="messageInfo_reciever_user_name_edit" name="messageInfo.reciever.user_name" style="width: auto"/>
			</span>
		</div>
		<div>
			<span class="label">发送时间:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="messageInfo_sendTime_edit" name="messageInfo.sendTime" />

			</span>

		</div>
		<div>
			<span class="label">阅读状态:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="messageInfo_readState_edit" name="messageInfo.readState" style="width:200px" />

			</span>

		</div>
		<div class="operation">
			<a id="messageInfoModifyButton" class="easyui-linkbutton">更新</a> 
		</div>
	</form>
</div>
<script src="${pageContext.request.contextPath}/MessageInfo/js/messageInfo_modify.js"></script> 
