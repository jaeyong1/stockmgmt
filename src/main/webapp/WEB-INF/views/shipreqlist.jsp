<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="header.jsp"%>


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
	<h3>출고요청건 리스트</h3>
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
					<p>shipId</p>
				</center>
			</th>
			<th width="130">
				<center>
					<p>수령지</p>
				</center>
			</th>
			<th width="130">
				<center>
					<p>출고접수일</p>
				</center>
			</th>
			<th width="130">
				<center>
					<p>출고요청일</p>
				</center>
			</th>
			<th width="130">
				<center>
					<p>출고담당자</p>
				</center>
			</th>
			<th width="130">
				<center>
					<p>진행상태</p>
				</center>
			</th>
			<th width="80">
				<center>
					<p></p>
			</th>

		</tr>
		<!-- DB 데이터 채움 (클래스 변수사용) -->
		<c:forEach var="i" items="${items}" varStatus="status">
			<form name="form${status.index}">
				<tr align="center">
					<td width="130">
						<p>${i.shipId}<input type=hidden name=ship-Id
								value='${i.shipId}'>
						</p>
					</td>
					<td width="130">
						<p>${i.shipDestination}</p>
					</td>
					<td width="130">
						<p>${i.shipToday}</p>
					</td>
					<td width="130">
						<p>${i.shipTargetdate}</p>
					</td>
					<td width="130">
						<p>
							<c:out value="${i.shipperName}" />
						</p>
					</td>
					<td width="130">
						<p>${i.shipStateId}
							<c:out value="${eshipstate.get((i.shipStateId-1))}" />
						</p>
					</td>
					<td width="80">
						<p>
							<input type=button name="formbutton2" value="자세히"
								class="btn btn-primary btn-xs"
								OnClick="javascript:DetailView('${status.index}');">
						</p>
					</td>
				</tr>
			</form>
		</c:forEach>
	</table>
</center>


<p>&nbsp;</p>
<!-- 
	*****************************
	페이지 표시
	*****************************
	 -->
<center>
	<div class="col-xs-8">
		<ul class="pagination pagination-sm" style="margin-top: 0px;">
			<!-- 시작페이지가 1부터면 이전 표시("<<") ​ 안함 -->
			<c:if test="${start-1 ==0 }">

			</c:if>
			<!-- 시작페이지가 1이 아니면 << 이전 표시.  링크는 시작페이지가 6부터 10까지일 경우 5페이지를 가르킴 -->
			​

			<c:if test="${start-1!=0 }">
				<li><a href="/shipreqlist/${start-1}">&laquo;</a></li>
			</c:if>
			<!-- 5개씩 페이지 표시-->
			​

			<c:forEach var="i" begin="${start }" end="${end }">
				<li id="page${i }"><a href="/shipreqlist/${i}">${i}</a></li>
			</c:forEach>
			<!-- end페이지 번호가 5, 10 인데 전체 페이지 갯수가 end페이지 보다 크면 다음 페이징 바로가기 표시  (">>")​ .-->
			​

			<c:if test="${end % 5 == 0 && pageNum > end}">
				<li><a href="/shipreqlist/${end+1}">&raquo;</a></li>
			</c:if>
			<!-- 마지막 페이지 번호와 전체 페이지 번호가 같으면서 5개 단위가 아니면 다음바로가기 표시 않함 -->
			​​

			<c:if test="${end % 5 != 0 && end == pageNum }">

			</c:if>
		</ul>
	</div>
	<p>&nbsp;</p>
</center>

<%@ include file="footer.jsp"%>