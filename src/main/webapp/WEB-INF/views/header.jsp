<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="java.util.*"%>

<!-- jquery -->
<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
<script language="javascript">
var rolekor ="";
	$(function() {			
		rolekor = lev2Kor(${sessionScope.userLoginInfo.userLevel});
		console.log(rolekor);
		$('#userinfo').html('('+rolekor +')');
	//로그인후에 표시	
	});
	
	function lev2Kor(userLevel) {
		if (userLevel == 2)
			return '개발담당자';
		else if (userLevel == 3)
			return '출고담당자';
		else if (userLevel == 4)
			return '손님';
		else if (userLevel == 5)
			return '관리자';
		return '관리자.개발자';
	}
	
	function jsLogin() {
		var id = document.forms["formlogin"].elements["login-id"].value;
		var pw = document.forms["formlogin"].elements["login-pw"].value;
		//console.log(id + "/" + pw);
		document.forms["formlogin"].method = "post";
		document.forms["formlogin"].action = "/loginProcess";
		document.forms["formlogin"].submit();
		//var response = confirm("Login Continue");
	}
</script>


<!DOCTYPE html>
<head>
<meta http-equiv="content-type" content="text/html; charset=euc-kr">
<title>LGIT 자재관리시스템</title>
<meta name="" content="">
</head>







<!-- Bootstrap core CSS -->
<!-- <link href="/bootstrap/css/bootstrap.min.css" rel="stylesheet"> -->
<link href="/bootstrap/css/cerulean/bootstrap.min.css" rel="stylesheet">

<body>

	<!-- Static navbar -->
	<nav class="navbar navbar-default navbar-static-top">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#navbar" aria-expanded="false"
					aria-controls="navbar">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#">Inno Parts System</a>

			</div>

			<div id="navbar" class="navbar-collapse collapse">
				<ul class="nav navbar-nav">

					<c:choose>
						<c:when test="${(3 == sessionScope.userLoginInfo.userLevel) }">

							<li class="active"><a href="/shipreqlist">출고요청처리</a></li>
							<li><a href="/myinventorycontrol">재물조사수행</a></li>
						</c:when>
					</c:choose>

					<c:choose>
						<c:when test="${(2 == sessionScope.userLoginInfo.userLevel) }">
							<li class="active"><a href="/mylist">나의자재</a></li>
							<li class="dropdown"><a href="#" class="dropdown-toggle"
								data-toggle="dropdown" role="button" aria-haspopup="true"
								aria-expanded="false">출고요청하기<span class="caret"></span></a>
								<ul class="dropdown-menu">
									<li><a href="/shipparts">출고요청 부품리스트</a></li>
									<li><a href="/shipreq">출고요청서 작성</a></li>
								</ul></li>
							<li><a href="/shipreqlist">출고진행상황</a></li>
							<li><a href="/otherslist">파트너자재</a></li>							
							<li class="dropdown"><a href="#" class="dropdown-toggle"
								data-toggle="dropdown" role="button" aria-haspopup="true"
								aria-expanded="false">파트너출고요청<span class="caret"></span></a>
								<ul class="dropdown-menu">
									<li><a href="/shipothersparts">출고요청 부품리스트</a></li>
									<li><a href="/shipothersreq">출고요청서 작성</a></li>
									<li role="separator" class="divider"></li>
									<li><a href="/myconfirmshipreqlist">파트너출고 승인하기</a></li>
								</ul></li>
								
							
						</c:when>
					</c:choose>


					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown" role="button" aria-haspopup="true"
						aria-expanded="false">DataBase관리<span class="caret"></span></a>
						<ul class="dropdown-menu">
							<c:choose>
								<c:when test="${(2 == sessionScope.userLoginInfo.userLevel) }">

									<li><a href="/myproject">Project 관리</a></li>
									<li><a href="/myparts">Parts 관리</a></li>
									<li role="separator" class="divider"></li>
								</c:when>
							</c:choose>

							<li><a href="/helppage">도움말</a></li>
							<c:choose>
								<c:when test="${(5 == sessionScope.userLoginInfo.userLevel) }">
									<li role="separator" class="divider"></li>
									<li><a href="/admin/user">User Mgmt</a></li>
									<li><a href="/admin/project">Project Mgmt</a></li>
									<li><a href="/admin/parts">Parts Mgmt</a></li>
								</c:when>
							</c:choose>
						</ul></li>
				</ul>

				<ul class="nav navbar-nav navbar-right">

					<li><c:choose>
							<c:when test="${not empty sessionScope.userLoginInfo}">
								<c:out value="${sessionScope.userLoginInfo.userName}" />님 로그인중
								<div id="userinfo"></div>
							</c:when>
						</c:choose></li>
					<li class="active"><c:choose>
							<c:when test="${not empty sessionScope.userLoginInfo}">
								<a href="logout">Logout</a>
							</c:when>
							<c:otherwise>
								<!-- 로그인이 필요함 -->
								<script>
								alert('로그인하세요^^');
								location.href = 'login';
							</script>
							</c:otherwise>
						</c:choose> <span class="sr-only">(current)</span></a></li>
					<li></li>
				</ul>
			</div>
			<!--/.nav-collapse -->
		</div>
	</nav>

	<div class="container">

		<!-- Main component for a primary marketing message or call to action -->


		<!-- 
	********************************************************
	HEADER
	******************************************************** 
	-->