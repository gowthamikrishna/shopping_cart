package com.ee.shopping.services.payment;

import java.util.List;

import com.ee.shopping.customer.Customer;
import com.ee.shopping.product.DiscountPricedProduct;
import com.ee.shopping.product.DiscountType;
import com.ee.shopping.product.PricedProduct;
import com.ee.shopping.product.Product;
import com.ee.shopping.product.offer.OfferServiceImpl;
import com.ee.shopping.services.cart.Cart;
import com.ee.shopping.services.cart.CartItem;
import com.ee.shopping.services.inventory.InventoryServiceImpl;
import com.ee.shopping.services.order.Order;
import com.ee.shopping.tax.TaxServiceImpl;

public class PaymentServiceImpl implements PaymentService {

	@Override
	public double getTotalPrice(Customer customer, Cart cart) {
		Order order = cart.checkout(customer);
		return calculateTotalPrice(customer, order.getCart());
	}

	private double calculateTotalPrice(Customer customer, List<CartItem> cartItems) {
		double totalPrice = 0;
		for (CartItem cartItem : cartItems) {
			Product product = cartItem.getProduct();
			int quantity = cartItem.getQuantity();
			double discountPrice = 0;
			double finalPrice = 0;
			if (product instanceof DiscountPricedProduct) {
				DiscountPricedProduct discountedProduct = ((DiscountPricedProduct) product);
				discountPrice = discountedProduct.getAmount();
				if (discountedProduct.getDiscountType() == DiscountType.PERCENTAGE) {
					discountPrice = discountedProduct.getPrice() * (discountPrice / 100);
				}
				// no -ve consideration
				finalPrice = discountedProduct.getPrice() - discountPrice;
			} else if (product instanceof PricedProduct) {
				finalPrice = ((PricedProduct) product).getPrice();
			}

			double tax = TaxServiceImpl.instance.calculateTaxForProduct(product, quantity);

			totalPrice = totalPrice + (finalPrice * quantity);
			// apply tax
			totalPrice = totalPrice + tax;
		}

		// rounding off 2 decimal digit
		totalPrice = Math.round(totalPrice * 100.0) / 100.0;
		return totalPrice;
	}

	@Override
	public Order doPayment(Order order) {
		double totalPrice = calculateTotalPrice(order.getCustomer(), order.getCart());
		if (order.getCustomer().getBalance() < totalPrice) {
			throw new InSufficientFundException(
					String.format("Available balance %f for the customer %s is less than cart value %f",
							order.getCustomer().getBalance(), order.getCustomer().getName(), totalPrice));
		}
		order.getCustomer().setBalance(order.getCustomer().getBalance() - totalPrice);
		// After payment, remove items from inventory
		for (CartItem cartItem : order.getCart()) {
			OfferServiceImpl.instance.applyOffer(cartItem);
			InventoryServiceImpl.instance.removeProduct(cartItem.getProduct(), cartItem.getQuantity());
		}
		return order;
	}

}