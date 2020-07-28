package com.marketplace.users.constants;

public class Constants {
    // Bean Validation Error Messages
    public static final String INVALID_NAME = "Invalid Name";
    public static final String INVALID_NAME_SIZE = "Name require more than 3 and less than 25 characters";
    public static final String BLANK_USERNAME = "Username can't be blank";
    public static final String INVALID_USERNAME_SIZE = "Username require more than 4 and less than 25 characters";
    public static final String BLANK_PASSWORD = "Password can't be blank";
    public static final String INVALID_PASSWORD_SIZE = "Minimum 5 characters required for password";
    public static final String BLANK_EMAIL = "Email can't be blank";
    public static final String INVALID_EMAIL = "Invalid e-mail";

    // Security Constants
    public static final String SECRET = "secretKeyToGenerateJwt";
    public static final long EXPIRATION_TIME = 864_000_000; // 10 day
    public static final String ROLES_CLAIM = "roles";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
}
