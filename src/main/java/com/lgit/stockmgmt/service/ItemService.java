package com.lgit.stockmgmt.service;

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

}
