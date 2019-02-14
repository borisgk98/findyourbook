package space.borisgk.findyourbook.tool;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import space.borisgk.findyourbook.model.annatation.DBField;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class ParametrMapToModel<T> {
    @SneakyThrows
    public T apply(Map<String, String[]> stringMap, Class<T> tClass) {
        Map<String, String> map = new HashMap<>();
        for (Map.Entry<String, String[]> entry : stringMap.entrySet()) {
            for (Field field : tClass.getDeclaredFields()) {
                if (field.getName().equals(entry.getKey())) {
                    map.put(entry.getKey(), (entry.getValue().length > 0 ? entry.getValue()[0] : ""));
                }
            }
        }

        T proto = tClass.newInstance();
        for (Field field : tClass.getDeclaredFields()) {
            if (!map.containsKey(field.getName()) && field.getAnnotation(DBField.class) != null) {
                field.setAccessible(true);
                try {
                    map.put(field.getName(), field.get(proto).toString());
                }
                catch (NullPointerException e) {
                    throw new IllegalArgumentException("Need more arguments!1!1!!!");
                }
            }
        }

        ObjectMapper objectMapper = new ObjectMapper();
        T model = objectMapper.readValue(objectMapper.writeValueAsString(map), tClass);
        return model;
    }
}
