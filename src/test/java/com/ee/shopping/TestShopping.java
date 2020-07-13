package com.ee.shopping;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.ee.shopping.customer.Customer;
import com.ee.shopping.services.cart.Cart;
import com.ee.shopping.services.cart.CartItem;

public class TestShopping {

	@Before
	public void setup() {
		WarehouseSetup.initServices();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testStep1() {
		String specFile = "step1.json";
		Customer customer = TestUtils.getTestCustomer();
		Cart cart = TestUtils.getCartLoadedUsingSpec(specFile, customer);
		List<CartItem> items = cart.showCart();
		assertEquals(items.get(0).getQuantity(), 5);
		double totalCost = TestUtils.getPriceOfCart(cart, customer);
		assertEquals(totalCost, 199.95, Math.abs(totalCost - 199.95));
	}

	@Test
	public void testStep2() {
		String specFile = "step2.json";
		Customer customer = TestUtils.getTestCustomer();
		Cart cart = TestUtils.getCartLoadedUsingSpec(specFile, customer);
		List<CartItem> items = cart.showCart();
		assertEquals(items.get(0).getQuantity(), 8);
		double totalCost = TestUtils.getPriceOfCart(cart, customer);
		assertEquals(totalCost, 319.92, Math.abs(totalCost - 199.95));
	}

}
