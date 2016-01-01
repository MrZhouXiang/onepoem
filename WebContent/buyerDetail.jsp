<!--客户详情界面-->
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
			url : "user/getBuyerInfo",
			data : {
				id : idVar
			},
			dataType : "json",
			type : "post",
			success : function(data) {
				if (data.success) {
					$("#username").val(data.result.name);
					$("#lv").val(data.result.lv);
					$("#sex").val(data.result.xingbie);
					//正常状态
					if(data.result.status == "0"){
						$("#status").val("正常");
						$("#unlockBtn").attr("disabled", true);
						$("#frozenBtn").attr({
							"disabled" : false,
							"data-toggle" : "modal",
							"data-target" : "#frozenModal",
							"data-backdrop" : "true"});
					}else{
						$("#status").val("冻结");
						$("#unlockBtn").attr("disabled", false);
						$("#frozenBtn").attr({
							"disabled" : true,
							"data-toggle" : "",
							"data-target" : "",
							"data-backdrop" : ""});
					}
					$('#identity').val(data.result.identity);
					$('#belong').val(data.result.belong);
					$('#province').val(data.result.province);
					$('#city').val(data.result.city);
					$('#is_international_academy').val(data.result.is_international_academy);
					$('#company_type').val(data.result.company_type);
				} else {
					layer.msg("页面读取失败, 请重新刷新页面",2,{type : 1,shade : false});
				}
			}
		});
	}
	function getDate(tm) {
		return new Date(parseInt(tm)).toLocaleString();
	}
	
	function save_memberChange() {
		var length = $("#username").val().length;
		if(length > 11){
			layer.msg("用户名最长为11个字符, 请重新输入",2,{type : 1,shade : false});
			$("#username").focus();
			return false;
		}
		
		if(Number($("#lv").val()) < 0 || isNaN($("#lv").val())){
			layer.msg("等级要求输入大于0的数字, 请重新输入",2,{type:1,shade:false});
			$("#lv").focus();
			return false;
		}
		
		$.ajax({
			url : "user/updateBuyerInfo",
			data : {
				id : idVar,
				name : $("#username").val(),
				xingbie : $("#sex").val(),
				lv : $("#lv").val(),
				quyu : $("#zone").val(),
				zhiye : $("#job").val(),
				weibo_name : $("#weibo").val(),
				weixin_name : $("#wechat").val(),
				status : $("#status").val() == "正常" ? 0 : 1,
				identity: $('#identity').val(),
				belong: $('#belong').val(),
				province: $('#province').val(),
				city: $('#city').val(),
				is_international_academy: $('#is_international_academy').val(),
				company_type: $('#company_type').val()
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

	/* 冻结用户指定天数 */
	function frozenUserForDays(){
		var flag = $("#frozenBtn").attr("disabled");
		var regex = /^[1-9]+$/;

		if(flag == undefined){
			if(!regex.test($('#days').val())){
				layer.msg("冻结天数只允许输入大于0的数字, 请重新输入", 2, {
					type : 1,
					shade : false
				});
				return false;
			}else{
				$.ajax({
					url : '/user/frozenUserForDays',
					type : "POST",
					dataType : "json",
					data : {
						userId : idVar,
						days : $('#days').val(),
						userType : "kehu"
					},
					success : function(data) {
						if (data.success) {
							layer.msg("冻结成功", 2, {
								type : 1,
								shade : false
							});
							loadPage();
							$('#frozenModal').modal('hide');
						}else{
							layer.msg("冻结失败, 请重新尝试", 2, {
								type : 1,
								shade : false
							});
						}
					}
				});
			}
			
		}
		
	}
	
	/* 解冻用户 */
	function unlockUser(){
		var flag = $("#unlockBtn").attr("disabled");
		if(flag == undefined){
			$.ajax({
				url : '/user/unlockUser',
				type : "POST",
				dataType : "json",
				data : {
					userId : idVar,
					userType : "kehu"
				},
				success : function(data) {
					if (data.success) {
						layer.msg("解冻成功", 2, {
							type : 1,
							shade : false
						});
						loadPage();
					}else{
						layer.msg("解冻失败, 请重新尝试", 2, {
							type : 1,
							shade : false
						});
					}
				}
			});
		}
		
	}

	/* 发送消息给客户 */
	function sendUserMsg() {

		if($('#title').val() === ""){
			layer.msg("请输入标题", 2, {
				type : 1,
				shade : false
			});
			return false;
		}
		if($('#content').val() === ""){
			layer.msg("请输入内容", 2, {
				type : 1,
				shade : false
			});
			return false;
		}
		$.ajax({
			url : "push/sendMsgToUser",
			data : {
				id : idVar,
				type : "kehu",
				title : $("#title").val(),
				content : $("#content").val()
			},
			dataType : "json",
			type : "post",
			success : function(data) {
				if (data.success) {
					layer.msg("发送成功", 2, {
						type : 1,
						shade : false
					});
					$('#sendMsg').modal('hide');
				} else {
					layer.msg("发送失败", 2, {
						type : 1,
						shade : false
					});
				}
			}
		});
	}
	
	//注册函数
	$(function() {
		$("#username").on("blur",function(){
			var length = $("#username").val().length;
			if(length > 11){
				layer.msg("用户名最长为11位, 请重新输入",2,{type : 1,shade : false});
			}
		});
		
		$('#sendMsg').on('show', function(){
			$('#title').val("");
			$('#content').val("");
		});
		
		$('#frozenModal').on('show', function(){
			$('#days').val("");
		});
	});
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
					客户详情<a href="javascript:history.go(-1);"
						style="float: right; display: block;">返回</a>
				</h2>

				<div class="well">
					<div id="myTabContent" class="tab-content">
						<div class="row-fluid">
							<div class="span12">
								<div class="row-fluid">
									<div class="span4">
										<form id="tab">
											<label>用户名</label> 
											<input type="text" value="" id="username" class="input-xlarge"> 
											<label>性别</label> 
											<select id="sex" class="input-xlarge">
												<option value="1">男</option>
												<option value="2">女</option>
											</select>
											<label>等级</label> 
											<input type="text" value="" id="lv" class="input-xlarge">
											<label>身份</label>
											<select id="identity" class="input-xlarge">
											<option value="学生">学生</option>
											<option value="教师">教师</option>
											<option value="手工裁缝">手工裁缝</option>
											<option value="高级定制">高级定制</option>
											<option value="时装大师">时装大师</option>
											<option value="企业代表">企业代表</option>
											</select>
											<label>所属</label>
											<select id="belong" class="input-xlarge">
											<option value="纺织学院">纺织学院</option>
											<option value="灵桥市场">灵桥市场</option>
											<option value="西门街商业区">西门街商业区</option>
											<option value="和义大道商业区">和义大道商业区</option>
											</select>
										</form>
										<div class="btn-toolbar">
											<button class="btn btn-primary" onclick="save_memberChange()">
												保存</button>
											<button class="btn btn-primary" data-toggle="modal"
												data-target="#sendMsg">发送消息</button>
											<a href="#" class="btn" id="frozenBtn">冻结</a> <a
												href="#" class="btn" id="unlockBtn" onclick="unlockUser()">解冻</a>
											<div class="btn-group"></div>
										</div>
									</div>
									<div class="span4">
										<form>
											<label>是否为国际分院</label>
											<select id="is_international_academy" class="input-xlarge">
											<option value="1">是</option>
											<option value="0">否</option>
											</select>
											<label>状态</label>
											<input type="text" value="" id="status" class="input-xlarge"
												readonly="readonly">
											<label>省</label>
											<input type="text" value="" id="province" class="input-xlarge">
											<label>市</label>
											<input type="text" value="" id="city" class="input-xlarge">
	                                        <label>企业商户类型</label>
	                                        <select id="company_type" class="input-xlarge">
											<option value="外贸企业">外贸企业</option>
											<option value="服装厂">服装厂</option>
											<option value="品牌服装">品牌服装</option>
											<option value="国际品牌服装">国际品牌服装</option>
											</select>
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
					</div>
					<div class="modal-footer">
						<button class="btn" data-dismiss="modal" aria-hidden="true">取消</button>
						<button class="btn btn-danger" onclick="frozenUserForDays()">确定</button>
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
							<label>标题</label> <input type="text" value="" id="title"
								class="input-xlarge"> <label>内容</label>
							<textarea id="content" rows="3" class="input-xlarge"></textarea>
						</form>
					</div>
					<div class="modal-footer">
						<button class="btn" data-dismiss="modal" aria-hidden="true">取消</button>
						<button class="btn btn-danger" onclick="sendUserMsg()">确定</button>
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
	<script src="/js/vendor/jquery.cityselect.js"></script>
</body>
</html>


