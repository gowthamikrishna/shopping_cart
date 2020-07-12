package com.ee.shopping;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.ee.shopping.company.supplier.Company;
import com.ee.shopping.company.supplier.CompanyImpl;
import com.ee.shopping.company.supplier.CompanyProductTypeMapping;
import com.ee.shopping.company.supplier.CompanyProductWarehouse;
import com.ee.shopping.company.supplier.ProductTypeToProductMapping;
import com.ee.shopping.product.CurrencyType;
import com.ee.shopping.product.DiscountType;
import com.ee.shopping.product.DiscountedItem;
import com.ee.shopping.product.PricedItem;
import com.ee.shopping.product.Product;
import com.ee.shopping.product.ProductType;
import com.ee.shopping.product.offer.OfferModel;
import com.ee.shopping.product.offer.OfferServiceImpl;
import com.ee.shopping.tax.TaxServiceImpl;

/*
 * Utility class to setup warehouse for all companies using configuration file
 */
public class WarehouseSetup {

	private static String WAREHOUSE_SPEC = "warouse_spec.json";

	public static void loadWarehouse() {
		try {
			InputStream in = WarehouseSetup.class.getClassLoader().getResourceAsStream(WAREHOUSE_SPEC);
			if (in != null) {
				JSONParser parser = new JSONParser();
				JSONObject spec = (JSONObject) parser.parse(new InputStreamReader(in));
				JSONArray warehouse = (JSONArray) spec.get("warehouse");
				for (int i = 0; i < warehouse.size(); i++) {
					JSONObject company = (JSONObject) warehouse.get(i);
					String companyName = company.get("companyName").toString();
					String location = company.get("location").toString();
					JSONArray products = (JSONArray) company.get("products");
					Company companyObj = new CompanyImpl(companyName, location);
					for (int j = 0; j < products.size(); j++) {
						JSONObject product = (JSONObject) products.get(j);
						Integer quantity = Integer.valueOf(product.get("quantity").toString());
						String productName = (String) product.get("productName");
						String productType = (String) product.get("productType");
						Double unitprice = Double.valueOf(product.get("unitprice").toString());
						String currency = (String) product.get("currency");
						JSONObject discount = (JSONObject) product.get("discount");
						Double discountAmount = 0.0;
						String discountType = null;
						if (discount != null) {
							discountAmount = Double.valueOf(discount.get("amount").toString());
							discountType = (String) discount.get("type");
						}
						addProductToWarehouse(companyObj, quantity, productName, productType, unitprice, currency,
								discountAmount, discountType);
					}
				}

				// Collect all tax rates
				JSONArray taxMapping = (JSONArray) spec.get("tax");
				for (int i = 0; i < taxMapping.size(); i++) {
					JSONObject tax = (JSONObject) taxMapping.get(i);
					ProductType productType = ProductType.of((String) tax.get("productType"));
					double taxRate = Double.valueOf(tax.get("taxRate").toString());
					TaxServiceImpl.instance.setTaxForProductType(productType, taxRate);
				}

				// Collect all Offers
				JSONArray offersMapping = (JSONArray) spec.get("offers");
				for (int i = 0; i < offersMapping.size(); i++) {
					JSONObject offer = (JSONObject) offersMapping.get(i);
					ProductType productType = ProductType.of((String) offer.get("productType"));
					boolean isApplicable = Boolean.valueOf(offer.get("isApplicable").toString());
					int forCount = Integer.valueOf(offer.get("forCount").toString());
					int offerCount = Integer.valueOf(offer.get("offerCount").toString());
					OfferServiceImpl.instance
							.setOfferForProductType(new OfferModel(isApplicable, productType, forCount, offerCount));
				}
				
				System.out.println("Configuration loaded successfully");
			}
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
	};

	private static void addProductToWarehouse(Company company, Integer quantity, String productName, String productType,
			Double unitprice, String currency, Double discountAmount, String discountType) {
		ProductType pdtType = ProductType.of(productType);
		// collect all product types a company supportss
		CompanyProductTypeMapping.instance.addProductTypeToCompany(company, pdtType);
		UUID productCode = UUID.randomUUID();
		Product product = null;

		CurrencyType priceCurrency = CurrencyType.of(currency);
		if (discountAmount > 0) {
			product = new DiscountedItem(productCode, productName, pdtType, company, unitprice, priceCurrency,
					DiscountType.of(discountType), discountAmount);
		} else {
			product = new PricedItem(productCode, productName, pdtType, company, unitprice, priceCurrency);
		}
		// Store (if already not done) reference product for the given product, so that
		// fly-weight pattern can be applied and new object will be created when
		// required.
		if (ProductTypeToProductMapping.instance().getProductForPriceReference(company, pdtType) == null) {
			ProductTypeToProductMapping.instance().addProductToProductType(company, pdtType, product);
		}

		// Generate required quantity of product based on spec
		CompanyProductWarehouse.instance.accumulate(company, pdtType, quantity);
	}

	public static void main(String[] args) {
		loadWarehouse();

		System.out
				.println(CompanyProductWarehouse.instance.supply(new CompanyImpl("HUL"), ProductType.SOAP, 100).size());
	}
}
