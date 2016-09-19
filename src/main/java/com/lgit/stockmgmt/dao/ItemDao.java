package com.lgit.stockmgmt.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lgit.stockmgmt.domain.Item;

@Repository("itemDao")
public class ItemDao {
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	public List<Item> queryItems() {
		return sqlSession.selectList("selectItemList");
		
	}
}