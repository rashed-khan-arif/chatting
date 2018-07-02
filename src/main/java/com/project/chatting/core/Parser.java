package com.project.chatting.core;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

/**
 * Created by arifk on 26.8.17.
 */
public final class Parser {
    public static <T> T parser(ResultSet set, Class<T> clazz) {
        T t = null;
        try {
            Field[] fields = clazz.getDeclaredFields();
            t = clazz.newInstance();
            for (Field field : fields) {
                try {
                    field.setAccessible(true);
                    ParseName parseName = field.getAnnotation(ParseName.class);
                    field.set(t, set.getObject(parseName.value()));
                } catch (Exception ignored) {

                }
            }
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return t;
    }


}
