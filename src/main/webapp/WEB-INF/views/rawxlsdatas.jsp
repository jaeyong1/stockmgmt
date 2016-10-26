<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>



<script language="javascript">
	function DetailView(id) {
		document.forms["form" + id].method = "post";
		document.forms["form" + id].action = "/viewshipreq"
		document.forms["form" + id].submit();
	}
</script>


<!-- 
	*****************************
	직전 작업 결과 표시
	*****************************
	 -->
<h3>${reqresult}</h3>
<center>
	<h3>업로드 데이터 확인</h3>
</center>


<!-- 
	*****************************
	리스트 표시 및 수정하기
	*****************************
	 -->

<p>&nbsp;</p>
<center>
	<table border="1">
		<tr>
			<th width="130">
				<center>
					<p>Project Name</p>
				</center>
			</th>
			<th width="130">
				<center>
					<p>개발담당자부서</p>
				</center>
			</th>
			<th width="130">
				<center>
					<p>개발담당자</p>
				</center>
			</th>
			<th width="130">
				<center>
					<p>시작담당자</p>
				</center>
			</th>
			<th width="130">
				<center>
					<p>LGIT P/N</p>
				</center>
			</th>

			<th width="130">
				<center>
					<p>Desc</p>
				</center>
			</th>
			<th width="130">
				<center>
					<p>Maker</p>
				</center>
			</th>
			<th width="130">
				<center>
					<p>재고수량</p>
				</center>
			</th>
			<th width="130">
				<center>
					<p>위치</p>
				</center>
			</th>





		</tr>
		<!-- DB 데이터 채움 (클래스 변수사용) -->
		<c:forEach var="i" items="${items}" varStatus="status">
			<form name="form${status.index}">
				<tr align="center">

					<td width="130">
						<p>${i.xlsProjectName}</p>
					</td>
					<td width="130">
						<p>${i.xlsOwnerTeamName}</p>
					</td>
					<td width="130">
						<p>${i.xlsOwnerName}</p>
					</td>
					<td width="130">
						<p>${i.xlsShipperName}</p>
					</td>
					<td width="130">
						<p>${i.xlsPartName}</p>
					</td>

					<td width="130">
						<p>${i.xlsPartDesc}</p>
					</td>
					<td width="130">
						<p>${i.xlsPartMemo}</p>
					</td>
					<td width="130">
						<p>${i.xlsPartStock}</p>
					</td>
					<td width="130">
						<p>${i.xlsPartLocation}</p>
					</td>


				</tr>
			</form>
		</c:forEach>
	</table>
</center>


<p>&nbsp;</p>
