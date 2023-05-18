var cooking_manage_tool = null; 
$(function () { 
	initCookingManageTool(); //建立Cooking管理对象
	cooking_manage_tool.init(); //如果需要通过下拉框查询，首先初始化下拉框的值
	$("#cooking_manage").datagrid({
		url : 'Cooking/list',
		fit : true,
		fitColumns : true,
		striped : true,
		rownumbers : true,
		border : false,
		pagination : true,
		pageSize : 5,
		pageList : [5, 10, 15, 20, 25],
		pageNumber : 1,
		sortName : "cookingId",
		sortOrder : "desc",
		toolbar : "#cooking_manage_tool",
		columns : [[
			{
				field : "cookingId",
				title : "记录id",
				width : 70,
			},
			{
				field : "cookClassObj",
				title : "菜谱分类",
				width : 140,
			},
			{
				field : "cookingName",
				title : "菜谱名称",
				width : 140,
			},
			{
				field : "cookingPhoto",
				title : "菜谱图片",
				width : "70px",
				height: "65px",
				formatter: function(val,row) {
					return "<img src='" + val + "' width='65px' height='55px' />";
				}
 			},
			{
				field : "gongxiao",
				title : "菜谱功效",
				width : 240,
			},
			
			{
				field : "addTime",
				title : "发布时间",
				width : 140,
			},
		]],
	});

	$("#cookingEditDiv").dialog({
		title : "修改管理",
		top: "10px",
		width : 1000,
		height : 600,
		modal : true,
		closed : true,
		iconCls : "icon-edit-new",
		buttons : [{
			text : "提交",
			iconCls : "icon-edit-new",
			handler : function () {
				if ($("#cookingEditForm").form("validate")) {
					//验证表单 
					if(!$("#cookingEditForm").form("validate")) {
						$.messager.alert("错误提示","你输入的信息还有错误！","warning");
					} else {
						$("#cookingEditForm").form({
						    url:"Cooking/" + $("#cooking_cookingId_edit").val() + "/update",
						    onSubmit: function(){
								if($("#cookingEditForm").form("validate"))  {
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
			                        $("#cookingEditDiv").dialog("close");
			                        cooking_manage_tool.reload();
			                    }else{
			                        $.messager.alert("消息",obj.message);
			                    } 
						    }
						});
						//提交表单
						$("#cookingEditForm").submit();
					}
				}
			},
		},{
			text : "取消",
			iconCls : "icon-redo",
			handler : function () {
				$("#cookingEditDiv").dialog("close");
				$("#cookingEditForm").form("reset"); 
			},
		}],
	});
});

function initCookingManageTool() {
	cooking_manage_tool = {
		init: function() {
			$.ajax({
				url : "CookClass/listAll",
				type : "post",
				success : function (data, response, status) {
					$("#cookClassObj_cookClassId_query").combobox({ 
					    valueField:"cookClassId",
					    textField:"className",
					    panelHeight: "200px",
				        editable: false, //不允许手动输入 
					});
					data.splice(0,0,{cookClassId:0,className:"不限制"});
					$("#cookClassObj_cookClassId_query").combobox("loadData",data); 
				}
			});
		},
		reload : function () {
			$("#cooking_manage").datagrid("reload");
		},
		redo : function () {
			$("#cooking_manage").datagrid("unselectAll");
		},
		search: function() {
			var queryParams = $("#cooking_manage").datagrid("options").queryParams;
			queryParams["cookClassObj.cookClassId"] = $("#cookClassObj_cookClassId_query").combobox("getValue");
			queryParams["cookingName"] = $("#cookingName").val();
			queryParams["gongxiao"] = $("#gongxiao").val();
			queryParams["yongliao"] = $("#yongliao").val();
			queryParams["addTime"] = $("#addTime").datebox("getValue"); 
			$("#cooking_manage").datagrid("options").queryParams=queryParams; 
			$("#cooking_manage").datagrid("load");
		},
		exportExcel: function() {
			$("#cookingQueryForm").form({
			    url:"Cooking/OutToExcel",
			});
			//提交表单
			$("#cookingQueryForm").submit();
		},
		remove : function () {
			var rows = $("#cooking_manage").datagrid("getSelections");
			if (rows.length > 0) {
				$.messager.confirm("确定操作", "您正在要删除所选的记录吗？", function (flag) {
					if (flag) {
						var cookingIds = [];
						for (var i = 0; i < rows.length; i ++) {
							cookingIds.push(rows[i].cookingId);
						}
						$.ajax({
							type : "POST",
							url : "Cooking/deletes",
							data : {
								cookingIds : cookingIds.join(","),
							},
							beforeSend : function () {
								$("#cooking_manage").datagrid("loading");
							},
							success : function (data) {
								if (data.success) {
									$("#cooking_manage").datagrid("loaded");
									$("#cooking_manage").datagrid("load");
									$("#cooking_manage").datagrid("unselectAll");
									$.messager.show({
										title : "提示",
										msg : data.message
									});
								} else {
									$("#cooking_manage").datagrid("loaded");
									$("#cooking_manage").datagrid("load");
									$("#cooking_manage").datagrid("unselectAll");
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
			var rows = $("#cooking_manage").datagrid("getSelections");
			if (rows.length > 1) {
				$.messager.alert("警告操作！", "编辑记录只能选定一条数据！", "warning");
			} else if (rows.length == 1) {
				$.ajax({
					url : "Cooking/" + rows[0].cookingId +  "/update",
					type : "get",
					data : {
						//cookingId : rows[0].cookingId,
					},
					beforeSend : function () {
						$.messager.progress({
							text : "正在获取中...",
						});
					},
					success : function (cooking, response, status) {
						$.messager.progress("close");
						if (cooking) { 
							$("#cookingEditDiv").dialog("open");
							$("#cooking_cookingId_edit").val(cooking.cookingId);
							$("#cooking_cookingId_edit").validatebox({
								required : true,
								missingMessage : "请输入记录id",
								editable: false
							});
							$("#cooking_cookClassObj_cookClassId_edit").combobox({
								url:"CookClass/listAll",
							    valueField:"cookClassId",
							    textField:"className",
							    panelHeight: "auto",
						        editable: false, //不允许手动输入 
						        onLoadSuccess: function () { //数据加载完毕事件
									$("#cooking_cookClassObj_cookClassId_edit").combobox("select", cooking.cookClassObjPri);
									//var data = $("#cooking_cookClassObj_cookClassId_edit").combobox("getData"); 
						            //if (data.length > 0) {
						                //$("#cooking_cookClassObj_cookClassId_edit").combobox("select", data[0].cookClassId);
						            //}
								}
							});
							$("#cooking_cookingName_edit").val(cooking.cookingName);
							$("#cooking_cookingName_edit").validatebox({
								required : true,
								missingMessage : "请输入菜谱名称",
							});
							$("#cooking_cookingPhoto").val(cooking.cookingPhoto);
							$("#cooking_cookingPhotoImg").attr("src", cooking.cookingPhoto);
							$("#cooking_gongxiao_edit").val(cooking.gongxiao);
							$("#cooking_gongxiao_edit").validatebox({
								required : true,
								missingMessage : "请输入菜谱功效",
							});
							cooking_cookingDesc_editor.setContent(cooking.cookingDesc, false);
							cooking_yongliao_editor.setContent(cooking.yongliao, false);
							$("#cooking_videoFile").val(cooking.videoFile);
							if(cooking.videoFile == "") $("#cooking_videoFileA").html("暂无文件");
							else $("#cooking_videoFileA").html(cooking.videoFile);
							$("#cooking_videoFileA").attr("href", cooking.videoFile);
							$("#cooking_addTime_edit").datetimebox({
								value: cooking.addTime,
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
