package com.ee.shopping.product;

import java.util.UUID;

import com.ee.shopping.company.supplier.Company;

public class PricedItem extends Item implements PricedProduct {
	private double price;
	private CurrencyType currency;

	public PricedItem(UUID code, String name, ProductType productType, Company company, double price,
			CurrencyType currency) {
		super(code, name, productType, company);
		this.price = price;
		this.currency = currency;
	}

	public PricedItem(PricedProduct ref) {
		super(UUID.randomUUID(), ref.getName(), ref.getProductType(), ref.getCompany());
		this.price = ref.getPrice();
		this.currency = ref.getCurrency();
	}

	@Override
	public double getPrice() {
		return price;
	}

	@Override
	public void setPrice(double price) {
		this.price = price;
	}

	@Override
	public CurrencyType getCurrency() {
		return currency;
	}

	@Override
	public void setCurrency(CurrencyType currency) {
		this.currency = currency;
	}

	@Override
	public String toString() {
		return "price=" + price + ", currency=" + currency + ", productCode()=" + getProductCode() + ", name()="
				+ getName() + ", productType()=" + getProductType() + ", company()=" + getCompany();
	}

}
