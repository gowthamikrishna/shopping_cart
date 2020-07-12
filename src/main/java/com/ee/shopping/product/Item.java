package com.ee.shopping.product;

import java.util.UUID;

import com.ee.shopping.company.supplier.Company;

public abstract class Item implements Product {

	private UUID productCode;
	private String name;
	private ProductType productType;
	private Company company;

	public Item(UUID productCode, String name, ProductType productType, Company company) {
		super();
		this.productCode = productCode;
		this.name = name;
		this.productType = productType;
		this.company = company;
	}

	@Override
	public UUID getProductCode() {
		return productCode;
	}
	@Override
	public void setProductCode(UUID productCode) {
		this.productCode = productCode;
	}
	@Override
	public String getName() {
		return name;
	}
	@Override
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public ProductType getProductType() {
		return productType;
	}
	@Override
	public void setProductType(ProductType productType) {
		this.productType = productType;
	}
	@Override
	public Company getCompany() {
		return company;
	}
	@Override
	public void setCompany(Company company) {
		this.company = company;
	}

	
	
}
