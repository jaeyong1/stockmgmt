<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="header.jsp"%>



<script language="javascript">
	function popup() {
		//출고담당자 리스트 호출
		window.open("/devuserlist", 'window', 'width=650,height=600');
	}

	function newItem() {
		if (!document.forms["newitemform"].elements["project-Code"].value) {
			alert("프로젝트코드를 기입해주세요");
			document.forms["newitemform"].elements["project-Code"].focus();
			return;
		}

		if (!document.forms["newitemform"].elements["project-Name"].value) {
			alert("프로젝트이름을 기입해주세요");
			document.forms["newitemform"].elements["project-Name"].focus();
			return;
		}

		if (!document.forms["newitemform"].elements["project-Owner-Id"].value) {
			alert("개발담당자 아이디를 기입해주세요(아래도움말과 찾기기능 사용)");
			document.forms["newitemform"].elements["project-Owner-Id"].focus();
			return;
		}

		if (!document.forms["newitemform"].elements["project-shipper-Id"].value) {
			alert("본인의 ID를 기입해주세요");
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
			document.forms["newitemform"].action = "/addmyproject"
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
			document.forms["form" + id].action = "/reqmyprojectmodify"
			document.forms["form" + id].submit();
		} else {
			//do no task
		}
	}
	function removeItem(id) {
		var nm = document.forms["form" + id].elements["project-Code"].value;
		var response = confirm(nm + "데이터 삭제할까요?")
		if (response) {
			var response1 = confirm("프로젝트 삭제는 이에 속한 파츠정보와, 관련된 출고요청 정보도 모두 잃게 됩니다. 그래도 데이터 삭제할까요?")
			if (response1) {
				//do yes task
				document.forms["form" + id].method = "post";
				document.forms["form" + id].action = "reqmyprojectremove"
				document.forms["form" + id].submit();
			}
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
	<h3>프로젝트 관리</h3>
</center>
<h5>
	<b>[신규추가]</b>
</h5>


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
					 <input type="text"
						name='project-Owner-Id'>
				</p>
			</td>
		</tr>
		<tr>
			<td width="143">
				<p>출고담당자ID</p>
			</td>
			<td width="328">
				<p>${sessionScope.userLoginInfo.userId}
					<input type="hidden" name='project-shipper-Id' 
						value='${sessionScope.userLoginInfo.userId}'>
				</p>
			</td>
		</tr>
	</table>

	<input type="button" value="신규생성" name="submitbtn1"
		class="btn btn-warning btn-md" OnClick="javascript:newItem();">
	&nbsp;&nbsp;&nbsp;
	<!-- Trigger the modal with a button -->
	<button type="button" class="btn  btn-success  btn-md  "
		data-toggle="modal" data-target="#myModal">도움말</button>

	<button type="button" class="btn btn-info btn-md"
		OnClick="javascript:popup();">개발담당자 보기</button>

</form>

<!-- 
	*****************************
	리스트 표시 및 수정하기
	*****************************
	 -->

<p>&nbsp;</p>
<h5>
	<b>[데이터수정]</b>
</h5>
<table border="1">
	<tr>
		<td width="204">
			<center>
				<p>프로젝트코드</p>
			</center>
		</td>
		<td width="204">
			<center>
				<p>프로젝트이름</p>
			</center>
		</td>
		<td width="200">
			<center>
				<p>개발담당자ID</p>
			</center>
		</td>
		<td width="200">
			<center>
				<p>출고담당자ID</p>
			</center>
		</td>
		<td width="100"></td>

	</tr>
	<!-- DB 데이터 채움 (클래스 변수사용) -->
	<c:forEach var="i" items="${items}" varStatus="status">

		<form name="form${status.index}">
			<tr>
				<td><center>${i.projectCode}</center> <input type=hidden
					name=project-Code value='${i.projectCode}'></td>
				<td>
					<p>
					<center>
						<input name=project-Name value='${i.projectName}'>
					</center>
					</p>
				</td>
				<td>
					<p>
					<center>
						<input type=text name=project-Owner-Id
							value='${i.projectOwnerId}'>
					</center>
					</p>
				</td>
				<td>
					<p>
					<center>${i.projectShipperId}
						<input type=hidden name=project-shipper-Id size=20 maxlength=30
							value='${i.projectShipperId}'></
						</p>
				</td>

				<td>
					<p>
					<center>
						<input type="button" value="수정" name="submitbtn"
							class="btn btn-primary btn-xs"
							OnClick="javascript:modifyCheck2('${status.index}');"> <input
							type="button" value="삭제" class="btn btn-warning btn-xs"
							name="submitbtn2"
							OnClick="javascript:removeItem('${status.index}');">
					</center>
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
			<li><a href="/myproject/${start-1}">&laquo;</a></li>
		</c:if>
		<!-- 5개씩 페이지 표시-->
		​

		<c:forEach var="i" begin="${start }" end="${end }">
			<li id="page${i }"><a href="/myproject/${i}">${i}</a></li>
		</c:forEach>
		<!-- end페이지 번호가 5, 10 인데 전체 페이지 갯수가 end페이지 보다 크면 다음 페이징 바로가기 표시  (">>")​ .-->
		​

		<c:if test="${end % 5 == 0 && pageNum > end}">
			<li><a href="/myproject/${end+1}">&raquo;</a></li>
		</c:if>
		<!-- 마지막 페이지 번호와 전체 페이지 번호가 같으면서 5개 단위가 아니면 다음바로가기 표시 않함 -->
		​​

		<c:if test="${end % 5 != 0 && end == pageNum }">

		</c:if>
	</ul>
</div>


<!-- Modal -->
<div class="modal fade" id="myModal" role="dialog">
	<div class="modal-dialog">
		<!-- Trigger the modal with a button -->
		<button type="button" class="btn btn-info btn-lg" data-toggle="modal"
			data-target="#myModal">알려드려요</button>

		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">프로젝트 생성안내</h4>
			</div>
			<div class="modal-body">
				<p>
					- <b>개발담당자</b>가 출고요청 작성하므로 프로젝트 생성시에 시작담당자(출고담당자)를 꼭 <b>지정</b>해야 합니다. <br>
					&nbsp;&nbsp;잘모를경우 "ADMIN"이라 써놓고 관리자에게 연락주세요.
				
				<p>
				<p>- 관리운영자 : jaeyong1.park@lginnotek.com</p>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">닫기</button>
			</div>
		</div>

	</div>
</div>



<%@ include file="footer.jsp"%>