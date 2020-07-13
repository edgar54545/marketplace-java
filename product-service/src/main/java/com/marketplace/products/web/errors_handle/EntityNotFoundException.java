package com.marketplace.products.web.errors_handle;

public class EntityNotFoundException extends RuntimeException {

    private static final String MESSAGE = "Nothing found for: ";

    public EntityNotFoundException(String message) {
        super(MESSAGE + message);
    }
}
