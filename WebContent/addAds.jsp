<!-- 新增广告位 -->
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
<link rel="stylesheet" href="css/base.css" />
<link rel="stylesheet" href="css/admin.css" />
<link rel="stylesheet" href="css/jquery.fileupload.css" />
<link rel="stylesheet" href="css/jquery.fileupload-ui.css" />
<link rel="stylesheet" href="css/lightbox.css" />
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
	var flag = false;
	var flag1 = false;
	var flag2 = false;
	var flag3 = false;
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
	
	//注册函数
	$(function() {
        var left_obj = {
                fileInput:$("#left_fileupload"),
                context:$("#left_files"),
                hiddenInput:$("#left_fileName"),
                progress:'left_progress'
            };
            infoUpload(left_obj); 
        var right_top_obj = {
                fileInput:$("#right_top_fileupload"),
                context:$("#right_top_files"),
                hiddenInput:$("#right_top_fileName"),
                progress:'right_top_progress'
            };
            infoUpload(right_top_obj); 
        var right_bottom_obj = {
                fileInput:$("#right_bottom_fileupload"),
                context:$("#right_bottom_files"),
                hiddenInput:$("#right_bottom_fileName"),
                progress:'right_bottom_progress'
            };
            infoUpload(right_bottom_obj); 
			
            addAds();
            
            isSalerExist();
	});
	
	/**
	 * 广告位新增, 保存
	 */
	function addAds(){
		
			$("#save").on("click",function(){
				flag = flag1 && flag2 && flag3;
				console.log("flag ---------->" + flag);
				if(flag){
					var telStr = $("#left_tel").val() + "," + $("#right_top_tel").val() + "," + $("#right_bottom_tel").val();
		            var titleStr = $("#left_title").val() + "," + $("#right_top_title").val() + "," + $("#right_bottom_title").val();
		            var descStr =  $("#left_desc").val() + "," + $("#right_top_desc").val() + "," + $("#right_bottom_desc").val();
		            var coverStr = $("#left_fileName").val() + "," + $("#right_top_fileName").val() + "," + $("#right_bottom_fileName").val();
		            var positionStr = $("#left_position").val() + "," + $("#right_top_position").val() + "," + $("#right_bottom_position").val();
		        	$.ajax({
		                url:'/ads/new',
		                type:"POST",
		                dataType:"json",
		                data:{
		                	telStr : telStr,
		                	titleStr : titleStr,
		                	descStr : descStr,
		                	coverStr : coverStr,
		                	positionStr : positionStr
		                },
		                success:function(data){
		                    if (data.success) {
		                    	layer.msg("新增成功",2,{type:1,shade:false});
		                        javascript:history.go(-1);
		                    }
		            }
		            });
				}else{
					layer.msg("您填写的信息有误, 请确认后重新提交", 2, {
						type : 1,
						shade : false
					});
				}
	    		
	        });
        
	}
	
	/* 判断商户是否存在 */
	function isSalerExist(){
		
		$("#left_tel").on("blur",function(){
			var left_tel = $("#left_tel").val();
			$.ajax({
                url:'/ads/isSalerExist',
                type:"POST",
                dataType:"json",
                data:{
					tel: left_tel
                },
                success:function(data){
                    if (data.success) {
                    	flag1 = true;
                    }else{
                    	layer.msg("该商户不存在, 请重新输入手机号", 2, {
							type : 1,
							shade : false
						});
                    	flag1 = false;
                    }
            }
            });
			
		});
		$("#right_top_tel").on("blur",function(){
			var right_top_tel = $("#right_top_tel").val();
			$.ajax({
                url:'/ads/isSalerExist',
                type:"POST",
                dataType:"json",
                data:{
					tel: right_top_tel
                },
                success:function(data){
                    if (data.success) {
                    	flag2 = true;
                    }else{
                    	layer.msg("该商户不存在, 请重新输入手机号", 2, {
							type : 1,
							shade : false
						});
                    	flag2 = false;
                    }
            }
            });
			
		});
		$("#right_bottom_tel").on("blur",function(){
			var right_bottom_tel = $("#right_bottom_tel").val();
			$.ajax({
                url:'/ads/isSalerExist',
                type:"POST",
                dataType:"json",
                data:{
					tel: right_bottom_tel
                },
                success:function(data){
                    if (data.success) {
                    	flag3 = true;
                    }else{
                    	layer.msg("该商户不存在, 请重新输入手机号", 2, {
							type : 1,
							shade : false
						});
                    	flag3 = false;
                    }
            }
            });
			
		});
		
	}
	/**
	 * 封面上传
	 *   var obj = {
	 *       fileInput:"",
	 *       context:"",
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
	               if(ext!=".GIF"&&ext!=".JPG"){//ext!=".PNG"&&
	                layer.msg("图片限于jpg,gif格式");
	                return false;
	               }
	            data.context = obj.context; //$("#files");
	            data.submit();
	        },
	        done: function (e, data) {

	            var path = data.result.filePath;
	            data.context.find("img").attr("src",path).show();
	            data.context.find("a").attr("href",path).show();
	            data.context.find("i").hide();
	            obj.hiddenInput.val(data.result.fileName); //隐藏域

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
<body>
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
					新增推荐商户<a href="javascript:history.go(-1);"
						style="float: right; display: block;">返回</a>
				</h2>
				<div class="well">
					<div id="myTabContent" class="tab-content">
						<div class="row-fluid">
							<div class="span12">
								<div class="row-fluid">
									<div class="span4">
										<form class="form-horizontal" id="adsForm">
											<div class="form-group">
												<label class="col-sm-2 control-label" for="left_tel">手机号</label>
												<div class="col-sm-10">
													<input type="text" class="form-control" id="left_tel"
														name="title"> <span class="help-block"></span>
												</div>
											</div>
											<div class="form-group">
												<label class="col-sm-2 control-label" for="left_title">名称</label>
												<div class="col-sm-10">
													<input type="text" class="form-control" id="left_title"
														name="title"> <span class="help-block"></span>
												</div>
											</div>
											<div class="form-group">
												<label class="col-sm-2 control-label" for="left_desc">描述</label>
												<div class="col-sm-10">
													<input type="text" class="form-control" id="left_desc"
														name="title"> <span class="help-block"></span>
												</div>
											</div>
											<div class="" style="display: none;">
												<input type="checkbox" id="coverSwitch"
													style="margin-left: 65px;" /> <i style="margin-left: 5px;">封面</i>
												<i style="font-size: 12px;">(公告中添加封面)</i>
											</div>
											<div class="form-group">
												<label class="col-sm-2 control-label" for="noticeContent">图片</label>
												<div class="col-sm-6">
													<button class="btn btn-success fileinput-button"
														type="button">
														<i class="glyphicon glyphicon-upload"></i> <span>选择图片</span>
														<input id="left_fileupload" type="file" name="files[]"
															data-url="controller/upload/7" accept=".jpg,.png,.gif">
														<input type="hidden" id="left_fileName" /> <input
															type="hidden" id="left_position" value="11" />
													</button>
													<span class="help-block"></span>
													<!-- The global progress bar -->
													<div id="left_progress" class="progress hide">
														<div class="progress-bar progress-bar-success"
															style="width: 0;"></div>
													</div>
													<!-- The container for the uploaded files -->
													<!-- preview -->
													<div class="cover_wrp" id="left_files">
														<a class="example-image-link" href="" data-lightbox="1"><img
															class="cover default" src="" alt="图片预览" /> </a> <!-- <i
															class="cover default">图片预览</i> -->
													</div>
												</div>
											</div>
										</form>
									</div>
									<div class="span4">
										<form class="form-horizontal" id="adsForm">
											<div class="form-group">
												<label class="col-sm-2 control-label" for="right_top_tel">手机号</label>
												<div class="col-sm-10">
													<input type="text" class="form-control" id="right_top_tel"
														name="title"> <span class="help-block"></span>
												</div>
											</div>
											<div class="form-group">
												<label class="col-sm-2 control-label" for="right_top_title">名称</label>
												<div class="col-sm-10">
													<input type="text" class="form-control"
														id="right_top_title" name="title"> <span
														class="help-block"></span>
												</div>
											</div>
											<div class="form-group">
												<label class="col-sm-2 control-label" for="right_top_desc">描述</label>
												<div class="col-sm-10">
													<input type="text" class="form-control" id="right_top_desc"
														name="title"> <span class="help-block"></span>
												</div>
											</div>
											<div class="" style="display: none;">
												<input type="checkbox" id="coverSwitch"
													style="margin-left: 65px;" /> <i style="margin-left: 5px;">封面</i>
												<i style="font-size: 12px;">(公告中添加封面)</i>
											</div>
											<div class="form-group">
												<label class="col-sm-2 control-label" for="noticeContent">图片</label>
												<div class="col-sm-6">
													<button class="btn btn-success fileinput-button"
														type="button">
														<i class="glyphicon glyphicon-upload"></i> <span>选择图片</span>
														<input id="right_top_fileupload" type="file"
															name="files[]" data-url="controller/upload/7"
															accept=".jpg,.png,.gif"> <input type="hidden"
															id="right_top_fileName" /> <input type="hidden"
															id="right_top_position" value="21" />
													</button>
													<span class="help-block"></span>
													<!-- The global progress bar -->
													<div id="right_top_progress" class="progress hide">
														<div class="progress-bar progress-bar-success"
															style="width: 0;"></div>
													</div>
													<!-- The container for the uploaded files -->
													<!-- preview -->
													<div class="cover_wrp" id="right_top_files">
														<a class="example-image-link" href="" data-lightbox="2"><img
															class="cover default" src="" alt="图片预览" /> </a> <!-- <i
															class="cover default">图片预览</i> -->
													</div>
												</div>
											</div>
										</form>
									</div>
									<div class="span4">
										<form class="form-horizontal" id="adsForm">
											<div class="form-group">
												<label class="col-sm-2 control-label" for="right_bottom_tel">手机号</label>
												<div class="col-sm-10">
													<input type="text" class="form-control"
														id="right_bottom_tel" name="title"> <span
														class="help-block"></span>
												</div>
											</div>
											<div class="form-group">
												<label class="col-sm-2 control-label"
													for="right_bottom_title">名称</label>
												<div class="col-sm-10">
													<input type="text" class="form-control"
														id="right_bottom_title" name="title"> <span
														class="help-block"></span>
												</div>
											</div>
											<div class="form-group">
												<label class="col-sm-2 control-label"
													for="right_bottom_desc">描述</label>
												<div class="col-sm-10">
													<input type="text" class="form-control"
														id="right_bottom_desc" name="title"> <span
														class="help-block"></span>
												</div>
											</div>
											<div class="" style="display: none;">
												<input type="checkbox" id="coverSwitch"
													style="margin-left: 65px;" /> <i style="margin-left: 5px;">封面</i>
												<i style="font-size: 12px;">(公告中添加封面)</i>
											</div>
											<div class="form-group">
												<label class="col-sm-2 control-label" for="noticeContent">图片</label>
												<div class="col-sm-6">
													<button class="btn btn-success fileinput-button"
														type="button">
														<i class="glyphicon glyphicon-upload"></i> <span>选择图片</span>
														<input id="right_bottom_fileupload" type="file"
															name="files[]" data-url="controller/upload/7"
															accept=".jpg,.png,.gif"> <input type="hidden"
															id="right_bottom_fileName" /> <input type="hidden"
															id="right_bottom_position" value="22" />
													</button>
													<span class="help-block"></span>
													<!-- The global progress bar -->
													<div id="right_bottom_progress" class="progress hide">
														<div class="progress-bar progress-bar-success"
															style="width: 0;"></div>
													</div>
													<!-- The container for the uploaded files -->
													<!-- preview -->
													<div class="cover_wrp" id="right_bottom_files">
														<a class="example-image-link" href="" data-lightbox="3"><img
															class="cover default" src="" alt="图片预览" /> </a> <!-- <i
															class="cover default">图片预览</i> -->
													</div>
												</div>
											</div>
										</form>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="btn-toolbar">
					<button class="btn btn-primary" id="save" style="float: right;">保存</button>
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
</body>
</html>
