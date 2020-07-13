package com.ee.shopping.product.offer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.ee.shopping.product.ProductType;
import com.ee.shopping.services.cart.CartItem;

public class OfferServiceImpl implements OfferService {
	private Map<ProductType, OfferModel> OfferMapping = new ConcurrentHashMap<>();
	private GlobalOfferModel globalOffer;

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
			extraItems = cartItem.getQuantity() / offer.getForCount() * offer.getOfferCount();
			if (offer.getOfferDiscount() > 0) {
				double discountToDeduct = (offer.getOfferDiscount() / 100) * cartItem.getProduct().getPrice();
				discountToDeduct = discountToDeduct * extraItems;
				cartItem.setTotalCost(cartItem.getTotalCost() - discountToDeduct);
			} else {
				cartItem.setQuantity(cartItem.getQuantity() + extraItems);
			}
		}
		return extraItems;
	}

	public void clear() {
		if (OfferMapping != null) {
			OfferMapping.clear();
		}
	}

	@Override
	public GlobalOfferModel getGlobalOffer() {
		return globalOffer;
	}

	@Override
	public void setGlobalOffer(GlobalOfferModel gloablOffer) {
		this.globalOffer = gloablOffer;
	}

	@Override
	public void applyGlobalOffer(CartItem cartItem) {
		if (globalOffer != null && globalOffer.getPercentageDiscount() > 0) {
			double productPrice = cartItem.getProduct().getPrice();
			cartItem.getProduct().setPrice((globalOffer.getPercentageDiscount() / 100) * productPrice);
		}
	}

}
