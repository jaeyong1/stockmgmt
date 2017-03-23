package com.lgit.stockmgmt.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.lgit.stockmgmt.dao.ItemDao;
import com.lgit.stockmgmt.domain.EUserLevel;
import com.lgit.stockmgmt.domain.Item;
import com.lgit.stockmgmt.domain.JoinDBItem;
import com.lgit.stockmgmt.domain.LogUserItem;
import com.lgit.stockmgmt.domain.PartsItem;
import com.lgit.stockmgmt.domain.ProjectItem;
import com.lgit.stockmgmt.domain.SecureUserItem;
import com.lgit.stockmgmt.domain.ShipReqItem;
import com.lgit.stockmgmt.domain.ShipReqPartsItem;
import com.lgit.stockmgmt.domain.UserItem;

@Service("itemService")
public class ItemService {

	@Autowired
	private ItemDao itemDao;

	/*
	 * Mail Service 연결
	 */
	@Autowired
	private MailService mailService;

	public List<Item> getItems() {
		return itemDao.queryItems();
	}

	public List<PartsItem> getPartsItems() {
		return itemDao.queryPartsItems();
	}

	public List<ProjectItem> getProjectItems() {
		return itemDao.queryProjectItems();
	}

	public List<ProjectItem> getProjectItems(int rowsPerPage, int page) {
		return itemDao.queryProjectItems();
	}

	public List<UserItem> getUserItems() {
		System.out.println("[getUserItems] :" + itemDao.getTime());
		return itemDao.queryUserItems();
	}

	public int setUserItem(UserItem userdata) {
		return itemDao.insertUserItem(userdata);
	}

	public int changeUserItem(UserItem userdata) {
		return itemDao.updateUserItem(userdata);
	}

	public int changeUserPassword(UserItem userdata) {
		return itemDao.updateUserItem4PwChange(userdata);
	}

	public String getUserItemsRow() {
		return itemDao.queryUserItemsRow();
	}

	public String getPrjectsItemsRow() {
		return itemDao.queryProjectItemsRow();
	}

	public int setProjectItem(ProjectItem item) {
		return itemDao.insertProjectItem(item);

	}

	public int changeProjectItem(ProjectItem item) {
		return itemDao.updateUserItem(item);

	}

	public int removeProjectItem(ProjectItem item) {
		return itemDao.deleteProjectItem(item);

	}

	public String getPartsItemsRow() {
		return itemDao.queryPartsItemsRow();
	}

	public int setPartsItem(PartsItem item) {
		return itemDao.insertPartsItem(item);

	}

	public int changePartsItem(PartsItem item) {
		return itemDao.updatePartsItem(item);

	}

	public int removePartsItem(PartsItem item) {
		return itemDao.deletePartsItem(item);
	}

	public UserItem findByUserIdAndPassword(String logingUserId, String loginUserPassword) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("logingUserId", logingUserId);
		paramMap.put("loginUserPassword", loginUserPassword);

		List<UserItem> list = itemDao.queryUserItemsByIDPW(paramMap);
		if (list.size() == 0) {
			// no user DB data
			System.out.println("[ItemService] no user data");
			return null;
		} else

			System.out.println("[ItemService] finded user. login ID" + list.get(0).getUserId());
		return list.get(0);

	}

	public UserItem findByUserId(String logingUserId) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("logingUserId", logingUserId);

		List<UserItem> list = itemDao.queryUserItemsByID(paramMap);
		if (list.size() == 0) {
			// no user DB data
			System.out.println("[ItemService] no user data");
			return null;
		} else

			System.out.println("[ItemService] finded user. login ID" + list.get(0).getUserId());
		return list.get(0);

	}

	/*
	 * Owner Name으로 검색
	 */
	public List<JoinDBItem> getMyItemsByOwnerName(String userOwnerName) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("keyWord", userOwnerName);
		List<JoinDBItem> ret = itemDao.queryJoinItemsByOwnerName(paramMap);
		// for (JoinDBItem a : ret)
		// System.out.println(a.getPartName() + ":" + a.getPartMsllevel());
		return ret;
		// return itemDao.queryJoinItemsByOwnerName(paramMap);
	}

	public List<JoinDBItem> getOthersItemsByOwnerName(String userOwnerName) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("keyWord", userOwnerName);
		return itemDao.queryOthersJoinItemsByOwner(paramMap);
	}

	public List<ShipReqPartsItem> getShipPartsListItems(int itemlistShipId, String loginID) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("itemlistShipId", itemlistShipId + "");
		paramMap.put("userId1", loginID);
		return itemDao.queryShipPartsListItems(paramMap);
	}

	public int getShipPartsListItemsCounter1(String loginID) {
		Map<String, String> paramMap1 = new HashMap<String, String>();
		paramMap1.put("itemlistShipId", -1 + "");
		paramMap1.put("userId1", loginID);

		int cnt1 = 0;
		try {
			cnt1 = Integer.parseInt(itemDao.queryShipPartsListItemsCounter(paramMap1)); // 내자재_출고대기
		} catch (Exception e) {
			System.out.println("[ERROR] User's cart item get FAILED!!!!");
			cnt1 = 0;
		}
		return cnt1;
	}

	public int getShipPartsListItemsCounter2(String loginID) {
		Map<String, String> paramMap2 = new HashMap<String, String>();
		paramMap2.put("itemlistShipId", -2 + "");
		paramMap2.put("userId1", loginID);

		int cnt2 = 0;
		try {
			cnt2 = Integer.parseInt(itemDao.queryShipPartsListItemsCounter(paramMap2)); // 파트너자재_출고대기
		} catch (Exception e) {
			System.out.println("[ERROR] User's cart item get FAILED!!!!");
			cnt2 = 0;
		}
		return cnt2;
	}

	public int changeShipPartsItem(ShipReqPartsItem shippartsdata) {
		return itemDao.updateShipPartsItem(shippartsdata);
	}

	public int removeShipPartsItem(ShipReqPartsItem shippartsdata) {
		return itemDao.deleteShipPartsItem(shippartsdata);

	}

	public int addShipPartsItem(ShipReqPartsItem shippartsdata) {
		return itemDao.insertShipPartsItem(shippartsdata);

	}

	public String getUserNamebyID(String logingUserId) {
		// TODO Auto-generated method stub
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("logingUserId", logingUserId);

		List<UserItem> list = itemDao.queryUserItemsbyID(paramMap);
		if (list.size() == 0) {
			System.out.println("[ItemService] no user data");
			return null;
		}
		return list.get(0).getUserName();
	}

	public String getUserEmailbyID(String logingUserId) {
		// TODO Auto-generated method stub
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("logingUserId", logingUserId);

		List<UserItem> list = itemDao.queryUserItemsbyID(paramMap);
		if (list.size() == 0) {
			System.out.println("[ItemService] no user data");
			return null;
		}
		return list.get(0).getUserEmail();
	}

	public String getTeamNamebyID(String logingUserId) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("logingUserId", logingUserId);

		List<UserItem> list = itemDao.queryUserItemsbyID(paramMap);
		if (list.size() == 0) {
			System.out.println("[ItemService] no user data");
			return null;
		}
		return list.get(0).getUserTeamname();
	}

	public String getOwnerEmailbyShipId(int shipid) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("shipId", String.valueOf(shipid));
		return itemDao.queryOwnerEmailbyShipId(paramMap);
	}

	public String getOwnerNamebyShipId(int shipid) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("shipId", String.valueOf(shipid));
		return itemDao.queryOwnerNamebyShipId(paramMap);
	}

	public String getShipperEmailbyShipId(int shipid) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("shipId", String.valueOf(shipid));
		return itemDao.queryShipperEmailbyShipId(paramMap);
	}

	public int stateMove1to3(ShipReqItem shipreqdata, String UserId) {

		int partsShipreqId = 0;

		if (shipreqdata.getShipId() == -1) {
			// shipreq 생성
			itemDao.insertShipReqItem(shipreqdata);

			// last id 받아옴 (myID, desc순으로 마지막꺼)
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("UserId", UserId);
			List<ShipReqItem> list = itemDao.queryShipReqItem(paramMap);
			partsShipreqId = list.get(0).getShipId();
			System.out.println("new ship id :" + partsShipreqId);

		} else {

			partsShipreqId = shipreqdata.getShipId();
			System.out.println("last ship id :" + partsShipreqId);
			itemDao.insertShipReqItem(shipreqdata);

			// update;
		}
		// tb_part에서 myID이면서 -1인것 id로 업데이트
		Map<String, String> paramMap2 = new HashMap<String, String>();
		paramMap2.put("UserId", UserId);
		System.out.println("requestor id : " + UserId);
		paramMap2.put("OldShipId", String.valueOf(-1));

		paramMap2.put("NewShipId", String.valueOf(partsShipreqId));
		itemDao.updateShipParts_ShipId(paramMap2);

		// notify mail
		mailService.notiMail1to3(partsShipreqId, UserId);// DB 지우기전 출고자에게 노티

		return 0;

	}

	public int stateMove3to1(ShipReqItem shippartsdata, String UserId) {
		// 2. 기존 ship_id인 아이템들은 -1로 변경
		// tb_part에서 myID이면서 -1인것 id로 업데이트
		Map<String, String> paramMap2 = new HashMap<String, String>();
		paramMap2.put("UserId", UserId);
		paramMap2.put("OldShipId", String.valueOf(shippartsdata.getShipId()));
		paramMap2.put("NewShipId", String.valueOf("-1"));
		itemDao.updateShipParts_ShipId(paramMap2);

		// 3. ship_id인거 reqship은 삭제
		itemDao.deleteShipReqItem(shippartsdata);
		return 0;

	}

	public int stateMove1to2(ShipReqItem shipreqdata, String UserId) {

		int partsShipreqId = 0;

		if (shipreqdata.getShipId() == -1) {
			// shipreq 생성
			itemDao.insertShipReqItem(shipreqdata);

			// last id 받아옴 (myID, desc순으로 마지막꺼)
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("UserId", UserId);
			List<ShipReqItem> list = itemDao.queryShipReqItem(paramMap);
			partsShipreqId = list.get(0).getShipId();
			System.out.println("new ship id :" + partsShipreqId);

		} else {

			partsShipreqId = shipreqdata.getShipId();
			System.out.println("last ship id :" + partsShipreqId);
			itemDao.insertShipReqItem(shipreqdata);

			// update;
		}
		// tb_part에서 myID이면서 -2인것 id로 업데이트
		Map<String, String> paramMap2 = new HashMap<String, String>();
		paramMap2.put("UserId", UserId);
		paramMap2.put("OldShipId", String.valueOf(-2));
		paramMap2.put("NewShipId", String.valueOf(partsShipreqId));
		itemDao.updateShipParts_ShipId(paramMap2);

		// notify mail
		mailService.notiMail1to2(shipreqdata.getShipCoworkerUserid(), UserId);// DB
																				// 지우기전
																				// 출고자에게
																				// 노티
		return 0;

	}

	public List<ShipReqItem> getShipReqItems(String userId) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("UserId", userId);
		return itemDao.queryShipReqItem(paramMap);

	}

	public List<ShipReqItem> getShipReqItems(int reqshipid) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("shipId", String.valueOf(reqshipid));
		return itemDao.queryShipReqItemByShipId(paramMap);
	}

	public List<ShipReqItem> getShipReqItemsForShipper(String userId) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("UserId", userId);
		return itemDao.queryShipReqItemForShipper(paramMap);
	}

	public List<ShipReqItem> getShipReqItemsForAdminShipper() {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("nothing", null);
		return itemDao.queryShipReqItemForAdminShipper(paramMap);
	}

	public int stateMove3to4(int shipId, int shipStateId, String shipRejectCause, String shipDeliveredDateMethod) {
		Map<String, String> paramMap2 = new HashMap<String, String>();
		paramMap2.put("shipId", String.valueOf(shipId));
		paramMap2.put("newShipStateId", String.valueOf(shipStateId));
		paramMap2.put("shipRejectCause", shipRejectCause);
		paramMap2.put("shipDeliveredDateMethod", shipDeliveredDateMethod);
		int r = itemDao.updateShipReqState_ShipId(paramMap2);
		return r;
	}

	public int stateMove4to6(int shipId, int shipStateId, String shipRejectCause, String shipDeliveredDateMethod) {
		// Get itemlist_ship_id
		/**
		 * itemlist_part_id=part_id | itemlist_amount
		 */
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("itemlistShipId", shipId + "");
		List<ShipReqPartsItem> partslist = itemDao.queryShipPartsitemsByShipid(paramMap);
		System.out.println(partslist.size() + "건의 재고증가 진행");

		int i;
		Map<String, String> paramMap3 = new HashMap<String, String>();
		for (i = 0; i < partslist.size(); i++) {
			paramMap3.clear();
			paramMap3.put("part_id", String.valueOf(partslist.get(i).getItemlistPartId()));
			paramMap3.put("plus_amount", String.valueOf(partslist.get(i).getItemlistAmount()));
			System.out.println("query plus ItemlistPartId:" + partslist.get(i).getItemlistPartId() + ", plus volume:"
					+ partslist.get(i).getItemlistAmount());
			itemDao.updatePlusStockToTBPart(paramMap3);
		}

		Map<String, String> paramMap2 = new HashMap<String, String>();
		paramMap2.put("shipId", String.valueOf(shipId));
		paramMap2.put("newShipStateId", String.valueOf(shipStateId));
		paramMap2.put("shipRejectCause", shipRejectCause);
		paramMap2.put("shipDeliveredDateMethod", shipDeliveredDateMethod);

		// db update
		int r = itemDao.updateShipReqState_ShipId(paramMap2);

		// notify mail
		mailService.notiMail4to6(shipId, shipRejectCause);

		return r;

	}

	public int stateMove4to5(int shipId, int shipStateId, String shipRejectCause, String shipDeliveredDateMethod) {

		// STATE UPDATE
		Map<String, String> paramMap2 = new HashMap<String, String>();
		paramMap2.put("shipId", String.valueOf(shipId));
		paramMap2.put("newShipStateId", String.valueOf(shipStateId));
		paramMap2.put("shipRejectCause", shipRejectCause);
		paramMap2.put("shipDeliveredDateMethod", shipDeliveredDateMethod);

		// notify mail
		mailService.notiMail4to5(shipId, shipDeliveredDateMethod);// DB 지우기전
																	// 출고자에게 노티

		return itemDao.updateShipReqState_ShipId(paramMap2);
	}

	public int getPartStock(int partId) {
		Map<String, String> paramMap2 = new HashMap<String, String>();
		paramMap2.put("part_id", String.valueOf(partId));
		String stock = itemDao.queryStockFromTBPart(paramMap2);
		return Integer.valueOf(stock);
	}

	public void setPartStock(int partId, int amount) {
		Map<String, String> paramMap2 = new HashMap<String, String>();
		paramMap2.put("part_id", String.valueOf(partId));
		paramMap2.put("value", String.valueOf(amount));
		itemDao.updateStockToTBPart(paramMap2);
	}

	public void increasePartStock(int partId, int amount) {
		Map<String, String> paramMap2 = new HashMap<String, String>();
		paramMap2.put("part_id", String.valueOf(partId));
		paramMap2.put("plus_amount", String.valueOf(amount));
		itemDao.updatePlusStockToTBPart(paramMap2);
	}

	public void decreasePartStock(int partId, int amount) {
		Map<String, String> paramMap3 = new HashMap<String, String>();
		paramMap3.put("part_id", String.valueOf(partId));
		paramMap3.put("minus_amount", String.valueOf(amount));
		itemDao.updateMinusStockToTBPart(paramMap3);
	}

	public List<ProjectItem> getMyProjectItems(String userId) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("UserId", userId);
		return itemDao.queryMyProjectItems(paramMap);
	}

	public List<ProjectItem> getMyProjectItems4shipper(String userId) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("UserId", userId);
		return itemDao.queryMyProjectItems4shipper(paramMap);
	}

	public List<PartsItem> getMyItemsByID(String userId) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("UserId", userId);
		return itemDao.queryMyPartsItemsById(paramMap);
	}

	public List<PartsItem> getMyItemsByID4Shipper(String userId) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("UserId", userId);
		return itemDao.queryMyPartsItemsById4Shipper(paramMap);
	}

	public List<UserItem> getShipperUserItems() {
		// 출고담당자 리스트
		return itemDao.queryShipperUserItems();
	}

	// 개발담당자 리스트
	public List<UserItem> getDevUserItems() {
		return itemDao.queryDevUserItems();
	}

	public boolean isVaildOthersCartItem(Integer partsId, String userId) {

		// 입력받은 아이템의 owner정보가져옴

		Map<String, String> paramMap1 = new HashMap<String, String>();
		paramMap1.put("partId", String.valueOf(partsId));
		List<UserItem> items1 = itemDao.getUserItemsByPartsId(paramMap1);
		if (items1.size() == 0) {
			System.out.println("[타인출고담기] 잘못된 PartId로 추정됨.. partsId:" + partsId);
			return false;
		}
		String partsOwnerrId = items1.get(0).getUserId();
		System.out.println("[타인출고담기] 아이템id (" + partsId + "의 소유자Id : " + partsOwnerrId);

		// 현재 장바구니에 있는 아이템의 프로젝트 OwnerID
		Map<String, String> paramMap2 = new HashMap<String, String>();
		paramMap2.put("UserId", userId);
		List<UserItem> items2 = itemDao.getOneUserItemsByOthersCart(paramMap2);

		System.out.println("[타인출고담기] 현재 담겨있는 아이템갯수 : " + items2.size());

		if (items2.size() != 0) {
			String othersCartOwnerId = items2.get(0).getUserId();
			if (!partsOwnerrId.equals(othersCartOwnerId)) {
				// 비교해서 다르면 에러팝업띄움

				// 담을려는 아이템의 OwnerID..
				// 현재 프로젝트의 OwnerID..
				System.out.println("[타인출고담기][NOT ALLOWED] 담겨있는 아이템의 소유자 ID :" + othersCartOwnerId);

				return false;

			} else {
				;// 비교해서 같으면 담을 수 있음
			}
		}
		// 장바구니에 아무것도 없으면그냥담음..
		return true;
	}

	public List<UserItem> getUserItemInOthersCart(String userId) {
		// 현재 장바구니에 있는 아이템의 프로젝트 OwnerID
		Map<String, String> paramMap2 = new HashMap<String, String>();
		paramMap2.put("UserId", userId);
		return itemDao.getOneUserItemsByOthersCart(paramMap2);

	}

	public List<ShipReqItem> getMyConfirmShipReqItems(String userId) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("UserId", userId);
		return itemDao.queryMyConfirmShipReqItem(paramMap);
	}

	public int stateMove2to3(int shipId, int shipStateId, String shipRejectCause, String shipDeliveredDateMethod) {
		Map<String, String> paramMap2 = new HashMap<String, String>();
		paramMap2.put("shipId", String.valueOf(shipId));
		paramMap2.put("newShipStateId", String.valueOf(shipStateId));
		paramMap2.put("shipRejectCause", shipRejectCause);
		paramMap2.put("shipDeliveredDateMethod", shipDeliveredDateMethod);

		// notify mail
		mailService.notiMail2to3(shipId);// DB 지우기전 출고자에게 노티

		return itemDao.updateShipReqState_ShipId(paramMap2);

	}

	/*
	 * project list에서 입력된 project code가 있는지 확인
	 */
	public boolean isExistProjectCode(List<ProjectItem> lstMyPrj, String myPartProjectCode) {
		boolean re = false;
		for (ProjectItem p : lstMyPrj) {
			if (p.getProjectCode().equals(myPartProjectCode)) {
				// System.out.println("finded prj code : " + myPartProjectCode);
				re = true;

			}

		}
		return re;
	}

	/*
	 * 내 자재검색..
	 */
	public boolean isExistMyPartsName(String userId, String partProjectCode, String partName, int userLevel) {
		// TODO Auto-generated method stub
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("UserId", userId);
		paramMap.put("PartProjectCode", partProjectCode);
		paramMap.put("PartName", partName);

		List<PartsItem> list;
		if (userLevel == EUserLevel.Lv2_DEV.getLevelInt()) {
			// lv2. dev
			list = itemDao.queryPart_PartsProjectAndParts(paramMap);
		} else {// lv3. lv6 shipper
			list = itemDao.queryPart_PartsProjectAndParts4Shipper(paramMap);
		}

		if (list.size() == 0) {
			System.out.println("[error] NOT exist item:" + partName);
			return false;
		}
		return true;

	}

	public int getMyPartId(String userId, String partProjectCode, String partName, int userLevel) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("UserId", userId);
		paramMap.put("PartProjectCode", partProjectCode);
		paramMap.put("PartName", partName);

		List<PartsItem> list;
		if (userLevel == EUserLevel.Lv2_DEV.getLevelInt()) {
			list = itemDao.queryPart_PartsProjectAndParts(paramMap);
		} else {// lv3. lv6 shipper
			list = itemDao.queryPart_PartsProjectAndParts4Shipper(paramMap);
		}

		return list.get(0).getPartId();
	}

	private int getShipperPartIdByPartsName(String userId, String partProjectCode, String partName) {
		// TODO Auto-generated method stub
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("UserId", userId);
		paramMap.put("PartProjectCode", partProjectCode);
		paramMap.put("PartName", partName);

		List<PartsItem> list = itemDao.queryPart_PartsProjectAndPartsByShipperId(paramMap);

		if (list.size() == 0) {
			System.out.println("[error] NOT exist item:" + partName);

			return 0;
		}
		return list.get(0).getPartId();
	}

	private boolean isExistShipperPartsName(String userId, String partProjectCode, String partName) {
		// TODO Auto-generated method stub
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("UserId", userId);
		paramMap.put("PartProjectCode", partProjectCode);
		paramMap.put("PartName", partName);

		List<PartsItem> list = itemDao.queryPart_PartsProjectAndPartsByShipperId(paramMap);

		if (list.size() == 0) {
			System.out.println("[error] NOT exist item:" + partName);
			return false;
		}
		return true;
	}

	/*
	 * /myparts 에서 신규로 Excel import 경우
	 */
	public boolean addMyNewPartsXls(UserItem loginUser, List<PartsItem> lst, List<String> errorlog) {
		boolean dbProcessSuccess = true;
		int i;
		List<ProjectItem> lstMyPrj;
		if (loginUser.getUserLevel() == EUserLevel.Lv2_DEV.getLevelInt()) {
			// lv2. dev
			lstMyPrj = getMyProjectItems(loginUser.getUserId());
		} else {// lv3. lv6 shipper
			lstMyPrj = getMyProjectItems4shipper(loginUser.getUserId());
		}
		if (lstMyPrj == null)
			return false;

		for (i = 0; i < lst.size(); i++) {
			// valid project 인지 체크
			if (isExistProjectCode(lstMyPrj, lst.get(i).getPartProjectCode())) {

				// 기존에 있는 PartsP/N이 인지확인
				if (isExistMyPartsName(loginUser.getUserId(), lst.get(i).getPartProjectCode(), lst.get(i).getPartName(),
						loginUser.getUserLevel())) {
					String err = "Error: 기존에 있는 LGIT P/N 입니다. " + (i + 1) + "번째 : " + lst.get(i).getPartName();
					System.out.println(err);
					errorlog.add(err);
					dbProcessSuccess = false;
				} else {
					// ok
					// keep going
				}
			} else {
				String err = "Error: 나의 ProjectCode가 아닙니다 " + (i + 1) + "번째 : " + lst.get(i).getPartProjectCode();
				System.out.println(err);
				errorlog.add(err);
				dbProcessSuccess = false;

			}
		}

		// 값 DB에 추가
		if (dbProcessSuccess) {
			for (i = 0; i < lst.size(); i++) {
				setPartsItem(lst.get(i));
			}
		} else {
			String err = "DB에 입력되지 않았습니다.";
			System.out.println(err);
			errorlog.add(err);
			return false;
		}

		return true;

	}

	/*
	 * Excel upload(신규)
	 */
	public boolean addMyCartXls(UserItem loginUser, ArrayList<String[]> lst, List<String> errorlog) {
		boolean dbProcessSuccess = true;
		int i;

		List<ProjectItem> lstMyPrj;
		if (loginUser.getUserLevel() == EUserLevel.Lv2_DEV.getLevelInt()) {
			// lv2. dev
			lstMyPrj = getMyProjectItems(loginUser.getUserId());
		} else {// lv3. lv6 shipper
			lstMyPrj = getMyProjectItems4shipper(loginUser.getUserId());
		}

		for (i = 0; i < lst.size(); i++) {
			// valid project 인지 체크
			if (isExistProjectCode(lstMyPrj, lst.get(i)[0])) {

				// 기존에 있는 PartsP/N이 인지확인
				if (isExistMyPartsName(loginUser.getUserId(), lst.get(i)[0], lst.get(i)[1], loginUser.getUserLevel())) {
					// OK. keep going

				} else {
					String err = "Error: 나에게 없는 LGIT P/N 입니다. " + (i + 1) + "번째 : " + lst.get(i)[1];
					System.out.println(err);
					errorlog.add(err);
					dbProcessSuccess = false;
				}
			} else {
				String err = "Error: 나의 ProjectCode가 아닙니다 " + (i + 1) + "번째 : " + lst.get(i)[0];
				System.out.println(err);
				errorlog.add(err);
				dbProcessSuccess = false;

			}
		}

		// 값 DB에 추가
		if (dbProcessSuccess) {
			for (i = 0; i < lst.size(); i++) {
				ShipReqPartsItem shippartsdata = new ShipReqPartsItem();

				// Get data from Web browser
				shippartsdata.setUserId(loginUser.getUserId());
				shippartsdata.setItemlistShipId(-1); // -1 은 아직 장바구니안에 아직대기

				int _partId = getMyPartsIDFromPartName(loginUser.getUserId(), lst.get(i)[1]);
				shippartsdata.setItemlistPartId(_partId);

				shippartsdata.setItemlistShipId(-1);
				shippartsdata.setItemlistAmount(Integer.valueOf(lst.get(i)[2]));

				// insert db
				addShipPartsItem(shippartsdata);

			}
		} else {
			String err = "DB에 입력되지 않았습니다.";
			System.out.println(err);
			errorlog.add(err);
			return false;
		}

		return true;
	}

	private int getMyPartsIDFromPartName(String userId, String partName) {

		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("UserId", userId);
		paramMap.put("PartName", partName);

		List<PartsItem> pdata = itemDao.queryMyPartsItemsByPartName(paramMap);
		if (pdata.size() == 0) {
			System.out.println("[오류] PN으로 PartId가져오기. 갯수가 0임");
			return -1;
		}

		return pdata.get(0).getPartId();
	}

	/*
	 * /shipothersparts(파트너출고요청리스트) 에서 Excel import 경우
	 */
	public boolean addOthersCartXls(UserItem loginUser, ArrayList<String[]> lst, List<String> errorlog) {

		boolean dbProcessSuccess = true;
		int i;

		List<ProjectItem> lstMyPrj;
		if (loginUser.getUserLevel() == EUserLevel.Lv2_DEV.getLevelInt()) {
			// lv2. dev
			lstMyPrj = getMyProjectItems(loginUser.getUserId());
		} else {// lv3. lv6 shipper
			lstMyPrj = getMyProjectItems4shipper(loginUser.getUserId());
		}
		if (lstMyPrj == null)
			return false;

		for (i = 0; i < lst.size(); i++) {
			// valid project 인지 체크

			String othersId = getUserIdByUserName(lst.get(i)[3]);
			if (othersId == null) {
				String err = "엑셀정보 " + (i + 1) + "번째) 사용자가 없습니다. 확인해 주세요. 사용자이름 : " + lst.get(i)[3];
				System.out.println(err);
				errorlog.add(err);
				dbProcessSuccess = false;
			} else if (isExistMyPartsName(othersId, lst.get(i)[0], lst.get(i)[1], EUserLevel.Lv2_DEV.getLevelInt())) {
				// 기존에 있는 PartsP/N이 인지확인
				// OK. keep going

			} else {
				String err = "엑셀정보 " + (i + 1) + "번째) 개발담당자: " + lst.get(i)[3] + "(id:" + othersId + ") / 프로젝트코드: "
						+ lst.get(i)[0] + " / LGIT P/N:" + lst.get(i)[1] + "는 존재하지 않습니다.";
				System.out.println(err);
				errorlog.add(err);
				dbProcessSuccess = false;
			}

		}

		// 값 DB에 추가
		if (dbProcessSuccess) {
			for (i = 0; i < lst.size(); i++) {
				ShipReqPartsItem shippartsdata = new ShipReqPartsItem();

				// Get data from Web browser
				String othersId = getUserIdByUserName(lst.get(i)[3]);

				shippartsdata.setUserId(loginUser.getUserId());
				shippartsdata.setItemlistShipId(-2); // -2 은 아직 타인 장바구니안에 대기

				int _partId = getMyPartsIDFromPartName(othersId, lst.get(i)[1]);
				shippartsdata.setItemlistPartId(_partId);

				shippartsdata.setItemlistShipId(-2);
				shippartsdata.setItemlistAmount(Integer.valueOf(lst.get(i)[2]));

				// insert db
				addShipPartsItem(shippartsdata);

			}
		} else {
			String err = "DB에 입력되지 않았습니다.";
			System.out.println(err);
			errorlog.add(err);
			return false;
		}

		return true;

	}

	public String getUserIdByUserName(String UserName) {
		// TODO Auto-generated method stub
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("UserName", UserName);

		List<UserItem> list = itemDao.queryUserItemsbyName(paramMap);
		if (list.size() == 0) {
			System.out.println("[ItemService] no user data");
			return null;
		}
		return list.get(0).getUserId();
	}

	/*
	 * DB관리 - 파츠관리
	 */
	public boolean modifyMyNewPartsXls(UserItem loginUser, List<PartsItem> lst, List<String> errorlog) {

		boolean dbProcessSuccess = true;
		int i;
		List<ProjectItem> lstMyPrj;
		if (loginUser.getUserLevel() == EUserLevel.Lv2_DEV.getLevelInt()) {
			// lv2. dev
			lstMyPrj = getMyProjectItems(loginUser.getUserId());
		} else {// lv3. lv6 shipper
			lstMyPrj = getMyProjectItems4shipper(loginUser.getUserId());
		}

		for (i = 0; i < lst.size(); i++) {
			// valid project 인지 체크
			if (isExistProjectCode(lstMyPrj, lst.get(i).getPartProjectCode())) {

				// 기존에 있는 PartsP/N이 인지확인
				if (isExistMyPartsName(loginUser.getUserId(), lst.get(i).getPartProjectCode(), lst.get(i).getPartName(),
						loginUser.getUserLevel())) {
					// ok

					// find my part id (엑셀에는 ID가 없음)
					int myPartsId = getMyPartId(loginUser.getUserId(), lst.get(i).getPartProjectCode(),
							lst.get(i).getPartName(), loginUser.getUserLevel());
					lst.get(i).setPartId(myPartsId);

					// keep going

				} else {
					String err = "Error: 기존에 없는 LGIT P/N 입니다. " + (i + 1) + "번째 : " + lst.get(i).getPartName();
					System.out.println(err);
					errorlog.add(err);
					dbProcessSuccess = false;
				}
			} else {
				String err = "Error: 나의 ProjectCode가 아닙니다 " + (i + 1) + "번째 : " + lst.get(i).getPartProjectCode();
				System.out.println(err);
				errorlog.add(err);
				dbProcessSuccess = false;

			}
		}

		// 값 DB에 추가
		if (dbProcessSuccess) {
			for (i = 0; i < lst.size(); i++) {
				System.out.println("[DB modify]" + lst.get(i).getPartId() + ":" + lst.get(i).getPartName() + ":"
						+ lst.get(i).getPartMsllevel());// for test
				changePartsItem(lst.get(i));
			}
		} else {
			String err = "DB에 입력되지 않았습니다.";
			System.out.println(err);
			errorlog.add(err);
			return false;
		}

		return true;

	}

	public List<JoinDBItem> getShipperItemsByShipperName(String shipperName) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("keyWord", shipperName);
		return itemDao.queryJoinItemsByShipperName(paramMap);

	}

	/*
	 * 출고담당자 - 재물조사 - Excel upload(수정하기)
	 * 
	 */
	public boolean modifyShipperPartsXls(UserItem loginUser, List<PartsItem> lst, List<String> errorlog) {

		boolean dbProcessSuccess = true;
		int i;
		List<ProjectItem> lstMyPrj = getShipperProjectItemsById(loginUser.getUserId());

		for (i = 0; i < lst.size(); i++) {
			// valid project 인지 체크
			if (isExistProjectCode(lstMyPrj, lst.get(i).getPartProjectCode())) {

				// 기존에 있는 PartsP/N이 인지확인

				if (isExistShipperPartsName(loginUser.getUserId(), lst.get(i).getPartProjectCode(),
						lst.get(i).getPartName())) {
					// ok
					// keep going
					int _partId = getShipperPartIdByPartsName(loginUser.getUserId(), lst.get(i).getPartProjectCode(),
							lst.get(i).getPartName());
					// System.out.println("finded parts Id:" + _partId);
					lst.get(i).setPartId(_partId);// 엑셀에서불렀으니 PartName은 있지만
													// PartId는 없음

				} else {
					String err = "Error: 기존에 없는 LGIT P/N 입니다. " + (i + 1) + "번째 : " + lst.get(i).getPartName();
					System.out.println(err);
					errorlog.add(err);
					dbProcessSuccess = false;
				}
			} else {
				String err = "Error: 나의 ProjectCode가 아닙니다 " + (i + 1) + "번째 : " + lst.get(i).getPartProjectCode();
				System.out.println(err);
				errorlog.add(err);
				dbProcessSuccess = false;

			}
		}
		System.out.println("loaded excel file is " + dbProcessSuccess);
		// 값 DB에 추가
		if (dbProcessSuccess)

		{
			for (i = 0; i < lst.size(); i++) {
				changePartsItem(lst.get(i));
			}
		} else {
			String err = "DB에 입력되지 않았습니다.";
			System.out.println(err);
			errorlog.add(err);
			return false;
		}

		return true;

	}

	private List<ProjectItem> getShipperProjectItemsById(String userId) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("UserId", userId);
		return itemDao.queryShipperProjectItemsById(paramMap);
	}

	public void deleteAllMyCartItems(String userId, int prevShipId) {
		ShipReqPartsItem item = new ShipReqPartsItem();
		item.setUserId(userId);
		item.setItemlistShipId(prevShipId);
		itemDao.deleteShipreqItemListByUserId(item);
		return;

	}

	public void addUserLog(String userId, String ip, String userAction) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		String logDate = dateFormat.format(cal.getTime());
		System.out.println("add log : " + logDate);

		LogUserItem logItem = new LogUserItem();
		logItem.setLogDate(logDate);
		logItem.setLogClientIP(ip);
		logItem.setLogUser(userId);
		logItem.setLogAction(userAction);

		itemDao.insertLogUserItem(logItem);
		return;
	}

	public List<LogUserItem> getLogUserItems() {
		return itemDao.queryUserLogItem();
	}

	public int getAmountFromItemlist(int itemlistId) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("itemlist_id", String.valueOf(itemlistId));

		String amount = itemDao.queryItemlistAmountbyItemlistId(paramMap);
		return Integer.valueOf(amount);

	}

	public int isValidItemForMyCart(String userId, int itemlistPartId, int reqAmount) {
		// 출고요청수량 0 입력
		if (reqAmount == 0) {
			return 1;
		}

		// 중복 생성 체크
		boolean exist = false;
		for (ShipReqPartsItem shipReqPartsItem : getShipPartsListItems(-1, userId)) {
			if (Integer.valueOf(itemlistPartId) == shipReqPartsItem.getItemlistPartId()) {
				exist = true;
			}

		}
		if (exist)
			return 2;

		// 수량 부족 체크
		int currAm = getPartStock(itemlistPartId);
		if (reqAmount > currAm) {
			return 3;
		}

		return 0;
	}

	public int isValidItemForOthersCart(String userId, int itemlistPartId, int reqAmount) {
		// 출고요청수량 0 입력
		if (reqAmount == 0) {
			return 1;
		}

		// 중복 생성 체크
		boolean exist = false;
		for (ShipReqPartsItem shipReqPartsItem : getShipPartsListItems(-2, userId)) {
			if (Integer.valueOf(itemlistPartId) == shipReqPartsItem.getItemlistPartId()) {
				exist = true;
			}

		}
		if (exist)
			return 2;

		// 수량 부족 체크
		int currAm = getPartStock(itemlistPartId);
		if (reqAmount > currAm) {
			return 3;
		}

		return 0;
	}

	public String getUserEmailById(String srchId) {
		String pw = "";
		List<UserItem> userItems = getUserItems();
		for (UserItem userItem : userItems) {
			if (userItem.getUserId().equals(srchId)) {
				pw = userItem.getUserEmail();
			}
		}
		return pw;
	}

	// Basic function - get item
	public SecureUserItem getSecureUserById(UserItem loginUser) {
		List<SecureUserItem> secuserItems;
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("userId", loginUser.getUserId());
		secuserItems = itemDao.querySecureuserItemById(paramMap);
		// secuserItems = itemDao.querySecureuserItem();

		System.out.println("sec len : " + secuserItems.size());
		// if none
		if (secuserItems.size() == 0) {
			// make new item with init value
			System.out.println("make new secure login info for " + loginUser.getUserId());
			LocalDateTime ldt = LocalDateTime.now(); // today
			String today = ldt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			SecureUserItem userinfo = new SecureUserItem(loginUser.getUserId(), today, today, 0, 0, 0);
			itemDao.insertSecureUserItem(userinfo);
			return userinfo;
		}
		return secuserItems.get(0);
	}

	// Basic function - update
	public void updateSecureUserById(SecureUserItem secureuser) {
		itemDao.updateSecureUserItem(secureuser);
	}

	public void removeSecureUser(SecureUserItem secureuser) {
		itemDao.deleteSecureUserItem(secureuser);
	}

	public void updateLoginedSecureInfoById(SecureUserItem secUserInfo) {

		LocalDateTime ldt = LocalDateTime.now();
		String today = ldt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

		secUserInfo.setLastLoginedDate(today);
		secUserInfo.setPwErrorCount(0);

		updateSecureUserById(secUserInfo);

	}

	public void refreshSecureDbEveryDay(LocalDate today) {
		System.out.println("refreshSecureDbEveryDay. once in a day");
		LocalDate threeMonthAgo = LocalDate.now().minusMonths(3);
		List<SecureUserItem> secuserItems;
		secuserItems = itemDao.querySecureuserItem();

		for (SecureUserItem item : secuserItems) {
			if (item.getIsLocked() == 1) {
				continue;
			}

			LocalDate lastlogined = LocalDate.parse(item.getLastLoginedDate());
			if (lastlogined.isBefore(threeMonthAgo)) {
				// set locked
				item.setIsLocked(1);
				System.out.println(item.getUserId() + " set to lock. update DB.");
				addUserLog("system", "system", item.getUserId() + " set Locked. 최근 접속일로부터 3개월 지남");
				updateSecureUserById(item);
			}

		}

	}

	public void loginProcessSuccessLog(UserItem loginUser) {
		// add user operation log
		HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
				.getRequest();
		String ip = req.getHeader("X-FORWARDED-FOR");
		if (ip == null) {
			ip = req.getRemoteAddr();
		}
		addUserLog(loginUser.getUserId(), ip, "로그인 성공");
	}

	public void excelExportLoging(UserItem loginUser, String requrl) {
		String sReqURL = "";
		if (requrl.equals("mylist")) {
			sReqURL = "나의자재";
		} else if (requrl.equals("otherslist")) {
			sReqURL = "파트너자재";
		} else if (requrl.equals("myinventorycontrol")) {
			sReqURL = "재물조사";
		} else {
			sReqURL = "(알수없음)";
		}

		// add user operation log
		HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
				.getRequest();
		String ip = req.getHeader("X-FORWARDED-FOR");
		if (ip == null) {
			ip = req.getRemoteAddr();
		}
		addUserLog(loginUser.getUserId(), ip, sReqURL + " 다운로드용 엑셀파일 생성, 다운로드 창 연결");
	}

	public int increasePWErrorCount(UserItem loginUser) {

		SecureUserItem secuser = getSecureUserById(loginUser);
		int pcnt = secuser.getPwErrorCount() + 1;
		int needblock = 0;

		// 5번이상 틀리면 블럭시킴..
		if (pcnt > 5) {
			needblock = 1;
		}
		secuser.setPwErrorCount(pcnt);
		secuser.setIsLocked(needblock);
		updateSecureUserById(secuser);

		// add user operation log
		HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
				.getRequest();
		String ip = req.getHeader("X-FORWARDED-FOR");
		if (ip == null) {
			ip = req.getRemoteAddr();
		}
		addUserLog(loginUser.getUserId(), ip, pcnt + "번째 패스워드 잘못입력.. set block=" + needblock);
		return pcnt;
	}

	public void resetSecureUserById(String userId) {
		SecureUserItem secuser = new SecureUserItem(userId, "", "", 0, 0, 0);
		removeSecureUser(secuser);
	}

}
