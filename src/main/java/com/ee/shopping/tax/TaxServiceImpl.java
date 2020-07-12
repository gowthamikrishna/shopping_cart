package com.ee.shopping.tax;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.ee.shopping.product.PricedProduct;
import com.ee.shopping.product.Product;
import com.ee.shopping.product.ProductType;

public enum TaxServiceImpl implements TaxService {
	instance;
	Map<ProductType, Double> taxMapping = new ConcurrentHashMap<ProductType, Double>();

	@Override
	public void setTaxForProductType(ProductType productType, double taxRate) {
		taxMapping.put(productType, taxRate);
	}

	@Override
	public double getTaxForProductType(ProductType productType) {
		return taxMapping.get(productType);
	}

	@Override
	public double calculateTaxForProduct(Product product, int quanity) {
		double taxRateToApply = (taxMapping.get(product.getProductType()) / 100);
		return ((PricedProduct) product).getPrice() * quanity * taxRateToApply;
	}

}
