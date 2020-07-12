package com.ee.shopping.product;

/**
 * Model to capture a priced product before discount
 * 
 * @author kriGow
 *
 */
public interface PricedProduct extends Product {
	void setPrice(double price);

	double getPrice();

	CurrencyType getCurrency();

	void setCurrency(CurrencyType currency);
}
