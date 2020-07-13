package com.ee.shopping.services.cart;

import com.ee.shopping.product.PricedProduct;

/**
 * Model to hold product and total quantity in cart for the user
 * 
 * @author kriGow
 *
 */
public class CartItem {
	private PricedProduct product;
	private int quantity;
	private double cost;
	private double tax;

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public double getTax() {
		return tax;
	}

	public void setTax(double tax) {
		this.tax = tax;
	}

	private double totalCost;

	public double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}

	public CartItem(PricedProduct product) {
		super();
		this.product = product;
		this.quantity = 0;
	}

	public PricedProduct getProduct() {
		return product;
	}

	public void setProduct(PricedProduct product) {
		this.product = product;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((product == null) ? 0 : product.hashCode());
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
		CartItem other = (CartItem) obj;
		if (product == null) {
			if (other.product != null)
				return false;
		} else if (!product.equals(other.product))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "product=" + product + ", quantity=" + quantity + ", cost=" + cost + ", tax=" + tax + ", totalCost="
				+ totalCost;
	}

}
