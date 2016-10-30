/**
 * Item 클래스별로 긁어올 SQL을 지정
 *   
 */
package com.lgit.stockmgmt.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lgit.stockmgmt.domain.Item;
import com.lgit.stockmgmt.domain.JoinDBItem;
import com.lgit.stockmgmt.domain.PartsItem;
import com.lgit.stockmgmt.domain.ProjectItem;
import com.lgit.stockmgmt.domain.ShipReqItem;
import com.lgit.stockmgmt.domain.ShipReqPartsItem;
import com.lgit.stockmgmt.domain.UserItem;

@Repository("itemDao")
public class ItemDao {
	@Autowired
	private SqlSessionTemplate sqlSession;

	/* Test Code */
	public List<Item> queryItems() {
		return sqlSession.selectList("selectItemList");
	}

	public String getTime() {
		return sqlSession.selectOne("getDBServerTime");
	}

	public List<PartsItem> queryPartsItems() {
		return sqlSession.selectList("selectPartsItemList"); /* SQL 쿼리 선택, 실행 */
	}

	public List<ProjectItem> queryProjectItems() {
		return sqlSession
				.selectList("selectProjectItemList"); /* SQL 쿼리 선택, 실행 */
	}

	public List<ProjectItem> queryProjectItems(int rowsPerPage, int page) {
		return sqlSession
				.selectList("selectProjectItemList"); /* SQL 쿼리 선택, 실행 */
	}

	public List<UserItem> queryUserItems() {
		return sqlSession.selectList("selectUserItemList"); /* SQL 쿼리 선택, 실행 */
	}

	public int insertUserItem(UserItem userdata) {
		return sqlSession.insert("insertUserItem", userdata);
	}

	public int updateUserItem(UserItem userdata) {
		return sqlSession.update("updateUserItem", userdata);
	}

	public int updateUserItem4PwChange(UserItem userdata) {
		return sqlSession.update("updateUserItem4PwChange", userdata);
	}

	public String queryUserItemsRow() {
		return sqlSession.selectOne("queryUserItemsRow");
	}

	public String queryProjectItemsRow() {
		return sqlSession.selectOne("queryProjectItemsRow");
	}

	public int insertProjectItem(ProjectItem item) {
		return sqlSession.insert("insertProjectItem", item);
	}

	public int updateUserItem(ProjectItem item) {
		return sqlSession.update("updateProjectItem", item);
	}

	public int deleteProjectItem(ProjectItem item) {
		return sqlSession.delete("deleteProjectItem", item);
	}

	public String queryPartsItemsRow() {
		return sqlSession.selectOne("queryPartsItemsRow");
	}

	public int insertPartsItem(PartsItem item) {
		return sqlSession.insert("insertPartsItem", item);
	}

	public int updatePartsItem(PartsItem item) {
		return sqlSession.update("updatePartsItem", item);
	}

	public int deletePartsItem(PartsItem item) {
		return sqlSession.delete("deletePartsItem", item);
	}

	public List<UserItem> queryUserItems(Map<String, String> paramMap) {
		return sqlSession.selectList("selectUserItemListForLogin", paramMap);

	}

	/*
	 * Joined Table query
	 */
	public List<JoinDBItem> queryJoinItemsByOwnerName(Map<String, String> paramMap) {
		return sqlSession.selectList("queryJoinedPartsItemByOwnerName", paramMap);
	}

	public List<ShipReqPartsItem> queryShipPartsListItems(Map<String, String> paramMap) {
		return sqlSession.selectList("queryShipPartsListItems", paramMap);
	}

	public int updateShipPartsItem(ShipReqPartsItem shippartsdata) {
		return sqlSession.update("updateShipReqPartsItem", shippartsdata);
	}

	public int deleteShipPartsItem(ShipReqPartsItem shippartsdata) {
		return sqlSession.delete("deleteShipPartsItem", shippartsdata);
	}

	public int insertShipPartsItem(ShipReqPartsItem shippartsdata) {
		return sqlSession.insert("insertShipReqPartsItem", shippartsdata);
	}

	public List<UserItem> queryUserItemsbyID(Map<String, String> paramMap) {
		return sqlSession.selectList("selectUserItemListByID", paramMap);
	}

	public int insertShipReqItem(ShipReqItem shipreqdata) {
		return sqlSession.insert("insertShipReqItem", shipreqdata);

	}

	public List<ShipReqItem> queryShipReqItem(Map<String, String> paramMap) {
		return sqlSession.selectList("queryShipReqListItems", paramMap);
	}

	public int updateShipParts_ShipId(Map<String, String> paramMap2) {
		return sqlSession.update("updateShipParts_ShipId", paramMap2);
	}

	public List<ShipReqItem> queryShipReqItemByShipId(Map<String, String> paramMap) {
		return sqlSession.selectList("queryShipReqListItemsByShipId", paramMap);
	}

	public List<ShipReqItem> queryShipReqItemForShipper(Map<String, String> paramMap) {
		return sqlSession.selectList("queryShipReqListItemsForShipper", paramMap);
	}

	public int updateShipReqState_ShipId(Map<String, String> paramMap2) {
		return sqlSession.update("updateShipReqState_ShipId", paramMap2);
	}

	public List<JoinDBItem> queryOthersJoinItemsByOwner(Map<String, String> paramMap) {
		return sqlSession.selectList("queryOthersJoinedPartsItemByOwnerName", paramMap);
	}

	public List<ProjectItem> queryMyProjectItems(Map<String, String> paramMap) {
		return sqlSession.selectList("queryProjectItemsByID", paramMap);
	}

	public List<PartsItem> queryMyPartsItemsById(Map<String, String> paramMap) {
		return sqlSession.selectList("selectPartsItemListByID", paramMap);
	}

	public List<UserItem> queryShipperUserItems() {
		return sqlSession.selectList("selectShipperUserItem");

	}

	public List<ShipReqPartsItem> queryShipPartsitemsByShipid(Map<String, String> paramMap) {
		return sqlSession.selectList("queryShipReqListItemsByShipid", paramMap);
	}

	public int updateMinusStockToTBPart(Map<String, String> paramMap3) {
		return sqlSession.update("updateMinusStockToTBPart", paramMap3);

	}

	public List<UserItem> getUserItemsByPartsId(Map<String, String> paramMap1) {
		return sqlSession.selectList("queryUserItemByPartId", paramMap1);
	}

	public List<UserItem> getOneUserItemsByOthersCart(Map<String, String> paramMap2) {
		return sqlSession.selectList("queryUserItemByCartOwnerId_minus2", paramMap2);
	}

	public List<ShipReqItem> queryMyConfirmShipReqItem(Map<String, String> paramMap) {
		return sqlSession.selectList("queryMyConfirmShipReqListItems", paramMap);
	}

	public List<PartsItem> queryPart_PartsProjectAndParts(Map<String, String> paramMap) {
		return sqlSession.selectList("queryPartsProjectAndParts_TBPart", paramMap);
	}

	public List<PartsItem> queryMyPartsItemsByPartName(Map<String, String> paramMap) {
		return sqlSession.selectList("queryMyPartsIDByPartName_TBPart", paramMap);
	}

	public List<UserItem> queryUserItemsbyName(Map<String, String> paramMap) {
		return sqlSession.selectList("queryUserItemsbyName", paramMap);
	}

}