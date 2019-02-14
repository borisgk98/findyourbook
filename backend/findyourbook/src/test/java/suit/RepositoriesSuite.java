package suit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import space.borisgk.findyourbook.repository.JDBCRepositoryTest;

public class RepositoriesSuite {
    @Suite.SuiteClasses({ JDBCRepositoryTest.class })
    @RunWith(Suite.class)
    public class JUnit4TestSuite {
    }
}