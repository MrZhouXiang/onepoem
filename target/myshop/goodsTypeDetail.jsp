<!-- 商品类型详情界面 -->
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
		$.ajax({
					url : "goodsType/getGoodsTypeDetail",
					data : {
						id : idVar
					},
					dataType : "json",
					type : "post",
					success : function(data) {
						if (data.success) {
							$("#name").attr("value",
									data.result.name);
							$("#rate").val(data.result.x);
						} else {
							layer.msg("页面读取错误, 请重新刷新页面",2,{type : 1,shade : false});
						}
					}
				});
	}
	function getDate(tm) {
		return new Date(parseInt(tm)).toLocaleString();
	}
    
	/* 修改商品类型详情 */
	function updateGoodsTypeDetail() {
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
			url : "goodsType/updateGoodsTypeDetail",
			data : {
				id : idVar,
				name : name,
				X : rate
			},
			dataType : "json",
			type : "post",
			success : function(data) {
				if (data.success) {
					layer.msg("修改成功", 2, {
						type : 1,
						shade : false
					});
				} else {
					layer.msg("修改失败", 2, {
						type : 1,
						shade : false
					});
				}
			}
		});
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
				<a class="brand" href="index.jsp"><span class="first">衣氏百秀</span>
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
					商品类型详情<a href="javascript:history.go(-1);"
						style="float: right; display: block;">返回</a>
				</h2>

				<div class="well">
					<div id="myTabContent" class="tab-content">
						<div class="row-fluid">
							<div class="span12">
								<div class="row-fluid">
									<div class="span4">
										<form id="tab">
											<label>名称</label> <input type="text" value="" id="name"
												class="input-xlarge"> <label>难度系数</label> <input
												type="text" value="" class="input-xlarge" id="rate">
										</form>
										<div class="btn-toolbar">
											<button class="btn btn-primary" onclick="updateGoodsTypeDetail()">
												 保存
											</button>
										</div>
									</div>
									<div class="span4">
										<form id="evi_form"></form>
									</div>

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
	<script src="js/layer/layer.min.js"></script>
</body>
</html>


