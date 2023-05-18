<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%>
<jsp:include page="../check_logstate.jsp"/>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/cookClass.css" />
<div id="cookClassAddDiv">
	<form id="cookClassAddForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">分类名称:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="cookClass_className" name="cookClass.className" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">烹饪分类介绍:</span>
			<span class="inputControl">
				<textarea id="cookClass_cookClassDesc" name="cookClass.cookClassDesc" rows="6" cols="80"></textarea>

			</span>

		</div>
		<div class="operation">
			<a id="cookClassAddButton" class="easyui-linkbutton">添加</a>
			<a id="cookClassClearButton" class="easyui-linkbutton">重填</a>
		</div> 
	</form>
</div>
<script src="${pageContext.request.contextPath}/CookClass/js/cookClass_add.js"></script> 
