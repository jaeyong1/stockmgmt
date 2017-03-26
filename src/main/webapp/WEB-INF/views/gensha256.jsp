<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="header.jsp"%>


<script>
	function jsLogin() {

		// 아이디/비밀번호 암호화 후 hidden form으로 submit        
		var pv = document.forms["formlogin"].elements["plaintext"].value;
		document.forms["formlogin"].elements["ciphertext"].value = "";

		document.forms["formlogin"].method = "post";
		document.forms["formlogin"].action = "/gensha256";
		document.forms["formlogin"].submit();
	}
</script>

<script>
	$(function() {
		//when loading..
		document.forms["formlogin"].elements["plaintext"].value = '${reqPlainText}';
		document.forms["formlogin"].elements["ciphertext"].value =  '${genCiperText}';

	});
</script>

<h3>
	<b>[SHA256 Test]</b>
</h3>
<br>

<form class="form-signin" name="formlogin" autocomplete='off'>
	<input type="text" id="plaintext" name='plaintext' class="form-control"
		placeholder="Input Plain Text for encryption" required autofocus>
	<br> <input type="text" id="ciphertext" name='ciphertext'
		class="form-control" placeholder="Generated ciphertext" required><br>
	<button class="btn btn-lg btn-primary btn-block"
		OnClick="javascript:jsLogin();">Generate SHA256</button>
</form>


<%@ include file="footer.jsp"%>
