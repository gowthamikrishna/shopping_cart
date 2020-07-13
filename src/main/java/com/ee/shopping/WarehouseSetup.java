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

	public static double toDouble(JSONObject json, String field) {
		if (json.containsKey(field)) {
			return Double.valueOf(json.get(field).toString());
		}

		return 0;
	}

	public static int toInt(JSONObject json, String field) {
		if (json.containsKey(field)) {
			return Integer.valueOf(json.get(field).toString());
		}

		return 0;
	}

	public static String asString(JSONObject json, String field) {
		if (json.containsKey(field)) {
			return json.get(field).toString();
		}
		return null;
	}

	public static JSONObject getField(JSONObject json, String field) {
		if (json.containsKey(field)) {
			return (JSONObject) json.get(field);
		}
		return null;
	}

	public static JSONArray getArrayField(JSONObject json, String field) {
		if (json.containsKey(field)) {
			Object arr = json.get(field);
			if (arr instanceof JSONArray) {
				return (JSONArray) arr;
			}
		}
		return null;
	}

	public static void loadConfigFromFile() {
		final String WAREHOUSE_SPEC = "warehouse_spec.json";
		try {
			InputStream in = WarehouseSetup.class.getClassLoader().getResourceAsStream(WAREHOUSE_SPEC);
			if (in != null) {
				JSONParser parser = new JSONParser();
				JSONObject spec = (JSONObject) parser.parse(new InputStreamReader(in));
				loadWarehouse(spec);
			}
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
	}

	public static void loadWarehouse(JSONObject spec) {
		JSONArray warehouse = getArrayField(spec, "warehouse");
		for (int i = 0; i < warehouse.size(); i++) {
			JSONObject company = (JSONObject) warehouse.get(i);
			String companyName = asString(company, "companyName");
			String location = asString(company, "location");
			JSONArray products = getArrayField(company, "products");
			Company companyObj = new CompanyImpl(companyName, location);
			for (int j = 0; j < products.size(); j++) {
				JSONObject product = (JSONObject) products.get(j);
				Integer quantity = toInt(product, "quantity");
				String productName = asString(product, "productName");
				String productType = asString(product, "productType");
				Double unitprice = toDouble(product, "unitprice");
				String currency = asString(product, "currency");
				JSONObject discount = getField(product, "discount");
				Double discountAmount = 0.0;
				String discountType = null;
				if (discount != null) {
					discountAmount = toDouble(discount, "amount");
					discountType = asString(discount, "type");
				}
				addProductToWarehouse(companyObj, quantity, productName, productType, unitprice, currency,
						discountAmount, discountType);
			}
		}

		// Collect all tax rates
		JSONArray taxMapping = getArrayField(spec, "tax");
		if (taxMapping != null) {
			for (int i = 0; i < taxMapping.size(); i++) {
				JSONObject tax = (JSONObject) taxMapping.get(i);
				ProductType productType = ProductType.of(asString(tax, "productType"));
				double taxRate = toDouble(tax, "taxRate");
				TaxServiceImpl.instance.setTaxForProductType(productType, taxRate);
			}
		}

		// Collect all Offers
		JSONArray offersMapping = getArrayField(spec, "offers");
		if (offersMapping != null) {
			for (int i = 0; i < offersMapping.size(); i++) {
				JSONObject offer = (JSONObject) offersMapping.get(i);
				ProductType productType = ProductType.of((String) offer.get("productType"));
				boolean isApplicable = Boolean.valueOf(offer.get("isApplicable").toString());
				int forCount = Integer.valueOf(offer.get("forCount").toString());
				int offerCount = Integer.valueOf(offer.get("offerCount").toString());
				OfferServiceImpl.instance
						.setOfferForProductType(new OfferModel(isApplicable, productType, forCount, offerCount));
			}
		}
		System.out.println("Configuration loaded successfully");

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
		loadConfigFromFile();

		CompanyProductWarehouse.instance.accumulate(new CompanyImpl("HUL"), ProductType.SOAP, 1000);
		System.out
				.println(CompanyProductWarehouse.instance.supply(new CompanyImpl("HUL"), ProductType.SOAP, 100).size());
	}

	public static void clear() {
		TaxServiceImpl.instance.clear();
		OfferServiceImpl.instance.clear();
		CompanyProductTypeMapping.instance.clear();
		CompanyProductWarehouse.instance.clear();
	}
}
