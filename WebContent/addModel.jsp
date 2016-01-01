<!-- 新增模板 -->
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
	function loadPage() {
		$.ajax({
			url : "check/getMadeTypeList",
			dataType : "json",
			type : "post",
			success : function(data) {
				if (data.success) {
					$("#made_type").empty();
					for (var i = 0; i < data.result.length; i++) {
						gradeArray[i] = data.result[i].id;
						$("#made_type").append(
								"<option value='"+data.result[i].id+"'>"
										+ data.result[i].name + "</option>");
					}
				} else {
				}
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

	function getDate(tm) {
		return new Date(parseInt(tm)).toLocaleString();
	}
	
	//注册函数
	$(function() {
		
        var front_obj = {
                fileInput:$("#front_fileupload"),
                context:$("#front_files"),
                hiddenInput:$("#front_fileName"),
                progress:'front_progress'
            };
            infoUpload(front_obj); 
        var back_obj = {
                fileInput:$("#back_fileupload"),
                context:$("#back_files"),
                hiddenInput:$("#back_fileName"),
                progress:'back_progress'
            };
            infoUpload(back_obj); 
        var side_obj = {
                fileInput:$("#side_fileupload"),
                context:$("#side_files"),
                hiddenInput:$("#side_fileName"),
                progress:'side_progress'
            };
            infoUpload(side_obj); 
			
            addModel();
            
	});
	
	/**
	 * 新增模板
	 */
	function addModel(){
		
			$("#save").on("click",function(){
				var name = $("#name").val();
				var price = $("#price").val();
				var description = $("#description").val();
	        	if(name === ""){
	        		layer.msg("请输入名称",2,{type : 1,shade : false});
	        		$("#name").focus();
	        		return false;
	        	}else if(name.length > 10){
	    			layer.msg("名称最长为10个字符, 请重新输入", 2, {
	    				type : 1,
	    				shade : false
	    			});
	    			$("#name").focus();
	    			return false;
	        	}
	        	
	        	if(description === ""){
	        		layer.msg("请输入描述",2,{type : 1,shade : false});
	        		$("#description").focus();
	        		return false;
	        	}else if(description.length > 30){
	        		layer.msg("描述最长为30个字符, 请重新输入",2,{type : 1,shade : false});
	        		$("#description").focus();
	        		return false;
	        	}
	        	
	        	if(price === ""){
	        		layer.msg("请输入价格",2,{type : 1,shade : false});
	        		$("#price").focus();
	        		return false;
	        	}else if(Number(price) <= 0 || isNaN(price)){
	        		layer.msg("请输入正确的价格",2,{type : 1,shade : false});
	        		$("#price").focus();
	        		return false;
	        	}else if(Number(price) > 999999){
	        		layer.msg("价格最高为999999, 请重新输入",2,{type : 1,shade : false});
	        		$("#price").focus();
	        		return false;
	        	}

	        	if($("#front_fileName").val() === ""){
	        		layer.msg("请上传正面照片",2,{type : 1,shade : false});
	        		$('#front_fileupload').focus();
	        		return false;
	        	}
	        	if($("#side_fileName").val() === ""){
	        		layer.msg("请上传侧面照片",2,{type : 1,shade : false});
	        		$('#side_fileupload').focus();
	        		return false;
	        	}
	        	if($("#back_fileName").val() === ""){
	        		layer.msg("请上传背面照片",2,{type : 1,shade : false});
	        		$('#back_fileupload').focus();
	        		return false;
	        	}
				var photoNameStr = $("#front_fileName").val() + "," + $("#side_fileName").val() + "," + $("#back_fileName").val();
	            var photoTypeStr =  $("#front_type").val() + "," + $("#back_type").val() + "," + $("#side_type").val();
	            $.ajax({
	                url:'/model/new',
	                type:"POST",
	                dataType:"json",
	                data:{
	                	ZhizuoLxID : $("#made_type").val(),
	                	Name : name,
	                	Jiage : price,
	                	photoNameStr : photoNameStr,
	                	photoTypeStr : photoTypeStr,
	                	description: description
	                },
	                success:function(data){
	                    if (data.success) {
	                    	layer.msg("新增成功",2,{type:1,shade:false});
	                        javascript:history.go(-1);
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
					新增模板<a href="javascript:history.go(-1);"
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
												<label class="col-sm-2 control-label" for="made_type">制作类型</label>
												<div class="col-sm-10">
													<select id="made_type"></select><span class="help-block"></span>
												</div>
											</div>
											<div class="form-group">
												<label class="col-sm-2 control-label" for="name">名称</label>
												<div class="col-sm-10">
													<input type="text" class="form-control" id="name"
														name="title"> <span class="help-block"></span>
												</div>
											</div>
											<div class="form-group">
												<label class="col-sm-2 control-label" for="name">描述</label>
												<div class="col-sm-10">
													<textarea  class="form-control" id="description"
														name="title" rows="3"></textarea>
													<span class="help-block"></span>
												</div>
											</div>
											<div class="form-group">
												<label class="col-sm-2 control-label" for="price">价格</label>
												<div class="col-sm-10">
													<input type="text" class="form-control" id="price"
														name="title"> <span class="help-block"></span>
												</div>
											</div>
											<div class="" style="display: none;">
												<input type="checkbox" id="coverSwitch"
													style="margin-front: 65px;" /> <i
													style="margin-front: 5px;">封面</i> <i
													style="font-size: 12px;">(公告中添加封面)</i>
											</div>
											<div class="form-group">
												<label class="col-sm-2 control-label" for="noticeContent">正面图片</label>
												<div class="col-sm-6">
													<button class="btn btn-success fileinput-button"
														type="button">
														<i class="glyphicon glyphicon-upload"></i> <span>选择图片</span>
														<input id="front_fileupload" type="file" name="files[]"
															data-url="controller/upload/8" accept=".jpg,.png,.gif">
														<input type="hidden" id="front_fileName" /> <input
															type="hidden" id="front_type" value="zhengmian" />
													</button>
													<span class="help-block"></span>
													<!-- The global progress bar -->
													<div id="front_progress" class="progress hide">
														<div class="progress-bar progress-bar-success"
															style="width: 0;"></div>
													</div>
													<!-- The container for the uploaded files -->
													<!-- preview -->
													<div class="cover_wrp" id="front_files">
														<a class="example-image-link" href="" data-lightbox="1"><img
															class="cover default" src="" alt="图片预览" /> </a> <!-- <i
															class="cover default">图片预览</i> -->
													</div>
												</div>
											</div>
											<div class="" style="display: none;">
												<input type="checkbox" id="coverSwitch"
													style="margin-front: 65px;" /> <i
													style="margin-front: 5px;">封面</i> <i
													style="font-size: 12px;">(公告中添加封面)</i>
											</div>
											<div class="form-group">
												<label class="col-sm-2 control-label" for="noticeContent">侧面图片</label>
												<div class="col-sm-6">
													<button class="btn btn-success fileinput-button"
														type="button">
														<i class="glyphicon glyphicon-upload"></i> <span>选择图片</span>
														<input id="side_fileupload" type="file" name="files[]"
															data-url="controller/upload/8" accept=".jpg,.png,.gif">
														<input type="hidden" id="side_fileName" /> <input
															type="hidden" id="side_type" value="cemian" />
													</button>
													<span class="help-block"></span>
													<!-- The global progress bar -->
													<div id="side_progress" class="progress hide">
														<div class="progress-bar progress-bar-success"
															style="width: 0;"></div>
													</div>
													<!-- The container for the uploaded files -->
													<!-- preview -->
													<div class="cover_wrp" id="side_files">
														<a class="example-image-link" href="" data-lightbox="2"><img
															class="cover default" src="" alt="图片预览" /> </a> <!-- <i
															class="cover default">图片预览</i> -->
													</div>
												</div>
											</div>
											<div class="" style="display: none;">
												<input type="checkbox" id="coverSwitch"
													style="margin-front: 65px;" /> <i
													style="margin-front: 5px;">封面</i> <i
													style="font-size: 12px;">(公告中添加封面)</i>
											</div>
											<div class="form-group">
												<label class="col-sm-2 control-label" for="noticeContent">背面图片</label>
												<div class="col-sm-6">
													<button class="btn btn-success fileinput-button"
														type="button">
														<i class="glyphicon glyphicon-upload"></i> <span>选择图片</span>
														<input id="back_fileupload" type="file" name="files[]"
															data-url="controller/upload/8" accept=".jpg,.png,.gif">
														<input type="hidden" id="back_fileName" /> <input
															type="hidden" id="back_type" value="beimian" />
													</button>
													<span class="help-block"></span>
													<!-- The global progress bar -->
													<div id="back_progress" class="progress hide">
														<div class="progress-bar progress-bar-success"
															style="width: 0;"></div>
													</div>
													<!-- The container for the uploaded files -->
													<!-- preview -->
													<div class="cover_wrp" id="back_files">
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
