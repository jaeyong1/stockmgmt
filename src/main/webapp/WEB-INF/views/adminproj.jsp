
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="java.util.*"%>

<html>

<head>
<meta http-equiv="content-type" content="text/html; charset=euc-kr">
<title>제목 없음</title>
<meta name="generator" content="Namo WebEditor v5.0">
</head>

<body bgcolor="white" text="black" link="blue" vlink="purple"
	alink="red">
	<p align="center">프로젝트 관리</p>
	<p>[신규추가]</p>
	<table border="1" width="487">
		<tr>
			<td width="143">
				<p>프로젝트코드</p>
			</td>
			<td width="328">
				<p>&nbsp;</p>
			</td>
		</tr>
		<tr>
			<td width="143">
				<p>프로젝트이름</p>
			</td>
			<td width="328">
				<p>&nbsp;</p>
			</td>
		</tr>
		<tr>
			<td width="143">
				<p>개발담당자ID</p>
			</td>
			<td width="328">
				<p>&nbsp;</p>
			</td>
		</tr>
		<tr>
			<td width="143">
				<p>출고담당자ID</p>
			</td>
			<td width="328">
				<p>&nbsp;</p>
			</td>
		</tr>
	</table>
	<p>&nbsp;</p>
	<p>[데이터수정]</p>
	<table border="1">
		<tr>
			<td width="240">
				<p>프로젝트코드</p>
			</td>
			<td width="240">
				<p>프로젝트이름</p>
			</td>
			<td width="240">
				<p>개발담당자ID</p>
			</td>
			<td width="240">
				<p>출고담당자ID</p>
			</td>
			<td width="240">
				<p>&nbsp;</p>
			</td>
		</tr>
		<!-- DB 데이터 채움 (클래스 변수사용) -->
		<c:forEach var="i" items="${items}">
			<tr>
				<td width="240">
					<p>${i.projectCode}</p>
				</td>
				<td width="240">
					<p>${i.projectName}</p>
				</td>
				<td width="240">
					<p>${i.projectOwnerId}</p>
				</td>
				<td width="240">
					<p>${i.projectShipperId}</p>
				</td>
				<td width="240">
					<form name="form14">
						<p>
							<input type="submit" name="formbutton1" value="업데이트">
						</p>
					</form>
				</td>
			</tr>

		</c:forEach>
	</table>
	<p>&nbsp;</p>
</body>

</html>
