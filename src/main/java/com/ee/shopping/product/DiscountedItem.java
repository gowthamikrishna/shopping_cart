package com.ee.shopping.product;

import java.util.UUID;

import com.ee.shopping.company.supplier.Company;

public class DiscountedItem extends PricedItem implements DiscountPricedProduct {
	private DiscountType discountType;
	private double amount;

	public DiscountedItem(UUID code, String name, ProductType productType, Company company, double price,
			CurrencyType currency, DiscountType discountType, double amount) {
		super(code, name, productType, company, price, currency);
		this.discountType = discountType;
		this.amount = amount;
	}

	public DiscountedItem(DiscountPricedProduct ref) {
		super(UUID.randomUUID(), ref.getName(), ref.getProductType(), ref.getCompany(), ref.getPrice(),
				ref.getCurrency());
		this.amount = ref.getAmount();
		this.discountType = ref.getDiscountType();
	}

	@Override
	public DiscountType getDiscountType() {
		return discountType;
	}

	@Override
	public void setDiscountType(DiscountType discountType) {
		this.discountType = discountType;
	}

	@Override
	public double getAmount() {
		return amount;
	}

	@Override
	public void setAmount(double amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "discountType=" + discountType + ", amount=" + amount + ", price=" + getPrice() + ", currency="
				+ getCurrency() + ", productCode=" + getProductCode() + ", name=" + getName() + ", productType="
				+ getProductType() + ", company=" + getCompany();
	}

}
