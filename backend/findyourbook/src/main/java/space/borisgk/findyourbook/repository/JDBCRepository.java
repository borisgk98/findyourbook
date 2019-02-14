package space.borisgk.findyourbook.repository;

import lombok.SneakyThrows;
import space.borisgk.findyourbook.connectionpull.ConnectionPool;
import space.borisgk.findyourbook.model.annatation.DBField;
import space.borisgk.findyourbook.model.annatation.DBManyToMany;
import space.borisgk.findyourbook.model.annatation.DBTable;
import space.borisgk.findyourbook.tool.ResultSetWrapper;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class JDBCRepository<T> implements Repository<T> {

    protected Connection connection;
    protected Class<T> modelClass;
    protected PreparedStatement putStatement, deleteStatement, getStatement, findAllStatement, updateStatement;

    @SneakyThrows
    public JDBCRepository(Class<T> modelClass) {
        this.modelClass = modelClass;
        this.connection = ConnectionPool.getInstance().getConnection();

        List<Field> fields = getModelFields();

        putStatement = connection.prepareStatement(generatePutStatementString(fields));
        getStatement = connection.prepareStatement(generateGetStatementString(fields));
        deleteStatement = connection.prepareStatement(generateDeleteStatementString(fields));
        findAllStatement = connection.prepareStatement(generateFindAllStatementString(fields));
        updateStatement = connection.prepareStatement(generateUpdateStatementString(fields));
    }

    protected List<Field> getModelFields() {

        List<Field> fields = new ArrayList<>();
        for (Field field : modelClass.getDeclaredFields()) {
            if (field.getAnnotation(DBField.class) != null) {
                fields.add(field);
            }
        }
        return fields;
    }

    protected List<Field> getModelManyToManyFields() {
        List<Field> fields = new ArrayList<>();
        for (Field field : modelClass.getDeclaredFields()) {
            if (field.getAnnotation(DBManyToMany.class) != null) {
                fields.add(field);
            }
        }
        return fields;
    }

    protected String generatePutStatementString(List<Field> fields) {

        StringBuilder stringBuilder;

        stringBuilder = new StringBuilder();
        stringBuilder.append("INSERT INTO ");
        stringBuilder.append(getTableName());
        stringBuilder.append("(");
        for (int i = 0; i < fields.size(); i++) {
            DBField dbField = fields.get(i).getAnnotation(DBField.class);
            if (dbField.constraint().equals("")) {
                stringBuilder.append("\"");
                if (!dbField.field().equals("")) {
                    stringBuilder.append(dbField.field());
                }
                else {
                    stringBuilder.append(fields.get(i).getName());
                }
                stringBuilder.append("\"");
                if (i != fields.size() - 1) {
                    stringBuilder.append(", ");
                }
            }
        }
        stringBuilder.append(") VALUES (");
        for (int i = 0;i < fields.size(); i++) {
            DBField dbField = fields.get(i).getAnnotation(DBField.class);
            if (dbField.constraint().equals("")) {
                stringBuilder.append("?");
                if (i != fields.size() - 1) {
                    stringBuilder.append(", ");
                }
            }
        }
        stringBuilder.append(") RETURNING *;");

        return stringBuilder.toString();
    }

    protected String getTableName() {
        if (modelClass.getAnnotation(DBTable.class) == null) {
            throw new IllegalArgumentException("Model class hasn't DBTable annotation!");
        }
        return modelClass.getAnnotation(DBTable.class).table();
    }

    protected String generateGetStatementString(List<Field> fields) {

        StringBuilder stringBuilder;

        stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT * FROM ");
        stringBuilder.append(getTableName());
        stringBuilder.append(" WHERE ");
        Boolean bl = false;
        for (Field field : fields) {
            DBField dbField = field.getAnnotation(DBField.class);
            if (dbField.constraint().equals("PRIMARY KEY")) {
                String dbf = dbField.field();
                if (dbf.equals("")) {
                    dbf = field.getName();
                }
                stringBuilder.append(dbf);
                bl = true;
            }
        }
        if (!bl) {
            throw new IllegalArgumentException("Model class hasn't primary key field!");
        }
        stringBuilder.append(" = ?;");

        return stringBuilder.toString();
    }

    protected String generateDeleteStatementString(List<Field> fields) {

        StringBuilder stringBuilder;

        stringBuilder = new StringBuilder();
        stringBuilder.append("DELETE FROM ");
        stringBuilder.append(getTableName());
        stringBuilder.append(" WHERE ");
        Boolean bl = false;
        for (Field field : fields) {
            DBField dbField = field.getAnnotation(DBField.class);
            if (dbField.constraint().equals("PRIMARY KEY")) {
                String dbf = dbField.field();
                if (dbf.equals("")) {
                    dbf = field.getName();
                }
                stringBuilder.append(dbf);
                bl = true;
            }
        }
        if (!bl) {
            throw new IllegalArgumentException("Model class hasn't primary key field!");
        }
        stringBuilder.append(" = ?;");

        return stringBuilder.toString();
    }

    protected String generateFindAllStatementString(List<Field> fields) {

        StringBuilder stringBuilder;

        stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT * FROM ");
        stringBuilder.append(getTableName());
        stringBuilder.append(";");

        return stringBuilder.toString();
    }

    protected String generateFindAllLimitStatementString(Integer start, Integer stop) {
        String s = findAllStatement.toString();
        return s + " LIMIT " + (stop - start) + " OFFSET " + start + ";";
    }

    protected String generateUpdateStatementString(List<Field> fields) {

        StringBuilder stringBuilder;

        stringBuilder = new StringBuilder();
        stringBuilder.append("UPDATE ");
        stringBuilder.append(getTableName());
        stringBuilder.append(" SET ");

        for (int i = 0;i < fields.size(); i++) {
            DBField dbField = fields.get(i).getAnnotation(DBField.class);
            if (dbField.constraint().equals("")) {
                if (dbField.field().equals("")) {
                    stringBuilder.append(fields.get(i).getName());
                }
                else {
                    stringBuilder.append(dbField.field());
                }
                stringBuilder.append("=");
                stringBuilder.append("?");
                if (i != fields.size() - 1) {
                    stringBuilder.append(", ");
                }
            }
        }

        stringBuilder.append(" WHERE id=?");

        return stringBuilder.toString();
    }

    @SneakyThrows
    protected Integer[] getSubModelsId(Field field, Integer id) {

        StringBuilder stringBuilder;

        DBManyToMany dbManyToMany = field.getAnnotation(DBManyToMany.class);
        stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT ");
        stringBuilder.append(dbManyToMany.secondColName());
        stringBuilder.append(" FROM ");
        String tableName;
        tableName = dbManyToMany.relationTable();
        stringBuilder.append(tableName);
        stringBuilder.append(" WHERE ");
        stringBuilder.append(dbManyToMany.firstColName());
        stringBuilder.append("=");
        stringBuilder.append(id);
        stringBuilder.append(";");

        ResultSet resultSet = connection.createStatement().executeQuery(stringBuilder.toString());
        List<Integer> arr = new ArrayList<>();
        while (resultSet.next()) {
            arr.add(resultSet.getInt(1));
        }

        return arr.toArray(new Integer[arr.size()]);
    }

    @SneakyThrows
    protected void removeSubModelsId(Field field, Integer id) {

        StringBuilder stringBuilder;

        DBManyToMany dbManyToMany = field.getAnnotation(DBManyToMany.class);
        stringBuilder = new StringBuilder();
        stringBuilder.append("DELETE FROM ");
        String tableName;
        tableName = dbManyToMany.relationTable();
        stringBuilder.append(tableName);
        stringBuilder.append(" WHERE ");
        stringBuilder.append(dbManyToMany.firstColName());
        stringBuilder.append("=");
        stringBuilder.append(id);
        stringBuilder.append(";");

        connection.createStatement().execute(stringBuilder.toString());
    }

    @SneakyThrows
    protected void addSubModelsId(Field field, Integer id, Integer[] ids) {

        StringBuilder stringBuilder;

        DBManyToMany dbManyToMany = field.getAnnotation(DBManyToMany.class);
        stringBuilder = new StringBuilder();
        stringBuilder.append("INSERT INTO ");
        String tableName;
        tableName = dbManyToMany.relationTable();
        stringBuilder.append(tableName);
        stringBuilder.append(" (");
        stringBuilder.append(dbManyToMany.firstColName());
        stringBuilder.append(", ");
        stringBuilder.append(dbManyToMany.secondColName());
        stringBuilder.append(") VALUES ");
        for (int i = 0; i < ids.length; i++) {
            stringBuilder.append("(");
            stringBuilder.append(id);
            stringBuilder.append(", ");
            stringBuilder.append(ids[i]);
            stringBuilder.append(")");
            if (i != ids.length - 1) {
                stringBuilder.append(", ");
            }
        }

        connection.createStatement().execute(stringBuilder.toString());
    }

    @SneakyThrows
    @Override
    public T put(T model) {
        int i = 1;
        for (Field field : getModelFields()) {
            if (field.getAnnotation(DBField.class).constraint().equals("")) {
                field.setAccessible(true);
                putStatement.setObject(i, field.get(model));
                field.setAccessible(false);
                i++;
            }
        }
        return (new ResultSetWrapper<T>(putStatement.executeQuery(), modelClass)).getOne();
    }

    @SneakyThrows
    @Override
    public void delete(Integer id) {
        deleteStatement.setInt(1, id);
        deleteStatement.execute();
        return;
    }

    @SneakyThrows
    @Override
    public List<T> findAll() {
        return (new ResultSetWrapper<T>(findAllStatement.executeQuery(), modelClass)).getAll();
    }

    @SneakyThrows
    public List<T> findAll(Integer start, Integer stop) {
        PreparedStatement statement = connection.prepareStatement(generateFindAllLimitStatementString(start, stop));
        return (new ResultSetWrapper<T>(statement.executeQuery(), modelClass)).getAll();
    }

    @SneakyThrows
    @Override
    public T get(Integer id) {
        getStatement.setInt(1, id);
        T model = (new ResultSetWrapper<T>(getStatement.executeQuery(), modelClass)).getOne();

        Field[] fields = model.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.getAnnotation(DBManyToMany.class) != null) {
                field.setAccessible(true);
                Integer[] values = getSubModelsId(field, id);
                field.set(model, values);
                field.setAccessible(false);
            }
        }

        return model;
    }

    @Override
    protected void finalize() throws Throwable {
        ConnectionPool.getInstance().putConnection(connection);
    }

    @SneakyThrows
    @Override
    public void update(Integer id, T model) {
        int i = 1;
        for (Field field : getModelFields()) {
            if (field.getAnnotation(DBField.class).constraint().equals("")) {
                field.setAccessible(true);
                updateStatement.setObject(i, field.get(model));
                field.setAccessible(false);
                i++;
            }
        }

        updateStatement.setInt(i, id);
        updateStatement.execute();

        Field[] fields = model.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.getAnnotation(DBManyToMany.class) != null) {
                field.setAccessible(true);
                removeSubModelsId(field, id);
                addSubModelsId(field, id, (Integer[]) field.get(model));
                Integer[] values = getSubModelsId(field, id);
                field.set(model, values);
                field.setAccessible(false);
                field.setAccessible(false);
            }
        }
    }

    @SneakyThrows
    public List<T> search(String name) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT * FROM ");
        stringBuilder.append(getTableName());
        stringBuilder.append(" WHERE name ~ '"); // TODO remove name to params and db annotations
        stringBuilder.append(name);
        stringBuilder.append("' LIMIT 100"); // TODO REMOVE HARDCODE!!!
        PreparedStatement statement = connection.prepareStatement(stringBuilder.toString());
        return (new ResultSetWrapper<T>(statement.executeQuery(), modelClass)).getAll();
    }
}
