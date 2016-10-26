<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="header.jsp"%>

<!-- 메세지 있으면 띄우고 팝업닫기, 부모이동.  -->
<!-- 메세지 없으면 그냥 닫기. 부모이동.  -->
<script>
	$(function() {
		//when loading..
		if ('${popupclosemsg}' != '') {
			confirm(' ${popupclosemsg} ');
		}

		window.opener.location.href = '${requestedURL}';
		window.close();

	});
</script>

<%@ include file="footer.jsp"%>


