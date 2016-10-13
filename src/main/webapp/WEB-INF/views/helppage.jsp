<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="header.jsp"%>


<h3>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>[도움말]</b>
</h3>
<br>

<img src="/img/statemachine.jpg">

<h5>
	STATE 1 : 요청서 작성중 - 개발담당자가 부품리스트를 추가삭제 및 수량변경 할 수 있음.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;출고요청하면
	출고담당자에게 작성내용이 넘어감<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;부품수량
	여러건을 출고요청 1건으로 진행할 수 있음
	<p>
	<p>STATE 2 : 합의요청중 - 동료개발자가 승인해야 출고진행함. 승인시 출고담당자에게 리스트가 넘어감.
	<p>STATE 3 : 출고접수중 - 출고담당자가 확인하고 프린트를 해야함
	<p>STATE 4 : 출고접수완료 - 출고담당자가 출고완료후 출고완료 버튼을 눌러야 함
	<p>STATE 5 : 출고완료 - 모든작업이 완료된 상태
	<p>STATE 6 : 반려 - 모든작업이 완료된 상태
	<p>
</h5>
<%@ include file="footer.jsp"%>
