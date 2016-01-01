<!-- 订单详情界面 -->
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

<!--[if lt IE 7 ]> <body class="ie ie6"> <![endif]-->
<!--[if IE 7 ]> <body class="ie ie7"> <![endif]-->
<!--[if IE 8 ]> <body class="ie ie8"> <![endif]-->
<!--[if IE 9 ]> <body class="ie ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!-->

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
			location.href = "403.html";
		} else {
		}
	});
	var idVar;
	var gradeArray = new Array();
	var idStatusVar = 0;
	function getQueryString(name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
		var r = window.location.search.substr(1).match(reg);
		if (r != null)
			return unescape(r[2]);
		return null;
	}

	function loadPage() {
		idVar = Number(getQueryString("id"));
		$("#frozenBtn").text("冻结");
		$.ajax({
			url : "order/getOrderDetail",
			data : {
				id : idVar
			},
			dataType : "json",
			type : "post",
			success : function(data) {
				if (data.success) {
					$("#order_no").val(data.result.order_no);
					$("#buyer_name").val(data.result.buyerName);
					$("#saler_name").val(data.result.salerName);
					$("#pay_account").val(data.result.salerPayAccount);
					$("#total_price").val(data.result.totalPrice);
					var tempStatus = data.result.status;
					var selectStatus = document.getElementById("status");
	                for(var i=0;i<selectStatus.options.length;i++){  
	                    if(selectStatus.options[i].value==tempStatus){  
	                    	selectStatus.options[i].selected=true;  
	                        break;  
	                    }  
	                }
					var loanStatus = data.result.fangkuanZht;
					var selectLoanStatus = document.getElementById("loan_status");
	                for(var i=0;i<selectLoanStatus.options.length;i++){  
	                    if(selectLoanStatus.options[i].value==loanStatus){  
	                    	selectLoanStatus.options[i].selected=true;  
	                        break;  
	                    }  
	                }
					$("#quantity").val(data.result.quantity);
					$("#sizename").val(data.result.sizename);
					$("#height").val(data.result.height);
					$("#weight").val(data.result.weight);
					$("#waist").val(data.result.waist);
					$("#bust").val(data.result.bust);
					$("#hip").val(data.result.hip);
					$("#description").val(data.result.description);
					$("#receiver_name").val(data.result.shouhuoren);
					$("#receiver_tel").val(data.result.shouhuodianhua);
					$("#receiver_address").val(data.result.shouhuodizhi);
					$("#zip").val(data.result.youbian);
					$("#payment_code").val(data.result.paymentCode);
					$("#alipay_code").val(data.result.alipayCode);
					$("#create_time").val(data.result.rukuSj);
					$("#end_time").val(data.result.daoqiSj);
					$("#deliver_time").val(data.result.fahuoSj);
					
					var isQangDan = data.result.qiangdan;
					if (isQangDan == "0") {
						isQangDan = "否";
					} else {
						isQangDan = "是";
					}
					$("#qiangdan").val(isQangDan);
				} else {
					layer.msg("页面读取错误, 请重新刷新页面",2,{type : 1,shade : false});
				}
			}
		});
	}
	function getDate(tm) {
		return new Date(parseInt(tm)).toLocaleString();
	}
	
	/* 修改订单状态 */
	function updateOrderStatus() {
		if (confirm("你确定要修改吗?")) {
			$.ajax({
				url : "order/updateOrderStatus",
				data : {
					id : idVar,
					status : $("#status").val()
				},
				dataType : "json",
				type : "post",
				success : function(data) {
					if (data.success) {
						layer.msg("保存成功",2,{type : 1,shade : false});
					} else {
						layer.msg("保存失败, 请重新尝试",2,{type : 1,shade : false});
					}
				}
			});
		}
	}
	
	/* 修改放款状态 */
	function updateLoanStatus() {
		if (confirm("你确定要修改吗?")) {
			$.ajax({
				url : "order/updateLoanStatus",
				data : {
					id : idVar,
					status : $("#loan_status").val()
				},
				dataType : "json",
				type : "post",
				success : function(data) {
					if (data.success) {
						layer.msg("修改成功",2,{type : 1,shade : false});
					} else {
						layer.msg("修改失败, 请重新尝试",2,{type : 1,shade : false});
					}
				}
			});
		}
	}
	
</script>
<body onload="loadPage()">
	<!--<![endif]-->

	<div class="navbar">
		<div class="navbar-inner">
			<div class="container-fluid">
				<ul class="nav pull-right">

					<li id="fat-menu" class="dropdown"><a href="#" id="drop3"
						role="button" class="dropdown-toggle" data-toggle="dropdown">
							<i class="icon-user"></i> ${sessionScope.userSessionInfo.name} <i
							class="icon-caret-down"></i>
					</a>
						<ul class="dropdown-menu">
							<li><a tabindex="-1" href="admin/logout">退出</a></li>
						</ul></li>
				</ul>
				<a class="brand" href="index.jsp"><span class="first">一首小诗</span>
					<span class="second">首页</span></a>
			</div>
		</div>
	</div>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span2">
				<jsp:include page="/left.jsp"></jsp:include>
			</div>
			<div class="span10">
				<h2>
					订单详情<a href="javascript:history.go(-1);"
						style="float: right; display: block;">返回</a>
				</h2>

				<div class="well">
					<div id="myTabContent" class="tab-content">
						<div class="row-fluid">
							<div class="span12">
								<div class="row-fluid">
									<div class="span4">
										<form id="tab">
											<label>订单号</label> <input type="text" value="" id="order_no"
												class="input-xlarge" readonly="readonly"> <label>付款方</label>
											<input type="text" value="" class="input-xlarge"
												id="buyer_name" readonly="readonly"> <label>收款方</label>
											<input type="text" value="" id="saler_name"
												class="input-xlarge" readonly="readonly"> <label>支付宝账号</label>
											<input type="text" value="" id="pay_account"
												class="input-xlarge" readonly="readonly"> <label>订单总价</label>
											<input id="total_price" type="text" value=""
												class="input-xlarge" readonly="readonly"></input> <label>订单状态</label>
											<select id="status" class="input-xlarge">
												<option value="0">待付款--客户未选择</option>
												<option value="1">待付款--客户已选择</option>
												<option value="2">已取消</option>
												<!-- <option value="4">已付款</option> -->
												<option value="8">待发货</option>
												<option value="16">已买断</option>
												<option value="32">已发货</option>
												<option value="64">已收货</option>
												<!-- <option value="128">待评价</option> -->
												<option value="256">已评价</option>
												<option value="512">延迟收货</option>
											</select>
											<label>放款状态</label> 
											<select id="loan_status" class="input-xlarge">
												<option value="0">未放款</option>
												<option value="1">已放款</option>
											</select>
											 <label>商品数量</label>
											<input id="quantity" type="text" value=""
												class="input-xlarge" readonly="readonly"></input> <label>尺寸名称</label>
											<input id="sizename" type="text" value=""
												class="input-xlarge" readonly="readonly"></input> <label>身高</label>
											<input id="height" type="text" value="" class="input-xlarge"
												readonly="readonly"></input> <label>体重</label> <input
												id="weight" type="text" value="" class="input-xlarge"
												readonly="readonly"></input> <label>腰围</label> <input
												id="waist" type="text" value="" class="input-xlarge"
												readonly="readonly"></input>
												<label>胸围</label> <input type="text" value="" id="bust"
												class="input-xlarge" readonly="readonly"> 
										</form>
										<div class="btn-toolbar">
											<button class="btn btn-primary" onclick="updateOrderStatus()">
												 修改订单状态
											</button>
											<button class="btn btn-primary" onclick="updateLoanStatus()">
												 修改放款状态
											</button>
											<div class="btn-group"></div>
										</div>
									</div>
									<div class="span4">
										<form>
											<label>臀围</label>
											<input type="text" value="" class="input-xlarge" id="hip"
												readonly="readonly"> <label>描述</label> <input
												type="text" value="" id="description" class="input-xlarge"
												readonly="readonly"> <label>收货人</label> <input
												type="text" value="" id="receiver_name" class="input-xlarge"
												readonly="readonly"> <label>收货电话</label> <input
												id="receiver_tel" type="text" value="" class="input-xlarge"
												readonly="readonly"></input> <label>收货地址</label> <input
												id="receiver_address" type="text" value=""
												class="input-xlarge" readonly="readonly"></input> <label>邮编</label>
											<input id="zip" type="text" value="" class="input-xlarge"
												readonly="readonly"></input> <label>收款码</label> <input
												id="payment_code" type="text" value="" class="input-xlarge"
												readonly="readonly"></input> <label>支付宝交易号</label> <input
												id="alipay_code" type="text" value="" class="input-xlarge"
												readonly="readonly"> <label>开始时间</label> <input
												id="create_time" type="text" value="" class="input-xlarge"
												readonly="readonly"></input>
												<label>结束时间</label> <input
												id="end_time" type="text" value="" class="input-xlarge"
												readonly="readonly"></input>
												<label>发货时间</label> <input
												id="deliver_time" type="text" value="" class="input-xlarge"
												readonly="readonly"></input>
												 <label>是否是抢单</label> <input
												id="qiangdan" type="text" value="" class="input-xlarge"
												readonly="readonly"></input>
										</form>
									</div>
									<div class="span4">
										<form id="evi_form"></form>
									</div>

								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="modal small hide fade" id="myModal" tabindex="-1"
					role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">×</button>
						<h3 id="myModalLabel">冻结账户确认</h3>
					</div>
					<div class="modal-body">

						<p class="error-text">
							<i class="icon-warning-sign modal-icon"></i>你确定要冻结此用户么?
						</p>
					</div>
					<div class="modal-footer">
						<button class="btn" data-dismiss="modal" aria-hidden="true">取消</button>
						<button class="btn btn-danger" data-dismiss="modal"
							onclick="frozen_user()">确定</button>
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


