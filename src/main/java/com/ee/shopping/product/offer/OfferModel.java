package com.ee.shopping.product.offer;

import com.ee.shopping.product.ProductType;

public class OfferModel {

	private boolean isApplicable;
	private ProductType productType;
	private int forCount;
	private int offerCount;
	private int offerDiscount;

	public int getOfferDiscount() {
		return offerDiscount;
	}

	public void setOfferDiscount(int offerDiscount) {
		this.offerDiscount = offerDiscount;
	}

	public OfferModel(boolean isApplicable, ProductType productType, int forCount, int offerCount, int offerDiscount) {
		super();
		this.isApplicable = isApplicable;
		this.productType = productType;
		this.forCount = forCount;
		this.offerCount = offerCount;
		this.offerDiscount = offerDiscount;
	}

	public boolean isApplicable() {
		return isApplicable;
	}

	public void setApplicable(boolean isApplicable) {
		this.isApplicable = isApplicable;
	}

	public ProductType getProductType() {
		return productType;
	}

	public void setProductType(ProductType productType) {
		this.productType = productType;
	}

	public int getForCount() {
		return forCount;
	}

	public void setForCount(int forCount) {
		this.forCount = forCount;
	}

	public int getOfferCount() {
		return offerCount;
	}

	public void setOfferCount(int offerCount) {
		this.offerCount = offerCount;
	}

}
