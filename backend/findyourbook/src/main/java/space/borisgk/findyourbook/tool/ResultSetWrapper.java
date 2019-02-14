package space.borisgk.findyourbook.tool;

import lombok.SneakyThrows;
import space.borisgk.findyourbook.model.annatation.DBField;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.*;

public class ResultSetWrapper<T> {
    protected ResultSet resultSet;
    protected Class<T> modelClass;
    // Для хранения пар поле модели -> столбец бд
    protected Set<Map.Entry<Field, String>> entries = new HashSet<>();
    protected Map<String, Integer> rsColMap = new HashMap<>();

    // TODO подумать надо обработкой искл
    @SneakyThrows
    public ResultSetWrapper(ResultSet resultSet, Class<T> modelClass) {
        this.resultSet = resultSet;
        this.modelClass = modelClass;

        Set<String> rsCols = new HashSet<>();
        for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
            rsCols.add(resultSet.getMetaData().getColumnName(i));
            rsColMap.put(resultSet.getMetaData().getColumnName(i), i);
        }

        Map<Field, String> fieldToCol = new HashMap<>();
        for (Field field : modelClass.getDeclaredFields()) {
            DBField dbField = field.getAnnotation(DBField.class);
            if (dbField != null) {
                // если в классе на это поле забиндено поле в rs, но в rs его нет
                String dbf = dbField.field();
                if (dbf.equals("")) {
                    dbf = field.getName();
                }
                if (!rsCols.contains(dbf)) {
                    throw new IllegalArgumentException("Incompatible ResultSet and Model class");
                }
                fieldToCol.put(field, dbf);
            }
        }

        entries = fieldToCol.entrySet();
    }

    @SneakyThrows
    public T getOne() {
        if (!resultSet.next()) {
            return null;
        }
        T model = modelClass.newInstance();
        for (Map.Entry<Field, String> entry : entries) {
            Field field = entry.getKey();
            // data base returning value class
            Class dbrvc = Class.forName(resultSet.getMetaData().getColumnClassName(rsColMap.get(entry.getValue())));
            field.setAccessible(true);
            field.set(model, mapper1(resultSet.getObject(entry.getValue(), dbrvc), field.getType(), dbrvc));
            field.setAccessible(false);
        }
        return model;
    }

    @SneakyThrows
    public List<T> getAll() {
        List<T> list = new ArrayList<>();
        while (true) {
            T model = getOne();
            if (model == null) {
                break;
            }
            list.add(model);
        }
        return list;
    }

    protected static <F> F mapper1(Object dbval, Class<F> rc, Class dc) {
        return rc.cast(dbval);
    }

}
