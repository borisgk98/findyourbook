package space.borisgk.findyourbook.repository;

import org.junit.Rule;
import org.junit.Test;
import rule.DbRule;
import space.borisgk.findyourbook.model.User;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class JDBCRepositoryTest {
    @Rule
    public DbRule dbRule = new DbRule();

    @Test
    public void generatePutStatementString() {
        JDBCRepository<User> userJDBCRepository = new JDBCRepository<>(User.class);
        String s = userJDBCRepository.generatePutStatementString(userJDBCRepository.getModelFields());
        assertEquals("INSERT INTO \"fub_user\"(\"login\", \"email\", \"hashpass\", \"country\", \"gender\", \"born\", \"news\")" +
                " VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING *;", s);
    }

    @Test
    public void getTableName() {
        JDBCRepository<User> userJDBCRepository = new JDBCRepository<>(User.class);
        assertEquals(userJDBCRepository.getTableName(), "\"fub_user\"");
    }

    @Test
    public void generateGetStatementString() {
        JDBCRepository<User> userJDBCRepository = new JDBCRepository<>(User.class);
        String s = userJDBCRepository.generateGetStatementString(userJDBCRepository.getModelFields());
        assertEquals("SELECT * FROM \"fub_user\" WHERE id = ?;", s);
    }

    @Test
    public void generateDeleteStatementString() {
        JDBCRepository<User> userJDBCRepository = new JDBCRepository<>(User.class);
        String s = userJDBCRepository.generateDeleteStatementString(userJDBCRepository.getModelFields());
        assertEquals("DELETE FROM \"fub_user\" WHERE id = ?;", s);
    }

    @Test
    public void generateFindAllStatementString() {
        JDBCRepository<User> userJDBCRepository = new JDBCRepository<>(User.class);
        String s = userJDBCRepository.generateFindAllStatementString(userJDBCRepository.getModelFields());
        assertEquals("SELECT * FROM \"fub_user\";", s);
    }

    @Test
    public void put() {
        JDBCRepository<User> userJDBCRepository = new JDBCRepository<>(User.class);
        User user = new User("fireb2", "b2@ya.ru", "0000000000000000");
        User user2 = userJDBCRepository.put(user);
        user.setId(user2.getId());
        assertEquals(user, user2);
        User user3 = userJDBCRepository.get(user.getId());
        assertEquals(user2, user3);
    }

    @Test
    public void delete() {
        JDBCRepository<User> userJDBCRepository = new JDBCRepository<>(User.class);
        userJDBCRepository.delete(1);
        assertEquals(userJDBCRepository.get(1), null);
    }

    @Test
    public void findAll() {
        JDBCRepository<User> userJDBCRepository = new JDBCRepository<>(User.class);
        List<User> list = userJDBCRepository.findAll();
        assertArrayEquals(list.toArray(), new ArrayList<User>() {{
            add(new User("boris", "borisgk98@ya.ru", "1"));
            add(new User("fireb", "fireb98@ya.ru", "0000000000000000") {{
                setId(2);
            }});
        }}.toArray());
    }

    @Test
    public void get() {
        JDBCRepository<User> userJDBCRepository = new JDBCRepository<>(User.class);
        User user = userJDBCRepository.get(1);
        assertEquals(new User("boris", "borisgk98@ya.ru", "0000000000000000"), user);
    }
}