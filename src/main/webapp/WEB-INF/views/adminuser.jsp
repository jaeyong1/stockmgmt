<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="header.jsp"%>


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
		var response = confirm(nm + "의 패스워드 리셋 시킬까요? 초기화값:defaultPassWord123")
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



<!-- 
	*****************************
	직전 작업 결과 표시
	*****************************
	 -->
<h3>${reqresult}</h3>
<center>
	<h3>사용자 관리</h3>
</center>
<h5>
	<b>[신규추가]</b>
</h5>


<!-- 
	*****************************
	회원 가입 폼
	*****************************
	 -->

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
				<p>Level</p>
			</td>
			<td width="328">
				<p>
					<input type="hidden" name='user-Level'>
					<!-- 클릭하면 input에 대입 -->
					<select name="user-level-choice" size="1" onchange="choice(this);">
						<option selected value="1">==선택하세요==</option>
						<option value="2">개발담당자</option>
						<option value="3">출고담당자</option>
						<option value="4">손님(입출고권한없음,탈퇴용)</option>
						<option value="5">시스템관리자</option>
						<option value="6">출고담당자.관리자</option>
					</select>
				</p>
			</td>
		</tr>
	</table>
	<input type="button" value="신규가입" name="submitbtn1"
		class="btn btn-success btn-md" OnClick="javascript:newUser();">
</form>


<!-- 
	*****************************
	회원 정보 리스트 및 수정하기
	*****************************
	 -->

<p>&nbsp;</p>
<h5>
	<b>[회원정보 수정]</b>
</h5>

<h5>
	Level 2: 개발담당자 / Level 3: 출고담당자 / Level 4: 손님(탈퇴용) / Level 5: 시스템관리자 /
	Level 6: 출고담당자.관리자
	<p>
</h5>
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
			<p>Level</p>
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


						<input type="button" value="수정" class="btn btn-primary btn-xs"
							name="submitbtn"
							OnClick="javascript:modifyCheck2('${status.index}');"> <input
							type=button name="formbutton2" value="비번초기화"
							class="btn btn-warning btn-xs"
							OnClick="javascript:ResetPassword('${status.index}');">
					</p>


				</td>

			</tr>

		</form>
	</c:forEach>

</table>
<p>&nbsp;</p>

<!-- 
	*****************************
	페이지 표시
	*****************************
	 -->

<div class="col-xs-8">
	<ul class="pagination pagination-sm" style="margin-top: 0px;">
		<!-- 시작페이지가 1부터면 이전 표시("<<") ​ 안함 -->
		<c:if test="${start-1 ==0 }">

		</c:if>
		<!-- 시작페이지가 1이 아니면 << 이전 표시.  링크는 시작페이지가 6부터 10까지일 경우 5페이지를 가르킴 -->
		​

		<c:if test="${start-1!=0 }">
			<li><a href="/admin/user/${start-1}">&laquo;</a></li>
		</c:if>
		<!-- 5개씩 페이지 표시-->
		​

		<c:forEach var="i" begin="${start }" end="${end }">
			<li id="page${i }"><a href="/admin/user/${i}">${i}</a></li>
		</c:forEach>
		<!-- end페이지 번호가 5, 10 인데 전체 페이지 갯수가 end페이지 보다 크면 다음 페이징 바로가기 표시  (">>")​ .-->
		​

		<c:if test="${end % 5 == 0 && pageNum > end}">
			<li><a href="/admin/user/${end+1}">&raquo;</a></li>
		</c:if>
		<!-- 마지막 페이지 번호와 전체 페이지 번호가 같으면서 5개 단위가 아니면 다음바로가기 표시 않함 -->
		​​

		<c:if test="${end % 5 != 0 && end == pageNum }">

		</c:if>
	</ul>
</div>


<%@ include file="footer.jsp"%>
