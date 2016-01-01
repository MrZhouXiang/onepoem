<!--客户统计功能界面-->
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
<link rel="stylesheet" href="/css/bootstrap-datetimepicker.css" />
<link rel="stylesheet" href="/css/style.css" type="text/css"
	media="screen" />
<link rel="stylesheet" href="/css/style1.css" type="text/css"
	media="screen" />
<!-- Demo page code -->

<style type="text/css">
/* @media (min-width: 1070px){
	.mladdress{
		margin-left:345px;
	}
} */

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
.fixed {
	position: fixed;
	_position: absolute;
	bottom: 0px;
	left: 0px;
	width: 100%;
	height: 104px;
	background: #333333;
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
	var idArray_check = new Array();
	var totalPage_check;
	var pageSizeVar_check = 6;
	var currentPageVar_check = 1;
	var serachVar = null;
	var currentLoc = 1;
	var keyword1;
	var keyword2;
	var keyword3;
	var keyword4;
	function getDate(tm) {
		return new Date(parseInt(tm)).toLocaleString();
	}
	function pageCallback(index, jq) {
		currentPageVar = index + 1;
	}

	/* 获取查询条件 */
	function getKeyword() {
		var ul1 = document.getElementById("nav");
		var lis1 = ul1.getElementsByTagName("li");
		var keyword4;
		var id1;
		for (var i = 0; i < lis1.length; i++) {
			lis1[i].onclick = function() {
				id1 = this.id;
				if(id1.indexOf("keyword3") > -1){
					keyword3 = id1.split("-")[1];
					$('#keyword3').val(keyword3);
					getDataByTabLoc();
					console.log("keyword3: "+ keyword3);
				}
			}
		}
		
		var ul2 = document.getElementById("case_is_international_academy");
		var lis2 = ul2.getElementsByTagName("li");
		var keyword4;
		var id2;
		for (var i = 0; i < lis2.length; i++) {
			lis2[i].onclick = function() {
				id2 = this.id;
				if(id2.indexOf("keyword4") > -1){
					keyword4 = id2.split("-")[1];
					$('#keyword4').val(keyword4);
					getDataByTabLoc();
					console.log("keyword4: "+ keyword4);
				}
			}
		}
		
		var ul3 = document.getElementById("company_type");
		var lis3 = ul3.getElementsByTagName("li");
		var keyword4;
		var id3;
		for (var i = 0; i < lis3.length; i++) {
			lis3[i].onclick = function() {
				id3 = this.id;
				if(id3.indexOf("keyword4") > -1){
					keyword4 = id3.split("-")[1];
					$('#keyword4').val(keyword4);
					getDataByTabLoc();
					console.log("keyword4: "+ keyword4);
				}
			}
		}
		$('#province').on('click', function() {
			keyword3 = $('#province').val();
			console.log("keyword3: "+ keyword3);
			getDataByTabLoc();
		});
		$('#city').on('click', function() {
			keyword3 = $('#city').val();
			console.log("keyword3: "+ keyword3);
			getDataByTabLoc();
		});
		$('#dist').on('click', function() {
			keyword3 = $('#dist').val();
			console.log("keyword3: "+ keyword3);
			getDataByTabLoc();
		});
	}
	
	/* 查询用户量相关数据统计 */
	function getUserDataCount() {
		$.ajax({
			url : "buyerstatistics/getUserDataCount/",
			data : {
				keyword1 : keyword1,
				keyword2 : keyword2,
				keyword3 : keyword3,
				keyword4 : keyword4
			},
			dataType : "json",
			type : "post",
			success : function(data) {

				if (data.success) {
					$("#userDataCount").empty();
					//显示列表
					$("#userDataCount").append(
							"<tr>" + "<td>" + data.registerUserCount + "</td>"
									+ "<td>" + data.onlineUserCount + "</td>"
									+ "<td>" + data.vipUserCount + "</td>"
									+ "<td>" + data.newUserCount + "</td>"
									+ "<td>" + data.loseUserCount + "</td>"
									+ "</tr>");
				}
			}
		});
	}
	/* 查询设计师相关数据统计 */
	function getDesignerDataCount() {
		$.ajax({
			url : "buyerstatistics/getDesignerDataCount/",
			data : {
				keyword1 : keyword1,
				keyword2 : keyword2,
				keyword3 : keyword3,
				keyword4 : keyword4
			},
			dataType : "json",
			type : "post",
			success : function(data) {

				if (data.success) {
					$("#designerDataCount").empty();
					//显示列表
					$("#designerDataCount").append(
							"<tr>" + "<td>" + data.designerTradeAmount
									+ "</td>" + "<td>" + data.designerBillCount
									+ "</td>" + "</tr>");
				}
			}
		});
	}
	/* 查询用户量相关数据统计 */
	function getUserBillDataCount() {
		$.ajax({
			url : "buyerstatistics/getUserBillDataCount/",
			data : {
				keyword1 : keyword1,
				keyword2 : keyword2,
				keyword3 : keyword3,
				keyword4 : keyword4
			},
			dataType : "json",
			type : "post",
			success : function(data) {

				if (data.success) {
					$("#userBillDataCount").empty();
					//显示列表
					$("#userBillDataCount").append(
							"<tr>" + "<td>" + data.userBillCount + "</td>"
									+ "<td>" + data.userSuccessBillCount
									+ "</td>" + "</tr>");
				}
			}
		});
	}
	/* 查询订单相关数据统计 */
	function getOrderDataCount() {
		$.ajax({
			url : "buyerstatistics/getOrderDataCount/",
			data : {
				keyword1 : keyword1,
				keyword2 : keyword2,
				keyword3 : keyword3,
				keyword4 : keyword4
			},
			dataType : "json",
			type : "post",
			success : function(data) {

				if (data.success) {
					$("#orderDataCount").empty();
					//显示列表
					$("#orderDataCount").append(
							"<tr>" + "<td>" + data.cancelOrderCount + "</td>"
									+ "<td>" + data.successOrderCount + "</td>"
									+ "</tr>");
				}
			}
		});
	}
	/* 查询加工单相关数据统计 */
	function getWorksheetDataCount() {
		$.ajax({
			url : "buyerstatistics/getWorksheetDataCount/",
			data : {
				keyword1 : keyword1,
				keyword2 : keyword2,
				keyword3 : keyword3,
				keyword4 : keyword4
			},
			dataType : "json",
			type : "post",
			success : function(data) {

				if (data.success) {
					$("#worksheetDataCount").empty();
					//显示列表
					$("#worksheetDataCount").append(
							"<tr>" + "<td>" + data.worksheetTradeAmount
									+ "</td>" + "<td>"
									+ data.worksheetOrderCount + "</td>"
									+ "</tr>");
				}
			}
		});
	}

	/* 查询设计稿买断相关数据统计 */
	function getBuyoutDataCount() {
		$.ajax({
			url : "buyerstatistics/getBuyoutDataCount/",
			data : {
				keyword1 : keyword1,
				keyword2 : keyword2,
				keyword3 : keyword3,
				keyword4 : keyword4
			},
			dataType : "json",
			type : "post",
			success : function(data) {

				if (data.success) {
					$("#buyoutDataCount").empty();
					//显示列表
					$("#buyoutDataCount").append(
							"<tr>" + "<td>" + data.buyoutTradeAmount + "</td>"
									+ "<td>" + data.buyoutOrderCount + "</td>"
									+ "</tr>");
				}
			}
		});
	}
	/* 查询评论和赞相关数据统计 */
	function getPraiseAndDiscussDataCount() {
		$.ajax({
			url : "buyerstatistics/getPraiseAndDiscussDataCount/",
			data : {
				keyword1 : keyword1,
				keyword2 : keyword2,
				keyword3 : keyword3,
				keyword4 : keyword4
			},
			dataType : "json",
			type : "post",
			success : function(data) {

				if (data.success) {
					$("#praiseAndDiscussDataCount").empty();
					//显示列表
					$("#praiseAndDiscussDataCount").append(
							"<tr>" + "<td>" + data.praiseCount + "</td>"
									+ "<td>" + data.discussCount + "</td>"
									+ "</tr>");
				}
			}
		});
	}

	/* TAB栏切换 */
	function switchTab() {

		$('#user_count').on('click', function() {
			currentLoc = 1;
			getUserDataCount();
		});

		$('#designer').on('click', function() {
			currentLoc = 2;
			getDesignerDataCount();
		});

		$('#user_bill').on('click', function() {
			currentLoc = 3;
			getUserBillDataCount();
		});

		$('#order').on('click', function() {
			currentLoc = 4;
			getOrderDataCount();
		});

		$('#worksheet').on('click', function() {
			currentLoc = 5;
			getWorksheetDataCount();
		});

		$('#buyout_trade').on('click', function() {
			currentLoc = 6;
			getBuyoutDataCount();
		});

		$('#praise_discuss').on('click', function() {
			currentLoc = 7;
			getPraiseAndDiscussDataCount();
		});
	}
	/* 根据TAB位置查询数据  */
	function getDataByTabLoc() {
		keyword1 = $("#startDateField").val();
		keyword2 = $("#endDateField").val();

		switch (currentLoc) {
		case 1:
			getUserDataCount();
			break;
		case 2:
			getDesignerDataCount();
			break;
		case 3:
			getUserBillDataCount();
			break;
		case 4:
			getOrderDataCount();
			break;
		case 5:
			getWorksheetDataCount();
			break;
		case 6:
			getBuyoutDataCount();
			break;
		case 7:
			getPraiseAndDiscussDataCount();
			break;
		default:
			break;
		}
	}

	/* 初始化时间选择控件 */
	function initDatePicker() {
		$('#startDatePicker').datetimepicker({
			language : 'zh-CN',
			weekStart : 1,
			todayBtn : 1,
			autoclose : 1,
			todayHighlight : 1,
			startView : 2,
			minView : 2,
			forceParse : 0,
			pickerPosition : "bottom-right"
		}).on('changeDate', function(ev) {
			getDataByTabLoc();
		});
		$('#endDatePicker').datetimepicker({
			language : 'zh-CN',
			weekStart : 1,
			todayBtn : 1,
			autoclose : 1,
			todayHighlight : 1,
			startView : 2,
			minView : 2,
			forceParse : 0,
			pickerPosition : "bottom-right"
		}).on('changeDate', function(ev) {
			getDataByTabLoc();
		});
	}
	
	/**
	 * 表单内容验证
	 */
	function formValidate(){
	    $("#endDate").off("blur").on("blur",function(){
	        var val = $("#startDate").val();
	        if(val === ""){
	        	layer.msg("请先选择开始时间",2,{type:1,shade:false});
	        }
	    });
	    
	    $("#keyword3").off("blur").on("blur",function(){
	        var startDate = $("#startDate").val();
	        var endDate = $("#endDate").val();
	        if(startDate === "" && endDate === ""){
	        	layer.msg("请先选择开始时间和结束时间",2,{type:1,shade:false});
	        }
	    });
	    
	    $("#keyword4").off("blur").on("blur",function(){
	        var val = $("#keyword3").val();
	        if(val === ""){
	        	layer.msg("请先选择筛选条件二",2,{type:1,shade:false});
	        }
	    });
	}

	//注册函数
	$(function() {
		switchTab();
		initDatePicker();
		getKeyword();
		$("#address").citySelect({  
		    prov:"浙江", //省份 
		    city:"宁波", //城市 
		    nodata:"none" //当子集无数据时，隐藏select 
		});  
		formValidate();
	});
</script>
<body onload="getUserDataCount()">
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
						<div class="left row" style="float:left;margin-left: 10px;margin-right:50px;">
							<div class="input-group date form_date col-sm-10"
								id="startDatePicker" data-date="" data-date-format="yyyy-mm-dd"
								data-link-field="startDateField" data-link-format="yyyy-mm-dd">
								<label class="col-sm-2 control-label" for="startDate">开始时间:</label>
								<input class="form-control" id="startDate" size="16" type="text">
								<span class="input-group-addon"><span
									class="glyphicon glyphicon-remove"></span></span> <span
									class="input-group-addon"><span
									class="glyphicon glyphicon-calendar"></span></span>
							</div>
							<span class="help-block" style="padding-left: 115px;"></span> <input
								type="hidden" id="startDateField" name="startDate" value="" />
							<div class="input-group date form_date col-sm-10"
								id="endDatePicker" data-date="" data-date-format="yyyy-mm-dd"
								data-link-field="endDateField" data-link-format="yyyy-mm-dd">
								<label class="col-sm-2 control-label" for="endDate">结束时间:
								</label> <input class="form-control" id="endDate" size="16" type="text">
								<span class="input-group-addon"><span
									class="glyphicon glyphicon-remove"></span></span> <span
									class="input-group-addon"><span
									class="glyphicon glyphicon-calendar"></span></span>
							</div >
							<span class="help-block" style="padding-left: 115px;"></span> <input
								type="hidden" id="endDateField" name="endDate" value="" />
						</div>
						<div class="wrap_all" style="height: 65px; width: 520px;float:left;">
									<div id="top" class="navwrap">
										<ul id="nav">
											<li class="page_item"><a href="#">筛选条件二</a>
												<ul class='children'>
													<li class="page_item"><a href="#">身份</a>
														<ul class='children' id="identity">
															<li id="keyword3-学生" class="page_item"><a href="#">学生</a></li>
															<li id="keyword3-教师" class="page_item"><a href="#">教师</a></li>
															<li id="keyword3-手工裁缝" class="page_item"><a href="#">手工裁缝</a></li>
															<li id="keyword3-高级定制" class="page_item"><a
																href="#">高级定制</a></li>
															<li id="keyword3-时装大师" class="page_item"><a href="#">时装大师</a></li>
															<li id="keyword3-企业代表" class="page_item"><a href="#">企业代表</a></li>
														</ul></li>
													<li class="page_item" id="belong"><a href="#">所属</a>
														<ul class='children'>
															<li id="keyword3-纺织学院" class="page_item"><a href="#">纺织学院</a></li>
															<li id="keyword3-灵桥市场" class="page_item"><a
																href="#">灵桥市场</a></li>
															<li id="keyword3-西门街商业区" class="page_item"><a
																href="#">西门街商业区</a></li>
															<li id="keyword3-和义大道商业区" class="page_item"><a
																href="#">和义大道商业区</a></li>
														</ul></li>
												</ul></li>
										</ul>
										<input class="form-control" id="keyword3" size="16" type="text" readonly="readonly" style="width:315px;" />
									</div>
									<!--end navwrap-->
								<!--end top-->
								
								<div class="form-group" >
                                    <div class="col-md-9">
                                        <div id="address" class="form-inline">
                                            <select id="province" class="prov form-control"></select>
                                            <select id="city" class="city form-control" disabled="disabled"></select>
                                            <select id="dist" class="dist form-control" disabled="disabled"></select>
                                        </div>
                                    </div>
                            </div>
                            
							</div>
							
							<div class="wrap_all" style="height: 65px; width: 420px;float:left;">
								<div id="top">
									<div class="navwrap">
										<ul id="nav">
											<li class="page_item"><a href="#">筛选条件三</a>
												<ul class='children' id="case_is_international_academy">
													<li class="page_item"><a href="#">是否为国际学院学生</a>
														<ul class='children' id="identity">
															<li id="keyword4-是" class="page_item"><a href="#">是</a></li>
															<li id="keyword4-否" class="page_item"><a href="#">否</a></li>
														</ul></li>
													<li class="page_item" id="belong"><a href="#">企业类型</a>
														<ul class='children' id="company_type">
															<li id="keyword4-外贸企业" class="page_item"><a href="#">外贸企业</a></li>
															<li id="keyword4-服装厂" class="page_item"><a
																href="#">服装厂</a></li>
															<li id="keyword4-品牌服装" class="page_item"><a
																href="#">品牌服装</a></li>
															<li id="keyword4-国际品牌服装" class="page_item"><a
																href="#">国际品牌服装</a></li>
														</ul></li>
												</ul></li>
										</ul>
										<input class="form-control" id="keyword4" size="16" type="text" readonly="readonly">
									</div>
									<!--end navwrap-->
								</div>
								<!--end top-->
							</div>
							
                            
						</form>
						<div style="clear:both;">
						</div>
						<div class="well"  style="margin-top:10px;">
							<ul class="nav nav-tabs">
								<li id="user_count"><a href="#tab1" data-toggle="tab">用户数量</a></li>
								<li id="designer"><a href="#tab2" data-toggle="tab">设计师</a></li>
								<li id="user_bill"><a href="#tab3" data-toggle="tab">用户发单</a></li>
								<li id="order"><a href="#tab4" data-toggle="tab">订单</a></li>
								<li id="worksheet"><a href="#tab5" data-toggle="tab">加工单</a></li>
								<li id="buyout_trade"><a href="#tab6" data-toggle="tab">设计稿</a></li>
								<li id="praise_discuss"><a href="#tab7" data-toggle="tab">点赞与评论</a></li>
							</ul>
							<div id="myTabContent" class="tab-content">
								<div class="tab-pane active in" id="tab1">
									<table class="table table-hover">
										<thead>
											<tr>
												<th>注册用户数量</th>
												<th>上线用户数量</th>
												<th>付费用户数量</th>
												<th>新增用户数量</th>
												<th>流失用户数量</th>
											</tr>
										</thead>
										<tbody id="userDataCount">
										</tbody>
									</table>
									<p id="error_userDataCount" style="color: red; display: none;">没有相关数据,请查证后刷新.</p>
									<div id="list_userDataCount" class="quotes"
										style="display: none"></div>
								</div>
								<div class="tab-pane fade" id="tab2">
									<table class="table table-hover">
										<thead>
											<tr>
												<th>设计师总交易额</th>
												<th>设计师总发单数</th>
											</tr>
										</thead>
										<tbody id="designerDataCount">
										</tbody>
									</table>
									<p id="error_designerDataCount"
										style="color: red; display: none;">没有相关数据,请查证后刷新.</p>
									<div id="list_designerDataCount" class="quotes"
										style="display: none"></div>
								</div>

								<div class="tab-pane fade" id="tab3">
									<table class="table table-hover">
										<thead>
											<tr>
												<th>用户发单数量</th>
												<th>用户发单成交数量</th>
											</tr>
										</thead>
										<tbody id="userBillDataCount">
										</tbody>
									</table>
									<p id="error_userBillDataCount"
										style="color: red; display: none;">没有相关数据,请查证后刷新.</p>
									<div id="list_userBillDataCount" class="quotes"
										style="display: none"></div>
								</div>
								<div class="tab-pane fade" id="tab4">
									<table class="table table-hover">
										<thead>
											<tr>
												<th>订单退货数量</th>
												<th>订单成功交易数量</th>
											</tr>
										</thead>
										<tbody id="orderDataCount">
										</tbody>
									</table>
									<p id="error_orderDataCount" style="color: red; display: none;">没有相关数据,请查证后刷新.</p>
									<div id="list_orderDataCount" class="quotes"
										style="display: none"></div>
								</div>
								<div class="tab-pane fade" id="tab5">
									<table class="table table-hover">
										<thead>
											<tr>
												<th>加工单交易额</th>
												<th>加工单交易数量</th>
											</tr>
										</thead>
										<tbody id="worksheetDataCount">
										</tbody>
									</table>
									<p id="error_worksheetDataCount"
										style="color: red; display: none;">没有相关数据,请查证后刷新.</p>
									<div id="list_worksheetDataCount" class="quotes"
										style="display: none"></div>
								</div>
								<div class="tab-pane fade" id="tab6">
									<table class="table table-hover">
										<thead>
											<tr>
												<th>设计稿买断额</th>
												<th>设计稿买断数量</th>
											</tr>
										</thead>
										<tbody id="buyoutDataCount">
										</tbody>
									</table>
									<p id="error_buyoutDataCount"
										style="color: red; display: none;">没有相关数据,请查证后刷新.</p>
									<div id="list_buyoutDataCount" class="quotes"
										style="display: none"></div>
								</div>
								<div class="tab-pane fade" id="tab7">
									<table class="table table-hover">
										<thead>
											<tr>
												<th>总点赞数</th>
												<th>总评论数</th>
											</tr>
										</thead>
										<tbody id="praiseAndDiscussDataCount">
										</tbody>
									</table>
									<p id="error_praiseAndDiscussDataCount"
										style="color: red; display: none;">没有相关数据,请查证后刷新.</p>
									<div id="list_praiseAndDiscussDataCount" class="quotes"
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
	<script src="/js/vendor/bootstrap-datetimepicker.js"></script>
	<script src="/js/vendor/bootstrap-datetimepicker.zh-CN.js"></script>
	<script src="/js/vendor/jquery.cityselect.js"></script>
	<script src="js/layer/layer.min.js"></script>
	
</body>
</html>


