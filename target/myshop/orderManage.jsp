<!--订单管理界面-->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>衣氏百秀</title>
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
	var idArray = new Array();
	var totalPage;
	var totalPage_loaning;
	var totalPage_loaned;
	var pageSizeVar = 1;
	var currentPageVar = 1;
	var currentPageVar_loaning = 1;
	var currentPageVar_loaned = 1;
	var idArray_check = new Array();
	var totalPage_check;
	var pageSizeVar_check = 6;
	var currentPageVar_check = 1;
	var serachVar = null;
	var currentLoc = 1;
	function loadPage() {
		$.ajax({
			url : "/order/getOrderCount",
			dataType : "json",
			type : "post",
			success : function(data) {
				$("#all").text(data.allCount);
				$("#loaning").text(data.loaningCount);
			}
		});
		InitTable(1);
	}
	function getDate(tm) {
		return new Date(parseInt(tm)).toLocaleString();
	}
	function pageCallback(index, jq) {
		currentPageVar = index + 1;
		InitTable(currentPageVar);
	}
	function pageCallback_loaning(index, jq) {
		currentPageVar_loaning = index + 1;
		InitTable_loaning(currentPageVar_loaning);
	}
	function pageCallback_loaned(index, jq) {
		currentPageVar_loaned = index + 1;
		InitTable_loaned(currentPageVar_loaned);
	}
	
	function orderDetail_Click(id) {

		location.href = "orderDetail.jsp?id=" + id;
	}
	function showPagination() {
		$("#list_all_order_table").pagination(parseInt(totalPage), {
			callback : pageCallback,
			prev_text : '上一页',
			next_text : '下一页',
			items_per_page : pageSizeVar,
			num_display_entries : 5,//连续分页主体部分分页条目数  
			current_page : (currentPageVar - 1),//当前页索引  
			num_edge_entries : 2 //两侧首尾分页条目数  
		});

	}
	
	function showPagination_loaning() {
		$("#list_loaning_order_table").pagination(parseInt(totalPage_loaning), {
			callback : pageCallback_loaning,
			prev_text : '上一页',
			next_text : '下一页',
			items_per_page : pageSizeVar,
			num_display_entries : 5,//连续分页主体部分分页条目数  
			current_page : currentPageVar_loaning - 1,//当前页索引  
			num_edge_entries : 2
		//两侧首尾分页条目数  
		});
	}
	function showPagination_loaned() {
		$("#list_loaned_order_table").pagination(parseInt(totalPage_loaned), {
			callback : pageCallback_loaned,
			prev_text : '上一页',
			next_text : '下一页',
			items_per_page : pageSizeVar,
			num_display_entries : 5,//连续分页主体部分分页条目数  
			current_page : currentPageVar_loaned - 1,//当前页索引  
			num_edge_entries : 2
		//两侧首尾分页条目数  
		});
	}
	
	/* 查询全部订单 */
	function InitTable(pageIndex) {
		currentPageVar = pageIndex;
		currentLoc = 1;
		$
				.ajax({
					url : "order/getOrderList/" + currentPageVar,
					data : {
						currentPage : currentPageVar,
						pageSize : pageSizeVar,
						keyword : serachVar,
						status: $('#order_status').val()
					},
					dataType : "json",
					type : "post",
					success : function(data) {

						if (data.success) {
							$("#all_order_table").empty();
							//显示列表
							for (var i = 0; i < data.result.length; i++) {
								idArray[i] = data.result[i].id;
								var tempStatus = data.result[i].status;
								switch (tempStatus) {
								case "0":
								case "1":
									tempStatus = "待付款";
									break;
								case "2":
									tempStatus = "已取消";
									break;
								case "4":
									tempStatus = "已付款";
									break;
								case "8":
									tempStatus = "待发货";
									break;
								case "16":
									tempStatus = "已买断";
									break;
								case "32":
									tempStatus = "已发货";
									break;
								case "64":
									tempStatus = "已收货";
									break;
								case "128":
									tempStatus = "待评价";
									break;
								case "256":
									tempStatus = "已评价";
									break;
								case "512":
									tempStatus = "延迟收货";
									break;
								}
								var loanStatus = data.result[i].fangkuanZht;
								if (loanStatus == "0") {
									loanStatus = "未放款";
								} else {
									loanStatus = "已放款";
								}
								var tempid = i + 1;
								$("#all_order_table")
										.append(
												"<tr>"
														+ "<td>"
														+ tempid
														+ "</td>"
														+ "<td>"
														+ data.result[i].order_no
														+ "</td>"
														+ "<td>"
														+ data.result[i].buyerName
														+ "</td>"
														+ "<td>"
														+ data.result[i].salerName
														+ "</td>"
														+ "<td>"
														+ data.result[i].salerPayAccount
														+ "</td>"
														+ "<td>"
														+ data.result[i].totalPrice
														+ "</td>"
														+ "<td>"
														+ tempStatus
														+ "</td>"
														+ "<td>"
														+ loanStatus
														+ "</td>"
														+ "<td><a href='#' onclick='orderDetail_Click("
														+ data.result[i].id
														+ ")'>详情</a></td>"
														+ "</tr>");
							}
							//显示分布按钮							
							totalPage = data.totalPage;

							if (Number(totalPage) > pageSizeVar) {
								$("#list_all_order_table").show();
								showPagination();
							} else {
								$("#list_all_order_table").hide();
							}

							$("#error_all_order_table").hide();
						} else {
							$("#error_all_order_table").show();
						}
					}
				});
	}
	
	/* 查询待标记放款订单 */
	function InitTable_loaning(pageIndex) {
		currentPageVar_loaning = pageIndex;
		currentLoc = 2;
		$
				.ajax({
					url : "order/getOrderList/" + currentPageVar_loaning,
					data : {
						currentPage : currentPageVar_loaning,
						pageSize : pageSizeVar,
						keyword : serachVar,
						loanStatus : 0,
						status: $('#order_status').val()
					},
					dataType : "json",
					type : "post",
					success : function(data) {

						if (data.success) {
							$("#loaning_order_table").empty();
							//显示列表
							for (var i = 0; i < data.result.length; i++) {
								idArray[i] = data.result[i].id;
								var tempStatus = data.result[i].status;
								switch (tempStatus) {
								case "0":
								case "1":
									tempStatus = "待付款";
									break;
								case "2":
									tempStatus = "已取消";
									break;
								case "4":
									tempStatus = "已付款";
									break;
								case "8":
									tempStatus = "待发货";
									break;
								case "16":
									tempStatus = "已买断";
									break;
								case "32":
									tempStatus = "已发货";
									break;
								case "64":
									tempStatus = "已收货";
									break;
								case "128":
									tempStatus = "待评价";
									break;
								case "256":
									tempStatus = "已评价";
									break;
								case "512":
									tempStatus = "延迟收货";
									break;
								}
								var loanStatus = data.result[i].fangkuanZht;
								if (loanStatus == "0") {
									loanStatus = "未放款";
								} else {
									loanStatus = "已放款";
								}
								var tempid = i + 1;
								$("#loaning_order_table")
								.append(
										"<tr>"
												+ "<td>"
												+ tempid
												+ "</td>"
												+ "<td>"
												+ data.result[i].order_no
												+ "</td>"
												+ "<td>"
												+ data.result[i].buyerName
												+ "</td>"
												+ "<td>"
												+ data.result[i].salerName
												+ "</td>"
												+ "<td>"
												+ data.result[i].salerPayAccount
												+ "</td>"
												+ "<td>"
												+ data.result[i].totalPrice
												+ "</td>"
												+ "<td>"
												+ tempStatus
												+ "</td>"
												+ "<td>"
												+ loanStatus
												+ "</td>"
												+ "<td><a href='#' onclick='orderDetail_Click("
												+ data.result[i].id
												+ ")'>详情</a></td>"
												+ "</tr>");
							}
							//显示分布按钮							
							totalPage_loaning = data.totalPage;

							if (Number(totalPage) > pageSizeVar) {
								$("#list_loaning_order_table").show();
								showPagination_loaning();
							} else {
								$("#list_loaning_order_table").hide();
							}

							$("#error_loaning_order_table").hide();
						} else {
							$("#error_loaning_order_table").show();
						}
					}
				});
	}
	
	/* 查询已标记放款订单 */
	function InitTable_loaned(pageIndex) {
		currentPageVar_loaned = pageIndex;
		currentLoc = 3;
		$
				.ajax({
					url : "order/getOrderList/" + currentPageVar_loaned,
					data : {
						currentPage : currentPageVar_loaned,
						pageSize : pageSizeVar,
						keyword : serachVar,
						loanStatus : 1,
						status: $('#order_status').val()
					},
					dataType : "json",
					type : "post",
					success : function(data) {

						if (data.success) {
							$("#loaned_order_table").empty();
							//显示列表
							for (var i = 0; i < data.result.length; i++) {
								idArray[i] = data.result[i].id;
								var tempStatus = data.result[i].status;
								switch (tempStatus) {
								case "0":
								case "1":
									tempStatus = "待付款";
									break;
								case "2":
									tempStatus = "已取消";
									break;
								case "4":
									tempStatus = "已付款";
									break;
								case "8":
									tempStatus = "待发货";
									break;
								case "16":
									tempStatus = "已买断";
									break;
								case "32":
									tempStatus = "已发货";
									break;
								case "64":
									tempStatus = "已收货";
									break;
								case "128":
									tempStatus = "待评价";
									break;
								case "256":
									tempStatus = "已评价";
									break;
								case "512":
									tempStatus = "延迟收货";
									break;
								}
								var loanStatus = data.result[i].fangkuanZht;
								if (loanStatus == "0") {
									loanStatus = "未放款";
								} else {
									loanStatus = "已放款";
								}
								var tempid = i + 1;
								$("#loaned_order_table")
								.append(
										"<tr>"
												+ "<td>"
												+ tempid
												+ "</td>"
												+ "<td>"
												+ data.result[i].order_no
												+ "</td>"
												+ "<td>"
												+ data.result[i].buyerName
												+ "</td>"
												+ "<td>"
												+ data.result[i].salerName
												+ "</td>"
												+ "<td>"
												+ data.result[i].salerPayAccount
												+ "</td>"
												+ "<td>"
												+ data.result[i].totalPrice
												+ "</td>"
												+ "<td>"
												+ tempStatus
												+ "</td>"
												+ "<td>"
												+ loanStatus
												+ "</td>"
												+ "<td><a href='#' onclick='orderDetail_Click("
												+ data.result[i].id
												+ ")'>详情</a></td>"
												+ "</tr>");
							}
							//显示分布按钮							
							totalPage_loaned = data.totalPage;

							if (Number(totalPage_loaned) > pageSizeVar) {
								$("#list_loaned_order_table").show();
								showPagination_loaning();
							} else {
								$("#list_loaned_order_table").hide();
							}

							$("#error_loaned_order_table").hide();
						} else {
							$("#error_loaned_order_table").show();
						}
					}
				});
	}
	
	
	/* 根据订单状态查询列表信息 */
	function getListByOrderStatus(){
		switch (currentLoc) {
		case 1:
			InitTable(1);
			break;
		case 2:
			InitTable_loaning(1);
			break;
		case 3:
			InitTable_loaned(1);
			break;
		default:
			break;
		}
	}
	$(function() {
		$('#input_search').bind('keypress', function(event) {
			if (event.keyCode == "13") {
				button_search();
			}
		});
	});
	function memberDetail_Click_check(id) {

		location.href = "memberPendingDet.jsp?id=" + id;
	}
	
	
	
	function button_search() {
		serachVar = $("#input_search").attr("value");
		switch (currentLoc) {
		case 1:
			InitTable(1);
			break;
		case 2:
			InitTable_loaning(1);
			break;
		case 3:
			InitTable_loaned(1);
			break;
		default:
			break;
		}
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
						<form class="form-inline" onsubmit="return false;">
							<font size="4">总数:<span id="all"></span> 待标记付款订单数:<span
								id="loaning"></span></font>
							<button class="btn" type="button" style="float: right"
								onclick="button_search()">
								 <i class="icon-search" style="padding-right: 5px;"></i>搜索
							</button>
							<input class="input-xlarge"
								style="float: right; margin-right: 10px" placeholder="请输入订单号或用户名"
								id="input_search" type="text">
							<select id="order_status" style="float: right;margin-right: 10px;" onchange="getListByOrderStatus()">
								<option value="-1">全部</option>
								<option value="0">待付款</option>
								<option value="2">已取消</option>
								<option value="8">待发货</option>
								<option value="16">已买断</option>
								<option value="32">已发货</option>
								<option value="64">待评价</option>
								<option value="256">已评价</option>
								<option value="512">延时收货</option>
							</select>
						</form>
						<div class="well">
							<ul class="nav nav-tabs">
								<li><a href="#tab1" data-toggle="tab">全部</a></li>
								<li><a href="#tab2" onclick="InitTable_loaning(1)"
									data-toggle="tab">待标记放款订单</a></li>
								<li><a href="#tab3" onclick="InitTable_loaned(1)"
									data-toggle="tab">已标记放款订单</a></li>
							</ul>
							<div id="myTabContent" class="tab-content">
								<div class="tab-pane active in" id="tab1">
									<table class="table table-hover">
										<thead>
											<tr>
												<th>序号</th>
												<th>订单号</th>
												<th>付款方</th>
												<th>收款方</th>
												<th>支付宝账号</th>
												<th>订单总价</th>
												<th>订单状态</th>
												<th>标记放款状态</th>
												<th></th>
											</tr>
										</thead>
										<tbody id="all_order_table">
										</tbody>
									</table>
									<p id="error_all_order_table"
										style="color: red; display: none;">没有相关数据,请查证后刷新.</p>
									<div id="list_all_order_table" class="quotes"
										style="display: none"></div>
								</div>
								<div class="tab-pane fade" id="tab2">
									<table class="table table-hover">
										<thead>
											<tr>
												<th>序号</th>
												<th>订单号</th>
												<th>付款方</th>
												<th>收款方</th>
												<th>支付宝账号</th>
												<th>订单总价</th>
												<th>订单状态</th>
												<th>标记放款状态</th>
												<th></th>
											</tr>
										</thead>
										<tbody id="loaning_order_table">
										</tbody>
									</table>
									<p id="error_loaning_order_table"
										style="color: red; display: none;">没有相关数据,请查证后刷新.</p>
									<div id="list_loaning_order_table" class="quotes"
										style="display: none"></div>
								</div>

								<div class="tab-pane fade" id="tab3">
									<table class="table table-hover">
										<thead>
											<tr>
												<th>序号</th>
												<th>订单号</th>
												<th>付款方</th>
												<th>收款方</th>
												<th>支付宝账号</th>
												<th>订单总价</th>
												<th>订单状态</th>
												<th>标记放款状态</th>
												<th></th>
											</tr>
										</thead>
										<tbody id="loaned_order_table">
										</tbody>
									</table>
									<p id="error_loaned_order_table"
										style="color: red; display: none;">没有相关数据,请查证后刷新.</p>
									<div id="list_loaned_order_table" class="quotes"
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


