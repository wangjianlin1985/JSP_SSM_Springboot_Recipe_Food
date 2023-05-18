$(function () {
	$("#learnRec_cookingObj_cookingId").combobox({
	    url:'Cooking/listAll',
	    valueField: "cookingId",
	    textField: "cookingName",
	    panelHeight: "auto",
        editable: false, //不允许手动输入
        required : true,
        onLoadSuccess: function () { //数据加载完毕事件
            var data = $("#learnRec_cookingObj_cookingId").combobox("getData"); 
            if (data.length > 0) {
                $("#learnRec_cookingObj_cookingId").combobox("select", data[0].cookingId);
            }
        }
	});
	$("#learnRec_userObj_user_name").combobox({
	    url:'UserInfo/listAll',
	    valueField: "user_name",
	    textField: "name",
	    panelHeight: "auto",
        editable: false, //不允许手动输入
        required : true,
        onLoadSuccess: function () { //数据加载完毕事件
            var data = $("#learnRec_userObj_user_name").combobox("getData"); 
            if (data.length > 0) {
                $("#learnRec_userObj_user_name").combobox("select", data[0].user_name);
            }
        }
	});
	$("#learnRec_jifen").validatebox({
		required : true,
		validType : "number",
		missingMessage : '请输入获得积分',
		invalidMessage : '获得积分输入不对',
	});

	$("#learnRec_learnTime").datetimebox({
	    required : true, 
	    showSeconds: true,
	    editable: false
	});

	//单击添加按钮
	$("#learnRecAddButton").click(function () {
		//验证表单 
		if(!$("#learnRecAddForm").form("validate")) {
			$.messager.alert("错误提示","你输入的信息还有错误！","warning");
			$(".messager-window").css("z-index",10000);
		} else {
			$("#learnRecAddForm").form({
			    url:"LearnRec/add",
			    onSubmit: function(){
					if($("#learnRecAddForm").form("validate"))  { 
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
                        $("#learnRecAddForm").form("clear");
                    }else{
                        $.messager.alert("消息",obj.message);
                        $(".messager-window").css("z-index",10000);
                    }
			    }
			});
			//提交表单
			$("#learnRecAddForm").submit();
		}
	});

	//单击清空按钮
	$("#learnRecClearButton").click(function () { 
		$("#learnRecAddForm").form("clear"); 
	});
});
