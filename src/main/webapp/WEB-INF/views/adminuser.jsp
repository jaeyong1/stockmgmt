<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="java.util.*"%>

<html>

<head>
<meta http-equiv="content-type" content="text/html; charset=euc-kr">
<title>회원 관리</title>
<meta name="generator" content="Namo WebEditor v5.0">
</head>

<body bgcolor="white" text="black" link="blue" vlink="purple"
	alink="red">
	<p>회원 관리</p>
	<table border="1">
		<tr>
			<td width="199">
				<p>user id</p>
			</td>
			<td width="199">
				<p>name</p>
			</td>
			<td width="199">
				<p>email</p>
			</td>
			<td width="199">
				<p>team</p>
			</td>
			<td width="199">
				<p>level</p>
			</td>
			<td width="199">
				<p>&nbsp;</p>
			</td>
		</tr>
		<!-- DB 데이터 채움 (클래스 변수사용) -->
		<c:forEach var="i" items="${items}">
			<tr>
				<td width="199">
					<p>${i.userId}</p>
				</td>
				<td width="199">
					<p>${i.userPassword}</p>
				</td>
				<td width="199">
					<p>${i.userEmail}</p>
				</td>
				<td width="199">
					<p>${i.userTeamname}</p>
				</td>
				<td width="199">
					<p>${i.userLevel}</p>
				</td>
				<td width="199">
					<form name="form14">
						<p>
							<input type="submit" name="formbutton1" value="업데이트"> <input
								type="submit" name="formbutton1" value="비번초기화">
						</p>
					</form>
				</td>
			</tr>
		</c:forEach>

	</table>
	<p>&nbsp;</p>
</body>

</html>
