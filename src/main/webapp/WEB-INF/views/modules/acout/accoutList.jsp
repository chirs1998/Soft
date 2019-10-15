<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商品种类管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			updateSum();
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
        //清空该商品
        function clearAccountById(saveId) {
            $.ajax({
                url:"${ctx}/acout/accout/clearAccountById",		// 请求后台方法。
                type:"post",	// 请求方式
                data:{
                    saveId:saveId
                    //  传入数据
                },
                success:function(data){
                    if(data != null && data != ""){
                        location.reload();
                    }
                },
                error:function(){
                    alert("网络错误，请重试 ！！");
                }
            });
        }
		//调整物品数量
        function add(obj) {
		    if(  parseInt($(obj).parents("tr").children(".count").text())!=parseInt($(obj).parents("tr").children(".restCount").text())
			)
			{
                $(obj).parents("tr").children(".count").text(parseInt($(obj).parents("tr").children(".count").text())+1)
			}
			else
			{
			    alert("超过调整范围!");
			}
            updateSum();
        }
        function mul(obj) {
            if($(obj).parents("tr").children(".count").text()!=1
            )
            {
                $(obj).parents("tr").children(".count").text(parseInt($(obj).parents("tr").children(".count").text())-1)
            }
            else
            {
                alert("超过调整范围!");
            }
            updateSum();
        }
        //刷新所有的产品总价
		function updateSum()
		{
		//    每一种的总价更新
			var sum=0;
			for(var i=0;i<$(".sumitem").length;i++)
			{
			    var flag=0;
                $(".sumitem").eq(i).text(parseFloat($(".price").eq(i).text())*parseFloat($(".count").eq(i).text())+"元");
                if($(".myBox").eq(i).attr("checked")){flag=1}
				sum=sum+parseFloat($(".price").eq(i).text())*parseFloat($(".count").eq(i).text())*flag;
            }
         //   最终总价更新
			$("#sum").text("总价:"+sum+"元")
		}
        //提交购物车
		function submitMyCar()
		{
		   var dataList=[];
		   for(var i=0;i<$(".myBox").length&&$(".myBox")[i].checked==true;i++)
		   {
		       var save=new Object();
               save.id=$(".saveId").eq(i).val();
               save.count=$(".count").eq(i).text();
		       dataList.push(save);
		   }
            var param=JSON.stringify(dataList);

            $.ajax({
                url:"${ctx}/acout/accout/addMyCarList",		// 请求后台方法。
                contentType : 'application/json;charset=utf-8',
                data: param,
                dataType:'text',
                type:'POST',
                success:function(data){
                    if(data != null && data != ""){
                        $.jBox.tip("操作成功");
                        location.reload();
                    }
                },
                error:function(){
                    // alert("网络错误，请重试 ！！");
                }
            });
		}
		//全选||全不选
		function selectAll()
		{$(".myBox").attr("checked",$("#selectFlag")[0].checked);
		updateSum();}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<%--<li class="active"><a href="${ctx}/acout/accout/">商品种类列表</a></li>--%>
		<%--<shiro:hasPermission name="acout:accout:edit"><li><a href="${ctx}/acout/accout/form">商品种类添加</a></li></shiro:hasPermission>--%>
	</ul>
	<form:form id="searchForm" modelAttribute="accout" action="${ctx}/acout/accout/findMyCar" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>商品名称：</label>
				<form:input path="remarks" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">

		<c:forEach items="${page.list}" var="accout" varStatus="i">
			<thead>
			<tr>
				<th>
					<input type="checkbox" onchange="selectAll()" id="selectFlag">
					选择</th>
				<th>商品名称</th>
				<th>库存</th>
				<th>图片</th>
				<th>价格</th>
				<%--<th>创建者</th>--%>
				<th>创建时间</th>
				<%--<th>更新者</th>--%>
				<th>更新时间</th>
				<th>商品信息</th>
					<%--<shiro:hasPermission name="acout:save:edit"><th>操作</th></shiro:hasPermission>--%>
				<th>数量</th>
				<th>添加进购物车</th>
				<th>当前价格</th>
			</tr>
			</thead>
		<tbody>
				<c:forEach items="${accout.saveList}" var="save">
					<c:if test="${save.type.status==1}">
					<tr>
						<td>
							<input type="checkbox" class="myBox" onchange="updateSum()">
							<%--隐藏域--%>
							<input type="text" style="display: none" class="saveId" value="${save.id}">
						</td>
						<td>
								${save.type.name}
						</td>
						<td class="restCount">
								${save.restCount}
						</td>
						<td>
							<img src="${save.imgUrl}" style="width: 200px" alt="">
						</td>
						<td class="price">
								${save.price}
						</td>
						<td>
							<fmt:formatDate value="${save.createDate}" pattern="yyyy-MM-dd"/>
						</td>
						<td>
							<fmt:formatDate value="${save.updateDate}" pattern="yyyy-MM-dd"/>
						</td>
						<td>
								${save.remarks}
						</td>
						<td class="count">${save.count}</td>
						<td>
							<%--<input type="text" class="adjust" placeholder="在此处可以调整数值" disabled>--%>
							<button class="btn btn-link" onclick="add(this)">+</button>
							<button class="btn btn-link" onclick="mul(this)">-</button>
							<button class="btn btn-link" onclick="clearAccountById('${save.id}')">删除</button>
						</td>
						<td class="sumitem">
							元
						</td>
					</tr>
					</c:if>
				</c:forEach>
		</tbody>
			<tr>
				<td id="sum">总价:${accout.count}元</td>
			</tr>
		</c:forEach>
	</table>
	<button class="btn btn-submit" onclick="submitMyCar()">提交</button>
	<div class="pagination">${page}</div>
</body>
</html>