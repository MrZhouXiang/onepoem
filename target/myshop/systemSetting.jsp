<!-- 系统设置界面  -->
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
<link rel="stylesheet" type="text/css"
	href="css/bootstrap-responsive.min.css">
<link rel="stylesheet" type="text/css" href="stylesheets/theme.css">
<link rel="stylesheet" href="lib/font-awesome/css/font-awesome.css">
<!-- <link rel="stylesheet" href="css/base.css" /> -->
<link rel="stylesheet" href="css/admin.css" />
<link rel="stylesheet" href="css/jquery.fileupload.css" />
<link rel="stylesheet" href="css/jquery.fileupload-ui.css" />
<link rel="stylesheet" href="css/lightbox.css" />
<script src="lib/jquery-1.8.1.min.js" type="text/javascript"></script>
<script src="lib/jQuery.md5.js" type="text/javascript"></script>
<script src="lib/addMangerUser.js" type="text/javascript"></script>
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
	var userPermission = '${sessionScope.userSessionInfo.role}';
	$(function() {
		if (tempSession == null || tempSession == "") {
			location.href = "403.html";
		} else {
			if (userPermission == 1) {
				$("#tabMenu")
						.append(
								"	<li id='tab5'><a href='#addMangerTab' data-toggle='tab'>增加管理员</a></li>");
				getManger();

				$("#tabMenu")
						.append(
								"	<li id='tab6'><a href='#uploadApk' data-toggle='tab'>上传安卓安装包</a></li>");
			}
		}
	});
	var listLength = 0;
	var tempChange = new Array();
	var tempRate = new Array();
	var tempBase = new Array();
	var changeState = 0;
	var currentLoc = 1;
	function loadPage() {
		changeState = 0;
		getSystemParameter();
	}
	
	/* 查询系统参数 */
	function getSystemParameter(){
		$.ajax({
			url: "/getSystemParameter",
			dataType: "json",
			type: "post",
			success: function(data){
				if (data.success) {
					$('#platform_fee').val(data.platformFee);
					$('#buyout_count').val(data.buyoutCount);
					$('#trade_amount').val(data.tradeAmount);
					$('#discuss_count').val(data.discussCount);
				}
			}
		});
	}
	/* 修改系统参数 */
	function updateSystemParameter() {
		
		var platform_fee = $('#platform_fee').val();
		var buyout_count = $('#buyout_count').val();
		var trade_amount = $('#trade_amount').val();
		var discuss_count = $('#discuss_count').val();
		var regex = /^[1-9]+$/;
		
		if(Number(platform_fee) < 0 || isNaN(platform_fee)){
			layer.msg("平台费用要求为大于等于0的数字, 请重新输入", 2, {
				type : 1,
				shade : false
			});
			$('#platform_fee').focus();
			return false;
		}
		if(!regex.test(buyout_count) || Number(buyout_count) <= 0 || isNaN(buyout_count)){
			layer.msg("设计图买断数量要求为大于0的整数, 请重新输入", 2, {
				type : 1,
				shade : false
			});
			$('#buyout_count').focus();
			return false;
		}
		if(Number(trade_amount) <= 0 || isNaN(trade_amount)){
			layer.msg("成衣秀交易额要求为大于0的数字, 请重新输入", 2, {
				type : 1,
				shade : false
			});
			$('#trade_amount').focus();
			return false;
		}
		if(!regex.test(discuss_count) || Number(discuss_count) <= 0 || isNaN(discuss_count)){
			layer.msg("累计评论数量要求为大于0的整数, 请重新输入", 2, {
				type : 1,
				shade : false
			});
			$('#discuss_count').focus();
			return false;
		}
		
		$.ajax({
			url: "/updateSystemParameter",
			dataType: "json",
			type: "post",
			data: {
				platformFee: platform_fee,
				buyoutCount: buyout_count,
				tradeAmount: trade_amount,
				discussCount: discuss_count
			},
			success: function(data){
				if (data.success) {
					layer.msg("修改成功",2,{type : 1,shade : false});
				}else{
					layer.msg("修改失败",2,{type : 1,shade : false});
				}
			}
		});
		
	}
	
	var idArray = new Array();
	var totalPage;
	var totalCash = null;
	var pageSizeVar = 1;
	var currentPageVar = 1;
	var serachVar = "";
	function pageCallback(index, jq) {
		currentPageVar = index + 1;
		initMadeTypeLvList(currentPageVar);
	}
	 
	function changePwdConfirm() {
		var newPwd = $("#newPwd").attr("value");
		var confirmPwd = $("#confirmPwd").attr("value");
		var oldPwd = $("#oldPwd").attr("value");
		if (confirmPwd != newPwd) {
			layer.msg("两次密码输入不一致, 请重新输入",2,{type : 1,shade : false});
			return false;
		} else {
			if (trim(confirmPwd) == null || trim(confirmPwd) == "" || trim(oldPwd) == null || trim(oldPwd) == "") {
				layer.msg("请勿提交空值",2,{type : 1,shade : false});
				return false;
			} else {
				$.ajax({
					url : "/updatePwd",
					data : {
						oldPwd : $.md5(oldPwd),
						newPwd : $.md5(newPwd)
					},
					dataType : "json",
					type : "post",
					success : function(data) {
						if (data.success) {
							layer.msg("修改成功",2,{type : 1,shade : false});
							getDataByTabLoc();
						}else{
							layer.msg("修改失败",2,{type : 1,shade : false});
						}
					}
				});
			}
		}
	}
/* 推送系统消息 */	
function addSysMsg(){
	
	if(trim($('#title').val()) === ""){
		layer.msg("请输入标题", 2, {
			type : 1,
			shade : false
		});
		return false;
	}
	if(trim($('#content').val()) === ""){
		layer.msg("请输入内容", 2, {
			type : 1,
			shade : false
		});
		return false;
	}
	
	$.ajax({
		url: "/pushSystemMsg",
		dataType: "json",
		type: "post",
		data: {
			title: $('#title').val(),
			content: $('#content').val()
		},
		success: function(data){
			if (data.success) {
				layer.msg("发送成功",2,{type : 1,shade : false});
				getDataByTabLoc();
			}else{
				layer.msg("发送失败",2,{type : 1,shade : false});
			}
		}
	});
	
}

/* 跳转到制作类型等级详情 */
function madeTypeLvDetail(id){
	location.href = "madeTypeLvDetail.jsp?id=" + id;
}

/* 查询制作类型等级列表 */
function initMadeTypeLvList(){
	$.ajax({
		url : "getMadeTypeLvList/" + currentPageVar,
		data : {
			currentPage : currentPageVar,
			pageSize : pageSizeVar,
		},
		dataType : "json",
		type : "post",
		success : function(data) {

			if (data.success) {
				$("#madeTypeLvTable").empty();
				//显示列表
				for (var i = 0; i < data.result.length; i++) {
					idArray[i] = data.result[i].id;
					var tempid = i + 1;
					$("#madeTypeLvTable")
							.append(
									"<tr>'"
											+ "<td>"
											+ tempid
											+ "</td>"
											+ "<td>"
											+ data.result[i].jishu
											+ "</td>"
											+ "<td>"
											+ data.result[i].jingyanmin
											+ "</td>"
											+ "<td>"
											+ data.result[i].jingyanmax
											+ "</td>"
											+ "<td>"
											+ data.result[i].jiagongfei
											+ "</td>"
/* 											+ "<td><a href='#' onclick='madeTypeLvDetail("
											+ data.result[i].id
											+ ")'>详情</a></td>" */
											+ "</tr>");
				}
				//显示分布按钮							
				totalPage = data.totalPage;

				if (Number(totalPage) > pageSizeVar) {
					$("#list_findMadeTypeLvList").show();
					showPagination();
				} else {
					$("#list_findMadeTypeLvList").hide();
				}

				$("#error_findMadeTypeLvList").hide();
			} else {
				$("#error_findMadeTypeLvList").show();
			}
		}
	});
}

function showPagination() {
	$("#list_findMadeTypeLvList").pagination(parseInt(totalPage), {
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

/* TAB栏切换 */
function switchTab() {

	console.log("currentLoc= " + currentLoc);
	
	$('#tab1').on('click', function() {
		currentLoc = 1;
		console.log("currentLoc= " + currentLoc);
		getSystemParameter();
	});

	$('#tab2').on('click', function() {
		currentLoc = 2;
		console.log("currentLoc= " + currentLoc);
		$('#newPwd').val("");
		$('#confirmPwd').val("");
		$('#oldPwd').val("");
	});

	$('#tab3').on('click', function() {
		currentLoc = 3;
		console.log("currentLoc= " + currentLoc);
		$('#title').val("");
		$('#content').val("");
	});

	$('#tab4').on('click', function() {
		currentLoc = 4;
		console.log("currentLoc= " + currentLoc);
		initMadeTypeLvList();
	});

	$('#tab5').on('click', function() {
		currentLoc = 5;
		console.log("currentLoc= " + currentLoc);
	});

	$('#tab6').on('click', function() {
		currentLoc = 6;
		console.log("currentLoc= " + currentLoc);
		$('#version_code').val("");
		$('#version_desc').val("");
		$('#version_name').val("");
	});

}

/* 根据TAB位置查询数据  */
function getDataByTabLoc() {
	console.log("currentLoc= " + currentLoc);
	switch (currentLoc) {
	case 1:
		$('#tab1').click();
		break;
	case 2:
		$('#tab2').click();
		break;
	case 3:
		$('#tab3').click();
		break;
	case 4:
		$('#tab4').click();
		break;
	case 5:
		$('#tab5').click();
		break;
	case 6:
		$('#tab6').click();
		break;
	default:
		break;
	}
}

/* 更新版本 */
function updateApk(){
	
	var forcedUpdate = $('#forced_update').val();
	var versionCode = $('#version_code').val();
	var versionDesc = $('#version_desc').val();
	var versionName = $('#version_name').val();
	var url = $('#download_url').val();
	
	if(Number(versionCode) <= 0 || isNaN(versionCode)){
		layer.msg("版本号要求为大于0的数字, 请重新输入",2,{type : 1,shade : false});
		$('#version_code').focus();
		return false;
	}
	if(trim(versionDesc) == ""){
		layer.msg("请输入版本描述",2,{type : 1,shade : false});
		$('#version_desc').focus();
		return false;
	}
	if(trim(versionName) == ""){
		layer.msg("请输入版本名称",2,{type : 1,shade : false});
		$('#version_name').focus();
		return false;
	}
	if(trim(url) == ""){
		layer.msg("请上传安装包",2,{type : 1,shade : false});
		$('#download_url').focus();
		return false;
	}
	
	$.ajax({
		url: "/uploadAndroidApk",
		dataType: "json",
		type: "post",
		data: {
			forcedUpdate: forcedUpdate,
			versionCode: versionCode,
			versionDesc: versionDesc,
			versionName: versionName,
			url: url
		},
		success: function(data){
			if (data.success) {
				layer.msg("上传成功",2,{type : 1,shade : false});
				getDataByTabLoc();
			}else{
				layer.msg("上传失败",2,{type : 1,shade : false});
			}
		}
	});
	
}

$(function(){
	
	switchTab();
	
    var apk_obj = {
            fileInput:$("#apk_fileupload"),
            hiddenInput:$("#download_url"),
            progress:'apk_progress'
        };
        infoUpload(apk_obj);  
        
});

/**
 * 封面上传
 *   var obj = {
 *       fileInput:"",
 *       hiddenInput:"", //文件名隐藏域
 *       progress:  //进度条
 *       uploaddone:
 *   }
 */
function infoUpload(obj){

    var upload = obj.fileInput; //$('#fileupload');
    upload.fileupload({
        dataType: 'json',
        add: function (e, data) {
        	   var filepath = data.fileInput.context.value;
        	   var extStart= filepath.lastIndexOf(".");
               var ext=filepath.substring(extStart,filepath.length).toUpperCase();
               if(ext!=".APK"){//ext!=".PNG"&&
                layer.msg("图片限于apk格式");
                return false;
               }
            data.submit();
        },
        done: function (e, data) {

            var path = data.result.filePath;
            obj.hiddenInput.val(data.result.filePath); //隐藏域, 存放下载地址

        },
        progressall: function (e, data) {
            var progress = parseInt(data.loaded / data.total * 100, 10);

            $("#"+obj.progress+" .progress-bar").css(
                'width',
                progress + '%'
            );
        },
        start:function(e){
            $("#"+obj.progress).removeClass("hide");
            if(typeof (obj.uploaddone) !== "undefined"){
                (obj.uploaddone)();
            }
        },
        stop:function(e){
            setTimeout(function(){$("#"+obj.progress).addClass('hide');},1000);
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
							<li><a tabindex="-1" href="/logout">退出</a></li>
						</ul></li>
				</ul>
				<a class="brand" href="index.jsp"><span class="first">衣氏百秀</span>
					<span class="second">首页</span></a>
			</div>
		</div>
	</div>


	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span12">

				<div class="row-fluid">
					<!-- 左边栏 -->
					<div class="span2">
						<jsp:include page="/left.jsp"></jsp:include>
					</div>
					<div class="span10">
						<div class="well">
							<ul class="nav nav-tabs" id="tabMenu">
								<li id="tab1"><a href="#systemParameter" data-toggle="tab">系统参数</a></li>
								<li id="tab2"><a href="#changePwd" data-toggle="tab">修改密码</a></li>
								<li id="tab3"><a href="#systemMsg" data-toggle="tab">系统消息</a></li>
								<li id="tab4"><a href="#madeTypeLvTab" data-toggle="tab">制作类型等级</a></li>
							</ul>
							<div id="myTabContent" class="tab-content">
								<div class="tab-pane active in" id="systemParameter">
									<div class="container-fluid">
										<div class="row-fluid">
											<div class="span12">
												<div class="row-fluid">
													<div class="span6">
														<form id="tab">
															<label>平台费用</label> <input type="text" value=""
																id="platform_fee" class="input-xlarge">
															<label>设计图买断数量</label> <input type="text" value=""
																id="buyout_count" class="input-xlarge">
															<label>成衣秀交易额</label> <input type="text" value=""
																id="trade_amount" class="input-xlarge">
															<label>累计评论数量</label> <input type="text" value=""
																id="discuss_count" class="input-xlarge">
														</form>
														<div class="btn-toolbar">
															<button id="updateSystemParameter" class="btn btn-primary" onclick="updateSystemParameter()">
																保存
															</button>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
								<div class="tab-pane fade" id="uploadApk">
								
									<form id="formApk">
										<label>强制更新</label>
										<select class="input-xlarge" id="forced_update" name="forced_update">
										<option value="1">是</option>
										<option value="0">否</option>
										<option></option>
										</select>
										<label>版本代码</label> 
										<input type="text" class="input-xlarge" id="version_code" name="version_code" /> 
										<label>版本描述</label>
										<input type="text" class="input-xlarge" id="version_desc" name="version_desc" />
										<label>版本名</label>
										<input type="text" class="input-xlarge" id="version_name" name="version_name" /> 
										<div class="form-group">
												<label class="col-sm-2 control-label" for="noticeContent">APK上传</label>
												<div class="col-sm-6">
													<button class="btn btn-success fileinput-button"
														type="button">
														<i class="glyphicon glyphicon-upload"></i> <span>选择文件</span>
														<input id="apk_fileupload" type="file"
															name="files[]" data-url="/controller/upload/apk"
															accept=".apk"> 
														<input type="hidden" id="download_url" /> 
													</button>
													<span class="help-block"></span>
													<!-- The global progress bar -->
													<div id="apk_progress" class="progress hide">
														<div class="progress-bar progress-bar-success"
															style="width: 0;"></div>
													</div>
												</div>
											</div>
									</form>
										<div class="btn-toolbar">
											<button class="btn btn-primary" onclick="updateApk()">提交</button>
										</div>
								</div>
								<div class="tab-pane fade" id="addMangerTab">
									<table class="table table-hover">
										<thead>
											<tr>
												<th>序号</th>
												<th>账号</th>
												<th>姓名</th>
												<th>操作</th>
											</tr>
										</thead>
										<tbody id="managerTable">
										</tbody>
									</table>
									<div id="btn_group" class="btn-toolbar">
										<button class="btn btn-primary" onclick="addRow()">
											增加管理员
										</button>
										<div class="btn-group"></div>
									</div>
								</div>
								<div class="tab-pane fade" id="changePwd">
									<form id="tab">
										<label>新密码</label> <input id="newPwd" type="password" value=""
											class="input-xlarge"> <label>请再次输入</label> <input
											id="confirmPwd" type="password" value="" class="input-xlarge">
										<label>旧密码</label> <input id="oldPwd" type="password"
											value="" class="input-xlarge">

									</form>
									<div class="btn-toolbar">
										<button class="btn btn-primary" onclick="changePwdConfirm()">确定</button>
										<div class="btn-group"></div>
									</div>
								</div>
								<div class="tab-pane fade" id="systemMsg">
									<form id="tab">
										<label>标题</label> <input type="text" value="" id="title"
											class="input-xlarge"> <label>内容</label>
										<textarea id="content" rows="3"
											class="input-xlarge"></textarea>
									</form>
									<div class="btn-toolbar">
										<button class="btn btn-primary" onclick="addSysMsg()">
											发布
										</button>
										<div class="btn-group"></div>
									</div>
								</div>
								<div class="tab-pane fade" id="madeTypeLvTab">
									<table class="table table-hover">
										<thead>
											<tr>
												<th>序号</th>
												<th>等级</th>
												<th>升级最小值</th>
												<th>升级最大值</th>
												<th>加工费</th>
												<th></th>
											</tr>
										</thead>
										<tbody id="madeTypeLvTable">
										</tbody>
									</table>
									<p id="error_findMadeTypeLvList" style="color: red; display: none;">没有相关数据,请查证后刷新.</p>
									<div id="list_findMadeTypeLvList" class="quotes"
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
	<script src="js/vendor/jquery.ui.widget.js"></script>
	<script src="js/vendor/load-image.min.js"></script>
	<script src="js/vendor/canvas-to-blob.min.js"></script>

	<script src="js/vendor/jquery.iframe-transport.js"></script>
	<script src="js/vendor/jquery.fileupload.js"></script>
	<script src="js/vendor/jquery.fileupload-process.js"></script>
	<script src="js/vendor/jquery.fileupload-image.js"></script>
	<script src="js/vendor/jquery.fileupload-validate.js"></script>
	<script src="js/vendor/icheck/icheck.min.js"></script>
	<script src="js/vendor/jquery.validate.min.js"></script>
	<script src="js/vendor/jquery.validate.message_cn.js"></script>

	<script src="js/vendor/bootstrap.min.js"></script>
	<script src="js/vendor/bootstrap-paginator.min.js"></script>
	<script src="js/vendor/lightbox-2.6.min.js"></script>

	<script src="js/layer/layer.min.js"></script>
	<script src="js/base.js"></script>
</body>
</html>


