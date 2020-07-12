package com.ee.shopping.services.inventory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.ee.shopping.company.supplier.Company;
import com.ee.shopping.company.supplier.CompanyProductTypeMapping;
import com.ee.shopping.company.supplier.CompanyProductWarehouse;
import com.ee.shopping.product.Product;
import com.ee.shopping.product.ProductType;

public enum InventoryServiceImpl implements InventoryService {

	instance;
	List<Product> inventory;

	private InventoryServiceImpl() {
		this.inventory = new ArrayList<Product>();
		init();
	}

	/**
	 * Load all products from all companies warehouse
	 */
	private void init() {
		Map<Company, List<ProductType>> supplierAndProducts = CompanyProductTypeMapping.instance
				.getAvailableSupplierAndTheirProducts();
		for (Company company : supplierAndProducts.keySet()) {
			if (supplierAndProducts.get(company) != null) {
				for (ProductType productType : supplierAndProducts.get(company)) {
					List<Product> companiesProductsByType = CompanyProductWarehouse.instance.supply(company,
							productType, 0);
					if (companiesProductsByType != null) {
						this.inventory.addAll(companiesProductsByType);
					}
				}
			}
		}
	}

	@Override
	public List<Product> getAllInventory() {
		return this.inventory;
	}

	@Override
	public List<Product> getInventoryByProductType(final ProductType productType) {
		return this.inventory.stream().filter(product -> product.getProductType() == productType)
				.collect(Collectors.toList());
	}

	@Override
	public void removeProduct(Product p, int quanity) {
		List<Product> availableProductsInInventory = this.inventory.stream()
				.filter(product -> product.getProductType() == p.getProductType()).collect(Collectors.toList());
		if (availableProductsInInventory != null && quanity < availableProductsInInventory.size()) {
			// Remove products from inventory
			this.inventory.removeAll(availableProductsInInventory.stream().limit(quanity).collect(Collectors.toList()));
		} else {
			throw new OutOfStockException("Out Of Stock on the product ,please try again");
		}
	}

}
