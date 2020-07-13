package com.ee.shopping.company.supplier;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.ee.shopping.product.ProductType;

/**
 * Datastructure to model what are the products a company can supply
 * 
 * @author kriGow
 *
 */
public class CompanyProductTypeMapping {

	Map<Company, List<ProductType>> productMapping;

	private Map<Company, List<ProductType>> getMap() {
		if (productMapping == null) {
			productMapping = new ConcurrentHashMap<Company, List<ProductType>>();
		}
		return productMapping;
	}

	private List<ProductType> getCompanyBucket(Company company) {
		List<ProductType> companyBucket = getMap().get(company);
		if (companyBucket == null) {
			companyBucket = new ArrayList<ProductType>();
			getMap().put(company, companyBucket);
		}

		return companyBucket;
	}

	public void addProductTypeToCompany(Company company, ProductType productType) {
		getCompanyBucket(company).add(productType);
	}

	public void removeProductTypeToCompany(Company company, ProductType productType) {
		getCompanyBucket(company).remove(productType);
	}

	public boolean isCompanyMaufactureProduct(Company company, ProductType productType) {
		return getCompanyBucket(company).contains(productType);
	}

	public Map<Company, List<ProductType>> getAvailableSupplierAndTheirProducts() {
		return getMap();
	}
}
