<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="header.jsp"%>

<script>
	//체크박스선택한것 갯수, id 집계
	function reqShipping(obj, obj2) {
		var i, sum = 0, tag = [], str = "";
		var tag2 = [], str2 = "";
		var chk = document.getElementsByName(obj);
		var reqnum = document.getElementsByName(obj2);
		var tot = chk.length; //체크박스총갯수
		for (i = 0; i < tot; i++) {
			if (chk[i].checked == true) {
				tag[sum] = chk[i].value; //파츠ID
				tag2[sum] = reqnum[i].value; //출고요청량
				sum++;
			}
		}
		str += "선택갯수 : " + sum;
		if (tag.length > 0)
			str += "\n chkbox id값 : " + tag.join(",");
		if (tag.length > 0)
			str2 += "\n textbox값 : " + tag2.join(",");
		alert(str);
		alert(tag2);

		//출고 장바구니에 넣을래?
		//리스트를 만들어서 컨트롤러로 넘김
		//현재 화면 다시 표시

	}

	function addCart(id) {
		console.log("addCart");
		
		chk = document.getElementsByName("chk");
		console.log("total chkbox length:" + chk.length);
		
		var sendStr ="";
		var i;
		for (i = 0; i < chk.length; i++) {
			if (chk[i].checked) {
				console.log("checked index : " + i );
				console.log("    part id : " + document.forms["form" + i].elements["part-Id"].value );
				console.log("    name : " + document.forms["form" + i].elements["part-Name"].value );
				console.log("    amount : " + document.forms["form" + i].elements["reqnum[]"].value );
				console.log("    myURL : " + document.forms["form" + i].elements["requestedURL"].value );
				console.log("    page num : " + document.forms["form" + i].elements["seq"].value );
				
				//to do: 잘 묶어서 던지고 받기.. spring에서 필요한 항목들 추려서... 
				//
				//
				//
				//
				//
				//
				//
				sendStr = sendStr +
				document.forms["form" + i].elements["part-Id"].value + "|" +
				document.forms["form" + i].elements["part-Name"].value + "|" +
				document.forms["form" + i].elements["reqnum[]"].value  + "|" +
				document.forms["form" + i].elements["requestedURL"].value + "|" +
				document.forms["form" + i].elements["seq"].value + "|"
			}
		}
		console.log(sendStr);
		var nm = document.forms["form" + id].elements["part-Name"].value;
		var response = confirm(nm + " 추가할까요?")
		if (response) {
			//do yes task
			var title = "Boxing..";
			var status = "toolbar=no,directories=no,scrollbars=no,resizable=no,status=no,menubar=no,width=240, height=200, top=200,left=200";
			window.open("", title, status); //popup
			document.forms["form" + id].target = title;
			document.forms["form" + id].method = "post";
			document.forms["form" + id].action = "${PostPageUrl}"; //"/reqshippartsadd"
			document.forms["form" + id].submit();
		} else {
			//do no task
		}
	}

	function changedValue(obj, id) {
		console.log("changedValue id" + id + ", value :" + obj.value);
		if (obj.value == 0) {
			obj.style.backgroundColor = 'white';
			chk = document.getElementsByName("chk");
			chk[id].checked = false;
		} else if (obj.value < 0) {
			obj.style.backgroundColor = 'red';
			chk = document.getElementsByName("chk");
			chk[id].checked = false;
		} else {
			obj.style.backgroundColor = 'yellow';
			chk = document.getElementsByName("chk");
			chk[id].checked = true;
		}
	}
</script>

<!-- Excel download -->
<script>
	function showExcelExportWindow() {
		window.open("/mylistexport${requestedURL}", 'window',
				'width=650,height=450');
		//  자동생성 경로..
		//1.  /mylistexport/mylist
		//2.  /mylistexport/otherslist

	}
</script>
<!-- Excel download -->

<!-- <center>	<h3>나의 재고 조회</h3> </center>  -->
<center>
	<h3>${PageTitleInfoFromerver}</h3>
</center>

<form name="formSearchType">
	<p align="right">
		<!--  not use anymore-->
		<!-- 
		&nbsp;<select name="srchtype" size="1">
			<option selected value="projcode">프로젝트Code</option>
			<option value="shipperid">출고담당자이름</option>
			<option value="devid">개발담당자이름</option>
			<option value="lgitpn">LGIT P/N</option>
		</select> <input type="text" name="srchword"> <input type="submit"
			name="btnsrch" value="검색">

			 -->
		<!-- Excel download -->
		<input type="button" value="Excel download" name="submitbtn1"
			class="btn btn-info btn-xs"
			OnClick="javascript:showExcelExportWindow();">
	</p>
</form>

<!-- 
	*********
	리스트 표시 
	*********
	 -->


<table border="1">
	<tr class="danger">

		<th width="42">
			<p>&nbsp;</p>
		</th>
		<th width="172"><center>
				<p>&nbsp;Project Code</p>
			</center></th>
		<th width="100">
			<center>
				<p>부서</p>
			</center>
		</th>
		<th width="130">
			<center>
				<p>개발담당자</p>
			</center>
		</th>
		<th width="130">
			<center>
				<p>출고담당자</p>
			</center>
		</th>
		<th width="112">
			<center>
				<p>LGIT P/N</p>
			</center>
		</th>
		<th width="126">
			<center>
				<p>Item&nbsp;Desc</p>
			</center>
		</th>
		<th width="114">
			<center>
				<p>Maker</p>
			</center>
		</th>
		<th width="90">
			<center>
				<p>재고</p>
			</center>
		</th>
		<th width="90">
			<center>
				<p>출고요청량</p>
			</center>
		</th>
		<th width="90">
			<center>
				<p>단가</p>
			</center>
		</th>
		<th width="67">
			<center>
				<p>위치</p>
			</center>
		</th>
		<th width="67">
			<center>
				<p>MSL Level</p>
			</center>
		</th>
	</tr>

	<!-- DB 데이터 채움 (클래스 변수사용) -->
	<c:forEach var="i" items="${items}" varStatus="status">

		<form name="form${status.index}">
			<tr>

				<td width="42">
					<p>
						&nbsp; <input type="checkbox" name="chk"
							value="chk${status.index}" disabled=true> <input
							type=hidden name=part-Id value='${i.partId}'> <input
							type=hidden name=requestedURL value='${requestedURL}'> <input
							type=hidden name=seq value='${seq}'>
					</p>
				</td>

				<td width="192">
					<center>
						<p>${i.partProjectCode}</p>
					</center>
				</td>
				<td width="85">
					<center>
						<p>${i.userTeamname}</p>
					</center>
				</td>
				<td width="129">
					<center>
						<p>${i.userOwnerName}</p>
					</center>
				</td>
				<td width="117">
					<center>
						<p>${i.userShipperName}</p>
					</center>
				</td>
				<td width="112">
					<center>
						<input type=hidden name=part-Name value='${i.partName}'>
						<p>${i.partName}</p>
					</center>
				</td>
				<td width="126">
					<center>
						<p>${i.partDesc}</p>
					</center>
				</td>
				<td width="114">
					<center>
						<p>${i.partMemo}</p>
					</center>
				</td>
				<td width="90">
					<center>
						<p>${i.partStock}</p>
					</center>
				</td>

				<td width="140">
					<p>
						<input type="text" name="reqnum[]" value="0" size="4"
							onchange="changedValue(this, '${status.index}')"> <input
							type="button" class="btn btn-primary btn-xs" value="Box"
							name="submitbtn" OnClick="javascript:addCart('${status.index}');">
					</p>
				</td>


				</td>
				<td width="90">
					<center>
						<p>${i.partCost}</p>
					</center>
				</td>
				<td width="67">
					<center>
						<p>${i.partLocation}</p>
					</center>
				</td>
				<td width="67">
					<center>
						<p>${i.partMsllevel}</p>
					</center>
				</td>
			</tr>
		</form>
	</c:forEach>

</table>
<!-- 
	*****************************
	페이지 표시
	*****************************
	 -->

<div class="col-xs-8">
	<ul class="pagination pagination-sm" style="margin-top: 0px;">
		<!-- 시작페이지가 1부터면 이전 표시("<<") ​ 안함 -->
		<c:if test="${start-1 ==0 }">

		</c:if>
		<!-- 시작페이지가 1이 아니면 << 이전 표시.  링크는 시작페이지가 6부터 10까지일 경우 5페이지를 가르킴 -->
		​

		<c:if test="${start-1!=0}">
			<li><a href="/mylist/${start-1}">&laquo;</a></li>
		</c:if>
		<!-- 5개씩 페이지 표시-->
		​

		<c:forEach var="i" begin="${start }" end="${end }">
			<li id="page${i }"><a href="/mylist/${i}">${i}</a></li>
		</c:forEach>
		<!-- end페이지 번호가 5, 10 인데 전체 페이지 갯수가 end페이지 보다 크면 다음 페이징 바로가기 표시  (">>")​ .-->
		​

		<c:if test="${end % 5 == 0 && pageNum > end}">
			<li><a href="/mylist/${end+1}">&raquo;</a></li>
		</c:if>
		<!-- 마지막 페이지 번호와 전체 페이지 번호가 같으면서 5개 단위가 아니면 다음바로가기 표시 않함 -->
		​​

		<c:if test="${end % 5 != 0 && end == pageNum }">

		</c:if>
	</ul>
</div>



<%@ include file="footer.jsp"%>