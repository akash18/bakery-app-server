package com.yummy.bakery.service;

import java.util.List;

import com.yummy.bakery.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yummy.bakery.dao.ItemsDAO;

@Service
public class ItemsService {
	
	@Autowired
	ItemsDAO itemsDAO;

	@Transactional
	public List<Product> getItems() {
		return itemsDAO.getItems();
	}
	

}
