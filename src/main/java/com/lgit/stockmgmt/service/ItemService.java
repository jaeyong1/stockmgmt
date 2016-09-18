package com.lgit.stockmgmt.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import com.lgit.stockmgmt.dao.ItemDao;
import com.lgit.stockmgmt.domain.Item;

@Service("itemService")
public class ItemService {

	// @Autowired
	// private ItemDao itemDao;

	public List<Item> getItems() {

		List<Item> lst = new ArrayList<Item>();
		lst.add(new Item("p001", "Alpha project"));
		lst.add(new Item("p002", "Bravo project"));
		return lst;

		// return itemDao.queryItems();
	}
}
 