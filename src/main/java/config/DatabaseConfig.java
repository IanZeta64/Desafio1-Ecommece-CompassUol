package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConfig {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/Ecommerce";
    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "3b3G+X8@vd?=G0p";

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
    }

    public void createTables() {
        try (Connection connection = getConnection(); Statement statement = connection.createStatement()){
            String sql_createTables = "CREATE TABLE IF NOT EXISTS customers (" +
                    "id SERIAL PRIMARY KEY," +
                    "name VARCHAR(255) NOT NULL," +
                    "birth_date DATE," +
                    "document VARCHAR(14)" +
                    ");" +
                    "CREATE TABLE IF NOT EXISTS products (" +
                    "id INTEGER," +
                    "name VARCHAR(255)," +
                    "category VARCHAR(255)," +
                    "price NUMERIC," +
                    "quantity INTEGER" +
                    ");" +
                    "CREATE TABLE IF NOT EXISTS order_lines (" +
                    "id SERIAL PRIMARY KEY," +
                    "product_id INTEGER NOT NULL," +
                    "quantity INTEGER," +
                    "final_price NUMERIC," +
                    "customer_id INTEGER," +
                    "ordered BOOLEAN," +
                    "order_id INTEGER" +
                    ")" +
                    "CREATE TABLE IF NOT EXISTS orders (" +
                    "id SERIAL PRIMARY KEY," +
                    "payment VARCHAR(255) NOT NULL," +
                    "customer_id INTEGER NOT NULL," +
                    "created_on TIMESTAMP," +
                    "final_price NUMERIC" +
                    ");";

            statement.executeUpdate(sql_createTables);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}