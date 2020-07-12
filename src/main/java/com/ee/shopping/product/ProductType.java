package com.ee.shopping.product;

public enum ProductType {
	SOAP("soap"), DEO("deo");
	String product;

	private ProductType(String product) {
		this.product = product;
	}

	public String getProduct() {
		return product;
	}

	public static ProductType of(String productType) {
		if (productType != null) {
			for (ProductType type : ProductType.values()) {
				if (type.product.equalsIgnoreCase(productType.trim())) {
					return type;
				}
			}

		}
		return null;
	}

}
