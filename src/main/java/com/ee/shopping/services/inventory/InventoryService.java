package com.ee.shopping.services.inventory;

import java.util.List;

import com.ee.shopping.product.Product;
import com.ee.shopping.product.ProductType;

public interface InventoryService {

	/**
	 * Display all kinds of products to user
	 * 
	 * @return
	 */
	List<Product> getAllInventory();

	/**
	 * Display only user searched products
	 * 
	 * @return
	 */
	List<Product> getInventoryByProductType(ProductType productType);

	void removeProduct(Product product, int quanity);
}
