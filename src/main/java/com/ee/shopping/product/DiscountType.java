package com.ee.shopping.product;

public enum DiscountType {
	PERCENTAGE("percentage"), ABSOLUTE("absolute");
	String type;

	private DiscountType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public static DiscountType of(String discountTypeStr) {
		if (discountTypeStr != null) {
			for (DiscountType discountType : DiscountType.values()) {
				if (discountType.type.equalsIgnoreCase(discountTypeStr.trim())) {
					return discountType;
				}
			}

		}
		return null;
	}

}
