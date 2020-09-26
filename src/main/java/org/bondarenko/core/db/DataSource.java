package org.bondarenko.core.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DataSource {
    private final HikariDataSource ds;

    public DataSource() {
        String dbUrl = System.getenv("DATABASE_URL");
        URI dbUri = urlToUri(dbUrl);

        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];
        String url = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);
        config.setMaximumPoolSize(20);
        config.setDriverClassName("org.postgresql.Driver");

        ds = new HikariDataSource(config);
    }

    private URI urlToUri(String dbUrl) {
        try {
            return new URI(dbUrl);
        } catch (URISyntaxException e) {
            throw new RuntimeException("database URL is invalid", e);
        }
    }

    public Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}