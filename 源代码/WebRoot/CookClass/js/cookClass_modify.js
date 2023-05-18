$(function () {
	$.ajax({
		url : "CookClass/" + $("#cookClass_cookClassId_edit").val() + "/update",
		type : "get",
		data : {
			//cookClassId : $("#cookClass_cookClassId_edit").val(),
		},
		beforeSend : function () {
			$.messager.progress({
				text : "正在获取中...",
			});
		},
		success : function (cookClass, response, status) {
			$.messager.progress("close");
			if (cookClass) { 
				$("#cookClass_cookClassId_edit").val(cookClass.cookClassId);
				$("#cookClass_cookClassId_edit").validatebox({
					required : true,
					missingMessage : "请输入分类id",
					editable: false
				});
				$("#cookClass_className_edit").val(cookClass.className);
				$("#cookClass_className_edit").validatebox({
					required : true,
					missingMessage : "请输入分类名称",
				});
				$("#cookClass_cookClassDesc_edit").val(cookClass.cookClassDesc);
				$("#cookClass_cookClassDesc_edit").validatebox({
					required : true,
					missingMessage : "请输入烹饪分类介绍",
				});
			} else {
				$.messager.alert("获取失败！", "未知错误导致失败，请重试！", "warning");
				$(".messager-window").css("z-index",10000);
			}
		}
	});

	$("#cookClassModifyButton").click(function(){ 
		if ($("#cookClassEditForm").form("validate")) {
			$("#cookClassEditForm").form({
			    url:"CookClass/" +  $("#cookClass_cookClassId_edit").val() + "/update",
			    onSubmit: function(){
					if($("#cookClassEditForm").form("validate"))  {
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
			$("#cookClassEditForm").submit();
		} else {
			$.messager.alert("错误提示","你输入的信息还有错误！","warning");
			$(".messager-window").css("z-index",10000);
		}
	});
});
