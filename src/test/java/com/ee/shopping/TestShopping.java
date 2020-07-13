package com.ee.shopping;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.ee.shopping.customer.Customer;
import com.ee.shopping.product.ProductType;
import com.ee.shopping.services.cart.Cart;
import com.ee.shopping.services.cart.CartItem;
import com.ee.shopping.services.inventory.ShoppingService;

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

	@Test
	public void testStep3() {
		String specFile = "step3.json";
		Customer customer = TestUtils.getTestCustomer();
		Cart cart = TestUtils.getCartLoadedUsingSpec(specFile, customer);
		List<CartItem> items = cart.showCart();
		double totalTax = 0;
		for (CartItem cartItem : items) {
			totalTax = totalTax + cartItem.getTax();
			if (ProductType.SOAP == cartItem.getProduct().getProductType()) {
				assertEquals(cartItem.getQuantity(), 2);
			} else if (ProductType.DEO == cartItem.getProduct().getProductType()) {
				assertEquals(cartItem.getQuantity(), 2);
			}
		}
		assertEquals(totalTax, 35.00, Math.abs(totalTax - 35.00));
		double totalCost = TestUtils.getPriceOfCart(cart, customer);
		assertEquals(totalCost, 314.96, Math.abs(totalCost - 314.96));
	}

	@Test
	public void testStep4() {
		String specFile = "step4.json";
		Customer customer = TestUtils.getTestCustomer();
		Cart cart = TestUtils.getCartLoadedUsingSpec(specFile, customer);
		List<CartItem> items = cart.showCart();
		double totalTax = 0;
		for (CartItem cartItem : items) {
			totalTax = totalTax + cartItem.getTax();
			if (ProductType.SOAP == cartItem.getProduct().getProductType()) {
				ShoppingService.getOfferService().applyOffer(cartItem);
				assertEquals(cartItem.getQuantity(), 3);
				assertEquals(cartItem.getCost(), 89.98, Math.abs(cartItem.getCost() - 89.98));
				assertEquals(cartItem.getTax(), 10.00, Math.abs(cartItem.getTax() - 10.00));
			} else if (ProductType.DEO == cartItem.getProduct().getProductType()) {
				assertEquals(cartItem.getQuantity(), 1);
			}
		}
		double totalCost = TestUtils.getPriceOfCart(cart, customer);
		double expectedTotalCost = 89.98 + 89.99;
		assertEquals(totalCost, expectedTotalCost, Math.abs(totalCost - expectedTotalCost));

		// part2

		String specFile2 = "step4_part2.json";
		WarehouseSetup.initServices();
		Cart cart2 = TestUtils.getCartLoadedUsingSpec(specFile2, customer);
		List<CartItem> items2 = cart2.showCart();
		double totalTax2 = 0;
		for (CartItem cartItem : items2) {
			totalTax2 = totalTax2 + cartItem.getTax();
			if (ProductType.SOAP == cartItem.getProduct().getProductType()) {
				ShoppingService.getOfferService().applyOffer(cartItem);
				assertEquals(cartItem.getQuantity(), 5);
				assertEquals(cartItem.getCost(), 179.96, Math.abs(cartItem.getCost() - 179.96));
				assertEquals(cartItem.getTax(), 20.00, Math.abs(cartItem.getTax() - 20.00));
			}
		}

		// part3
		String specFile3 = "step4_part3.json";
		WarehouseSetup.initServices();
		Cart cart3 = TestUtils.getCartLoadedUsingSpec(specFile3, customer);
		List<CartItem> items3 = cart3.showCart();
		double totalTax3 = 0;
		for (CartItem cartItem : items3) {
			totalTax3 = totalTax3 + cartItem.getTax();
			if (ProductType.SOAP == cartItem.getProduct().getProductType()) {
				ShoppingService.getOfferService().applyOffer(cartItem);
				assertEquals(cartItem.getQuantity(), 3);
			} else if (ProductType.DEO == cartItem.getProduct().getProductType()) {
				assertEquals(cartItem.getQuantity(), 2);
			}
		}
		assertEquals(totalTax3, 32.50, Math.abs(totalTax3 - 32.50));
		double totalCost3 = TestUtils.getPriceOfCart(cart, customer);
		double expectedTotalCost3 = 292.46;
		assertEquals(totalCost3, expectedTotalCost3, Math.abs(totalCost3 - expectedTotalCost3));
	}

	@Test
	public void testStep5() {
		String specFile = "step5.json";
		Customer customer = TestUtils.getTestCustomer();
		Cart cart = TestUtils.getCartLoadedUsingSpec(specFile, customer);
		List<CartItem> items = cart.showCart();
		double totalTax = 0;
		for (CartItem cartItem : items) {
			totalTax = totalTax + cartItem.getTax();
			if (ProductType.SOAP == cartItem.getProduct().getProductType()) {
				assertEquals(cartItem.getQuantity(), 2);
				assertEquals(cartItem.getCost(), 67.48, Math.abs(cartItem.getCost() - 67.48));
				assertEquals(cartItem.getTax(), 7.50, Math.abs(cartItem.getTax() - 7.50));
			}
		}
	}

}
