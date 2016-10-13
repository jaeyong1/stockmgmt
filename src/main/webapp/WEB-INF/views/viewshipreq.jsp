<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="header.jsp"%>

<script language="javascript">
	//State 3 -> 4 
	function shipperChecked() {
		alert("프린트창을 닫으면 출고진행상태가 변경됩니다.");
		window.print();

		document.forms["formshipreq"].method = "post";
		document.forms["formshipreq"].action = "/shipreqprocess/state4";
		document.forms["formshipreq"].submit();

	}

	//State 4 -> 5
	function shipperAccept() {
		document.forms["formshipreq"].method = "post";
		document.forms["formshipreq"].action = "/shipreqprocess/state5";
		document.forms["formshipreq"].submit();

	}

	//State 4 -> 6
	function shipperRej() {
		document.forms["formshipreq"].method = "post";
		document.forms["formshipreq"].action = "/shipreqprocess/state6";
		document.forms["formshipreq"].submit();

	}
</script>



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
		if (parseInt(diff / currDay) < 3) {
			alert("[알림]\n출고요청일이 오늘로부터 " + parseInt(diff / currDay)
					+ "일 이후 입니다. \n3일이후 날짜로 지정해주세요.");
			return;
		}

		//newitemform		
		//var nm = document.forms["formshipreq"].elements["part-Name"].value;
		var response = confirm("출고요청 데이터를 생성할까요?")
		if (response) {
			//do yes task
			document.forms["formshipreq"].method = "post";
			document.forms["formshipreq"].action = "/shipreqprocess/state3" //'3출고접수'단계로 이동
			document.forms["formshipreq"].submit();
		} else {
			//do no task
		}
	}
</script>
<h3>1. 출고요청 정보</h3>

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

				<p>${dbUserName}(${reqshipinfo.shipRequestorId})</p> <input
				type="hidden" name='ship-RequestorId'
				value='${reqshipinfo.shipRequestorId}'>
			</td>
			<td width="37"></td>
			<td width="126">
				<p>대표프로젝트</p>
			</td>
			<td width="225">
				<p>
					${reqshipinfo.shipProjectCode} <input type="hidden"
						name='ship-ProjectCode' value='${reqshipinfo.shipProjectCode}'>
				</p>
			</td>
		</tr>
		<tr>
			<td width="115">
				<p>수령지</p>
			</td>
			<td width="259">
				<p>
					${reqshipinfo.shipDestination} <input type="hidden"
						name='ship-Destination' value='${reqshipinfo.shipDestination}'>
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
					${reqshipinfo.shipMemo} <input type="hidden" name='ship-Memo'
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
				<p>본인자산여부</p>
			</td>
			<td width="225" height="27">
				<p>
					<c:choose>
						<c:when test="${0 == reqshipinfo.shipIsmyproject }">
							  No(타개발자와합의)
							</c:when>
						<c:otherwise>
							 Yes
							</c:otherwise>
					</c:choose>

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
					${reqshipinfo.shipTargetdate} <input type="hidden"
						name='ship-Targetdate' value='${reqshipinfo.shipTargetdate}'>
				</p>

			</td>
			<td width="37" height="25">
				<p>&nbsp;</p>
			</td>
			<td width="126" height="25">
				<p>
					<c:choose>
						<c:when test="${0 == reqshipinfo.shipIsmyproject }">
								자산협의자
						</c:when>
					</c:choose>
				</p>
			</td>
			<td width="225" height="25">
				<p>
					<c:choose>
						<c:when test="${0 == reqshipinfo.shipIsmyproject }">
							${reqshipinfo.shipCoworkerUserid}
							</c:when>
					</c:choose>
					<input type="hidden" name='ship-CoworkerUserid'
						value='${reqshipinfo.shipCoworkerUserid}'>
				</p>
			</td>
		</tr>
		<!-- <input type="button" value="출고요청" name="submitbtn1"
				OnClick="javascript:newItem();">  -->

	</form>
</table>

<p>&nbsp;</p>
<h3>2. 출고요청 리스트</h3>
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
		<td width="169">
			<p align="center">Maker</p>
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
				<td width="67">
					<p align="center">
					${status.index}
						<input type=hidden name=itemlist-Id value='${i.itemlistId}'>
						
					</p>
				</td>
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
				<td width="169">
					<p align="center">${i.partMemo}</p>
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
	Button 
	******************************************************** 
	-->
<!-- state 3 > 4 -->

<c:choose>
	<c:when
		test="${(3 == sessionScope.userLoginInfo.userLevel) && (3 == reqshipinfo.shipStateId) }">
		<input type="button" value="출고접수완료" name="submitbtn1" class="btn btn-primary btn-md"
			OnClick="javascript:shipperChecked();">&nbsp;&nbsp;
	</c:when>
</c:choose>

<!-- state 4 > 5 -->

<c:choose>
	<c:when
		test="${(3 == sessionScope.userLoginInfo.userLevel) && (4 == reqshipinfo.shipStateId) }">
		<input type="button" value="출고완료" name="submitbtn2" class="btn btn-primary btn-md"
			OnClick="javascript:shipperAccept();">&nbsp;&nbsp;
	</c:when>
</c:choose>

<!-- state 4 > 6 -->

<c:choose>
	<c:when
		test="${(3 == sessionScope.userLoginInfo.userLevel) && (4 == reqshipinfo.shipStateId) }">
		<input type="button" value="출고요청반려" name="submitbtn3" class="btn btn-primary btn-md"
			OnClick="javascript:shipperRej();">&nbsp;&nbsp;
	</c:when>
</c:choose>



<%@ include file="footer.jsp"%>

