<%@ page language="java"  contentType="text/html;charset=UTF-8"%>
<jsp:include page="../check_logstate.jsp"/> 
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/cooking.css" /> 

<div id="cooking_manage"></div>
<div id="cooking_manage_tool" style="padding:5px;">
	<div style="margin-bottom:5px;">
		<a href="#" class="easyui-linkbutton" iconCls="icon-edit-new" plain="true" onclick="cooking_manage_tool.edit();">修改</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-delete-new" plain="true" onclick="cooking_manage_tool.remove();">删除</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-reload" plain="true"  onclick="cooking_manage_tool.reload();">刷新</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-redo" plain="true" onclick="cooking_manage_tool.redo();">取消选择</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-export" plain="true" onclick="cooking_manage_tool.exportExcel();">导出到excel</a>
	</div>
	<div style="padding:0 0 0 7px;color:#333;">
		<form id="cookingQueryForm" method="post">
			菜谱分类：<input class="textbox" type="text" id="cookClassObj_cookClassId_query" name="cookClassObj.cookClassId" style="width: auto"/>
			菜谱名称：<input type="text" class="textbox" id="cookingName" name="cookingName" style="width:110px" />
			菜谱功效：<input type="text" class="textbox" id="gongxiao" name="gongxiao" style="width:110px" />
			菜谱用料：<input type="text" class="textbox" id="yongliao" name="yongliao" style="width:110px" />
			发布时间：<input type="text" id="addTime" name="addTime" class="easyui-datebox" editable="false" style="width:100px">
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="cooking_manage_tool.search();">查询</a>
		</form>	
	</div>
</div>

<div id="cookingEditDiv">
	<form id="cookingEditForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">记录id:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="cooking_cookingId_edit" name="cooking.cookingId" style="width:200px" />
			</span>
		</div>
		<div>
			<span class="label">菜谱分类:</span>
			<span class="inputControl">
				<input class="textbox"  id="cooking_cookClassObj_cookClassId_edit" name="cooking.cookClassObj.cookClassId" style="width: auto"/>
			</span>
		</div>
		<div>
			<span class="label">菜谱名称:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="cooking_cookingName_edit" name="cooking.cookingName" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">菜谱图片:</span>
			<span class="inputControl">
				<img id="cooking_cookingPhotoImg" width="200px" border="0px"/><br/>
    			<input type="hidden" id="cooking_cookingPhoto" name="cooking.cookingPhoto"/>
				<input id="cookingPhotoFile" name="cookingPhotoFile" type="file" size="50" />
			</span>
		</div>
		<div>
			<span class="label">菜谱功效:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="cooking_gongxiao_edit" name="cooking.gongxiao" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">菜谱介绍:</span>
			<span class="inputControl">
				<script name="cooking.cookingDesc" id="cooking_cookingDesc_edit" type="text/plain"   style="width:100%;height:500px;"></script>

			</span>

		</div>
		<div>
			<span class="label">菜谱用料:</span>
			<span class="inputControl">
				<script name="cooking.yongliao" id="cooking_yongliao_edit" type="text/plain"   style="width:100%;height:500px;"></script>

			</span>

		</div>
		<div>
			<span class="label">做法视频:</span>
			<span class="inputControl">
				<a id="cooking_videoFileA" style="color:red;margin-bottom:5px;">查看</a>&nbsp;&nbsp;
    			<input type="hidden" id="cooking_videoFile" name="cooking.videoFile"/>
				<input id="videoFileFile" name="videoFileFile" value="重新选择文件" type="file" size="50" />
			</span>
		</div>
		<div>
			<span class="label">发布时间:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="cooking_addTime_edit" name="cooking.addTime" />

			</span>

		</div>
	</form>
</div>
<script>
//实例化编辑器
//建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
var cooking_cookingDesc_editor = UE.getEditor('cooking_cookingDesc_edit'); //菜谱介绍编辑器
var cooking_yongliao_editor = UE.getEditor('cooking_yongliao_edit'); //菜谱用料编辑器
</script>
<script type="text/javascript" src="Cooking/js/cooking_manage.js"></script> 
