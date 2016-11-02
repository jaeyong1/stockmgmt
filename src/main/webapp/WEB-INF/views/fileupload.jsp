<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="header.jsp"%>


<center>
	<h3>파일업로드</h3>
</center>

<script language="javascript">
	function aaa() {
		var nm = document.forms["formaaa"].elements["file1"].value;
		confirm(nm + "?");
			document.forms["formaaa"].method = "post";
			document.forms["formaaa"].action = "/fileupload"
			//document.forms["formaaa"].submit();
			
			return;
		
	}
</script>

<form enctype="multipart/form-data"
	action='<c:url value="/fileupload" />' method="post">

	파일첨부 <input type="file" name="file1"> <br /> <input
		type="submit" value="파일 업로드 하기" class="btn btn-primary btn-md">
	</input> <input type=hidden name=requestedURL value='${requestedURL}'>
</form>


<%@ include file="footer.jsp"%>