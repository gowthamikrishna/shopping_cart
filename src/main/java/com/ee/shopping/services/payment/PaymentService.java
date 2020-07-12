package com.ee.shopping.services.payment;

import com.ee.shopping.customer.Customer;
import com.ee.shopping.services.cart.Cart;
import com.ee.shopping.services.order.Order;

public interface PaymentService {
	/**
	 * Calculates total chargeable amount on given cart
	 * 
	 * @param customer
	 * @param orer
	 * @return
	 */
	double getTotalPrice(Customer customer, Cart orer);

	/**
	 * To do payment on given order
	 * 
	 * @param cart
	 * @return Order
	 */
	Order doPayment(Order orer);

}
