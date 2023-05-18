$(function () {
	$("#messageInfo_content").validatebox({
		required : true, 
		missingMessage : '请输入消息内容',
	});

	$("#messageInfo_sender_user_name").combobox({
	    url:'UserInfo/listAll',
	    valueField: "user_name",
	    textField: "name",
	    panelHeight: "auto",
        editable: false, //不允许手动输入
        required : true,
        onLoadSuccess: function () { //数据加载完毕事件
            var data = $("#messageInfo_sender_user_name").combobox("getData"); 
            if (data.length > 0) {
                $("#messageInfo_sender_user_name").combobox("select", data[0].user_name);
            }
        }
	});
	$("#messageInfo_reciever_user_name").combobox({
	    url:'UserInfo/listAll',
	    valueField: "user_name",
	    textField: "name",
	    panelHeight: "auto",
        editable: false, //不允许手动输入
        required : true,
        onLoadSuccess: function () { //数据加载完毕事件
            var data = $("#messageInfo_reciever_user_name").combobox("getData"); 
            if (data.length > 0) {
                $("#messageInfo_reciever_user_name").combobox("select", data[0].user_name);
            }
        }
	});
	$("#messageInfo_sendTime").datetimebox({
	    required : true, 
	    showSeconds: true,
	    editable: false
	});

	$("#messageInfo_readState").validatebox({
		required : true, 
		missingMessage : '请输入阅读状态',
	});

	//单击添加按钮
	$("#messageInfoAddButton").click(function () {
		//验证表单 
		if(!$("#messageInfoAddForm").form("validate")) {
			$.messager.alert("错误提示","你输入的信息还有错误！","warning");
			$(".messager-window").css("z-index",10000);
		} else {
			$("#messageInfoAddForm").form({
			    url:"MessageInfo/add",
			    onSubmit: function(){
					if($("#messageInfoAddForm").form("validate"))  { 
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
                        $("#messageInfoAddForm").form("clear");
                    }else{
                        $.messager.alert("消息",obj.message);
                        $(".messager-window").css("z-index",10000);
                    }
			    }
			});
			//提交表单
			$("#messageInfoAddForm").submit();
		}
	});

	//单击清空按钮
	$("#messageInfoClearButton").click(function () { 
		$("#messageInfoAddForm").form("clear"); 
	});
});
