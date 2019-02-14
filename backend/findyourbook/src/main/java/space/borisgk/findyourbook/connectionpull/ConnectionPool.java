package space.borisgk.findyourbook.connectionpull;

import lombok.SneakyThrows;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

public class ConnectionPool {
    // TODO вынести отсюда connectionURL
    protected static String connectionURL = "jdbc:postgresql://localhost:5432/fub?password=pass&user=postgres";
    protected static ConnectionPool instance = new ConnectionPool();
    public static ConnectionPool getInstance() {
        return instance;
    }

    @SneakyThrows
    protected ConnectionPool() {
        Properties properties = new Properties();
        properties.load(new FileInputStream("/home/boris/D/my programms/find-your-book/backend/findyourbook/src/main/resources/application.properties"));
        ConnectionPool.connectionURL = properties.getProperty("connectionUrl");
    }

    protected List<Connection> connections = new LinkedList<>();

    // TODO нормальную обработку исключений
    @SneakyThrows
    public synchronized Connection getConnection() {
        if (connections.isEmpty()) {
            return DriverManager.getConnection(connectionURL);
        }
        else {
            Connection connection = connections.get(0);
            connections.remove(0);
            return connection;
        }
    }

    // TODO Обработка того, что добабляем "левое" соеденение
    @SneakyThrows
    public void putConnection(Connection connection) {
        connections.add(connection);
    }
}
