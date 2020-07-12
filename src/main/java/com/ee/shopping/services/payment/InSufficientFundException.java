package com.ee.shopping.services.payment;

public class InSufficientFundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InSufficientFundException() {
	}

	public InSufficientFundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public InSufficientFundException(String message, Throwable cause) {
		super(message, cause);
	}

	public InSufficientFundException(String message) {
		super(message);
	}

	public InSufficientFundException(Throwable cause) {
		super(cause);
	}

}
