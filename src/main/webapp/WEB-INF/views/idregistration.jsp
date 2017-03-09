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
		//신규가입 레벨선택
		function choice(o) {
			var v = o.value;
			document.forms["newuserform"].elements["user-Level"].value = v;
			console
					.log("lv:"
							+ document.forms["newuserform"].elements["user-Level"].value);
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

		function newUser() {
			
			if (!document.forms["newuserform"].elements["user-Id"].value) {
				alert("Id를 기입해주세요");
				document.forms["newuserform"].elements["user-Id"].focus();
				return;
			}

			if (!document.forms["newuserform"].elements["user-Name"].value) {
				alert("이름을 기입해주세요");
				document.forms["newuserform"].elements["user-Name"].focus();
				return;
			}

			if (!document.forms["newuserform"].elements["user-Email"].value) {
				alert("Email을 기입해주세요");
				document.forms["newuserform"].elements["user-Email"].focus();
				return;
			}

			if (!document.forms["newuserform"].elements["user-Teamname"].value) {
				alert("소속팀명을 기입해주세요");
				document.forms["newuserform"].elements["user-Teamname"].focus();
				return;
			}

			if (!document.forms["newuserform"].elements["user-Password"].value) {
				alert("비빌번호를 기입해주세요");
				document.forms["newuserform"].elements["user-Password"].focus();
				return;
			}

			if (!document.forms["newuserform"].elements["user-Level"].value) {
				alert("업무를 선택해주세요");
				document.forms["newuserform"].elements["user-Level-choice"]
						.focus();
				return;
			}
			
			var pwvalid = checkpasswordrule();
			if (pwvalid == false) {
				return;
			}
			//newuserform		
			var nm = document.forms["newuserform"].elements["user-Id"].value;
			var response = confirm(nm + "로 등록하시겠습니까?")
			if (response) {
				//do yes task
				document.forms["newuserform"].method = "post";
				document.forms["newuserform"].action = "/newuser"
				document.forms["newuserform"].submit();
			} else {
				//do no task
			}
			alert("로그인해주세요");
		}
		function modifyCheck2(id) {
			var nm = document.forms["form" + id].elements["user-Id"].value;
			var response = confirm(nm + "데이터 수정할까요?")
			if (response) {
				//do yes task
				document.forms["form" + id].method = "post";
				document.forms["form" + id].action = "/admin/reqmodify"
				document.forms["form" + id].submit();
			} else {
				//do no task
			}
		}

		function ResetPassword(id) {
			var nm = document.forms["form" + id].elements["user-Id"].value;
			var response = confirm(nm + "의 패스워드 리셋 시킬까요?")
			if (response) {
				//do yes task	
				document.forms["form" + id].method = "post";
				document.forms["form" + id].action = "/admin/reqresetpassword"
				document.forms["form" + id].submit();
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
			<center>회원가입</center>
		</h2>

		<!-- Trigger the modal with a button -->
		<button type="button" class="btn btn-info btn-lg  btn-block"
			data-toggle="modal" data-target="#myModal">주의사항 확인해 주세요</button>


		<br>
		<!-- <label for="inputEmail" class="sr-only">Email address</label> -->

		<!-- ID -->
		<input type="text" id="user-Id" name='user-Id' class="form-control"
			placeholder="LG Innotek ID" required autofocus>

		<!--  Name -->
		<input type="text" id="user-Name" name='user-Name'
			class="form-control" placeholder="이름" required autofocus>


		<!--  Email -->
		<input type="text" id="user-Email" name='user-Email'
			class="form-control" placeholder="E-mail" required autofocus>

		<!--  Team name -->
		<input type="text" id="user-Teamname" name='user-Teamname'
			class="form-control" placeholder="소속 팀명" required autofocus>


		<!--  ROLE -->
		<input type="hidden" name='user-Level'>

		<!-- 클릭하면 input에 대입 -->
		<select class="form-control" name="user-level-choice" size="1"
			onchange="choice(this);">
			<option selected value="1">==선택하세요==</option>
			<option value="2">개발담당자</option>
			<option value="3">출고담당자</option>
			<option value="4">손님(테스트용)</option>
		</select>

		<!--  Password -->
		<label for="inputPassword" class="sr-only">Password</label> <input
			type="Password" id="user-Password" name='user-Password'
			class="form-control" placeholder="Password" required>

		<!-- button -->
		<br>
		<button type="button" class="btn btn-lg btn-primary btn-block"
			OnClick="javascript:newUser();">ID Registration</button>


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
					<h4 class="modal-title">회원가입 안내</h4>
				</div>
				<div class="modal-body">
					<p>

						- 휴가/부재중 대리업무를 위해 <b>동료에게 알려줘도되는 암호</b>를 권장합니다. <br> (평소쓰는 것과
						별개로)
					<p>
						- <b>개발담당자</b>는 자신의 프로젝트를 생성후에 자산을 등록할 수 있습니다. <br> - 개발담당자는
						프로젝트 생성시에 시작담당자(출고담당자)를 꼭 <b>지정</b>해야 합니다. <br>
						&nbsp;&nbsp;잘모를경우 "ADMIN"이라 써놓고 관리자에게 연락주세요.
					<p>
						- <b>시작담당자(출고담당자)</b>는 출고업무를 위해서는 프로젝트를 할당 받아야 합니다. <br>
						&nbsp;&nbsp;프로젝트가 할당안되어 있으면 출고요청업무도 없습니다. &nbsp;&nbsp;개발담당자가 프로젝트
						관리메뉴에서 시작담당자를 지정합니다.
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
