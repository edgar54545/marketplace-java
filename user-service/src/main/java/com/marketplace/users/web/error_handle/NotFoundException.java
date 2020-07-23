package com.marketplace.users.web.error_handle;

public class NotFoundException extends RuntimeException {

    private static final String MESSAGE = "Nothing found for: ";

    public NotFoundException(String searchRequest) {
        super(MESSAGE + searchRequest);
    }
}
