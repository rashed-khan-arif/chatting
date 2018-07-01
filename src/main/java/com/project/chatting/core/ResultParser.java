package com.project.chatting.core;

import java.lang.reflect.Field;
import java.sql.ResultSet;

/**
 * Created by arifk on 26.8.17.
 */
public final class ResultParser {
    public static <T> T parser(ResultSet set, Class<T> clazz) {
        T t = null;
        try {
            Field[] fields = clazz.getDeclaredFields();
            t = clazz.newInstance();

            for (Field field : fields) {
                try {
                    field.setAccessible(true);

                        field.set(t, set.getObject(field.getName()));

                } catch (Exception ignored) {

                }
            }

        } catch (InstantiationException | IllegalAccessException e) {

        }
        return t;
    }

}
