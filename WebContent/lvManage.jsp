<!--细分等级列表-->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>一首小诗</title>
<meta content="IE=edge,chrome=1" http-equiv="X-UA-Compatible">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<link rel="stylesheet" type="text/css"
	href="lib/bootstrap/css/bootstrap.css">

<link rel="stylesheet" type="text/css" href="stylesheets/theme.css">
<link rel="stylesheet" href="lib/font-awesome/css/font-awesome.css">

<script src="lib/jquery-1.8.1.min.js" type="text/javascript"></script>

<script src="lib/jquery.pagination.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="stylesheets/pagination.css">
<!-- Demo page code -->

<style type="text/css">
#line-chart {
	height: 300px;
	width: 800px;
	margin: 0px auto;
	margin-top: 1em;
}

.brand {
	font-family: georgia, serif;
}

.brand .first {
	color: #ccc;
	font-style: italic;
}

.brand .second {
	color: #fff;
	font-weight: bold;
}
</style>

<!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
      <script src="javascripts/html5.js"></script>
    <![endif]-->

<!-- Le fav and touch icons -->
<link rel="shortcut icon" href="../assets/ico/favicon.ico">
<link rel="apple-touch-icon-precomposed" sizes="144x144"
	href="../assets/ico/apple-touch-icon-144-precomposed.png">
<link rel="apple-touch-icon-precomposed" sizes="114x114"
	href="../assets/ico/apple-touch-icon-114-precomposed.png">
<link rel="apple-touch-icon-precomposed" sizes="72x72"
	href="../assets/ico/apple-touch-icon-72-precomposed.png">
<link rel="apple-touch-icon-precomposed"
	href="../assets/ico/apple-touch-icon-57-precomposed.png">
</head>

<script type="text/javascript">
	function logout() {
		$.ajax({
			url : "admin/logout",
			dataType : "json",
			type : "post",
			success : function(data) {
				console.log(data);
			}
		});
	}

	var tempSession = '${sessionScope.userSessionInfo.name}';
	$(function() {
		if (tempSession == null || tempSession == "") {
			location.href="403.html";
		} else {

		}
	});
	var idVar;
	var idArray = new Array();
	var totalPage;
	var pageSizeVar = 1;
	var currentPageVar = 1;
	var idArray_lock = new Array();
	var totalPage_lock;
	var pageSizeVar_lock = 6;
	var currentPageVar_lock = 1;
	var serachVar = null;
	function loadPage() {
		idVar = Number(getQueryString("id"));
		InitTable(1);
	}
	function getQueryString(name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
		var r = window.location.search.substr(1).match(reg);
		if (r != null)
			return unescape(r[2]);
		return null;
	}
	function getDate(tm) {
		return new Date(parseInt(tm)).toLocaleString();
	}
	function pageCallback(index, jq) {
		currentPageVar = index + 1;
		InitTable(currentPageVar);
	}
	
	/* 跳转到细分等级详情 */	
	function lvDetail_Click(id,userId,madeTypeId) {
		location.href = "lvDetail.jsp?id=" + id + "&userId=" + userId + "&madeTypeId=" + madeTypeId;
	}
	function showPagination() {
		$("#list_findActiveUser").pagination(parseInt(totalPage), {
			callback : pageCallback,
			prev_text : '上一页',
			next_text : '下一页',
			items_per_page : pageSizeVar,
			num_display_entries : 5,//连续分页主体部分分页条目数  
			current_page : (currentPageVar - 1),//当前页索引  
			num_edge_entries : 2
		//两侧首尾分页条目数  
		});

	}
	function InitTable(pageIndex) {
		currentPageVar = pageIndex;
		
		$.ajax({
			url : "/user/getUserLvList",
			dataType : "json",
			type : "post",
			data : {
				userId : idVar
			},
			success : function(data) {
						if (data.success) {
							$("#table_findAllUser").empty();
							//显示列表
							for (var i = 0; i < data.result.length; i++) {
								idArray[i] = data.result[i].id;
								var tempid = i + 1;
								$("#table_findAllUser")
										.append(
												"<tr>'"
														+ "<td>"
														+ tempid
														+ "</td>"
														+ "<td>"
														+ data.result[i].zhizuoLxName
														+ "</td>"
														+ "<td>"
														+ data.result[i].jingyan
														+ "</td>"
														+ "<td>"
														+ data.result[i].dengji
														+ "</td>"
														+ "<td>"
														+ "<td><a href='#' onclick='lvDetail_Click("
														+ data.result[i].id + "," + idVar + "," + data.result[i].zhizuoLxID
														+ ")'>详情</a></td>"
														+ "</tr>");
							}
							//显示分布按钮							
							totalPage = data.totalPage;

							if (Number(totalPage) > pageSizeVar) {
								$("#list_findActiveUser").show();
								showPagination();
							} else {
								$("#list_findActiveUser").hide();
							}

							$("#error_findActiveUser").hide();
						} else {
							$("#error_findActiveUser").show();
						}
					}
				});
	}
	function pageCallback_lock(index, jq) {

		currentPageVar_lock = index + 1;
		InitTable_lock(currentPageVar_lock);

	}
	function memberDetail_Click_check(id) {

		location.href = "memberPendingDet.jsp?id=" + id;
	}
	function showPagination_check() {
		$("#list_findCheckingUser").pagination(parseInt(totalPage_lock), {
			callback : pageCallback_lock,
			prev_text : '上一页',
			next_text : '下一页',
			items_per_page : pageSizeVar_lock,
			num_display_entries : 5,//连续分页主体部分分页条目数  
			current_page : currentPageVar_lock - 1,//当前页索引  
			num_edge_entries : 2
		//两侧首尾分页条目数  
		});
	}
	
</script>
<body onload="loadPage()">
	<!-- 左边栏 -->
	<jsp:include page="/head.jsp"></jsp:include>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span12">
				<div class="row-fluid">
					<!-- 左边栏 -->
					<div class="span2">
						<jsp:include page="/left.jsp"></jsp:include>
					</div>
					<div class="span10">
					<h2>
					修改等级
					<a href="javascript:history.go(-1);"
						style="float: right; display: block;">返回</a>
					</h2>
						<div class="well">
							<div id="myTabContent" class="tab-content">
								<div class="tab-pane active in" id="home">
									<table class="table table-hover">
										<thead>
											<tr>
												<th>序号</th>
												<th>制作类型</th>
												<th>经验值</th>
												<th>等级</th>
												<th></th>
											</tr>
										</thead>
										<tbody id="table_findAllUser">
										</tbody>
									</table>
									<p id="error_findActiveUser" style="color: red; display: none;">没有相关数据,请查证后刷新.</p>
									<div id="list_findActiveUser" class="quotes"
										style="display: none"></div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>

	</div>

	<footer>
		<jsp:include page="/footer.jsp"></jsp:include>
	</footer>




	<!-- Le javascript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script src="lib/bootstrap/js/bootstrap.js"></script>
</body>
</html>


