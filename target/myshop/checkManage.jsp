<!--审核管理界面-->
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
<link rel="stylesheet" href="/js/vendor/icheck/skins/minimal/minimal.css" />
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
	var currentLoc = 1;
	function loadPage() {
		$.ajax({
			url : "/check/getClothesCount",
			dataType : "json",
			type : "post",
			success : function(data) {
				$("#all").text(data.allCount);
				$("#soldOut").text(data.soldOutCount);
			}
		});
		InitClothesTable(1);
		$("#allcheck").attr("checked", false);
		$('input').iCheck({
			handle : 'checkbox',
			checkboxClass : 'icheckbox_minimal'
		});
	}
	function getDate(tm) {
		return new Date(parseInt(tm)).toLocaleString();
	}
	function pageCallback(index, jq) {
		currentPageVar = index + 1;
		InitClothesTable(currentPageVar);
	}
	function clothesDetail_Click(id) {

		location.href = "clothesDetail.jsp?id=" + id;
	}
	function showPagination() {
		$("#list_find_clothes").pagination(parseInt(totalPage), {
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
	function InitClothesTable(pageIndex) {
		$('#search_btn').show();
		$('#input_search').show();
		$('#photo_del').hide();
		$('#warn').hide();
		currentPageVar = pageIndex;
		currentLoc = 1;
		$
				.ajax({
					url : "check/getClothesList/" + currentPageVar,
					data : {
						currentPage : currentPageVar,
						pageSize : pageSizeVar,
						keyword : serachVar
					},
					dataType : "json",
					type : "post",
					success : function(data) {

						if (data.success) {
							$("#table_clothes").empty();
							//显示列表
							for (var i = 0; i < data.result.length; i++) {
								idArray[i] = data.result[i].id;
								var tempStatus = data.result[i].isxiajia;
								switch (tempStatus) {
								case "0":
									tempStatus = "正常";
									break;
								case "1":
									tempStatus = "已下架";
									break;
								}
								var publishertype = data.result[i].fabuzhelx == 'kehu' ? '0' : '1';
								var tempid = i + 1;
								$("#table_clothes")
										.append(
												"<tr>'"
														+ "<td>"
														+ tempid
														+ "</td>"
														+ "<td>"
														+ data.result[i].zhizuolxname
														+ "</td>"
														+ "<td>"
														+ data.result[i].mingcheng
														+ "</td>"
														+ "<td>"
														+ data.result[i].jiage
														+ "</td>"
														+ "<td>"
														+ "<a href='#' onclick='turnToUserDetail("
														+ publishertype
														+ ","
														+ data.result[i].fabuzheid
														+ ")'>"
														+ data.result[i].username
														+ "</a>"
														+ "</td>"
														+ "<td>"
														+ tempStatus
														+ "</td>"
														+ "<td><a href='#' onclick='clothesDetail_Click("
														+ data.result[i].id
														+ ")'>详情</a></td>"
														+ "</tr>");
							}
							//显示分布按钮							
							totalPage = data.totalPage;

							if (Number(totalPage) > pageSizeVar) {
								$("#list_find_clothes").show();
								showPagination();
							} else {
								$("#list_find_clothes").hide();
							}

							$("#error_find_clothes").hide();
						} else {
							$("#error_find_clothes").show();
						}
					}
				});
	}

	function deletePhoto() {

		$("#photo_del")
				.on(
						'click',
						function() {
							var checkedItems = inputChecked("table_list");
							if (checkedItems.length < 1) {
								layer.msg("请选择要删除的照片", 2, {
									type : 1,
									shade : false
								});
								return false;
							}
							if (checkedItems.length > 0) {
								layer
										.confirm(
												'确定删除吗？',
												function(index) {

													console
															.log(checkedItems.length);
													if (checkedItems.length === 1) {
														var _id = Number(checkedItems[0]
																.getAttribute("primary_id"));
														var _type = Number(checkedItems[0]
														.getAttribute("photo_type"));
														$
																.ajax({
																	url : "/check/deletePhoto/",
																	type : "post",
																	data : {
																		type : _type,
																		id : _id
																	},
																	dataType : "json",
																	success : function(
																			data) {
																		if (data.success) {
																			layer
																					.msg(
																							"删除成功",
																							2,
																							{
																								type : 1,
																								shade : false
																							});
																			$('#photo_list').checked=true;  
																			InitPhotoTable(1);
																		}
																	}
																});
													} else {
														var idstr = "";
														var typestr = "";
														for (var i = 0; i < checkedItems.length; i++) {
															var obj = checkedItems[i];
															var _id = Number(obj
																	.getAttribute("primary_id"));
															var _type = Number(obj
																	.getAttribute("photo_type"));
															if (i == checkedItems.length - 1) {
																idstr += _id;
																typestr += _type;
															} else {
																idstr += _id
																		+ ",";
																typestr += _type
																+ ",";
															}
															
														}
														$
																.ajax({
																	url : "/check/batchDeletePhoto/",
																	type : "post",
																	dataType : "json",
																	data : {
																		types : typestr,
																		ids : idstr
																	},
																	success : function(
																			data) {
																		if (data.success) {
																			layer
																					.msg(
																							"删除成功",
																							2,
																							{
																								type : 1,
																								shade : false
																							});
																			for (var i = 0; i < checkedItems.length; i++) {
																				var obj1 = checkedItems[i];
																				$(
																						obj1)
																						.parents(
																								"tr")
																						.remove();
																			}
																			$('#photo_list').checked=true; 
																			InitPhotoTable(1);
																			$(input).iCheck('uncheck');
																		}
																	}
																});
													}
													layer.close(index);
												});
							}
						});
	}

	function warn() {

		$("#warn")
				.on(
						'click',
						function() {
							var checkedItems = inputChecked("table_list");
							if (checkedItems.length < 1) {
								layer.msg("请选择要发送的警告图片", 2, {
									type : 1,
									shade : false
								});
								return false;
							}
							if (checkedItems.length > 1){
								layer.msg("发送警告暂时不支持批量操作", 2, {
									type : 1,
									shade : false
								});
								return false;
							}
							if (checkedItems.length > 0) {
								layer
										.confirm(
												'确定发送吗？',
												function(index) {

													console
															.log(checkedItems.length);
													if (checkedItems.length === 1) {
														var _id = Number(checkedItems[0]
																.getAttribute("primary_id"));
														var _type = Number(checkedItems[0]
														.getAttribute("photo_type"));
														$
																.ajax({
																	url : "/check/warn/",
																	type : "post",
																	data : {
																		type : _type,
																		id : _id
																	},
																	dataType : "json",
																	success : function(
																			data) {
																		if (data.success) {
																			layer
																					.msg(
																							"发送成功",
																							2,
																							{
																								type : 1,
																								shade : false
																							});
																			$('#photo_list').checked=true;  
																			InitPhotoTable(1);
																		}
																	}
																});
													} 
													layer.close(index);
												});
							}
						});
	}
	
	function turnToClothesDetail(typeId) {
		location.href = "clothesDetail.jsp?id=" + typeId;
	}

	function turnToUserDetail(type, typeId) {
		//客户
		if (0 == type) {
			location.href = "buyerDetail.jsp?id=" + typeId;
		//商户
		} else {
			location.href = "salerDetail.jsp?id=" + typeId;
		}
		
	}

	//注册函数
	$(function() {
		$('#input_search').bind('keypress', function(event) {
			if (event.keyCode == "13") {
				button_search();
			}
		});
		
		deletePhoto();
		checkAllEvent();
		warn();
	});
	function pageCallback_lock(index, jq) {

		currentPageVar_lock = index + 1;
		InitPhotoTable(currentPageVar_lock);

	}
	function clothesDetail_Click_check(id) {

		location.href = "memberPendingDet.jsp?id=" + id;
	}
	function showPagination_check() {
		$("#list_find_photo").pagination(parseInt(totalPage_lock), {
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
	function InitPhotoTable(pageIndex) {
		$('#search_btn').hide();
		$('#input_search').hide();
		$('#photo_del').show();
		$('#warn').show();
		$("#allcheck").attr("checked", false);
		currentPageVar_lock = pageIndex;
		currentLoc = 2;
		$
				.ajax({
					url : "check/getPhotoList/" + currentPageVar,
					data : {
						currentPage : currentPageVar,
						pageSize : pageSizeVar,
					},
					dataType : "json",
					type : "post",
					success : function(data) {
						if (data.success) {
							$("#table_photo").empty();
							//显示列表
							for (var i = 0; i < data.result.length; i++) {
								idArray[i] = data.result[i].id;
								var tempid = i + 1;
								var obj = 
								$("#table_photo")
										.append(
												"<tr>'" + "<td>"
														+ tempid
														+ "</td>"
														+ "<td>"
														+ "<a href='"+data.result[i].url+"' target='_blank'><img width='150' height='150' src='"
															+data.result[i].url+"' align='middle''></a>"
														+ "</td>"
														+ "<td>"
														+ data.result[i].tupianmc
														+ "</td>"
														+ "<td>"
														+ data.result[i].jubaocs
														+ "</td>"
														+ "<td>"
														+ data.result[i].rukusj
														+ "</td>"
														+ "<td>"
														+ "<a href='#' onclick='turnToClothesDetail("
														+ data.result[i].typeId
														+ ")'>"
														+ data.result[i].goodsname
														+ "</a>"
														+ "</td>"
														+ "<td>"
														+ "<a href='#' onclick='turnToUserDetail("
														+ data.result[i].publisherType
														+ ","
														+ data.result[i].publisherId
														+ ")'>"
														+ data.result[i].publisher
														+ "</a>"
														+ "</td>"
														+ "<td>"
														+ "<input type='checkbox' photo_type='" + data.result[i].type + "' "
														+ " primary_id='"
														+ data.result[i].id + "'" + "/>"
														+ "</td>" + "</tr>");
								$('input').iCheck({
									handle : 'checkbox',
									checkboxClass : 'icheckbox_minimal'
								});
							}
							//显示分布按钮
							totalPage_lock = data.totalPage;
							if (Number(totalPage_lock) > pageSizeVar_lock) {
								$("#list_find_photo").show();
								showPagination_check();

							} else {
								$("#list_find_photo").hide();
							}

							$("#error_find_photo").hide();
						} else {
							$("#error_find_photo").show();
						}
					}
				});

	}
	function button_search() {
		serachVar = $("#input_search").attr("value");
		if (currentLoc == 1) {
			InitClothesTable(1);
		} else {
			InitPhotoTable(1);
		}
	}

	/**
	 * 返回 所有已被选中的checkbox
	 * @param id [夫元素id]
	 * @returns {Array}
	 */
	function inputChecked(id) {
		var inputObj = get(id).getElementsByTagName("input");
		var inputAry = [];
		for (var i = 0; i < inputObj.length; i++) {
			var input = inputObj[i];
			if ($(input).parent().hasClass("checked")) {
				if (input.getAttribute('photo_type') && input.getAttribute('primary_id')) {
					inputAry.push(input);
				}
			}
		}
		return inputAry;
	}
	 /**
	 *
	 * @returns {*}
	 */
	function get() {
	    var elements = new Array();

	    for (var i = 0; i < arguments.length; i++) {
	        var element = arguments[i];

	        if (typeof element == 'string') {
	            element = document.getElementById(element);
	        }
	        if (arguments.length == 1) {
	            return element;
	        }

	        elements.push(element);
	    }
	    return elements;
	}
	 
	 /**
	  * 全选事件触发
	  */
	 function checkAllEvent(){

	     $(document).on('ifToggled','#allcheck',function(event){
	         var isCheck = event.target.checked;
	         checkAll(isCheck);
	     });
	     
	 }
	 
	 /**
	  * checkbox 全选,全不选
	  */
	 function checkAll(txt){

	     var inputObj = get("table_list").getElementsByTagName("input");

	     for (var i = 1; i < inputObj.length; i++) {
	         var input = inputObj[i];
	         if(input.type === "checkbox"){
	             if(txt){
	                 $(input).iCheck('check');
	             }else{
	                 $(input).iCheck('uncheck');
	             }
	         }
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
							<font size="4">成衣秀总数:<span id="all"></span> 被下架数:<span
								id="soldOut"></span></font>
							<button id="search_btn" class="btn" type="button"
								style="float: right" onclick="button_search()">
								<i class="icon-search" style="padding-right: 5px;"></i>搜索
							</button>
							<button id="warn" class="btn" type="button"
								style="float: right">
								 <i class="icon-exclamation-sign" style="padding-right: 5px;"></i>发送警告
							</button>
							<button id="photo_del" class="btn" type="button"
								style="float: right;margin-right: 10px;">
								 <i class="icon-trash" style="padding-right: 5px;"></i>删除照片
							</button>
							<input class="input-xlarge"
								style="float: right; margin-right: 10px" placeholder="请输入商品名称"
								id="input_search" type="text">
						</form>
						<div class="well">
							<ul class="nav nav-tabs">
								<li id="clothes_li" class="active"><a href="#clothes_check"
									data-toggle="tab" onclick="InitClothesTable(1)">成衣秀审核</a></li>
								<li id="photo_list"><a href="#photo_check" data-toggle="tab"
									onclick="InitPhotoTable(1)">举报图片审核</a></li>
							</ul>
							<div id="myTabContent" class="tab-content">
								<div class="tab-pane active in" id="clothes_check">
									<table class="table table-hover">
										<thead>
											<tr>
												<th>序号</th>
												<th>制作类型</th>
												<th>名称</th>
												<th>价格</th>
												<th>发布人</th>
												<th>状态</th>
												<th></th>
											</tr>
										</thead>
										<tbody id="table_clothes">
										</tbody>
									</table>
									<p id="error_find_clothes" style="color: red; display: none;">没有相关数据,请查证后刷新.</p>
									<div id="list_find_clothes" class="quotes"
										style="display: none"></div>
								</div>
								<div class="tab-pane fade" id="photo_check">
									<table class="table table-hover" id="table_list">
										<thead>
											<tr>
												<th>序号</th>
												<th>图片</th>
												<th>名称</th>
												<th>举报次数</th>
												<th>上传时间</th>
												<th>所属商品</th>
												<th>发布人</th>
												<th style="width: 5%;"><input
													type="checkbox" id="allcheck" /></th>
											</tr>
										</thead>
										<tbody id="table_photo">
										</tbody>
									</table>
									<p id="error_find_photo" style="color: red; display: none;">没有相关数据,请查证后刷新.</p>
									<div id="list_find_photo" class="quotes" style="display: none"></div>
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
	<script src="/js/vendor/icheck/icheck.min.js"></script>
	<script src="js/layer/layer.min.js"></script>
</body>
</html>


