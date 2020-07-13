package com.ee.shopping.company.supplier;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.ee.shopping.product.Product;
import com.ee.shopping.product.ProductType;

public class ProductTypeToProductMapping {
	// priceMapping by product type in a company
	Map<Company, Map<ProductType, Product>> productsByType;

	private Map<Company, Map<ProductType, Product>> getMap() {
		if (productsByType == null) {
			productsByType = new ConcurrentHashMap<Company, Map<ProductType, Product>>();
		}
		return productsByType;
	}

	private Map<ProductType, Product> getCompanyBucket(Company company) {
		Map<ProductType, Product> companyBucket = getMap().get(company);
		if (companyBucket == null) {
			companyBucket = new ConcurrentHashMap<ProductType, Product>();
			getMap().put(company, companyBucket);
		}

		return companyBucket;
	}

	public void addProductToProductType(Company company, ProductType productType, Product product) {
		getCompanyBucket(company).put(productType, product);
	}

	public void removeProductFromProductType(Company company, ProductType productType) {
		getCompanyBucket(company).remove(productType);
	}

	public Product getProductForPriceReference(Company company, ProductType productType) {
		return getCompanyBucket(company).get(productType);
	}

}
