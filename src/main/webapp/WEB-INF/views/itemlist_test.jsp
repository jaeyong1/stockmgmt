<jsp:include page="header.jsp"/>

<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

 
<html>

<head>
<title>재고 조회</title>
</head>

<body bgcolor="white" text="black" link="blue" vlink="purple"
	alink="red">
	<p align="center">재고 조회</p>
	<form name="form2">
		<p align="right">
			&nbsp;<select name="srchtype" size="1">
				<option selected value="projcode">프로젝트Code</option>
				<option value="shipperid">출고담당자ID</option>
				<option value="devid">개발담당자ID</option>
				<option value="projname">프로젝트명</option>
				<option value="lgitpn">LGIT P/N</option>
			</select> <input type="text" name="srchword"> <input type="submit"
				name="btnsrch" value="검색"> <input type="submit"
				name="exportxls" value="선택엑셀받기"> <input type="submit"
				name="addshiplist" value="출고요청담기">
		</p>
	</form>
	<h2>Hello! ${name}</h2>

	<h2>Spring MVC and List Example</h2>

	<table border="1" width="1128">
		<tr>
			<td width="42">
				<p>&nbsp;</p>
			</td>
			<td width="192">
				<p>&nbsp;프로젝트 Code</p>
			</td>
			<td width="85">
				<p>부서</p>
			</td>
			<td width="129">
				<p>개발담당자</p>
			</td>
			<td width="107">
				<p>출고담당자</p>
			</td>
			<td width="112">
				<p>LGIT P/N</p>
			</td>
			<td width="126">
				<p>Item&nbsp;Desc</p>
			</td>
			<td width="90">
				<p>재고</p>
			</td>
			<td width="67">
				<p>위치</p>
			</td>
			<td width="114">
				<p>비고</p>
			</td>
		</tr>
		<tr>
			<td width="42">
				<p>
					&nbsp;<input type="checkbox" name="formcheckbox1">
				</p>
			</td>
			<td width="192">
				<p>&nbsp;</p>
			</td>
			<td width="85">
				<p>&nbsp;</p>
			</td>
			<td width="129">
				<p>&nbsp;</p>
			</td>
			<td width="107">
				<p>&nbsp;</p>
			</td>
			<td width="112">
				<p>&nbsp;</p>
			</td>
			<td width="126">
				<p>&nbsp;</p>
			</td>
			<td width="90">
				<p>&nbsp;</p>
			</td>
			<td width="67">
				<p>&nbsp;</p>
			</td>
			<td width="114">
				<p>&nbsp;</p>
			</td>
		</tr>
		<tr>
			<td width="42">
				<p>
					&nbsp;<input type="checkbox" name="formcheckbox1">
				</p>
			</td>
			<td width="192">
				<p>&nbsp;</p>
			</td>
			<td width="85">
				<p>&nbsp;</p>
			</td>
			<td width="129">
				<p>&nbsp;</p>
			</td>
			<td width="107">
				<p>&nbsp;</p>
			</td>
			<td width="112">
				<p>&nbsp;</p>
			</td>
			<td width="126">
				<p>&nbsp;</p>
			</td>
			<td width="90">
				<p>&nbsp;</p>
			</td>
			<td width="67">
				<p>&nbsp;</p>
			</td>
			<td width="114">
				<p>&nbsp;</p>
			</td>
		</tr>
		<tr>
			<td width="42">
				<p>
					&nbsp;<input type="checkbox" name="formcheckbox1">
				</p>
			</td>
			<td width="192">
				<p>&nbsp;</p>
			</td>
			<td width="85">
				<p>&nbsp;</p>
			</td>
			<td width="129">
				<p>&nbsp;</p>
			</td>
			<td width="107">
				<p>&nbsp;</p>
			</td>
			<td width="112">
				<p>&nbsp;</p>
			</td>
			<td width="126">
				<p>&nbsp;</p>
			</td>
			<td width="90">
				<p>&nbsp;</p>
			</td>
			<td width="67">
				<p>&nbsp;</p>
			</td>
			<td width="114">
				<p>&nbsp;</p>
			</td>
		</tr>
	</table>
	<p align="center">&nbsp;[1] [2] [3] ... [100]</p>
<jsp:include page="footer.jsp"/>

