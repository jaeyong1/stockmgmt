<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="java.util.*"%>

<script language="javascript">
	//신규가입 레벨선택
	function choice(o) {
		var v = o.value;
		document.forms["newuserform"].elements["user-Level"].value = v;
	}

	function newUser() {
		if (!document.forms["newuserform"].elements["user-Id"].value) {
			alert("Id를 기입해주세요");
			document.forms["newuserform"].elements["user-Id"].focus();
			return;
		}

		if (!document.forms["newuserform"].elements["user-Name"].value) {
			alert("이름을 기입해주세요");
			document.forms["newuserform"].elements["user-Named"].focus();
			return;
		}

		if (!document.forms["newuserform"].elements["user-Email"].value) {
			alert("Email을 기입해주세요");
			document.forms["newuserform"].elements["user-Email"].focus();
			return;
		}

		if (!document.forms["newuserform"].elements["user-Teamname"].value) {
			alert("소속팀명을 기입해주세요");
			document.forms["newuserform"].elements["user-Teamname"].focus();
			return;
		}

		if (!document.forms["newuserform"].elements["user-Password"].value) {
			alert("비빌번호를 기입해주세요");
			document.forms["newuserform"].elements["user-Password"].focus();
			return;
		}

		if (!document.forms["newuserform"].elements["user-Level"].value) {
			alert("업무를 선택해주세요");
			document.forms["newuserform"].elements["user-Level-choice"].focus();
			return;
		}

		//newuserform		
		var nm = document.forms["newuserform"].elements["user-Id"].value;
		var response = confirm(nm + "의 데이터를 신규생성할까요?")
		if (response) {
			//do yes task
			document.forms["newuserform"].method = "post";
			document.forms["newuserform"].action = "/admin/adduser"
			document.forms["newuserform"].submit();
		} else {
			//do no task
		}
	}
	function modifyCheck2(id) {
		var nm = document.forms["form" + id].elements["user-Id"].value;
		var response = confirm(nm + "데이터 수정할까요?")
		if (response) {
			//do yes task
			document.forms["form" + id].method = "post";
			document.forms["form" + id].action = "/admin/reqmodify"
			document.forms["form" + id].submit();
		} else {
			//do no task
		}
	}

	function ResetPassword(id) {
		var nm = document.forms["form" + id].elements["user-Id"].value;
		var response = confirm(nm + "의 패스워드 리셋 시킬까요?")
		if (response) {
			//do yes task	
			document.forms["form" + id].method = "post";
			document.forms["form" + id].action = "/admin/reqresetpassword"
			document.forms["form" + id].submit();
		} else {
			//do no task
		}
	}
</script>


<!DOCTYPE html>

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
	<form name="newuserform">
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
						<input type="hidden" name='user-Level'>
						<!-- 클릭하면 input에 대입 -->
						<select name="user-level-choice" size="1" onchange="choice(this);">
							<option selected value="1">==선택하세요==</option>
							<option value="2">개발담당자</option>
							<option value="3">출고담당자</option>
							<option value="3">손님(입출고권한없음)</option>
							<option value="4">관리자</option>
						</select>
					</p>
				</td>
			</tr>
		</table>
		<input type="button" value="신규가입" name="submitbtn1"
			OnClick="javascript:newUser();">
	</form>


	<p>&nbsp;</p>
	<p>[회원정보 수정]</p>
	<table border="1">
		<tr>
			<td width="30">${status.index}</td>
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
		<c:forEach var="i" items="${items}" varStatus="status">

			<form name="form${status.index}">
			<tr>
				<td width="30">${status.index}</td>
				<td width="199">
					<p>${i.userId}</p> <input type=hidden name=user-Id
					value='${i.userId}'>
				</td>
				<td width="199">
					<p>
						<input name=user-Name value='${i.userName}'>
					</p>
				</td>
				<td width="199">
					<p>
						<input type=text name=user-Email value='${i.userEmail}'>
					</p>
				</td>
				<td width="199">
					<p>
						<input type=text name=user-Teamname size=30 maxlength=30
							value='${i.userTeamname}'>
					</p>
				</td>
				<td width="199">
					<p>
						<input type=text name=user-Level size=5 maxlength=5
							value=${i.userLevel}>
					</p>
				</td>
				<td width="199">
					<p>


						<input type="button" value="수정" name="submitbtn"
							OnClick="javascript:modifyCheck2('${status.index}');"> <input
							type=button name="formbutton2" value="비번초기화"
							OnClick="javascript:ResetPassword('${status.index}');">
					</p>


				</td>

			</tr>

			</form>
		</c:forEach>

	</table>
	<p>&nbsp;</p>
</body>

</html>


