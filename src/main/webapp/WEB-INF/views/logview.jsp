<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="header.jsp"%>

<center>
	<h3>회원정보 주요동작 Log</h3>
</center>

<!-- 
	*********
	리스트 표시 
	*********
	 -->
<center>

	<table border="1">
		<tr class="danger">

			<th width="42">
				<p>&nbsp;</p>
			</th>
			<th width="192"><center>
					<p>&nbsp;처리일시</p>
				</center></th>
			<th width="100">
				<center>
					<p>접속IP</p>
				</center>
			</th>
			<th width="70">
				<center>
					<p>User ID</p>
				</center>
			</th>
			<th width="200">
				<center>
					<p>동작</p>
				</center>
			</th>
		</tr>
		<!-- DB 데이터 채움 (클래스 변수사용) -->
		<c:forEach var="i" items="${items}" varStatus="status">

			<form name="form${status.index}">
				<tr>

					<td width="42">
						<p><center>${i.logId}</center></p>
					</td>

					<td width="192">
						<center>
							<p>${i.logDate}</p>
						</center>
					</td>
					<td width="100">
						<center>
							<p>${i.logClientIP}</p>
						</center>
					</td>
					<td width="70">
						<center>
							<p>${i.logUser}</p>
						</center>
					</td>
					<td width=""200"">
						<center>
							<p>${i.logAction}</p>
						</center>
					</td>
				</tr>
			</form>
		</c:forEach>

	</table>
</center>


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

		<c:if test="${start-1!=0}">
			<li><a href="/logview/${start-1}">&laquo;</a></li>
		</c:if>
		<!-- 5개씩 페이지 표시-->
		​

		<c:forEach var="i" begin="${start }" end="${end }">
			<li id="page${i }"><a href="/logview/${i}">${i}</a></li>
		</c:forEach>
		<!-- end페이지 번호가 5, 10 인데 전체 페이지 갯수가 end페이지 보다 크면 다음 페이징 바로가기 표시  (">>")​ .-->
		​

		<c:if test="${end % 5 == 0 && pageNum > end}">
			<li><a href="/logview/${end+1}">&raquo;</a></li>
		</c:if>
		<!-- 마지막 페이지 번호와 전체 페이지 번호가 같으면서 5개 단위가 아니면 다음바로가기 표시 않함 -->
		​​

		<c:if test="${end % 5 != 0 && end == pageNum }">

		</c:if>
	</ul>
</div>



<%@ include file="footer.jsp"%>