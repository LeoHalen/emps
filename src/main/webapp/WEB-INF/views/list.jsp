<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<c:set var="ctx" value="${pageContext.request.contextPath }"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>员工列表</title>
<!-- 引入Bootstrap样式 -->
<link href="${ctx }/static/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet">
<!-- 引入jquery -->
<script type="text/javascript" src="${ctx }/static/js/jquery-1.7.2.min.js"></script>
<!-- 引入js -->
<script src="${ctx }/static/bootsstrap-3.3.7-dist/js/bootstrap.min.js"></script>
</head>
<body>
	<!-- 搭建显示页面 -->
	<div class="container">
		<!-- 标题 -->
		<div class="row">
			<div class="col-md-12">
				<h1>SSM-CRUD 系统</h1>
			</div>
		</div>
		<!-- 按钮 -->
		<div class="row">
			<div class="col-md-4 col-md-offset-8">
				<button class="btn btn-primary">新增</button>
				<button class="btn btn-danger">删除</button>
			</div>
		</div>
		<!-- 显示表格数据 -->
		<div class="row">
			<div class="col-md-12">
				<table class="table table-hover">
					<tr>
						<th>员工编号</th>
						<th>员工姓名</th>
						<th>性别</th>
						<th>邮箱</th>
						<th>所属部门</th>
						<th>操作</th>
					</tr>
					<c:forEach var="emp" items="${pageInfo.list }">
					<tr>
						<th>${emp.empId }</th>
						<th>${emp.empName }</th>
						<th>${emp.gender }</th>
						<th>${emp.email }</th>
						<th>${emp.department.deptName }</th>
						<th>
							<button class="btn btn-primary">
							<span class="glyphicon glyphicon-pencil" aria-hidden="true">编辑</span>
							</button>
							<button class="btn btn-danger">
							<span class="glyphicon glyphicon-trash" aria-hidden="true">删除</span>
							</button>
						</th>
					</tr>
					</c:forEach>
				</table>
			</div>
		</div>
		<!-- 显示分页信息 -->
		<div class="row">
			<!-- 分页文字信息 -->
			<div class="col-md-6">
				当前${pageInfo.pageNum }页,总${pageInfo.pages }页,总共${pageInfo.total }条记录
			</div>
			<!-- 分页条信息 -->
			<div class="col-md-6">
				<nav aria-label="Page navigation">
				  <ul class="pagination">
				  	<li><a href="${ctx }/emps?pageNo=1">首页</a></li>
				  	<c:if test="${pageInfo.hasPreviousPage }">
				    <li>
				      <a href="${ctx }/emps?pageNo=${pageInfo.pageNum-1 }" aria-label="Previous">
				        <span aria-hidden="true">&laquo;</span>
				      </a>
				    </li>
				  	</c:if>
				    <c:forEach var="page_Num" items="${pageInfo.navigatepageNums }">
				    	<c:if test="${page_Num==pageInfo.pageNum}">
				    		<li class="active"><a href="#">${page_Num }</a></li>
				    	</c:if>
				    	<c:if test="${page_Num!=pageInfo.pageNum}">
				    		<li><a href="${ctx }/emps?pageNo=${page_Num }">${page_Num }</a></li>
				    	</c:if>
				    </c:forEach>
				    <c:if test="${pageInfo.hasNextPage }">
				    <li>
				      <a href="${ctx }/emps?pageNo=${pageInfo.pageNum+1 }" aria-label="Next">
				        <span aria-hidden="true">&raquo;</span>
				      </a>
				    </li>
				    </c:if>
				    <li><a href="${ctx }/emps?pageNo=${pageInfo.pages }">尾页</a></li>
				  </ul>
				</nav>
			</div>
		</div>
	</div>
</body>
</html>