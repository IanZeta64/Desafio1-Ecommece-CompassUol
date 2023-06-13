package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/Ecommerce";
    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "3b3G+X8@vd?=G0p";

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
    }
}