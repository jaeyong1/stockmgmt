package com.lgit.stockmgmt.service;

import java.awt.event.ItemListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgit.stockmgmt.dao.ItemDao;
import com.lgit.stockmgmt.domain.Item;
import com.lgit.stockmgmt.domain.JoinDBItem;
import com.lgit.stockmgmt.domain.PartsItem;
import com.lgit.stockmgmt.domain.ProjectItem;
import com.lgit.stockmgmt.domain.ShipReqItem;
import com.lgit.stockmgmt.domain.ShipReqPartsItem;
import com.lgit.stockmgmt.domain.UserItem;

@Service("itemService")
public class ItemService {

	@Autowired
	private ItemDao itemDao;

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

		List<UserItem> list = itemDao.queryUserItems(paramMap);
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
		return itemDao.queryJoinItemsByOwnerName(paramMap);
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
		paramMap2.put("OldShipId", String.valueOf(-1));
		paramMap2.put("NewShipId", String.valueOf(partsShipreqId));
		itemDao.updateShipParts_ShipId(paramMap2);

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
		// tb_part에서 myID이면서 -1인것 id로 업데이트
		Map<String, String> paramMap2 = new HashMap<String, String>();
		paramMap2.put("UserId", UserId);
		paramMap2.put("OldShipId", String.valueOf(-1));
		paramMap2.put("NewShipId", String.valueOf(partsShipreqId));
		itemDao.updateShipParts_ShipId(paramMap2);

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

	public int stateMove3to4(int shipId, int shipStateId) {
		Map<String, String> paramMap2 = new HashMap<String, String>();
		paramMap2.put("shipId", String.valueOf(shipId));
		paramMap2.put("newShipStateId", String.valueOf(shipStateId));
		return itemDao.updateShipReqState_ShipId(paramMap2);

	}

	public int stateMove4to6(int shipId, int shipStateId) {
		Map<String, String> paramMap2 = new HashMap<String, String>();
		paramMap2.put("shipId", String.valueOf(shipId));
		paramMap2.put("newShipStateId", String.valueOf(shipStateId));
		return itemDao.updateShipReqState_ShipId(paramMap2);

	}

	public int stateMove4to5(int shipId, int shipStateId) {
		// Get itemlist_ship_id
		/**
		 * itemlist_part_id=part_id | itemlist_amount
		 */
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("itemlistShipId", shipId + "");
		List<ShipReqPartsItem> partslist = itemDao.queryShipPartsitemsByShipid(paramMap);
		System.out.println(partslist.size() + "건의 재고감소 진행");

		/**
		 * ex) update board set 칼럼명 = 칼럼명 + 2 where num = '7' 은 스스로 값을계산하여 업데이트
		 * 수행
		 */
		// reduce query
		int i;
		Map<String, String> paramMap3 = new HashMap<String, String>();
		for (i = 0; i < partslist.size(); i++) {
			paramMap3.clear();
			paramMap3.put("part_id", String.valueOf(partslist.get(i).getItemlistPartId()));
			paramMap3.put("minus_amount", String.valueOf(partslist.get(i).getItemlistAmount()));
			System.out.println("query minus ItemlistPartId:" + partslist.get(i).getItemlistPartId() + ", minusvolume:"
					+ partslist.get(i).getItemlistAmount());
			itemDao.updateMinusStockToTBPart(paramMap3);
		}

		// STATE UPDATE
		Map<String, String> paramMap2 = new HashMap<String, String>();
		paramMap2.put("shipId", String.valueOf(shipId));
		paramMap2.put("newShipStateId", String.valueOf(shipStateId));
		return itemDao.updateShipReqState_ShipId(paramMap2);
	}

	public List<ProjectItem> getMyProjectItems(String userId) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("UserId", userId);
		return itemDao.queryMyProjectItems(paramMap);
	}

	public List<PartsItem> getMyItemsByID(String userId) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("UserId", userId);
		return itemDao.queryMyPartsItemsById(paramMap);
	}

	public List<UserItem> getShipperUserItems() {
		// 출고담당자 리스트
		return itemDao.queryShipperUserItems();
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

}
