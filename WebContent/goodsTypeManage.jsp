<!--商品类型管理界面-->
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
		InitTable(1);
	}
	function getDate(tm) {
		return new Date(parseInt(tm)).toLocaleString();
	}
	function pageCallback(index, jq) {
		currentPageVar = index + 1;
		InitTable(currentPageVar);
	}
	/* 跳转到商品类型详情 */
	function goodsTypeDetail(id) {

		location.href = "goodsTypeDetail.jsp?id=" + id;
	}
	function showPagination() {
		$("#list_findAllGoodsType").pagination(parseInt(totalPage), {
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
			url : "goodsType/getGoodsTypeList/" + currentPageVar,
			data : {
				currentPage : currentPageVar,
				pageSize : pageSizeVar,
				keyword : serachVar
			},
			dataType : "json",
			type : "post",
			success : function(data) {

				if (data.success) {
					$("#table_findAllGoodsType").empty();
					//显示列表
					for (var i = 0; i < data.result.length; i++) {
						idArray[i] = data.result[i].id;
						var tempid = i + 1;
						$("#table_findAllGoodsType").append(
								"<tr>'" 
										+ "<td>" + tempid + "</td>" 
										+ "<td>"
										+ data.result[i].name 
										+ "</td>"
										+ "<td>" 
										+ data.result[i].x
										+ "</td>"
										+ "<td><a href='#' onclick='goodsTypeDetail("
										+ data.result[i].id
										+ ")'>详情</a></td>"
										+ "</tr>");
					}
					//显示分布按钮							
					totalPage = data.totalPage;

					if (Number(totalPage) > pageSizeVar) {
						$("#list_findAllGoodsType").show();
						showPagination();
					} else {
						$("#list_findAllGoodsType").hide();
					}

					$("#error_findAllGoodsType").hide();
				} else {
					$("#error_findAllGoodsType").show();
				}
			}
		});
	}
	$(function() {
		$('#input_search').bind('keypress', function(event) {
			if (event.keyCode == "13") {
				button_search();
			}
		});


	});

	/**
	 * 商品类型新增
	 */
	function addGoodsType() {
		var name = $("#name").val();
		var rate = $("#rate").val();
		if(name === ""){
			layer.msg("请输入名称", 2, {
				type : 1,
				shade : false
			});
			return false;
		}else if(name.length > 12){
			layer.msg("名称最长为12位, 请重新输入", 2, {
				type : 1,
				shade : false
			});
			return false;
		}
		if(Number(rate) <= 0 || isNaN(Number(rate))){
			layer.msg("难度系数要求为大于0的数字, 请重新输入", 2, {
				type : 1,
				shade : false
			});
			return false;
		}
		
		$.ajax({
			url : '/goodsType/addGoodsType',
			type : "POST",
			dataType : "json",
			data : {
				name : name,
				X : rate
			},
			success : function(data) {
				if (data.success) {
					layer.msg("新增成功", 2, {
						type : 1,
						shade : false
					});
					InitTable(1);
					$('#addGoodsType').modal('hide');
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
	function button_search() {
		serachVar = $("#input_search").attr("value");
		InitTable(1);
	}
	$(function(){
		$('#addGoodsType').on('show', function(){
			$('#name').val("");
			$('#rate').val("");
		});
	});
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
							<font size="4"><button data-toggle="modal"
									data-target="#addGoodsType" data-backdrop="true" id="add"
									type="button" class="btn"><i class="icon-plus" style="padding-right: 5px;"></i>新增</button></font>
							<button class="btn" type="button" style="float: right"
								onclick="button_search()"><i class="icon-search" style="padding-right: 5px;"></i>搜索</button>
							<input class="input-xlarge"
								style="float: right; margin-right: 10px" placeholder="请输入商品类型名称"
								id="input_search" type="text">
						</form>
						<div class="well">
							<div id="myTabContent" class="tab-content">
								<div class="tab-pane active in" id="home">
									<table class="table table-hover">
										<thead>
											<tr>
												<th>序号</th>
												<th>名称</th>
												<th>难度系数</th>
												<th></th>
											</tr>
										</thead>
										<tbody id="table_findAllGoodsType">
										</tbody>
									</table>
									<p id="error_findAllGoodsType"
										style="color: red; display: none;">没有相关数据,请查证后刷新.</p>
									<div id="list_findAllGoodsType" class="quotes"
										style="display: none"></div>
								</div>
							</div>
						</div>
						<div class="modal small hide fade" id="addGoodsType" tabindex="-1"
							role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal"
									aria-hidden="true">×</button>
								<h3 id="myModalLabel">新增商品类型</h3>
							</div>
							<div class="modal-body">

								<form id="tab">
									<label>名称</label> <input type="text" value="" id="name"
										class="input-xlarge"> <label>难度系数</label> <input
										type="text" value="" id="rate" class="input-xlarge">
								</form>
							</div>
							<div class="modal-footer">
								<button class="btn" data-dismiss="modal" aria-hidden="true">取消</button>
								<button class="btn btn-danger" onclick="addGoodsType()">确定</button>
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
	<script src="js/layer/layer.min.js"></script>
</body>
</html>