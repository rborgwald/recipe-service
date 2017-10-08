package com.bufkes.service.recipe.security.constants;

public class SecurityConstants {
	public static final String SECRET = "SecretKeyToGenJWTs";
    public static final Long EXPIRATION_TIME = new Long(86400000000L); // 1000 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/users/sign-up";
}
