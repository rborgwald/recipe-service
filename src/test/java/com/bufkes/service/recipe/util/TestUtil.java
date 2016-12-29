package com.bufkes.service.recipe.util;

import java.lang.reflect.Field;

import org.springframework.util.ReflectionUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

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
}
