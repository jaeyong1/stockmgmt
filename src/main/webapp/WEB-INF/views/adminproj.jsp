<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>



<%@ page import="java.util.*"%>



<script language="javascript">



	function newItem() {
		if (!document.forms["newitemform"].elements["project-Code"].value) {
			alert("Id를 기입해주세요");
			document.forms["newitemform"].elements["project-Code"].focus();
			return;
		}

		if (!document.forms["newitemform"].elements["project-Name"].value) {
			alert("이름을 기입해주세요");
			document.forms["newitemform"].elements["project-Name"].focus();
			return;
		}

		if (!document.forms["newitemform"].elements["project-Owner-Id"].value) {
			alert("Email을 기입해주세요");
			document.forms["newitemform"].elements["project-Owner-Id"].focus();
			return;
		}

		if (!document.forms["newitemform"].elements["project-shipper-Id"].value) {
			alert("소속팀명을 기입해주세요");
			document.forms["newitemform"].elements["project-shipper-Id"]
					.focus();
			return;
		}

		//newitemform		
		var nm = document.forms["newitemform"].elements["project-Code"].value;
		var response = confirm(nm + "의 데이터를 신규생성할까요?")
		if (response) {
			//do yes task
			document.forms["newitemform"].method = "post";
			document.forms["newitemform"].action = "/admin/addproject"
			document.forms["newitemform"].submit();
		} else {
			//do no task
		}
	}

	function modifyCheck2(id) {
		var nm = document.forms["form" + id].elements["project-Code"].value;
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
		var nm = document.forms["form" + id].elements["project-Code"].value;
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
<html>

<head>
<meta http-equiv="content-type" content="text/html; charset=euc-kr">
<title>제목 없음</title>
<meta name="generator" content="Namo WebEditor v5.0">
<script src="https://code.jquery.com/jquery-3.1.1.min.js"> </script>

<script language="javascript">
$(function() {
	alert("aaa");
	  // Handler for .ready() called.

	});



</script>
</head>

<body bgcolor="white" text="black" link="blue" vlink="purple"
	alink="red">


	<!-- 
	*****************************
	직전 작업 결과 표시
	*****************************
	 -->
	<h2>${reqresult}</h2>

	<p align="center">프로젝트 관리</p>
	<p>[신규추가]</p>

	<form name="newitemform">
		<table border="1" width="487">
			<tr>
				<td width="143">
					<p>프로젝트코드</p>
				</td>
				<td width="328">
					<p>
						<input type="text" name='project-Code'>
					</p>
				</td>
			</tr>
			<tr>
				<td width="143">
					<p>프로젝트이름</p>
				</td>
				<td width="328">
					<p>
						<input type="text" name='project-Name'>
					</p>
				</td>
			</tr>

			<tr>
				<td width="143">
					<p>개발담당자ID</p>
				</td>
				<td width="328">
					<p>
						<input type="text" name='project-Owner-Id'>
					</p>
				</td>
			</tr>
			<tr>
				<td width="143">
					<p>출고담당자ID</p>
				</td>
				<td width="328">
					<p>
						<input type="text" name='project-shipper-Id'>
					</p>
				</td>
			</tr>
		</table>
		<input type="button" value="신규" name="submitbtn1"
			OnClick="javascript:newItem();">
	</form>

	<!-- 
	*****************************
	리스트 표시 및 수정하기
	*****************************
	 -->

	<p>&nbsp;</p>
	<p>[데이터수정]</p>
	<table border="1">
		<tr>
			<td width="204">
				<p>프로젝트코드</p>
			</td>
			<td width="204">
				<p>프로젝트이름</p>
			</td>
			<td width="240">
				<p>개발담당자ID</p>
			</td>
			<td width="240">
				<p>출고담당자ID</p>
			</td>
		</tr>
		<!-- DB 데이터 채움 (클래스 변수사용) -->
		<c:forEach var="i" items="${items}" varStatus="status">

			<form name="form${status.index}">
				<tr>
					<td width="199">${i.projectCode} <input type=hidden
						name=project-Code value='${i.projectCode}'>
					</td>
					<td width="199">
						<p>
							<input name=project-Name value='${i.projectName}'>
						</p>
					</td>
					<td width="199">
						<p>
							<input type=text name=project-Owner-Id
								value='${i.projectOwnerId}'>
						</p>
					</td>
					<td width="199">
						<p>
							<input type=text name=project-shipper-Id size=30 maxlength=30
								value='${i.projectShipperId}'>
						</p>
					</td>

					<td width="199">
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
				<li><a href="/admin/project/${start-1}">&laquo;</a></li>
			</c:if>
			<!-- 5개씩 페이지 표시-->
			​

			<c:forEach var="i" begin="${start }" end="${end }">
				<li id="page${i }"><a href="/admin/project/${i}">${i}</a></li>
			</c:forEach>
			<!-- end페이지 번호가 5, 10 인데 전체 페이지 갯수가 end페이지 보다 크면 다음 페이징 바로가기 표시  (">>")​ .-->
			​

			<c:if test="${end % 5 == 0 && pageNum > end}">
				<li><a href="/admin/project/${end+1}">&raquo;</a></li>
			</c:if>
			<!-- 마지막 페이지 번호와 전체 페이지 번호가 같으면서 5개 단위가 아니면 다음바로가기 표시 않함 -->
			​​

			<c:if test="${end % 5 != 0 && end == pageNum }">

			</c:if>
		</ul>
	</div>

	<!-- FOOTER -->
</body>

</html>
