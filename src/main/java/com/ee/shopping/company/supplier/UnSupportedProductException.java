package com.ee.shopping.company.supplier;

public class UnSupportedProductException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UnSupportedProductException() {
	}

	public UnSupportedProductException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public UnSupportedProductException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnSupportedProductException(String message) {
		super(message);
	}

	public UnSupportedProductException(Throwable cause) {
		super(cause);
	}

}
