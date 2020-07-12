package com.ee.shopping.tax;

import com.ee.shopping.product.Product;
import com.ee.shopping.product.ProductType;

public interface TaxService {
	void setTaxForProductType(ProductType productType, double taxRate);

	double getTaxForProductType(ProductType productType);

	double calculateTaxForProduct(Product product, int quantity);
}
