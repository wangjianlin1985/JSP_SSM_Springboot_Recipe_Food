var messageInfo_manage_tool = null; 
$(function () { 
	initMessageInfoManageTool(); //建立MessageInfo管理对象
	messageInfo_manage_tool.init(); //如果需要通过下拉框查询，首先初始化下拉框的值
	$("#messageInfo_manage").datagrid({
		url : 'MessageInfo/list',
		fit : true,
		fitColumns : true,
		striped : true,
		rownumbers : true,
		border : false,
		pagination : true,
		pageSize : 5,
		pageList : [5, 10, 15, 20, 25],
		pageNumber : 1,
		sortName : "messageId",
		sortOrder : "desc",
		toolbar : "#messageInfo_manage_tool",
		columns : [[
			{
				field : "messageId",
				title : "消息id",
				width : 70,
			},
			{
				field : "content",
				title : "消息内容",
				width : 140,
			},
			{
				field : "sender",
				title : "发送人",
				width : 140,
			},
			{
				field : "reciever",
				title : "接收人",
				width : 140,
			},
			{
				field : "sendTime",
				title : "发送时间",
				width : 140,
			},
			{
				field : "readState",
				title : "阅读状态",
				width : 140,
			},
		]],
	});

	$("#messageInfoEditDiv").dialog({
		title : "修改管理",
		top: "50px",
		width : 700,
		height : 515,
		modal : true,
		closed : true,
		iconCls : "icon-edit-new",
		buttons : [{
			text : "提交",
			iconCls : "icon-edit-new",
			handler : function () {
				if ($("#messageInfoEditForm").form("validate")) {
					//验证表单 
					if(!$("#messageInfoEditForm").form("validate")) {
						$.messager.alert("错误提示","你输入的信息还有错误！","warning");
					} else {
						$("#messageInfoEditForm").form({
						    url:"MessageInfo/" + $("#messageInfo_messageId_edit").val() + "/update",
						    onSubmit: function(){
								if($("#messageInfoEditForm").form("validate"))  {
				                	$.messager.progress({
										text : "正在提交数据中...",
									});
				                	return true;
				                } else { 
				                    return false; 
				                }
						    },
						    success:function(data){
						    	$.messager.progress("close");
						    	console.log(data);
			                	var obj = jQuery.parseJSON(data);
			                    if(obj.success){
			                        $.messager.alert("消息","信息修改成功！");
			                        $("#messageInfoEditDiv").dialog("close");
			                        messageInfo_manage_tool.reload();
			                    }else{
			                        $.messager.alert("消息",obj.message);
			                    } 
						    }
						});
						//提交表单
						$("#messageInfoEditForm").submit();
					}
				}
			},
		},{
			text : "取消",
			iconCls : "icon-redo",
			handler : function () {
				$("#messageInfoEditDiv").dialog("close");
				$("#messageInfoEditForm").form("reset"); 
			},
		}],
	});
});

function initMessageInfoManageTool() {
	messageInfo_manage_tool = {
		init: function() {
			$.ajax({
				url : "UserInfo/listAll",
				type : "post",
				success : function (data, response, status) {
					$("#sender_user_name_query").combobox({ 
					    valueField:"user_name",
					    textField:"name",
					    panelHeight: "200px",
				        editable: false, //不允许手动输入 
					});
					data.splice(0,0,{user_name:"",name:"不限制"});
					$("#sender_user_name_query").combobox("loadData",data); 
				}
			});
			$.ajax({
				url : "UserInfo/listAll",
				type : "post",
				success : function (data, response, status) {
					$("#reciever_user_name_query").combobox({ 
					    valueField:"user_name",
					    textField:"name",
					    panelHeight: "200px",
				        editable: false, //不允许手动输入 
					});
					data.splice(0,0,{user_name:"",name:"不限制"});
					$("#reciever_user_name_query").combobox("loadData",data); 
				}
			});
		},
		reload : function () {
			$("#messageInfo_manage").datagrid("reload");
		},
		redo : function () {
			$("#messageInfo_manage").datagrid("unselectAll");
		},
		search: function() {
			var queryParams = $("#messageInfo_manage").datagrid("options").queryParams;
			queryParams["content"] = $("#content").val();
			queryParams["sender.user_name"] = $("#sender_user_name_query").combobox("getValue");
			queryParams["reciever.user_name"] = $("#reciever_user_name_query").combobox("getValue");
			queryParams["sendTime"] = $("#sendTime").datebox("getValue"); 
			queryParams["readState"] = $("#readState").val();
			$("#messageInfo_manage").datagrid("options").queryParams=queryParams; 
			$("#messageInfo_manage").datagrid("load");
		},
		exportExcel: function() {
			$("#messageInfoQueryForm").form({
			    url:"MessageInfo/OutToExcel",
			});
			//提交表单
			$("#messageInfoQueryForm").submit();
		},
		remove : function () {
			var rows = $("#messageInfo_manage").datagrid("getSelections");
			if (rows.length > 0) {
				$.messager.confirm("确定操作", "您正在要删除所选的记录吗？", function (flag) {
					if (flag) {
						var messageIds = [];
						for (var i = 0; i < rows.length; i ++) {
							messageIds.push(rows[i].messageId);
						}
						$.ajax({
							type : "POST",
							url : "MessageInfo/deletes",
							data : {
								messageIds : messageIds.join(","),
							},
							beforeSend : function () {
								$("#messageInfo_manage").datagrid("loading");
							},
							success : function (data) {
								if (data.success) {
									$("#messageInfo_manage").datagrid("loaded");
									$("#messageInfo_manage").datagrid("load");
									$("#messageInfo_manage").datagrid("unselectAll");
									$.messager.show({
										title : "提示",
										msg : data.message
									});
								} else {
									$("#messageInfo_manage").datagrid("loaded");
									$("#messageInfo_manage").datagrid("load");
									$("#messageInfo_manage").datagrid("unselectAll");
									$.messager.alert("消息",data.message);
								}
							},
						});
					}
				});
			} else {
				$.messager.alert("提示", "请选择要删除的记录！", "info");
			}
		},
		edit : function () {
			var rows = $("#messageInfo_manage").datagrid("getSelections");
			if (rows.length > 1) {
				$.messager.alert("警告操作！", "编辑记录只能选定一条数据！", "warning");
			} else if (rows.length == 1) {
				$.ajax({
					url : "MessageInfo/" + rows[0].messageId +  "/update",
					type : "get",
					data : {
						//messageId : rows[0].messageId,
					},
					beforeSend : function () {
						$.messager.progress({
							text : "正在获取中...",
						});
					},
					success : function (messageInfo, response, status) {
						$.messager.progress("close");
						if (messageInfo) { 
							$("#messageInfoEditDiv").dialog("open");
							$("#messageInfo_messageId_edit").val(messageInfo.messageId);
							$("#messageInfo_messageId_edit").validatebox({
								required : true,
								missingMessage : "请输入消息id",
								editable: false
							});
							$("#messageInfo_content_edit").val(messageInfo.content);
							$("#messageInfo_content_edit").validatebox({
								required : true,
								missingMessage : "请输入消息内容",
							});
							$("#messageInfo_sender_user_name_edit").combobox({
								url:"UserInfo/listAll",
							    valueField:"user_name",
							    textField:"name",
							    panelHeight: "auto",
						        editable: false, //不允许手动输入 
						        onLoadSuccess: function () { //数据加载完毕事件
									$("#messageInfo_sender_user_name_edit").combobox("select", messageInfo.senderPri);
									//var data = $("#messageInfo_sender_user_name_edit").combobox("getData"); 
						            //if (data.length > 0) {
						                //$("#messageInfo_sender_user_name_edit").combobox("select", data[0].user_name);
						            //}
								}
							});
							$("#messageInfo_reciever_user_name_edit").combobox({
								url:"UserInfo/listAll",
							    valueField:"user_name",
							    textField:"name",
							    panelHeight: "auto",
						        editable: false, //不允许手动输入 
						        onLoadSuccess: function () { //数据加载完毕事件
									$("#messageInfo_reciever_user_name_edit").combobox("select", messageInfo.recieverPri);
									//var data = $("#messageInfo_reciever_user_name_edit").combobox("getData"); 
						            //if (data.length > 0) {
						                //$("#messageInfo_reciever_user_name_edit").combobox("select", data[0].user_name);
						            //}
								}
							});
							$("#messageInfo_sendTime_edit").datetimebox({
								value: messageInfo.sendTime,
							    required: true,
							    showSeconds: true,
							});
							$("#messageInfo_readState_edit").val(messageInfo.readState);
							$("#messageInfo_readState_edit").validatebox({
								required : true,
								missingMessage : "请输入阅读状态",
							});
						} else {
							$.messager.alert("获取失败！", "未知错误导致失败，请重试！", "warning");
						}
					}
				});
			} else if (rows.length == 0) {
				$.messager.alert("警告操作！", "编辑记录至少选定一条数据！", "warning");
			}
		},
	};
}
