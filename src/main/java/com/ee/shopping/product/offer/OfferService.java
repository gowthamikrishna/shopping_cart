package com.ee.shopping.product.offer;

import com.ee.shopping.product.ProductType;
import com.ee.shopping.services.cart.CartItem;

public interface OfferService {

	void setOfferForProductType(OfferModel offer);

	OfferModel getOfferForProduct(ProductType productType);

	int applyOffer(CartItem cartItem);
}
