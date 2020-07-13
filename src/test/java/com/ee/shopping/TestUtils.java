package com.ee.shopping;

import static com.ee.shopping.services.inventory.ShoppingService.getCompanyProductTypeMapping;
import static com.ee.shopping.services.inventory.ShoppingService.getInventoryService;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.ee.shopping.company.supplier.Company;
import com.ee.shopping.customer.Customer;
import com.ee.shopping.product.PricedProduct;
import com.ee.shopping.product.Product;
import com.ee.shopping.product.ProductType;
import com.ee.shopping.services.cart.Cart;
import com.ee.shopping.services.cart.CartImpl;
import com.ee.shopping.services.inventory.InventoryService;
import com.ee.shopping.services.order.Order;
import com.ee.shopping.services.payment.PaymentService;
import com.ee.shopping.services.payment.PaymentServiceImpl;

public class TestUtils {
	public static JSONObject readFileIntoJsonObject(String fileName) {
		JSONObject spec = null;
		try {
			InputStream in = TestUtils.class.getClassLoader().getResourceAsStream(fileName);
			if (in != null) {
				JSONParser parser = new JSONParser();
				spec = (JSONObject) parser.parse(new InputStreamReader(in));
			}
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		return spec;
	}

	public static Customer getTestCustomer() {
		return new Customer(UUID.randomUUID(), "Krishna", "WhiteField, Bangalore, pin-560066", 5000);
	}

	/**
	 * Spec driven cart filling
	 * 
	 * @param specFile
	 */
	public static Cart addItemsToCartUsingSpec(String specFile, Customer customer) {
		JSONObject spec = readFileIntoJsonObject(specFile);
		WarehouseSetup.loadWarehouse(spec);
		InventoryService inventoryService =getInventoryService();
		inventoryService.setupInventory();
		Cart cart = new CartImpl(customer);
		Map<Company, List<ProductType>> allProductTypeFromSepc = getCompanyProductTypeMapping()
				.getAvailableSupplierAndTheirProducts();
		for (Company company : allProductTypeFromSepc.keySet()) {
			for (ProductType productType : allProductTypeFromSepc.get(company)) {
				List<Product> products = inventoryService.getInventoryByProductType(productType);
				cart.addItem((PricedProduct) products.get(0), products.size());
			}
		}
		return cart;
	}

	public static Cart getCartLoadedUsingSpec(String specFile, Customer customer) {
		return addItemsToCartUsingSpec(specFile, customer);
	}

	public static Order checkoutCart(Cart cart, Customer customer) {
		return cart.checkout(customer);
	}

	public static double getPriceOfCart(Cart cart, Customer customer) {
		PaymentService paymentService = new PaymentServiceImpl();
		return paymentService.getTotalPrice(customer, cart);
	}
}
