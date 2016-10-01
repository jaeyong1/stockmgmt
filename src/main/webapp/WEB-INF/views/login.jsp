<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

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

<body>
	<!-- HEADER -->
	<c:choose>
		<c:when test="${not empty sessionScope.userLoginInfo}">
			<!--   ID : <c:out value="${sessionScope.userLoginInfo.userId}" /> -->
			<!-- Name : ${sessionScope.userLoginInfo.userName}님 -->
			<!-- Level : <c:out value="${sessionScope.userLoginInfo.userLevel}" /> -->
			<c:out value="${sessionScope.userLoginInfo.userId}" /> 님
			<div id="userinfo"></div>


			<!-- Team : <c:out value="${sessionScope.userLoginInfo.userTeamname}" />  -->
			<a href="logout">로그아웃</a>
			<br>
			<br>
			<a href="page1">페이지1</a>&nbsp;&nbsp;<a href="page2">페이지2</a>
		</c:when>
		<c:otherwise>
			<form class="form-signin" name="formlogin">
				<h2 class="form-signin-heading">LGIT 자재관리시스템</h2>
				<!-- <label for="inputEmail" class="sr-only">Email address</label> -->

				<input type="text" id="login-id" name='login-id'
					class="form-control" placeholder="LG Innotek ID" required autofocus>
				<label for="inputPassword" class="sr-only">Password</label> <input
					type="password" id="login-pw" name='login-pw' class="form-control"
					placeholder="Password" required>

				<button class="btn btn-lg btn-primary btn-block"
					OnClick="javascript:jsLogin();">Log-in</button>
				<button type="button" class="btn btn-default btn-block"
					onclick="location.href='idregi/' ">ID Registration</button>
				<button type="button" class="btn btn-default btn-block">Change
					Password</button>
			</form>
		</c:otherwise>
	</c:choose>
	<!-- FOOTER -->
</body>

</html>