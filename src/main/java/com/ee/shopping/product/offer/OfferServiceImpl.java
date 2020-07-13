package com.ee.shopping.product.offer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.ee.shopping.product.ProductType;
import com.ee.shopping.services.cart.CartItem;

public class OfferServiceImpl implements OfferService {
	Map<ProductType, OfferModel> OfferMapping = new ConcurrentHashMap<>();

	@Override
	public void setOfferForProductType(OfferModel offer) {
		OfferMapping.put(offer.getProductType(), offer);
	}

	@Override
	public OfferModel getOfferForProduct(ProductType productType) {
		return OfferMapping.get(productType);
	}

	@Override
	public int applyOffer(CartItem cartItem) {
		int extraItems = 0;
		OfferModel offer = OfferMapping.get(cartItem.getProduct().getProductType());
		if (offer.isApplicable()) {
			extraItems = cartItem.getQuantity()/offer.getForCount() * offer.getOfferCount();
			cartItem.setQuantity(cartItem.getQuantity()+extraItems);
		}
		return extraItems;
	}

	public void clear() {
		if (OfferMapping != null) {
			OfferMapping.clear();
		}
	}

}
