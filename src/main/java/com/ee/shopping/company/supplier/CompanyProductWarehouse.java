package com.ee.shopping.company.supplier;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import com.ee.shopping.product.DiscountPricedProduct;
import com.ee.shopping.product.DiscountedItem;
import com.ee.shopping.product.PricedItem;
import com.ee.shopping.product.PricedProduct;
import com.ee.shopping.product.Product;
import com.ee.shopping.product.ProductType;

public enum CompanyProductWarehouse {
	instance;

	Map<Company, List<Product>> warehouse;

	private Map<Company, List<Product>> getMap() {
		if (warehouse == null) {
			warehouse = new ConcurrentHashMap<Company, List<Product>>();
		}
		return warehouse;
	}

	private List<Product> getCompanyBucket(Company company) {
		List<Product> companyBucket = getMap().get(company);
		if (companyBucket == null) {
			companyBucket=new ArrayList<Product>();
			getMap().put(company, companyBucket);
		}

		return companyBucket;
	}

	private CompanyProductWarehouse() {
	}

	private List<Product> listItemsByProductType(Company company, final ProductType productType) {
		return getCompanyBucket(company).stream().filter(product -> product.getProductType() == productType)
				.collect(Collectors.toList());
	}

	/**
	 * To load company's warehouse based on supplied configuration
	 * 
	 * @param company
	 */
	public void accumulate(Company company, final ProductType productType, int quantity) {
		List<Product> list = listItemsByProductType(company, productType);
		int newQuantity = quantity;
		if (list.size() > 0) {
			newQuantity = quantity + list.size();
		}
		List<Product> newList = supply(company, productType, newQuantity);
		getCompanyBucket(company).addAll(newList);
	}

	public void addProductToWarehouse(Company company, PricedProduct product) {
		if (CompanyProductTypeMapping.instance.isCompanyMaufactureProduct(company, product.getProductType())) {
			getCompanyBucket(company).add(product);
		} else {
			throw new UnSupportedProductException(String.format("Company %s doesn't support product type %s ",
					company.getCompanyName(), product.getProductType()));
		}
	}

	public void removeProductFromWarehouse(Company company, PricedProduct product) {
		if (CompanyProductTypeMapping.instance.isCompanyMaufactureProduct(company, product.getProductType())) {
			getCompanyBucket(company).add(product);
		} else {
			throw new NoProductFoundException(String.format("No Prodct found %s in  Company %s's warehouse",
					product.getName(), company.getCompanyName()));
		}
	}

	/**
	 * To supply the product by type for the given company. If warehouse of company
	 * has less than required quantity ,then products manufactured and added to
	 * supply.
	 * if quantity is 0, then all items of given product type will be returned
	 * @param company
	 * @param productType
	 * @param name
	 * @param quantity
	 * @return List of Products by type for the given company
	 */
	public List<Product> supply(Company company, final ProductType productType, int quantity) {
		List<Product> availableProductForType =listItemsByProductType(company, productType);

		if (availableProductForType.size() < quantity) {
			int extraNeededQuantity = quantity - availableProductForType.size();
			Product refProduct = ProductTypeToProductMapping.instance().getProductForPriceReference(company,
					productType);
			Product product = null;
			for (int i = 0; i < extraNeededQuantity; i++) {
				if (refProduct instanceof DiscountPricedProduct) {
					product = new DiscountedItem((DiscountPricedProduct) refProduct);
				} else {
					product = new PricedItem((PricedProduct) refProduct);
				}

				availableProductForType.add(product);
			}
		}

		// Remove existing items from warehouse once supplied
		getCompanyBucket(company).removeAll(availableProductForType);

		return availableProductForType;
	}

}
