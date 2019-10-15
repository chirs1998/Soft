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
        //添加进购物车
        function addToCar(saveId,index,obj) {
			var Min='0';
			console.log(index)
			var Max= parseInt($(".restCount").eq(index).text());
            var value=$(obj).parent("td").children("input").val();
			console.log(Max, Min, parseInt(value));
			if(parseInt(Min)<parseInt(value)&&value%1 == 0&&parseInt(value)<=parseInt(Max))
			{
                $.ajax({
                    url:"${ctx}/acout/accout/addNewAccount",		// 请求后台方法。
                    type:"post",	// 请求方式
                    data:{
                        saveId:saveId,
						count:value
                        //  传入数据
                    },
                    success:function(data){
                        if(data != null && data != ""){
                            $.jBox.tip("操作成功!");
                           location.reload();
                        }
                        $(".restCount").eq(index).text(parseInt($(".restCount").eq(index).text())-1);
                    },
                    error:function(){
                        alert("网络错误，请重试 ！！");
                    }
                });

            }
			else
			{
			    alert("输入不合法!")
			}

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
	<%--<a href="${ctx}/acout/accout/findMyCar">我的购物车</a>--%>
	<%--<a href="${ctx}/acout/accout/findMyAccount">我的以往订单</a>--%>
	<%--<a href="${ctx}/acout/accout/manageAccount">订单管理</a>--%>
	<%--<br>--%>

	<%--<a href="${ctx}/acout/type/">种类列表</a>--%>
	<%--<a href="${ctx}/acout/type/form">添加种类</a>--%>
	<%--<br>--%>
	<%--<a href="${ctx}/acout/type/">库存列表</a>--%>
	<%--<a href="${ctx}/acout/save/form">添加库存(进货)</a>--%>
	<%--<br>--%>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>商品名称</th>
				<th>可选数量</th>
				<th>图片</th>
				<th>价格</th>
				<th>创建时间</th>
				<th>商品信息</th>
				<%--<shiro:hasPermission name="acout:save:edit"><th>操作</th></shiro:hasPermission>--%>
				<th>购物车数量</th>
				<th>添加进购物车</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="save" varStatus="i">
			<tr>

				<td>
					${save.type.name}
				</td>
				<td class="restCount">${save.count}</td>
				<td>
					<img src="${save.imgUrl}" style="width: 200px" alt="">
				</td>
				<td>
					${save.price}
				</td>
				<td>
					<fmt:formatDate value="${save.createDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					${save.remarks}
				</td>
				<%--<shiro:hasPermission name="acout:save:edit">--%>
				<td>
						${save.checkedCount}
				<%--<a href="${ctx}/acout/save/form?id=${save.id}">修改</a>--%>
					<%--<a href="${ctx}/acout/save/delete?id=${save.id}" onclick="return confirmx('确认要删除该商品吗？', this.href)">删除</a>--%>
				</td>
				<td>
					<input type="text"  style="margin: 2px;padding: 2px" id="${i.index}"><button class="btn btn-link" onclick="addToCar('${save.id}','${i.index}',this)">+</button>
				</td>
				<%--/shiro:hasPermission>--%>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>