<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="header.jsp"%>


<h3>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>[도움말]</b>
</h3>
<br>




<h5>
<b> [출고단계별 설명]</b><p>
<img src="/img/statemachine.jpg"><p>
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
	<p>
</h5>


<br><br>
<h5>
<b> [사용자, 프로젝트와 부품간 관계]</b><p>
<img src="/img/relation1.jpg"><p>
       하나의 프로젝트에는 개발담당자와 출고담당자가 지정됨<p>
       프로젝트에는 여러 부품이 속해있음<p>개발담당자는 프로젝트의 자재를 출고요청 할 수 있음<p>
       출고담당자가 승인 or 반려 후 출고진행<p>
       다른프로젝트의 부품도 해당개발자의 승인단계 거쳐서 출고 가능<p>
       
       <br><br>
<b>[메뉴얼다운로드]</b><p>
<a href="/resources/doc/innoparts_user_guide.pptx" target="_blank">Download(다른이름으로 링크저장..)</a>       
</h5>
<%@ include file="footer.jsp"%>
