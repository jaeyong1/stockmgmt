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
	<h2>${reqresult}</h2>


	<p>회원 관리</p>
	<p>[신규추가]</p>
	<form role="form" method="post">
		<table border="1" width="487">
			<tr>
				<td width="143">
					<p>user id</p>
				</td>
				<td width="328">
					<p>
						<input type="text" name='user-Id'>
					</p>
				</td>
			</tr>
			<tr>
				<td width="143">
					<p>name</p>
				</td>
				<td width="328">
					<p>
						<input type="text" name='user-Name'>
					</p>
				</td>
			</tr>

			<tr>
				<td width="143">
					<p>email</p>
				</td>
				<td width="328">
					<p>
						<input type="text" name='user-Email'>
					</p>
				</td>
			</tr>
			<tr>
				<td width="143">
					<p>team name</p>
				</td>
				<td width="328">
					<p>
						<input type="text" name='user-Teamname'>
					</p>
				</td>
			</tr>
			<tr>
				<td width="143">
					<p>password</p>
				</td>
				<td width="328">
					<p>
						<input type="text" name='user-Password'>
					</p>
				</td>
			</tr>			
			<tr>
				<td width="143">
					<p>level(숫자로..)</p>
				</td>
				<td width="328">
					<p>
						<input type="text" name='user-Level'>
					</p>
				</td>
			</tr>
		</table>
		<button type="submit">submit</button>
	</form>


	<p>&nbsp;</p>
	<p>[회원정보 수정]</p>
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
