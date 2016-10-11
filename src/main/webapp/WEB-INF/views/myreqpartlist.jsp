<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="header.jsp"%>


<script language="javascript">
	function modifyCheck2(id) {
		var nm = document.forms["form" + id].elements["itemlist-Id"].value;
		var response = confirm(nm + "데이터 수정할까요?")
		if (response) {
			//do yes task
			document.forms["form" + id].method = "post";
			document.forms["form" + id].action = "/reqshippartsmodify"
			document.forms["form" + id].submit();
		} else {
			//do no task
		}
	}
	function removeItem(id) {
		var nm = document.forms["form" + id].elements["itemlist-Id"].value;
		var response = confirm(nm + "데이터 삭제할까요?")
		if (response) {
			//do yes task
			document.forms["form" + id].method = "post";
			document.forms["form" + id].action = "/reqshippartsremove"
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
	<h3>출고요청의 파츠리스트 조회</h3>
</center>



<!-- 
	*********
	리스트 표시 
	*********
	 -->

<table border="1">
	<tr>
		<td width="67">
			<p align="center">No</p>
		</td>
		<!-- 
			<td width="135">
				<p align="center">출고요청 Seq</p>
			</td>
			 -->
		<td width="135">
			<p align="center">프로젝트Code</p>
		</td>
		<td width="135">
			<p align="center">LGIT P/N</p>
		</td>
		<td width="169">
			<p align="center">Item Desc</p>
		</td>
		<td width="86">
			<p align="center">재고수량</p>
		</td>
		<td width="76">
			<p align="center">요청수량</p>
		</td>
		<td width="100"></td>
	</tr>
	<!-- DB 데이터 채움 (클래스 변수사용) -->
	<c:forEach var="i" items="${items}" varStatus="status">

		<form name="form${status.index}">
			<tr>
				<td width="67">
					<p>
						<input type=hidden name=itemlist-Id value='${i.itemlistId}'>
						${status.index} (sn:${i.itemlistId})
					</p>
				</td>
				<!-- 
					<td width="135">
						<p align="center">${i.itemlistShipId}</p>
					</td>
					 -->
				<td width="135">
					<p align="center">${i.partProjectCode}</p>
				</td>
				<td width="135">
					<p align="center">${i.itemlistPartId}</p>
				</td>
				<td width="169">
					<p align="center">${i.partDesc}</p>
				</td>
				<td width="86">
					<p align="center">${i.partStock}</p>

				</td>
				<td width="76">
					<p align="center">
						<input type=text name=itemlist-Amount size=5
							value='${i.itemlistAmount}'>
					</p>
				</td>
				<td width="100">
					<p>
						<input type="button" value="수정" name="submitbtn"
							class="btn btn-primary btn-xs"
							OnClick="javascript:modifyCheck2('${status.index}');"> <input
							type="button" value="삭제" name="submitbtn2"
							class="btn btn-default btn-xs"
							OnClick="javascript:removeItem('${status.index}');">
					</p>


				</td>
			</tr>
		</form>
	</c:forEach>

</table>

<%@ include file="footer.jsp"%>
