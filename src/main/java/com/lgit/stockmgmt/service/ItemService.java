package com.lgit.stockmgmt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgit.stockmgmt.dao.ItemDao;
import com.lgit.stockmgmt.domain.Item;

@Service("itemService")
public class ItemService {
	
	@Autowired
	private ItemDao itemDao;
	
	public List<Item> getItems() {
		
		return itemDao.queryItems();
	}
}
