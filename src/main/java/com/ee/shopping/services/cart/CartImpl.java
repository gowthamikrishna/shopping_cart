package com.ee.shopping.services.cart;

import java.util.List;

import com.ee.shopping.customer.Customer;
import com.ee.shopping.product.PricedProduct;
import com.ee.shopping.services.order.Order;

public class CartImpl implements Cart {

	private Customer customer;
	private List<CartItem> cart;

	public CartImpl(Customer customer) {
		super();
		this.customer = customer;
	}

	@Override
	public void addItem(PricedProduct item, int quantity) {
		CartItem cartItem = new CartItem(item);
		quantity = quantity <= 0 ? 1 : quantity;
		if (!cart.contains(cartItem)) {
			cartItem.setQuantity(quantity);
			cart.add(cartItem);
		} else {
			CartItem existingItem = cart.get(cart.indexOf(cartItem));
			existingItem.setQuantity(existingItem.getQuantity() + quantity);
		}
	}

	@Override
	public void removeItem(PricedProduct item, int quantity) {
		CartItem cartItem = new CartItem(item);
		quantity = quantity > 0 ? quantity : 0;
		if (cart.contains(cartItem)) {
			CartItem existingItem = cart.get(cart.indexOf(cartItem));
			int newQuantity = existingItem.getQuantity() - quantity;
			existingItem.setQuantity(newQuantity);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((customer == null) ? 0 : customer.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CartImpl other = (CartImpl) obj;
		if (customer == null) {
			if (other.customer != null)
				return false;
		} else if (!customer.equals(other.customer))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "customer=" + customer + ", cart=" + cart;
	}

	@Override
	public Order checkout(Customer customer) {
		return new Order(customer, cart);
	}

}
