$(function () {
	$.ajax({
		url : "LearnRec/" + $("#learnRec_recId_edit").val() + "/update",
		type : "get",
		data : {
			//recId : $("#learnRec_recId_edit").val(),
		},
		beforeSend : function () {
			$.messager.progress({
				text : "正在获取中...",
			});
		},
		success : function (learnRec, response, status) {
			$.messager.progress("close");
			if (learnRec) { 
				$("#learnRec_recId_edit").val(learnRec.recId);
				$("#learnRec_recId_edit").validatebox({
					required : true,
					missingMessage : "请输入记录id",
					editable: false
				});
				$("#learnRec_cookingObj_cookingId_edit").combobox({
					url:"Cooking/listAll",
					valueField:"cookingId",
					textField:"cookingName",
					panelHeight: "auto",
					editable: false, //不允许手动输入 
					onLoadSuccess: function () { //数据加载完毕事件
						$("#learnRec_cookingObj_cookingId_edit").combobox("select", learnRec.cookingObjPri);
						//var data = $("#learnRec_cookingObj_cookingId_edit").combobox("getData"); 
						//if (data.length > 0) {
							//$("#learnRec_cookingObj_cookingId_edit").combobox("select", data[0].cookingId);
						//}
					}
				});
				$("#learnRec_userObj_user_name_edit").combobox({
					url:"UserInfo/listAll",
					valueField:"user_name",
					textField:"name",
					panelHeight: "auto",
					editable: false, //不允许手动输入 
					onLoadSuccess: function () { //数据加载完毕事件
						$("#learnRec_userObj_user_name_edit").combobox("select", learnRec.userObjPri);
						//var data = $("#learnRec_userObj_user_name_edit").combobox("getData"); 
						//if (data.length > 0) {
							//$("#learnRec_userObj_user_name_edit").combobox("select", data[0].user_name);
						//}
					}
				});
				$("#learnRec_jifen_edit").val(learnRec.jifen);
				$("#learnRec_jifen_edit").validatebox({
					required : true,
					validType : "number",
					missingMessage : "请输入获得积分",
					invalidMessage : "获得积分输入不对",
				});
				$("#learnRec_learnTime_edit").datetimebox({
					value: learnRec.learnTime,
					required: true,
					showSeconds: true,
				});
			} else {
				$.messager.alert("获取失败！", "未知错误导致失败，请重试！", "warning");
				$(".messager-window").css("z-index",10000);
			}
		}
	});

	$("#learnRecModifyButton").click(function(){ 
		if ($("#learnRecEditForm").form("validate")) {
			$("#learnRecEditForm").form({
			    url:"LearnRec/" +  $("#learnRec_recId_edit").val() + "/update",
			    onSubmit: function(){
					if($("#learnRecEditForm").form("validate"))  {
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
			$("#learnRecEditForm").submit();
		} else {
			$.messager.alert("错误提示","你输入的信息还有错误！","warning");
			$(".messager-window").css("z-index",10000);
		}
	});
});
