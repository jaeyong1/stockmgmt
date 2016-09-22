package com.lgit.stockmgmt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgit.stockmgmt.dao.ItemDao;
import com.lgit.stockmgmt.domain.Item;
import com.lgit.stockmgmt.domain.PartsItem;
import com.lgit.stockmgmt.domain.ProjectItem;
import com.lgit.stockmgmt.domain.UserItem;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

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

	public List<UserItem> getUserItems() {
		System.out.println("[getUserItems] :"+ itemDao.getTime());
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

}
