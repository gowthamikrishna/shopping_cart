package com.ee.shopping.customer;

import java.util.UUID;

public interface CustomerService {

	void addCustomer(Customer customer);

	Customer removeCustomer(Customer customer);

	Customer getCustomerById(UUID customerId);

}
