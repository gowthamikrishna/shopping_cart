package com.ee.shopping.services.order;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.ee.shopping.customer.Customer;

public enum OrderServiceImpl implements OrderService {
	instance;

	private Map<Customer, List<Order>> orderHistory = new ConcurrentHashMap<Customer, List<Order>>();

	@Override
	public void addOrder(Order order) {
		if (orderHistory.get(order.getCustomer()) == null) {
			orderHistory.put(order.getCustomer(), new ArrayList<Order>());
		}
		orderHistory.get(order.getCustomer()).add(order);
	}

	@Override
	public void removeOrder(Order order) {
		orderHistory.get(order.getCustomer()).remove(order);
	}

	@Override
	public void getOrderHistoryForCustomer(Customer customer) {
		orderHistory.get(customer);
	}

}
