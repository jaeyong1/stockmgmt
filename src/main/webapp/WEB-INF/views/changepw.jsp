<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!-- jquery -->
<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>


<!DOCTYPE html>
<head>
<meta http-equiv="content-type" content="text/html; charset=euc-kr">
<title>LGIT 자재관리시스템</title>
<meta name="" content="">


<!-- Bootstrap core CSS -->
<link href="/bootstrap/css/bootstrap.min.css" rel="stylesheet">

<!-- Custom styles for this template -->
<link href="/css/login.css" rel="stylesheet">
</head>

<body>
	<!--  HEADER  -->
	<script language="javascript">
		function changePW() {
			if (!document.forms["newuserform"].elements["user-Id"].value) {
				alert("Id를 기입해주세요");
				document.forms["newuserform"].elements["user-Id"].focus();
				return;
			}

			function checkpasswordrule() {
				var id = document.forms["newuserform"].elements["user-Id"].value;
				var pw = document.forms["newuserform"].elements["user-Password"].value;
				var pwvalid = true;

				//1.
				if (pw.length < 10) {
					pwvalid = false;
				}
				//2.
				var chk = 0;
				if (pw.search(/[0-9]/g) != -1)
					chk++;
				if (pw.search(/[a-z]/ig) != -1)
					chk++;
				if (pw.search(/[!@#$%^&*()?_~]/g) != -1)
					chk++;
				if (chk < 3) {
					pwvalid = false;
				}
				//3.
				if (id == pw) {
					pwvalid = false;
				}

				if (pwvalid == false) {
					alert("패스워드를 확인해 주세요..\n 1) 최소길이 10자 이상 사용하게 하는 기능\n 2) 영문, 숫자, 특수문자(!@#$%^&*()?_~)를 조합하여 사용\n 3) ID와 같은 패스워드 금지");
				}
				return pwvalid;
			}

			if (!document.forms["newuserform"].elements["user-Password"].value) {
				alert("비빌번호를 기입해주세요");
				document.forms["newuserform"].elements["user-Password"].focus();
				return;
			}

			if (!document.forms["newuserform"].elements["user-Password2"].value) {
				alert("새 비빌번호를 기입해주세요");
				document.forms["newuserform"].elements["user-Password2"]
						.focus();
				return;
			}

			var pwvalid = checkpasswordrule();
			if (pwvalid == false) {
				return;
			}

			//newuserform		
			var nm = document.forms["newuserform"].elements["user-Id"].value;
			var response = confirm(nm + "비밀번호를 바꾸시겠습니까?")
			if (response) {
				//do yes task
				document.forms["newuserform"].method = "post";
				document.forms["newuserform"].action = "/changetonewpw"
				document.forms["newuserform"].submit();
			} else {
				//do no task
			}
		}
	</script>



	<!-- 
	*****************************
	회원 가입 폼
	*****************************
	 -->

	<form class="form-signin" name="newuserform">
		<h2 class="form-signin-heading">
			<center>비밀번호변경</center>
		</h2>

		<!-- Trigger the modal with a button -->
		<button type="button" class="btn btn-info btn-lg  btn-block"
			data-toggle="modal" data-target="#myModal">forgot password?</button>


		<br>
		<!-- <label for="inputEmail" class="sr-only">Email address</label> -->

		<!-- ID -->
		<input type="text" id="user-Id" name='user-Id' class="form-control"
			placeholder="LG Innotek ID" required autofocus>

		<!--  Password -->
		<label for="inputPassword" class="sr-only">기존 Password</label> <input
			type="Password" id="user-Password" name='user-Password'
			class="form-control" placeholder="Current Password" required>

		<!--  Password -->
		<label for="inputPassword" class="sr-only">새로운 Password</label> <input
			type="Password" id="user-Password2" name='user-Password2'
			class="form-control" placeholder="New Password" required>


		<!-- button -->
		<br>
		<button type="button" class="btn btn-lg btn-primary btn-block"
			OnClick="javascript:changePW();">Change Password</button>


	</form>


	<!-- Modal -->
	<div class="modal fade" id="myModal" role="dialog">
		<div class="modal-dialog">
			<!-- Trigger the modal with a button -->
			<button type="button" class="btn btn-info btn-lg" data-toggle="modal"
				data-target="#myModal">알려드려요</button>

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">비밀번호 변경 안내</h4>
				</div>
				<div class="modal-body">
					<p>
						- 비밀번호를 잊어버렸을경우에는, 관리자가 찾아줄 수는 없고 기본값으로 바꿔줄 수 있습니다.<br>
					<p>
					<p>- 관리운영자 : jaeyong1.park@lginnotek.com</p>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">닫기</button>
				</div>
			</div>

		</div>
	</div>





	<!-- FOOTER -->
	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
</body>

</html>
