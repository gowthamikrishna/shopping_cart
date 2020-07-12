package com.ee.shopping.services.order;

import com.ee.shopping.customer.Customer;

public interface OrderService {

	void addOrder(Order order);

	void removeOrder(Order order);

	void getOrderHistoryForCustomer(Customer customer);
}
