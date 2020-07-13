package com.ee.shopping.services.inventory;

import com.ee.shopping.company.supplier.CompanyProductTypeMapping;
import com.ee.shopping.company.supplier.CompanyProductWarehouse;
import com.ee.shopping.company.supplier.ProductTypeToProductMapping;
import com.ee.shopping.product.offer.OfferService;
import com.ee.shopping.tax.TaxService;

public enum ShoppingService {
	instance;
	private CompanyProductTypeMapping companyProductTypeMapping;
	private TaxService taxService;
	private OfferService offerService;
	private InventoryService inventoryService;
	private CompanyProductWarehouse companyProductWarehouse;
	private ProductTypeToProductMapping productTypeToProductMapping;

	public static ProductTypeToProductMapping getProductTypeToProductMapping() {
		return ShoppingService.instance.productTypeToProductMapping;
	}

	public void setProductTypeToProductMapping(ProductTypeToProductMapping productTypeToProductMapping) {
		this.productTypeToProductMapping = productTypeToProductMapping;
	}

	public static CompanyProductTypeMapping getCompanyProductTypeMapping() {
		return ShoppingService.instance.companyProductTypeMapping;
	}

	public void setCompanyProductTypeMapping(CompanyProductTypeMapping companyProductTypeMapping) {
		this.companyProductTypeMapping = companyProductTypeMapping;
	}

	public static TaxService getTaxService() {
		return ShoppingService.instance.taxService;
	}

	public void setTaxService(TaxService taxService) {
		this.taxService = taxService;
	}

	public static OfferService getOfferService() {
		return ShoppingService.instance.offerService;
	}

	public void setOfferService(OfferService offerService) {
		this.offerService = offerService;
	}

	public static InventoryService getInventoryService() {
		return ShoppingService.instance.inventoryService;
	}

	public void setInventoryService(InventoryService inventoryService) {
		this.inventoryService = inventoryService;
	}

	public static CompanyProductWarehouse getCompanyProductWarehouse() {
		return ShoppingService.instance.companyProductWarehouse;
	}

	public void setCompanyProductWarehouse(CompanyProductWarehouse companyProductWarehouse) {
		this.companyProductWarehouse = companyProductWarehouse;
	}

}
