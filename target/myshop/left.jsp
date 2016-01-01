<!-- 左边菜单栏 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="sidebar-nav">
	<div class="nav-header"
		onClick="window.location.href='orderManage.jsp'"
		data-target="#legal-menu">
		<i class="icon-align-justify"></i>订单管理
	</div>
	<ul id="legal-menu" class="nav nav-list collapse in">
	</ul>
	<div class="nav-header" data-target="#dashboard-menu"
		onClick="window.location.href='buyerManage.jsp'">
		<i class="icon-user"></i>客户管理
	</div>
	<ul id="dashboard-menu" class="nav nav-list collapse in">
	</ul>
	<div class="nav-header" data-target="#accounts-menu"
		onClick="window.location.href='salerManage.jsp'">
		<i class="icon-user"></i><span class="active">商户管理</span>
	</div>
	<ul id="accounts-menu" class="nav nav-list collapse in">
	</ul>
	<div class="nav-header" data-target="#accounts-menu"
		onClick="window.location.href='checkManage.jsp'">
		<i class="icon-briefcase"></i><span class="active">审核管理</span>
	</div>
	<ul id="accounts-menu" class="nav nav-list collapse in">
	</ul>

	<div class="nav-header" data-target="#settings-menu"
		onClick="window.location.href='goodsTypeManage.jsp'">
		<i class="icon-gift"></i>商品类型管理
	</div>
	<ul id="settings-menu" class="nav nav-list collapse in">
	</ul>
	<div class="nav-header"
		onClick="window.location.href='adsManage.jsp'"
		data-target="#legal-menu">
		<i class="icon-fire"></i>推荐商户管理
	</div>
	<ul id="legal-menu" class="nav nav-list collapse in">
	</ul>
	<div class="nav-header"
		onClick="window.location.href='modelManage.jsp'"
		data-target="#legal-menu">
		<i class="icon-list-alt"></i>模板管理
	</div>
	<ul id="legal-menu" class="nav nav-list collapse in">
	</ul>
	<div class="nav-header"
		onClick="window.location.href='buyerstatisticsManage.jsp'"
		data-target="#legal-menu">
		<i class="icon-edit"></i>客户统计功能
	</div>
	<ul id="legal-menu" class="nav nav-list collapse in">
	</ul>
	<div class="nav-header"
		onClick="window.location.href='salerstatisticsManage.jsp'"
		data-target="#legal-menu">
		<i class="icon-edit"></i>商户统计功能
	</div>
	<ul id="legal-menu" class="nav nav-list collapse in">
	</ul>
	<div class="nav-header"
		onClick="window.location.href='systemSetting.jsp'"
		data-target="#legal-menu">
		<i class="icon-cog"></i>系统设置
	</div>
	<ul id="legal-menu" class="nav nav-list collapse in">
	</ul>
<!--  	<div class="nav-header"
		onClick="window.location.href='useableOrderList.jsp'"
		data-target="#legal-menu">
		<i class="icon-align-justify"></i>有效订单
	</div>
	<ul id="legal-menu" class="nav nav-list collapse in">
	</ul> -->
</div>
