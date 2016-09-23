/**
 * Item 클래스별로 긁어올 SQL을 지정
 *   
 */
package com.lgit.stockmgmt.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lgit.stockmgmt.domain.Item;
import com.lgit.stockmgmt.domain.PartsItem;
import com.lgit.stockmgmt.domain.ProjectItem;
import com.lgit.stockmgmt.domain.UserItem;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

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

}