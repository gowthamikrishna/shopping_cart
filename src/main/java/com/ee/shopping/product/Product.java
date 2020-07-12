package com.ee.shopping.product;

import java.util.UUID;

import com.ee.shopping.company.supplier.Company;

/**
 * Model to capture basic product details and identify it uniquely
 * 
 * @author kriGow
 *
 */
public interface Product {
	void setProductCode(UUID code);

	UUID getProductCode();

	void setName(String name);

	String getName();

	void setCompany(Company company);

	Company getCompany();

	ProductType getProductType();

	void setProductType(ProductType productType);

}
