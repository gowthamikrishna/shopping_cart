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

	/**
	 * To remove items from inventory ,once payment is done
	 * 
	 * @param product
	 * @param quanity
	 */
	void removeProduct(Product product, int quanity);

	/**
	 * To load products and keep ready inventory. Load all products from all
	 * companies warehouse
	 */
	void setupInventory();
}
