var learnRec_manage_tool = null; 
$(function () { 
	initLearnRecManageTool(); //建立LearnRec管理对象
	learnRec_manage_tool.init(); //如果需要通过下拉框查询，首先初始化下拉框的值
	$("#learnRec_manage").datagrid({
		url : 'LearnRec/list',
		fit : true,
		fitColumns : true,
		striped : true,
		rownumbers : true,
		border : false,
		pagination : true,
		pageSize : 5,
		pageList : [5, 10, 15, 20, 25],
		pageNumber : 1,
		sortName : "recId",
		sortOrder : "desc",
		toolbar : "#learnRec_manage_tool",
		columns : [[
			{
				field : "recId",
				title : "记录id",
				width : 70,
			},
			{
				field : "cookingObj",
				title : "学会菜谱",
				width : 140,
			},
			{
				field : "userObj",
				title : "学习用户",
				width : 140,
			},
			{
				field : "jifen",
				title : "获得积分",
				width : 70,
			},
			{
				field : "learnTime",
				title : "学会时间",
				width : 140,
			},
		]],
	});

	$("#learnRecEditDiv").dialog({
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
				if ($("#learnRecEditForm").form("validate")) {
					//验证表单 
					if(!$("#learnRecEditForm").form("validate")) {
						$.messager.alert("错误提示","你输入的信息还有错误！","warning");
					} else {
						$("#learnRecEditForm").form({
						    url:"LearnRec/" + $("#learnRec_recId_edit").val() + "/update",
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
						    	console.log(data);
			                	var obj = jQuery.parseJSON(data);
			                    if(obj.success){
			                        $.messager.alert("消息","信息修改成功！");
			                        $("#learnRecEditDiv").dialog("close");
			                        learnRec_manage_tool.reload();
			                    }else{
			                        $.messager.alert("消息",obj.message);
			                    } 
						    }
						});
						//提交表单
						$("#learnRecEditForm").submit();
					}
				}
			},
		},{
			text : "取消",
			iconCls : "icon-redo",
			handler : function () {
				$("#learnRecEditDiv").dialog("close");
				$("#learnRecEditForm").form("reset"); 
			},
		}],
	});
});

function initLearnRecManageTool() {
	learnRec_manage_tool = {
		init: function() {
			$.ajax({
				url : "Cooking/listAll",
				type : "post",
				success : function (data, response, status) {
					$("#cookingObj_cookingId_query").combobox({ 
					    valueField:"cookingId",
					    textField:"cookingName",
					    panelHeight: "200px",
				        editable: false, //不允许手动输入 
					});
					data.splice(0,0,{cookingId:0,cookingName:"不限制"});
					$("#cookingObj_cookingId_query").combobox("loadData",data); 
				}
			});
			$.ajax({
				url : "UserInfo/listAll",
				type : "post",
				success : function (data, response, status) {
					$("#userObj_user_name_query").combobox({ 
					    valueField:"user_name",
					    textField:"name",
					    panelHeight: "200px",
				        editable: false, //不允许手动输入 
					});
					data.splice(0,0,{user_name:"",name:"不限制"});
					$("#userObj_user_name_query").combobox("loadData",data); 
				}
			});
		},
		reload : function () {
			$("#learnRec_manage").datagrid("reload");
		},
		redo : function () {
			$("#learnRec_manage").datagrid("unselectAll");
		},
		search: function() {
			var queryParams = $("#learnRec_manage").datagrid("options").queryParams;
			queryParams["cookingObj.cookingId"] = $("#cookingObj_cookingId_query").combobox("getValue");
			queryParams["userObj.user_name"] = $("#userObj_user_name_query").combobox("getValue");
			queryParams["learnTime"] = $("#learnTime").datebox("getValue"); 
			$("#learnRec_manage").datagrid("options").queryParams=queryParams; 
			$("#learnRec_manage").datagrid("load");
		},
		exportExcel: function() {
			$("#learnRecQueryForm").form({
			    url:"LearnRec/OutToExcel",
			});
			//提交表单
			$("#learnRecQueryForm").submit();
		},
		remove : function () {
			var rows = $("#learnRec_manage").datagrid("getSelections");
			if (rows.length > 0) {
				$.messager.confirm("确定操作", "您正在要删除所选的记录吗？", function (flag) {
					if (flag) {
						var recIds = [];
						for (var i = 0; i < rows.length; i ++) {
							recIds.push(rows[i].recId);
						}
						$.ajax({
							type : "POST",
							url : "LearnRec/deletes",
							data : {
								recIds : recIds.join(","),
							},
							beforeSend : function () {
								$("#learnRec_manage").datagrid("loading");
							},
							success : function (data) {
								if (data.success) {
									$("#learnRec_manage").datagrid("loaded");
									$("#learnRec_manage").datagrid("load");
									$("#learnRec_manage").datagrid("unselectAll");
									$.messager.show({
										title : "提示",
										msg : data.message
									});
								} else {
									$("#learnRec_manage").datagrid("loaded");
									$("#learnRec_manage").datagrid("load");
									$("#learnRec_manage").datagrid("unselectAll");
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
			var rows = $("#learnRec_manage").datagrid("getSelections");
			if (rows.length > 1) {
				$.messager.alert("警告操作！", "编辑记录只能选定一条数据！", "warning");
			} else if (rows.length == 1) {
				$.ajax({
					url : "LearnRec/" + rows[0].recId +  "/update",
					type : "get",
					data : {
						//recId : rows[0].recId,
					},
					beforeSend : function () {
						$.messager.progress({
							text : "正在获取中...",
						});
					},
					success : function (learnRec, response, status) {
						$.messager.progress("close");
						if (learnRec) { 
							$("#learnRecEditDiv").dialog("open");
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
						}
					}
				});
			} else if (rows.length == 0) {
				$.messager.alert("警告操作！", "编辑记录至少选定一条数据！", "warning");
			}
		},
	};
}
