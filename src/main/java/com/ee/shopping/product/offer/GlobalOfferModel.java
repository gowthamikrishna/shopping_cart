package com.ee.shopping.product.offer;

public class GlobalOfferModel {
	private double minPurchase;
	private double percentageDiscount;

	public GlobalOfferModel(double minPurchase, double percentageDiscount) {
		super();
		this.minPurchase = minPurchase;
		this.percentageDiscount = percentageDiscount;
	}

	public double getMinPurchase() {
		return minPurchase;
	}

	public void setMinPurchase(double minPurchase) {
		this.minPurchase = minPurchase;
	}

	public double getPercentageDiscount() {
		return percentageDiscount;
	}

	public void setPercentageDiscount(double percentageDiscount) {
		this.percentageDiscount = percentageDiscount;
	}

}
