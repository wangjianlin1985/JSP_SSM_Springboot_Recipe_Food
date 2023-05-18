$(function () {
	$("#cookClass_className").validatebox({
		required : true, 
		missingMessage : '请输入分类名称',
	});

	$("#cookClass_cookClassDesc").validatebox({
		required : true, 
		missingMessage : '请输入烹饪分类介绍',
	});

	//单击添加按钮
	$("#cookClassAddButton").click(function () {
		//验证表单 
		if(!$("#cookClassAddForm").form("validate")) {
			$.messager.alert("错误提示","你输入的信息还有错误！","warning");
			$(".messager-window").css("z-index",10000);
		} else {
			$("#cookClassAddForm").form({
			    url:"CookClass/add",
			    onSubmit: function(){
					if($("#cookClassAddForm").form("validate"))  { 
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
                    //此处data={"Success":true}是字符串
                	var obj = jQuery.parseJSON(data); 
                    if(obj.success){ 
                        $.messager.alert("消息","保存成功！");
                        $(".messager-window").css("z-index",10000);
                        $("#cookClassAddForm").form("clear");
                    }else{
                        $.messager.alert("消息",obj.message);
                        $(".messager-window").css("z-index",10000);
                    }
			    }
			});
			//提交表单
			$("#cookClassAddForm").submit();
		}
	});

	//单击清空按钮
	$("#cookClassClearButton").click(function () { 
		$("#cookClassAddForm").form("clear"); 
	});
});
