<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!-- jquery -->
<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
<script language="javascript">
var rolekor ="";
	$(function() {			
		rolekor = lev2Kor(${sessionScope.userLoginInfo.userLevel});
		console.log(rolekor);
		$('#userinfo').html('('+rolekor +')');
	//로그인후에 표시	
	});
	
	function lev2Kor(userLevel) {
		if (userLevel == 2)
			return '개발담당자';
		else if (userLevel == 3)
			return '출고담당자';
		else if (userLevel == 4)
			return '손님';
		else if (userLevel == 5)
			return '관리자';
		return '관리자.개발자';
	}
	
	function jsLogin() {
		var id = document.forms["formlogin"].elements["login-id"].value;
		var pw = document.forms["formlogin"].elements["login-pw"].value;
		//console.log(id + "/" + pw);
		document.forms["formlogin"].method = "post";
		document.forms["formlogin"].action = "/loginProcess";
		document.forms["formlogin"].submit();
		//var response = confirm("Login Continue");
	}
</script>


<script language="javascript">
	function modifyCheck2(id) {
		var nm = document.forms["form" + id].elements["itemlist-Id"].value;
		var response = confirm(nm + "데이터 수정할까요?")
		if (response) {
			//do yes task
			document.forms["form" + id].method = "post";
			document.forms["form" + id].action = "/reqshippartsmodify"
			document.forms["form" + id].submit();
		} else {
			//do no task
		}
	}
	function removeItem(id) {
		var nm = document.forms["form" + id].elements["itemlist-Id"].value;
		var response = confirm(nm + "데이터 삭제할까요?")
		if (response) {
			//do yes task
			document.forms["form" + id].method = "post";
			document.forms["form" + id].action = "/reqshippartsremove"
			document.forms["form" + id].submit();
		} else {
			//do no task
		}
	}
</script>
<!DOCTYPE html>
<head>
<meta http-equiv="content-type" content="text/html; charset=euc-kr">
<title>LGIT 자재관리시스템</title>
<meta name="" content="">
</head>

<body>

	<c:choose>
		<c:when test="${not empty sessionScope.userLoginInfo}">
			<!--   ID : <c:out value="${sessionScope.userLoginInfo.userId}" /> -->
			<!-- Name : ${sessionScope.userLoginInfo.userName}님 -->
			<!-- Level : <c:out value="${sessionScope.userLoginInfo.userLevel}" /> -->
			<c:out value="${sessionScope.userLoginInfo.userId}" /> 님
			<div id="userinfo"></div>


			<!-- Team : <c:out value="${sessionScope.userLoginInfo.userTeamname}" />  -->
			<a href="logout">로그아웃</a>

		</c:when>
		<c:otherwise>
			<!-- 로그인 안되어 있으면 로그인페이지로 -->
			<script>alert('로그인 하세요');location.href='login';</script>
		</c:otherwise>
	</c:choose>
	<!-- 
	********************************************************
	HEADER
	******************************************************** 
	-->


	<script language="javascript">

	function newItem() {
		if (!document.forms["formshipreq"].elements["ship-Targetdate"].value) {
			alert("날짜를 기입해주세요\n 형식 : YYYY-MM-DD");
			document.forms["formshipreq"].elements["ship-Targetdate"].focus();
			return;
		}
		
		//get target date		
		var strDate1 = document.forms["formshipreq"].elements["ship-Today"].value;
		var strDate2 = document.forms["formshipreq"].elements["ship-Targetdate"].value;
		var arr1 = strDate1.split('-');
		var arr2 = strDate2.split('-');
		var dat1 = new Date(arr1[0], arr1[1], arr1[2]);
		var dat2 = new Date(arr2[0], arr2[1], arr2[2]);

		//calc date diff
		var diff = dat2 - dat1;
		var currDay = 24 * 60 * 60 * 1000;// 시 * 분 * 초 * 밀리세컨
		var currMonth = currDay * 30;// 월 만듬
		var currYear = currMonth * 12; // 년 만듬
		
		//날짜차이 3일이하면 alert
		if (parseInt(diff/currDay) < 3)
			{
		alert("[알림]\n출고요청일이 오늘로부터 " +parseInt(diff/currDay) +"일 이후 입니다. \n3일이후 날짜로 지정해주세요." );
		return;
			}
		
		//newitemform		
		//var nm = document.forms["formshipreq"].elements["part-Name"].value;
		var response = confirm( "의 데이터를 신규생성할까요?")
		if (response) {
			//do yes task
			document.forms["formshipreq"].method = "post";
			document.forms["formshipreq"].action = "/shipreqprocess"
			document.forms["formshipreq"].submit();
		} else {
			//do no task
		}
	}
	</script>
	<p>1. 출고요청 정보</p>
	<table border="1" width="796">
		<form name="formshipreq">
			<tr>
				<td width="115" height="20">
					<p>요청부서</p> <input type="hidden" name='ship-Id'
					value='${reqshipinfo.shipId}'>


				</td>
				<td width="259" height="20">
					<p>${dbUserTeamName}</p>
				</td>
				<td width="37" height="20">
					<p>&nbsp;</p>
				</td>
				<td width="126" height="20">출고진행상태</td>
				<td width="225" height="20">
					<p>${shipstatekor}</p> <input type="hidden" name='ship-StateId'
					value='${reqshipinfo.shipStateId}'>
				</td>
			</tr>
			<tr>
				<td width="115">
					<p>요청자</p>
				</td>
				<td width="259">
					<p>${dbUserName}(${reqshipinfo.shipRequestorId})</p>
				</td>
				<td width="37"></td>
				<td width="126">
					<p>대표프로젝트</p>
				</td>
				<td width="225"><input type="text" name='ship-ProjectCode'
					value='${reqshipinfo.shipProjectCode}'>
					<p></p></td>
			</tr>
			<tr>
				<td width="115">
					<p>수령지</p>
				</td>
				<td width="259">
					<p>
						<input type="text" name='ship-Destination'
							value='${reqshipinfo.shipDestination}'>
					</p>

				</td>
				<td width="37">
					<p>&nbsp;</p>
				</td>
				<td width="126">
					<p>출고사유</p>
				</td>
				<td width="225">
					<p>
						<input type="text" name='ship-Memo'
							value='${reqshipinfo.shipMemo}'>
					</p>

				</td>
			</tr>
			<tr>
				<td width="115" height="27">
					<p>출고접수일</p>
				</td>
				<td width="259" height="27">
					<p>${reqshipinfo.shipToday}</p> <input type="hidden"
					name='ship-Today' value='${reqshipinfo.shipToday}'>
				</td>
				<td width="37" height="27">
					<p>&nbsp;</p>
				</td>
				<td width="126" height="27">
					<p>&nbsp;</p>
				</td>
				<td width="225" height="27">
					<!-- 본인자산유무 -->
					<p>
						<input type="hidden" name='ship-Ismyproject'
							value='${reqshipinfo.shipIsmyproject}'>&nbsp;
					</p>
				</td>
			</tr>
			<tr>
				<td width="115" height="25">
					<p>출고요청일</p>
				</td>
				<td width="259" height="25">

					<p>
						<input type="text" name='ship-Targetdate'
							value='${reqshipinfo.shipTargetdate}'>
					</p>

				</td>
				<td width="37" height="25">
					<p>&nbsp;</p>
				</td>
				<td width="126" height="25">
					<p>자산협의자</p>
				</td>
				<td width="225" height="25">
					<p>
						<input type="text" name='ship-CoworkerUserid'
							value='${reqshipinfo.shipCoworkerUserid}'>
					</p>
				</td>
			</tr>
			<input type="button" value="출고요청" name="submitbtn1"
				OnClick="javascript:newItem();">
		</form>
	</table>
	<p>&nbsp;</p>
	<p>2.출고요청 리스트</p>
	<!-- 
	*********
	리스트 표시 
	*********
	 -->

	<table border="1">
		<tr>
			<td width="67">
				<p align="center">No</p>
			</td>
			<!-- 
			<td width="135">
				<p align="center">출고요청 Seq</p>
			</td>
			 -->
			<td width="135">
				<p align="center">프로젝트Code</p>
			</td>
			<td width="135">
				<p align="center">LGIT P/N</p>
			</td>
			<td width="169">
				<p align="center">Item Desc</p>
			</td>
			<td width="86">
				<p align="center">재고수량</p>
			</td>
			<td width="76">
				<p align="center">요청수량</p>
			</td>
			<!--<td width="100"></td> -->
		</tr>
		<!-- DB 데이터 채움 (클래스 변수사용) -->
		<c:forEach var="i" items="${items}" varStatus="status">

			<form name="form${status.index}">
				<tr>
					<td width="67"><input type=hidden name=itemlist-Id
						value='${i.itemlistId}'> ${status.index}</td>
					<!-- 
					<td width="135">
						<p align="center">${i.itemlistShipId}</p>
					</td>
					 -->
					<td width="135">
						<p align="center">${i.partProjectCode}</p>
					</td>
					<td width="135">
						<p align="center">${i.itemlistPartId}</p>
					</td>
					<td width="169">
						<p align="center">${i.partDesc}</p>
					</td>
					<td width="86">
						<p align="center">${i.partStock}</p>

					</td>
					<td width="76">
						<p align="center">
							<!-- <input type=text name=itemlist-Amount size=5
								value='${i.itemlistAmount}'>  -->
							${i.itemlistAmount}
						</p>
					</td>
					<!-- 
					<td width="100">
						<p>
							<input type="button" value="수정" name="submitbtn"
								OnClick="javascript:modifyCheck2('${status.index}');"> <input
								type="button" value="삭제" name="submitbtn2"
								OnClick="javascript:removeItem('${status.index}');">
						</p>


					</td>
					 -->
				</tr>
			</form>
		</c:forEach>

	</table>

	<!--
	********************************************************
	FOOTER 
	******************************************************** 
	-->
</body>

</html>

