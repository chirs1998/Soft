<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商品管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/acout/save/">商品列表</a></li>
		<%--<shiro:hasPermission name="acout:save:edit"><li><a href="${ctx}/acout/save/form">商品添加</a></li></shiro:hasPermission>--%>
	</ul>
	<form:form id="searchForm" modelAttribute="save" action="${ctx}/acout/save/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>商品名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<a href="">我的购物车</a>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>

				<th>商品名称</th>
				<th>图片地址</th>
				<th>价格</th>
				<th>数量</th>
				<th>创建者</th>
				<th>创建时间</th>
				<th>更新者</th>
				<th>更新时间</th>
				<th>商品信息</th>
				<shiro:hasPermission name="acout:save:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="save">
			<tr>

				<td>
					${save.name}
				</td>
				<td>
					${save.imgUrl}
				</td>
				<td>
					${save.price}
				</td>
				<td>
					${save.count}
				</td>
				<td>
					${save.createBy.id}
				</td>
				<td>
					<fmt:formatDate value="${save.createDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${save.updateBy.id}
				</td>
				<td>
					<fmt:formatDate value="${save.updateDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${save.remarks}
				</td>
				<shiro:hasPermission name="acout:save:edit"><td>
    				<a href="${ctx}/acout/save/form?id=${save.id}">修改</a>
					<a href="${ctx}/acout/save/delete?id=${save.id}" onclick="return confirmx('确认要删除该商品吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>