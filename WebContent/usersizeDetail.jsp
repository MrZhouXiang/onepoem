<!-- 用户尺寸详情界面  -->
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
		$.ajax({
					url : "user/getUsersizeDetail",
					data : {
						userId : idVar
					},
					dataType : "json",
					type : "post",
					success : function(data) {
						if (data.success) {
							$("#name").attr("value",
									data.result.name);
							$("#normal_size").attr("value",
									data.result.normal_size);
							$("#height").attr("value",
									data.result.height);
							$("#weight").attr("value",
									data.result.weight);
							$("#waist").attr("value",
									data.result.waist);
							$("#hip").attr("value",
									data.result.hip);
							$("#bust").attr("value",
									data.result.bust);
							$("#description").attr("value",
									data.result.description);
						} else {
							layer.msg("页面读取错误, 请重新刷新页面",2,{type : 1,shade : false});
						}
					}
				});
	}
	function getDate(tm) {
		return new Date(parseInt(tm)).toLocaleString();
	}
    
	function updateUsersize() {
		if(trim($("#name").val()) == ""){
			layer.msg("请输入名称", 2, {type : 1,shade : false});
			$("#name").focus();
			return false;
		}
		if(trim($("#normal_size").val()) == ""){
			layer.msg("请选择尺码", 2, {type : 1,shade : false});
			return false;
		}
		if(Number($("#height").val()) <= 0){
			layer.msg("身高要求是大于0的数字, 请重新输入", 2, {type : 1,shade : false});
			$("#height").focus();
			return false;
		}
		if(Number($("#weight").val()) <= 0){
			layer.msg("体重要求是大于0的数字, 请重新输入", 2, {type : 1,shade : false});
			$("#weight").focus();
			return false;
		}
		if(Number($("#waist").val()) <= 0){
			layer.msg("腰围要求是大于0的数字, 请重新输入", 2, {type : 1,shade : false});
			$("#waist").focus();
			return false;
		}
		if(Number($("#bust").val()) <= 0){
			layer.msg("臀围要求是大于0的数字, 请重新输入", 2, {type : 1,shade : false});
			$("#bust").focus();
			return false;
		}
		if(Number($("#hip").val()) <= 0){
			layer.msg("胸围要求是大于0的数字, 请重新输入", 2, {type : 1,shade : false});
			$("#hip").focus();
			return false;
		}
		$.ajax({
			url : "user/updateUsersize",
			data : {
				id : idVar,
				name : $("#name").val(),
				normal_size : $("#normal_size").val(),
				height : $("#height").val(),
				weight : $("#weight").val(),
				waist : $("#waist").val(),
				bust : $("#bust").val(),
				hip : $("#hip").val(),
				description : $("#description").val()
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
					客户尺寸详情<a href="javascript:history.go(-1);"
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
												class="input-xlarge">
												 <label>尺码</label> 
												 <select class="input-xlarge" id="normal_size">
												 <option value="XS">XS</option>
												 <option value="S">S</option>
												 <option value="M">M</option>
												 <option value="L">L</option>
												 <option value="XL">XL</option>
												 <option value="XXL">XXL</option>
												 </select>
											<label>身高(cm)</label> <input type="text" value="" id="height"
												class="input-xlarge"> <label>体重(kg)</label> <input
												type="text" value="" id="weight" class="input-xlarge">
										</form>
										<div class="btn-toolbar">
											<button class="btn btn-primary" onclick="updateUsersize()">
												 保存
											</button>
										</div>
									</div>
									<div class="span4">
										<form>
											<label>腰围(cm)</label><input type="text" value="" id="waist"
												class="input-xlarge"> <label>臀围(cm)</label><input
												type="text" value="" id="hip" class="input-xlarge">
											<label>胸围(cm)</label> <input type="text" value="" id="bust"
												class="input-xlarge"> <label>描述</label> <input
												type="text" value="" id="description" class="input-xlarge">

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
				<div class="modal small hide fade" id="frozenModal" tabindex="-1"
					role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">×</button>
						<h3 id="myModalLabel">冻结账户确认</h3>
					</div>
					<div class="modal-body">
						<form id="tab">
									<label>冻结天数</label> <input type="text" value="" id="days"
										class="input-xlarge">
						</form>
						<!-- <p class="error-text">
							<i class="icon-warning-sign modal-icon"></i>你确定要冻结此用户么?
						</p> -->
					</div>
					<div class="modal-footer">
						<button class="btn" data-dismiss="modal" aria-hidden="true">取消</button>
						<button class="btn btn-danger" data-dismiss="modal"
							onclick="frozenUserForDays()">确定</button>
					</div>
				</div>

				<div class="modal small hide fade" id="sendMsg" tabindex="-1"
					role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">×</button>
						<h3 id="myModalLabel">发送消息</h3>
					</div>
					<div class="modal-body">

						<form id="tab">
							<label>标题</label> <input type="text" value="" id="titleId"
								class="input-xlarge"> <label>内容</label>
							<textarea id="contextId" value="" rows="3" class="input-xlarge"></textarea>
						</form>
					</div>
					<div class="modal-footer">
						<button class="btn" data-dismiss="modal" aria-hidden="true">取消</button>
						<button class="btn btn-danger" data-dismiss="modal"
							onclick="sendUserMsg()">确定</button>
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
	<script src="js/base.js"></script>
</body>
</html>


