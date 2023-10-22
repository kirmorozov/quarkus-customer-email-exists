package group.morozov.mc.rest.customer;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
// import org.mockito.MockitoExtension;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.MySQLContainer;

import java.util.Map;

@QuarkusTestResource(value = MySqlDatabaseTestResource.class)
public class MySqlDatabaseTestResource implements QuarkusTestResourceLifecycleManager {

    JdbcDatabaseContainer<?> dbServer;

    private static final String MY_SQL_IMAGE = "mysql:latest";
    private static final int MY_SQL_PORT = 3306;

    @Override
    public Map<String, String> start() {
        dbServer = new MySQLContainer<>(MY_SQL_IMAGE);
        dbServer.withInitScript("db.sql");
        dbServer.start();

        return Map.of(
                "quarkus.datasource.jdbc.url", dbServer.getJdbcUrl(),
                "quarkus.datasource.username", dbServer.getUsername(),
                "quarkus.datasource.password", dbServer.getPassword()
        );
    }

    @Override
    public synchronized void stop() {
        if (dbServer != null) {
            dbServer.stop();
            dbServer = null;
        }
    }
}