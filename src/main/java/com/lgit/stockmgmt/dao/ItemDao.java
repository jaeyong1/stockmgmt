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

@Repository("itemDao")
public class ItemDao {
	@Autowired
	private SqlSessionTemplate sqlSession;

	 /* Test Code */
	public List<Item> queryItems() {
		return sqlSession.selectList("selectItemList");

	}

	public List<PartsItem> queryPartsItems() {
		return sqlSession.selectList("selectPartsItemList"); /* SQL 쿼리 선택, 실행 */
	}

	public List<ProjectItem> queryProjectItems() {
		return sqlSession.selectList("selectProjectItemList"); /* SQL 쿼리 선택, 실행 */
	}

	public List<UserItem> queryUserItems() {
		return sqlSession.selectList("selectUserItemList"); /* SQL 쿼리 선택, 실행 */
	}

}