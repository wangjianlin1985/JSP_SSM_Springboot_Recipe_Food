<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<jsp:include page="../check_logstate.jsp"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/cookClass.css" />
<div id="cookClass_editDiv">
	<form id="cookClassEditForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">分类id:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="cookClass_cookClassId_edit" name="cookClass.cookClassId" value="<%=request.getParameter("cookClassId") %>" style="width:200px" />
			</span>
		</div>

		<div>
			<span class="label">分类名称:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="cookClass_className_edit" name="cookClass.className" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">烹饪分类介绍:</span>
			<span class="inputControl">
				<textarea id="cookClass_cookClassDesc_edit" name="cookClass.cookClassDesc" rows="8" cols="60"></textarea>

			</span>

		</div>
		<div class="operation">
			<a id="cookClassModifyButton" class="easyui-linkbutton">更新</a> 
		</div>
	</form>
</div>
<script src="${pageContext.request.contextPath}/CookClass/js/cookClass_modify.js"></script> 
