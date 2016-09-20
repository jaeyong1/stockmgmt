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
	<p>Parts 관리</p>
	<table border="1">
		<tr>
			<td width="130">
				<p>parts id(idx)</p>
			</td>
			<td width="130">
				<p>프로젝트코드</p>
			</td>
			<td width="130">
				<p>LGIT P/N</p>
			</td>
			<td width="130">
				<p>Desc</p>
			</td>
			<td width="130">
				<p>위치</p>
			</td>
			<td width="130">
				<p>단가</p>
			</td>
			<td width="130">
				<p>재고</p>
			</td>
			<td width="130">
				<p>비고</p>
			</td>
			<td width="130">
				<p>&nbsp;</p>
			</td>
		</tr>

		<!-- DB 데이터 채움 (클래스 변수사용) -->
		<c:forEach var="i" items="${items}">
			<tr>
				<td width="130">
					<p>${i.partId}</p>
				</td>
				<td width="130">
					<p>${i.partProjectCode}</p>
				</td>
				<td width="130">
					<p>${i.partName}</p>
				</td>
				<td width="130">
					<p>${i.partDesc}</p>
				</td>
				<td width="130">
					<p>${i.partLocation}</p>
				</td>
				<td width="130">
					<p>${i.partCost}</p>
				</td>
				<td width="130">
					<p>${i.partStock}</p>
				</td>
				<td width="130">
					<p>${i.partMemo}</p>
				</td>
				<td width="130">
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
	<p align="center">[1][2][3]...[9]</p>
	<form name="form2">
		<p align="center">
			&nbsp;<select name="srchtype" size="1">
				<option selected value="projcode">프로젝트Code</option>
				<option value="shipperid">출고담당자ID</option>
				<option value="devid">개발담당자ID</option>
				<option value="projname">프로젝트명</option>
				<option value="lgitpn">LGIT P/N</option>
			</select> <input type="text" name="srchword"> <input type="submit"
				name="btnsrch" value="검색">
		</p>
	</form>
</body>

</html>
