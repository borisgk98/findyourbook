package rule;

import lombok.SneakyThrows;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import ru.yandex.qatools.embed.postgresql.EmbeddedPostgres;
import space.borisgk.findyourbook.connectionpull.ConnectionPool;

import java.io.File;
import java.lang.reflect.Field;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;

public class DbRule implements TestRule {
    protected static EmbeddedPostgres postgres = new EmbeddedPostgres();
    protected static String url;

    static {
        DbRule.init();
    }

    @SneakyThrows
    protected static void init() {
        DbRule.url = DbRule.postgres.start();
        Field conurl = ConnectionPool.class.getDeclaredField("connectionURL");
        conurl.setAccessible(true);
        conurl.set(ConnectionPool.class, DbRule.url);
        conurl.setAccessible(false);
    }

    @SneakyThrows
    @Override
    public Statement apply(final Statement statement, Description description) {
        return new Statement() {
            public void evaluate() throws Throwable {
                // Here is BEFORE_CODE
                // Создаеться для каждого теста новое
                Connection connection = ConnectionPool.getInstance().getConnection();
                String dir = System.getProperty("user.dir");
                DbRule.postgres.getProcess().get().importFromFile(Paths.get(dir).resolve("sql/create_fub.sql").toFile());
                DbRule.postgres.getProcess().get().importFromFile(Paths.get(dir).resolve("sql/insert.sql").toFile());
                try {
                    statement.evaluate();
                } finally {
                    // Here is AFTER_CODE
                    DbRule.postgres.getProcess().get().importFromFile(Paths.get(dir).resolve("sql/drop_fub.sql").toFile());
                    connection.close();
                    connection.close();
                }
            }
        };
    }
}
