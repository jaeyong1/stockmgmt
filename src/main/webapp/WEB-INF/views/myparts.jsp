<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="header.jsp"%>

<script language="javascript">
	function newItem() {
		if (!document.forms["newitemform"].elements["part-Project-Code"].value) {
			alert("프로젝트코드 기입해주세요");
			document.forms["newitemform"].elements["part-Project-Code"].focus();
			return;
		}

		if (!document.forms["newitemform"].elements["part-Name"].value) {
			alert("LGIT P/N을 기입해주세요");
			document.forms["newitemform"].elements["part-Name"].focus();
			return;
		}

		if (!document.forms["newitemform"].elements["part-Desc"].value) {
			alert("설명을 기입해주세요");
			document.forms["newitemform"].elements["part-Desc"].focus();
			return;
		}

		if (!document.forms["newitemform"].elements["part-Cost"].value) {
			alert("단가를 기입해주세요");
			document.forms["newitemform"].elements["part-Cost"].focus();
			return;
		}
		if (!document.forms["newitemform"].elements["part-Stock"].value) {
			alert("재고량를 기입해주세요");
			document.forms["newitemform"].elements["part-Stock"].focus();
			return;
		}
		//newitemform		
		var nm = document.forms["newitemform"].elements["part-Name"].value;
		var response = confirm(nm + "의 데이터를 신규생성할까요?")
		if (response) {
			//do yes task
			document.forms["newitemform"].method = "post";
			document.forms["newitemform"].action = "/addmyparts"
			document.forms["newitemform"].submit();
		} else {
			//do no task
		}
	}

	function modifyCheck2(id) {
		var nm = document.forms["form" + id].elements["part-Name"].value;
		var response = confirm(nm + "데이터 수정할까요?")
		if (response) {
			//do yes task
			document.forms["form" + id].method = "post";
			document.forms["form" + id].action = "/reqmypartsmodify"
			document.forms["form" + id].submit();
		} else {
			//do no task
		}
	}
	function removeItem(id) {
		var nm = document.forms["form" + id].elements["part-Name"].value;
		var response = confirm(nm + "데이터 삭제할까요?")
		if (response) {
			//do yes task
			document.forms["form" + id].method = "post";
			document.forms["form" + id].action = "/reqmypartsremove"
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
	<h3>파츠 관리</h3>
</center>
<h5>
	<b>[신규추가]</b>
</h5>


<form name="newitemform">
	<table border="1" width="487">
		<tr>
			<td width="143">
				<p>parts id(idx)</p>
			</td>
			<td width="328">
				<p>
					<input type="hidden" name='part-Id'>
				</p>
			</td>
		</tr>
		<tr>
			<td width="143">
				<p>프로젝트코드</p>
			</td>
			<td width="328">
				<p>
					<input type="text" name='part-Project-Code'><br>(나의
					프로젝트만 기입)

				</p>
			</td>
		</tr>

		<tr>
			<td width="143">
				<p>LGIT P/N</p>
			</td>
			<td width="328">
				<p>
					<input type="text" name='part-Name'>
				</p>
			</td>
		</tr>
		<tr>
			<td width="143">
				<p>Desc</p>
			</td>
			<td width="328">
				<p>
					<input type="text" name='part-Desc'>
				</p>
			</td>
		</tr>
		<tr>
			<td width="143">
				<p>위치</p>
			</td>
			<td width="328">
				<p>
					<input type="text" name='part-Location'>
				</p>
			</td>
		</tr>
		<tr>
			<td width="143">
				<p>단가</p>
			</td>
			<td width="328">
				<p>
					<input type="text" name='part-Cost'>
				</p>
			</td>
		</tr>
		<tr>
			<td width="143">
				<p>재고</p>
			</td>
			<td width="328">
				<p>
					<input type="text" name='part-Stock'>
				</p>
			</td>
		</tr>

		<tr>
			<td width="143">
				<p>비고</p>
			</td>
			<td width="328">
				<p>
					<input type="text" id="aa" name='part-Memo'>
				</p>
			</td>
		</tr>
	</table>
	<input type="button" value="신규" name="submitbtn1"
		class="btn btn-success btn-md" OnClick="javascript:newItem();">
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
	<c:forEach var="i" items="${items}" varStatus="status">
		<form name="form${status.index}">
			<tr>
				<td>${i.partId}<input type=hidden name=part-Id
					value='${i.partId}'>
				</td>
				<td>
					<p>${i.partProjectCode}
						<input type=hidden name=part-Project-Code size="14"
							value='${i.partProjectCode}'>
					</p>
				</td>
				<td>
					<p>
						<input type=text name=part-Name size="14" value='${i.partName}'>
					</p>
				</td>
				<td>
					<p>
						<input type=text name=part-Desc size="14" value='${i.partDesc}'>
					</p>
				</td>
				<td>
					<p>
						<input type=text name=part-Location size="14"
							value='${i.partLocation}'>
					</p>
				</td>
				<td>
					<p>
						<input type=text name=part-Cost size="4" value='${i.partCost}'>
					</p>
				</td>
				<td>
					<p>
						<input type=text name=part-Stock size="4" value='${i.partStock}'>
					</p>
				</td>
				<td>
					<p>
						<input type=text name=part-Memo size="14" value='${i.partMemo}'>
					</p>
				</td>


				<td>
					<p>
						<input type="button" value="수정" name="submitbtn"
							class="btn btn-primary btn-xs"
							OnClick="javascript:modifyCheck2('${status.index}');"> <input
							type="button" value="삭제" name="submitbtn2"
							class="btn btn-warning btn-xs"
							OnClick="javascript:removeItem('${status.index}');">
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
			<li><a href="/myparts/${start-1}">&laquo;</a></li>
		</c:if>
		<!-- 5개씩 페이지 표시-->
		​

		<c:forEach var="i" begin="${start }" end="${end }">
			<li id="page${i }"><a href="/myparts/${i}">${i}</a></li>
		</c:forEach>
		<!-- end페이지 번호가 5, 10 인데 전체 페이지 갯수가 end페이지 보다 크면 다음 페이징 바로가기 표시  (">>")​ .-->
		​

		<c:if test="${end % 5 == 0 && pageNum > end}">
			<li><a href="/myparts/${end+1}">&raquo;</a></li>
		</c:if>
		<!-- 마지막 페이지 번호와 전체 페이지 번호가 같으면서 5개 단위가 아니면 다음바로가기 표시 않함 -->
		​​

		<c:if test="${end % 5 != 0 && end == pageNum }">

		</c:if>
	</ul>
</div>
<p>&nbsp;</p>

<!-- 	
	*****************************
	검색 창
	*****************************		
		 -->
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

<%@ include file="footer.jsp"%>