<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="header.jsp"%>


<!-- 
	*****************************
	회원 정보 리스트 및 수정하기
	*****************************
	 -->

<h5>
	<center>
		<b>가입되어있는 시작담당자(출고담당자) 리스트</b>
	</center>
</h5><br>
<table border="1">
	<tr>
		<!-- <td width="30">${status.index}</td> -->
		<td width="199">
			<center>
				<b><p>user id</p></b>
			</center>
		</td>
		<td width="199">
			<center>
				<b><p>name</p></b>
			</center>
		</td>
		<td width="199">
			<center>
				<b><p>email</p></b>
			</center>
		</td>
		<td width="199">
			<center>
				<b><p>team</p></b>
			</center>
		</td>

	</tr>
	<!-- DB 데이터 채움 (클래스 변수사용) -->
	<c:forEach var="i" items="${items}" varStatus="status">

		<form name="form${status.index}">
			<tr>
				<!-- <td width="30">${status.index}</td>  -->
				<td width="199">
					<center>
						<p>${i.userId}</p>
					</center>
				</td>
				<td width="199">
					<center>
						<p>${i.userName}</p>
					</center>
				</td>
				<td width="199">
					<center>
						<p>${i.userEmail}</p>
					</center>
				</td>
				<td width="199">
					<center>
						<p>${i.userTeamname}</p>
					</center>
				</td>


			</tr>

		</form>
	</c:forEach>

</table>
<p>&nbsp;</p>

<%@ include file="footer.jsp"%>
