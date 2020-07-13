package com.ee.shopping.services.cart;

import java.util.List;

import com.ee.shopping.customer.Customer;
import com.ee.shopping.product.PricedProduct;
import com.ee.shopping.services.order.Order;

public interface Cart {

	void addItem(PricedProduct item, int quantity);

	void removeItem(PricedProduct item, int quantity);
	
	List<CartItem> showCart();

	Order checkout(Customer customer);
}
