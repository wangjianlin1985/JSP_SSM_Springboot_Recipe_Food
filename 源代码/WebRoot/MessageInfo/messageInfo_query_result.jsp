<%@ page language="java"  contentType="text/html;charset=UTF-8"%>
<jsp:include page="../check_logstate.jsp"/> 
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/messageInfo.css" /> 

<div id="messageInfo_manage"></div>
<div id="messageInfo_manage_tool" style="padding:5px;">
	<div style="margin-bottom:5px;">
		<a href="#" class="easyui-linkbutton" iconCls="icon-edit-new" plain="true" onclick="messageInfo_manage_tool.edit();">修改</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-delete-new" plain="true" onclick="messageInfo_manage_tool.remove();">删除</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-reload" plain="true"  onclick="messageInfo_manage_tool.reload();">刷新</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-redo" plain="true" onclick="messageInfo_manage_tool.redo();">取消选择</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-export" plain="true" onclick="messageInfo_manage_tool.exportExcel();">导出到excel</a>
	</div>
	<div style="padding:0 0 0 7px;color:#333;">
		<form id="messageInfoQueryForm" method="post">
			消息内容：<input type="text" class="textbox" id="content" name="content" style="width:110px" />
			发送人：<input class="textbox" type="text" id="sender_user_name_query" name="sender.user_name" style="width: auto"/>
			接收人：<input class="textbox" type="text" id="reciever_user_name_query" name="reciever.user_name" style="width: auto"/>
			发送时间：<input type="text" id="sendTime" name="sendTime" class="easyui-datebox" editable="false" style="width:100px">
			阅读状态：<input type="text" class="textbox" id="readState" name="readState" style="width:110px" />
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="messageInfo_manage_tool.search();">查询</a>
		</form>	
	</div>
</div>

<div id="messageInfoEditDiv">
	<form id="messageInfoEditForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">消息id:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="messageInfo_messageId_edit" name="messageInfo.messageId" style="width:200px" />
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
	</form>
</div>
<script type="text/javascript" src="MessageInfo/js/messageInfo_manage.js"></script> 
