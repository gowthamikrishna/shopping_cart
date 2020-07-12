package com.ee.shopping.product;

/**
 * Model to capture priced product with discount applicable
 * 
 * @author kriGow
 *
 */
public interface DiscountPricedProduct extends PricedProduct {

	DiscountType getDiscountType();

	void setDiscountType(DiscountType discountType);

	double getAmount();

	void setAmount(double amount);

}
