<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="header.jsp"%>


<center>
	<h3>파일업로드</h3>
</center>


<form enctype="multipart/form-data"
	action='<c:url value="/fileupload" />' method="post">

	파일첨부 <input type="file" name="file1"> <br /> <input
		type="submit" value="파일 업로드 하기" class="btn btn-primary btn-md">
	</input>
	${requestedURL}
	
	<input type=hidden	name=requestedURL value='${requestedURL}'>
</form>


<%@ include file="footer.jsp"%>