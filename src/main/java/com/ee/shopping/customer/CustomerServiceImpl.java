package com.ee.shopping.customer;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public enum CustomerServiceImpl implements CustomerService {
	instance;
	Map<UUID, Customer> customers = new ConcurrentHashMap<UUID, Customer>();

	@Override
	public void addCustomer(Customer customer) {
		customers.put(customer.getCustomerId(), customer);
	}

	@Override
	public Customer removeCustomer(Customer customer) {
		return customers.remove(customer.getCustomerId());
	}

	@Override
	public Customer getCustomerById(UUID customerId) {
		return customers.get(customerId);
	}

}
