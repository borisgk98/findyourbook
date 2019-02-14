package space.borisgk.findyourbook.repository;

import lombok.SneakyThrows;
import space.borisgk.findyourbook.model.User;
import space.borisgk.findyourbook.tool.ResultSetWrapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserRepository extends JDBCRepository<User> {
    protected PreparedStatement getByLoginStatement;

    @SneakyThrows
    public UserRepository() {
        super(User.class);
        getByLoginStatement = connection.prepareStatement("SELECT * FROM " + getTableName() + " WHERE login = ?;");
    }

    @SneakyThrows
    public User get(String login) {
        getByLoginStatement.setString(1, login);
        ResultSet resultSet = getByLoginStatement.executeQuery();
        return (new ResultSetWrapper<User>(resultSet, User.class)).getOne();
    }
}
