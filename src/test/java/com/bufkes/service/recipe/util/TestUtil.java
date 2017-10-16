package com.bufkes.service.recipe.util;

import static com.bufkes.service.recipe.security.constants.SecurityConstants.EXPIRATION_TIME;
import static com.bufkes.service.recipe.security.constants.SecurityConstants.SECRET;

import java.lang.reflect.Field;
import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.util.ReflectionUtils;

import com.bufkes.service.recipe.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class TestUtil {

	private TestUtil() {}

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void setField(Object object, String fieldName, Object value) {
        Class<?> clazz = object.getClass();
        Field field = ReflectionUtils.findField(clazz, fieldName);
        ReflectionUtils.makeAccessible(field);
        ReflectionUtils.setField(field, object, value);
    }

	public static HttpHeaders getAuthHeader() {
		HttpHeaders headers = new HttpHeaders();
        StringBuilder token = new StringBuilder("Bearer ");
        token.append(TestUtil.generateJwtToken());
        headers.add("Authorization", token.toString());
        return headers;
	}
	
	public static String generateJwtToken() {

        User userDetails = TestDataBuilder.buildUser();
        
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }
}
