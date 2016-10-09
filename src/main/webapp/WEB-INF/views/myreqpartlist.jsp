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


<script language="javascript">
	function modifyCheck2(id) {
		var nm = document.forms["form" + id].elements["itemlist-Id"].value;
		var response = confirm(nm + "데이터 수정할까요?")
		if (response) {
			//do yes task
			document.forms["form" + id].method = "post";
			document.forms["form" + id].action = "/admin/reqprojectmodify"
			document.forms["form" + id].submit();
		} else {
			//do no task
		}
	}
	function removeItem(id) {
		var nm = document.forms["form" + id].elements["itemlist-Id"].value;
		var response = confirm(nm + "데이터 삭제할까요?")
		if (response) {
			//do yes task
			document.forms["form" + id].method = "post";
			document.forms["form" + id].action = "/admin/reqprojectremove"
			document.forms["form" + id].submit();
		} else {
			//do no task
		}
	}
</script>
<!DOCTYPE html>
<head>
<meta http-equiv="content-type" content="text/html; charset=euc-kr">
<title>LGIT 자재관리시스템</title>
<meta name="" content="">
</head>

<body>

	<c:choose>
		<c:when test="${not empty sessionScope.userLoginInfo}">
			<!--   ID : <c:out value="${sessionScope.userLoginInfo.userId}" /> -->
			<!-- Name : ${sessionScope.userLoginInfo.userName}님 -->
			<!-- Level : <c:out value="${sessionScope.userLoginInfo.userLevel}" /> -->
			<c:out value="${sessionScope.userLoginInfo.userId}" /> 님
			<div id="userinfo"></div>


			<!-- Team : <c:out value="${sessionScope.userLoginInfo.userTeamname}" />  -->
			<a href="logout">로그아웃</a>

		</c:when>
		<c:otherwise>
			<!-- 로그인 안되어 있으면 로그인페이지로 -->
			<script>alert('로그인 하세요');location.href='login';</script>
		</c:otherwise>
	</c:choose>
	<!-- 
	********************************************************
	HEADER
	******************************************************** 
	-->

	<br>
	<p align="center">출고요청의 파츠리스트 조회</p>

	<!-- 
	*********
	리스트 표시 
	*********
	 -->

	<table border="1">
		<tr>
			<td width="67">
				<p align="center">No</p>
			</td>
			<td width="135">
				<p align="center">출고요청 Seq</p>
			</td>
			<td width="135">
				<p align="center">프로젝트Code</p>
			</td>
			<td width="135">
				<p align="center">LGIT P/N</p>
			</td>
			<td width="169">
				<p align="center">Item Desc</p>
			</td>
			<td width="86">
				<p align="center">재고수량</p>
			</td>
			<td width="76">
				<p align="center">요청수량</p>
			</td>
			<td width="100"></td>
		</tr>
		<!-- DB 데이터 채움 (클래스 변수사용) -->
		<c:forEach var="i" items="${items}" varStatus="status">

			<form name="form${status.index}">
				<tr>
					<td width="67"><input type=hidden name=itemlist-Id
						value='${i.itemlistId}'> ${status.index}
						(sn:${i.itemlistId})</td>

					<td width="135">
						<p align="center">${i.itemlistShipId}</p>
					</td>
					<td width="135">
						<p align="center">${i.partProjectCode}</p>
					</td>
					<td width="135">
						<p align="center">${i.itemlistPartId}</p>
					</td>
					<td width="169">
						<p align="center">${i.partDesc}</p>
					</td>
					<td width="86">
						<p align="center">${i.partStock}</p>

					</td>
					<td width="76">
						<p align="center">
							<input type=text name=itemlist-Amount size=5
								value='${i.itemlistAmount}'>
						</p>
					</td>
					<td width="100">
						<p>
							<input type="button" value="수정" name="submitbtn"
								OnClick="javascript:modifyCheck2('${status.index}');"> <input
								type="button" value="삭제" name="submitbtn2"
								OnClick="javascript:removeItem('${status.index}');">
						</p>


					</td>
				</tr>
			</form>
		</c:forEach>

	</table>

	<!--
	********************************************************
	FOOTER 
	******************************************************** 
	-->
</body>

</html>
