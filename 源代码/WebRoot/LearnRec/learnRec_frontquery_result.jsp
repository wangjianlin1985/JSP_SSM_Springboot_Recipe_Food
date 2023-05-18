<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%> 
<%@ page import="com.chengxusheji.po.LearnRec" %>
<%@ page import="com.chengxusheji.po.Cooking" %>
<%@ page import="com.chengxusheji.po.UserInfo" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    List<LearnRec> learnRecList = (List<LearnRec>)request.getAttribute("learnRecList");
    //获取所有的cookingObj信息
    List<Cooking> cookingList = (List<Cooking>)request.getAttribute("cookingList");
    //获取所有的userObj信息
    List<UserInfo> userInfoList = (List<UserInfo>)request.getAttribute("userInfoList");
    int currentPage =  (Integer)request.getAttribute("currentPage"); //当前页
    int totalPage =   (Integer)request.getAttribute("totalPage");  //一共多少页
    int recordNumber =   (Integer)request.getAttribute("recordNumber");  //一共多少记录
    Cooking cookingObj = (Cooking)request.getAttribute("cookingObj");
    UserInfo userObj = (UserInfo)request.getAttribute("userObj");
    String learnTime = (String)request.getAttribute("learnTime"); //学会时间查询关键字
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1 , user-scalable=no">
<title>学习记录查询</title>
<link href="<%=basePath %>plugins/bootstrap.css" rel="stylesheet">
<link href="<%=basePath %>plugins/bootstrap-dashen.css" rel="stylesheet">
<link href="<%=basePath %>plugins/font-awesome.css" rel="stylesheet">
<link href="<%=basePath %>plugins/animate.css" rel="stylesheet">
<link href="<%=basePath %>plugins/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
</head>
<body style="margin-top:70px;">
<div class="container">
<jsp:include page="../header.jsp"></jsp:include>
	<div class="row"> 
		<div class="col-md-9 wow fadeInDown" data-wow-duration="0.5s">
			<div>
				<!-- Nav tabs -->
				<ul class="nav nav-tabs" role="tablist">
			    	<li><a href="<%=basePath %>index.jsp">首页</a></li>
			    	<li role="presentation" class="active"><a href="#learnRecListPanel" aria-controls="learnRecListPanel" role="tab" data-toggle="tab">学习记录列表</a></li>
			    	<li role="presentation" ><a href="<%=basePath %>LearnRec/learnRec_frontAdd.jsp" style="display:none;">添加学习记录</a></li>
				</ul>
			  	<!-- Tab panes -->
			  	<div class="tab-content">
				    <div role="tabpanel" class="tab-pane active" id="learnRecListPanel">
				    		<div class="row">
				    			<div class="col-md-12 top5">
				    				<div class="table-responsive">
				    				<table class="table table-condensed table-hover">
				    					<tr class="success bold"><td>序号</td><td>记录id</td><td>学会菜谱</td><td>学习用户</td><td>获得积分</td><td>学会时间</td><td>操作</td></tr>
				    					<% 
				    						/*计算起始序号*/
				    	            		int startIndex = (currentPage -1) * 5;
				    	            		/*遍历记录*/
				    	            		for(int i=0;i<learnRecList.size();i++) {
					    	            		int currentIndex = startIndex + i + 1; //当前记录的序号
					    	            		LearnRec learnRec = learnRecList.get(i); //获取到学习记录对象
 										%>
 										<tr>
 											<td><%=currentIndex %></td>
 											<td><%=learnRec.getRecId() %></td>
 											<td><%=learnRec.getCookingObj().getCookingName() %></td>
 											<td><%=learnRec.getUserObj().getName() %></td>
 											<td><%=learnRec.getJifen() %></td>
 											<td><%=learnRec.getLearnTime() %></td>
 											<td>
 												<a href="<%=basePath  %>LearnRec/<%=learnRec.getRecId() %>/frontshow"><i class="fa fa-info"></i>&nbsp;查看</a>&nbsp;
 												<a href="#" onclick="learnRecEdit('<%=learnRec.getRecId() %>');" style="display:none;"><i class="fa fa-pencil fa-fw"></i>编辑</a>&nbsp;
 												<a href="#" onclick="learnRecDelete('<%=learnRec.getRecId() %>');" style="display:none;"><i class="fa fa-trash-o fa-fw"></i>删除</a>
 											</td> 
 										</tr>
 										<%}%>
				    				</table>
				    				</div>
				    			</div>
				    		</div>

				    		<div class="row">
					            <div class="col-md-12">
						            <nav class="pull-left">
						                <ul class="pagination">
						                    <li><a href="#" onclick="GoToPage(<%=currentPage-1 %>,<%=totalPage %>);" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>
						                     <%
						                    	int startPage = currentPage - 5;
						                    	int endPage = currentPage + 5;
						                    	if(startPage < 1) startPage=1;
						                    	if(endPage > totalPage) endPage = totalPage;
						                    	for(int i=startPage;i<=endPage;i++) {
						                    %>
						                    <li class="<%= currentPage==i?"active":"" %>"><a href="#"  onclick="GoToPage(<%=i %>,<%=totalPage %>);"><%=i %></a></li>
						                    <%  } %> 
						                    <li><a href="#" onclick="GoToPage(<%=currentPage+1 %>,<%=totalPage %>);"><span aria-hidden="true">&raquo;</span></a></li>
						                </ul>
						            </nav>
						            <div class="pull-right" style="line-height:75px;" >共有<%=recordNumber %>条记录，当前第 <%=currentPage %>/<%=totalPage %> 页</div>
					            </div>
				            </div> 
				    </div>
				</div>
			</div>
		</div>
	<div class="col-md-3 wow fadeInRight">
		<div class="page-header">
    		<h1>学习记录查询</h1>
		</div>
		<form name="learnRecQueryForm" id="learnRecQueryForm" action="<%=basePath %>LearnRec/frontlist" class="mar_t15" method="post">
            <div class="form-group">
            	<label for="cookingObj_cookingId">学会菜谱：</label>
                <select id="cookingObj_cookingId" name="cookingObj.cookingId" class="form-control">
                	<option value="0">不限制</option>
	 				<%
	 				for(Cooking cookingTemp:cookingList) {
	 					String selected = "";
 					if(cookingObj!=null && cookingObj.getCookingId()!=null && cookingObj.getCookingId().intValue()==cookingTemp.getCookingId().intValue())
 						selected = "selected";
	 				%>
 				 <option value="<%=cookingTemp.getCookingId() %>" <%=selected %>><%=cookingTemp.getCookingName() %></option>
	 				<%
	 				}
	 				%>
 			</select>
            </div>
            <div class="form-group">
            	<label for="userObj_user_name">学习用户：</label>
                <select id="userObj_user_name" name="userObj.user_name" class="form-control">
                	<option value="">不限制</option>
	 				<%
	 				for(UserInfo userInfoTemp:userInfoList) {
	 					String selected = "";
 					if(userObj!=null && userObj.getUser_name()!=null && userObj.getUser_name().equals(userInfoTemp.getUser_name()))
 						selected = "selected";
	 				%>
 				 <option value="<%=userInfoTemp.getUser_name() %>" <%=selected %>><%=userInfoTemp.getName() %></option>
	 				<%
	 				}
	 				%>
 			</select>
            </div>
			<div class="form-group">
				<label for="learnTime">学会时间:</label>
				<input type="text" id="learnTime" name="learnTime" class="form-control"  placeholder="请选择学会时间" value="<%=learnTime %>" onclick="SelectDate(this,'yyyy-MM-dd')" />
			</div>
            <input type=hidden name=currentPage value="<%=currentPage %>" />
            <button type="submit" class="btn btn-primary">查询</button>
        </form>
	</div>

		</div>
	</div> 
<div id="learnRecEditDialog" class="modal fade" tabindex="-1" role="dialog">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title"><i class="fa fa-edit"></i>&nbsp;学习记录信息编辑</h4>
      </div>
      <div class="modal-body" style="height:450px; overflow: scroll;">
      	<form class="form-horizontal" name="learnRecEditForm" id="learnRecEditForm" enctype="multipart/form-data" method="post"  class="mar_t15">
		  <div class="form-group">
			 <label for="learnRec_recId_edit" class="col-md-3 text-right">记录id:</label>
			 <div class="col-md-9"> 
			 	<input type="text" id="learnRec_recId_edit" name="learnRec.recId" class="form-control" placeholder="请输入记录id" readOnly>
			 </div>
		  </div> 
		  <div class="form-group">
		  	 <label for="learnRec_cookingObj_cookingId_edit" class="col-md-3 text-right">学会菜谱:</label>
		  	 <div class="col-md-9">
			    <select id="learnRec_cookingObj_cookingId_edit" name="learnRec.cookingObj.cookingId" class="form-control">
			    </select>
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="learnRec_userObj_user_name_edit" class="col-md-3 text-right">学习用户:</label>
		  	 <div class="col-md-9">
			    <select id="learnRec_userObj_user_name_edit" name="learnRec.userObj.user_name" class="form-control">
			    </select>
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="learnRec_jifen_edit" class="col-md-3 text-right">获得积分:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="learnRec_jifen_edit" name="learnRec.jifen" class="form-control" placeholder="请输入获得积分">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="learnRec_learnTime_edit" class="col-md-3 text-right">学会时间:</label>
		  	 <div class="col-md-9">
                <div class="input-group date learnRec_learnTime_edit col-md-12" data-link-field="learnRec_learnTime_edit">
                    <input class="form-control" id="learnRec_learnTime_edit" name="learnRec.learnTime" size="16" type="text" value="" placeholder="请选择学会时间" readonly>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                </div>
		  	 </div>
		  </div>
		</form> 
	    <style>#learnRecEditForm .form-group {margin-bottom:5px;}  </style>
      </div>
      <div class="modal-footer"> 
      	<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
      	<button type="button" class="btn btn-primary" onclick="ajaxLearnRecModify();">提交</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<jsp:include page="../footer.jsp"></jsp:include> 
<script src="<%=basePath %>plugins/jquery.min.js"></script>
<script src="<%=basePath %>plugins/bootstrap.js"></script>
<script src="<%=basePath %>plugins/wow.min.js"></script>
<script src="<%=basePath %>plugins/bootstrap-datetimepicker.min.js"></script>
<script src="<%=basePath %>plugins/locales/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="<%=basePath %>js/jsdate.js"></script>
<script>
var basePath = "<%=basePath%>";
/*跳转到查询结果的某页*/
function GoToPage(currentPage,totalPage) {
    if(currentPage==0) return;
    if(currentPage>totalPage) return;
    document.learnRecQueryForm.currentPage.value = currentPage;
    document.learnRecQueryForm.submit();
}

/*可以直接跳转到某页*/
function changepage(totalPage)
{
    var pageValue=document.learnRecQueryForm.pageValue.value;
    if(pageValue>totalPage) {
        alert('你输入的页码超出了总页数!');
        return ;
    }
    document.learnRecQueryForm.currentPage.value = pageValue;
    documentlearnRecQueryForm.submit();
}

/*弹出修改学习记录界面并初始化数据*/
function learnRecEdit(recId) {
	$.ajax({
		url :  basePath + "LearnRec/" + recId + "/update",
		type : "get",
		dataType: "json",
		success : function (learnRec, response, status) {
			if (learnRec) {
				$("#learnRec_recId_edit").val(learnRec.recId);
				$.ajax({
					url: basePath + "Cooking/listAll",
					type: "get",
					success: function(cookings,response,status) { 
						$("#learnRec_cookingObj_cookingId_edit").empty();
						var html="";
		        		$(cookings).each(function(i,cooking){
		        			html += "<option value='" + cooking.cookingId + "'>" + cooking.cookingName + "</option>";
		        		});
		        		$("#learnRec_cookingObj_cookingId_edit").html(html);
		        		$("#learnRec_cookingObj_cookingId_edit").val(learnRec.cookingObjPri);
					}
				});
				$.ajax({
					url: basePath + "UserInfo/listAll",
					type: "get",
					success: function(userInfos,response,status) { 
						$("#learnRec_userObj_user_name_edit").empty();
						var html="";
		        		$(userInfos).each(function(i,userInfo){
		        			html += "<option value='" + userInfo.user_name + "'>" + userInfo.name + "</option>";
		        		});
		        		$("#learnRec_userObj_user_name_edit").html(html);
		        		$("#learnRec_userObj_user_name_edit").val(learnRec.userObjPri);
					}
				});
				$("#learnRec_jifen_edit").val(learnRec.jifen);
				$("#learnRec_learnTime_edit").val(learnRec.learnTime);
				$('#learnRecEditDialog').modal('show');
			} else {
				alert("获取信息失败！");
			}
		}
	});
}

/*删除学习记录信息*/
function learnRecDelete(recId) {
	if(confirm("确认删除这个记录")) {
		$.ajax({
			type : "POST",
			url : basePath + "LearnRec/deletes",
			data : {
				recIds : recId,
			},
			success : function (obj) {
				if (obj.success) {
					alert("删除成功");
					$("#learnRecQueryForm").submit();
					//location.href= basePath + "LearnRec/frontlist";
				}
				else 
					alert(obj.message);
			},
		});
	}
}

/*ajax方式提交学习记录信息表单给服务器端修改*/
function ajaxLearnRecModify() {
	$.ajax({
		url :  basePath + "LearnRec/" + $("#learnRec_recId_edit").val() + "/update",
		type : "post",
		dataType: "json",
		data: new FormData($("#learnRecEditForm")[0]),
		success : function (obj, response, status) {
            if(obj.success){
                alert("信息修改成功！");
                $("#learnRecQueryForm").submit();
            }else{
                alert(obj.message);
            } 
		},
		processData: false,
		contentType: false,
	});
}

$(function(){
	/*小屏幕导航点击关闭菜单*/
    $('.navbar-collapse a').click(function(){
        $('.navbar-collapse').collapse('hide');
    });
    new WOW().init();

    /*学会时间组件*/
    $('.learnRec_learnTime_edit').datetimepicker({
    	language:  'zh-CN',  //语言
    	format: 'yyyy-mm-dd hh:ii:ss',
    	weekStart: 1,
    	todayBtn:  1,
    	autoclose: 1,
    	minuteStep: 1,
    	todayHighlight: 1,
    	startView: 2,
    	forceParse: 0
    });
})
</script>
</body>
</html>

