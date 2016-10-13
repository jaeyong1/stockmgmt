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
		console.log("담기");
		var nm = document.forms["form" + id].elements["part-Id"].value;
		var response = confirm(nm + "데이터 추가할까요?")
		if (response) {
			//do yes task
			document.forms["form" + id].method = "post";
			document.forms["form" + id].action = "/reqshippartsadd"
			document.forms["form" + id].submit();
		} else {
			//do no task
		}
	}
</script>


<center>
	<h3>파트너의 재고 조회</h3>
</center>

<form name="formSearchType">
	<p align="right">
		&nbsp;<select name="srchtype" size="1">
			<option selected value="projcode">프로젝트Code</option>
			<option value="shipperid">출고담당자이름</option>
			<option value="devid">개발담당자이름</option>
			<option value="lgitpn">LGIT P/N</option>
		</select> <input type="text" name="srchword"> <input type="submit"
			name="btnsrch" value="검색"> <input type="submit"
			name="exportxls" disabled=true value="선택엑셀받기"> <input
			type="button" name="addshiplist" value="출고요청담기" disabled=true
			onClick="reqShipping('chk[]', 'reqnum[]')" />
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
		<th width="192">
			<p>&nbsp;Project Code</p>
		</th>
		<th width="85">
			<p>부서</p>
		</th>
		<th width="129">
			<p>개발담당자</p>
		</th>
		<th width="117">
			<p>출고담당자</p>
		</th>
		<th width="112">
			<p>LGIT P/N</p>
		</th>
		<th width="126">
			<p>Item&nbsp;Desc</p>
		</th>
		<th width="114">
			<p>Maker</p>
		</th>
		<th width="90">
			<p>재고</p>
		</th>
		<th width="90">
			<p>출고요청량</p>
		</th>
		<th width="90">
			<p>단가</p>
		</th>
		<th width="67">
			<p>위치</p>
		</th>

	</tr>
	<!-- DB 데이터 채움 (클래스 변수사용) -->
	<c:forEach var="i" items="${items}" varStatus="status">

		<form name="form${status.index}">
			<tr>
				<td width="42">
					<p>
						&nbsp;<input type="checkbox" name="chk[]" value='${i.partId}'
							disabled=true> <input type=hidden name=part-Id
							value='${i.partId}'>
					</p>
				</td>
				<td width="192">
					<p>${i.partProjectCode}</p>
				</td>
				<td width="85">
					<p>${i.userTeamname}</p>
				</td>
				<td width="129">
					<p>${i.userOwnerName}</p>
				</td>
				<td width="117">
					<p>${i.userShipperName}</p>
				</td>
				<td width="112">
					<p>${i.partName}</p>
				</td>
				<td width="126">
					<p>${i.partDesc}</p>
				</td>
				<td width="114">
					<p>${i.partMemo}</p>
				</td>
				<td width="90">
					<p>${i.partStock}</p>
				</td>
				<td width="140">
					<p>
						<input type="text" name="reqnum[]" value="0" size="4"> <input
							type="button" class="btn btn-primary btn-xs" value="Cart"
							name="submitbtn" OnClick="javascript:addCart('${status.index}');">
					</p>
				</td>

				</td>
				<td width="90">
					<p>${i.partCost}</p>
				</td>
				<td width="67">
					<p>${i.partLocation}</p>
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