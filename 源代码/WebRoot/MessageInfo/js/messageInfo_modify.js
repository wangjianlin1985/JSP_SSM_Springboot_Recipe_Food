$(function () {
	$.ajax({
		url : "MessageInfo/" + $("#messageInfo_messageId_edit").val() + "/update",
		type : "get",
		data : {
			//messageId : $("#messageInfo_messageId_edit").val(),
		},
		beforeSend : function () {
			$.messager.progress({
				text : "正在获取中...",
			});
		},
		success : function (messageInfo, response, status) {
			$.messager.progress("close");
			if (messageInfo) { 
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
				$(".messager-window").css("z-index",10000);
			}
		}
	});

	$("#messageInfoModifyButton").click(function(){ 
		if ($("#messageInfoEditForm").form("validate")) {
			$("#messageInfoEditForm").form({
			    url:"MessageInfo/" +  $("#messageInfo_messageId_edit").val() + "/update",
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
                	var obj = jQuery.parseJSON(data);
                    if(obj.success){
                        $.messager.alert("消息","信息修改成功！");
                        $(".messager-window").css("z-index",10000);
                        //location.href="frontlist";
                    }else{
                        $.messager.alert("消息",obj.message);
                        $(".messager-window").css("z-index",10000);
                    } 
			    }
			});
			//提交表单
			$("#messageInfoEditForm").submit();
		} else {
			$.messager.alert("错误提示","你输入的信息还有错误！","warning");
			$(".messager-window").css("z-index",10000);
		}
	});
});
