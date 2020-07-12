package com.ee.shopping.product;

public enum CurrencyType {
	INR("Rupee"), DOLLAR("Dollar"), EURO("Euro");

	String type;

	private CurrencyType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public static CurrencyType of(String currencyStr) {
		if (currencyStr != null) {
			for (CurrencyType currency : CurrencyType.values()) {
				if (currency.type.equalsIgnoreCase(currencyStr.trim())) {
					return currency;
				}
			}

		}
		return null;
	}
}
