package com.ee.shopping.services.order;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.ee.shopping.customer.Customer;
import com.ee.shopping.services.cart.CartItem;

public class Order {
	List<CartItem> cart;
	LocalDate date;
	UUID orderId;
	Customer customer;

	public Order(Customer customer, List<CartItem> cart) {
		super();
		this.cart = cart;
		this.date = LocalDate.now();
		orderId = UUID.randomUUID();
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public List<CartItem> getCart() {
		return cart;
	}

	public void setCart(List<CartItem> cart) {
		this.cart = cart;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public UUID getOrderId() {
		return orderId;
	}

	public void setOrderId(UUID orderId) {
		this.orderId = orderId;
	}

}
