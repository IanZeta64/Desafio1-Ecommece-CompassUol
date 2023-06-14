package repositories.impl;
import Entities.Product;
import config.DatabaseConfig;
import repositories.ProductRepository;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductRepositoryImpl implements ProductRepository {

    private final DatabaseConfig databaseConfig;

    public ProductRepositoryImpl(DatabaseConfig databaseConfig) {
        this.databaseConfig = databaseConfig;
    }
    @Override
    public Product insert(Product product) {
        String sql_insert = "INSERT INTO products (name, category, price, quantity) VALUES (?, ?, ?, ?)";
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql_insert, Statement.RETURN_GENERATED_KEYS)){
            setProduct(product, statement);
            affectedRowsVerify(statement, "Failed to save customer, no rows affected.");
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int generatedId = generatedKeys.getInt(1);
                product.setId(generatedId);
            } else {
                throw new SQLException("Failed to save product, no ID obtained.");
            }
            generatedKeys.close();
        } catch (SQLException e) {
            throw new RuntimeException(e); // TRATAR EXCESSAO
        }
        return product;
    }



    @Override
    public List<Product> selectAll() {
        List<Product> products = new ArrayList<>();
        String sql_selectAll = "SELECT * FROM products";
        try (Connection connection = databaseConfig.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql_selectAll)) {
            while (resultSet.next()) {
                Product product = getProduct(resultSet);
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();// TRATAR EXCESSAO
        }
        return products;
    }



    @Override
    public Optional<Product> selectById(Integer id) {
        String sql_selectById = "SELECT * FROM products WHERE id = ?";
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql_selectById)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Product product = getProduct(resultSet);
                return Optional.of(product);
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();// TRATAR EXCESSAO
        }

        return Optional.empty();
    }


    @Override
    public Product update(Product product) {
        String sql_update = "UPDATE products SET name = ?, category = ?, price = ?, quantity = ? WHERE id = ?";
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql_update)) {
            setProduct(product, statement);
            statement.setInt(5, product.getId());
            affectedRowsVerify(statement, "Failed to update product, no rows affected.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    @Override
    public void deleteById(Integer id) {
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM products WHERE id = ?")) {
            statement.setInt(1, id);
            affectedRowsVerify(statement, "Failed to remove product, no rows affected.");
        } catch (SQLException e) {
            e.printStackTrace();/// TRATAR EXCEÇÃO
        }
    }
    private Product getProduct(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        String category = resultSet.getString("category");
        BigDecimal price = resultSet.getBigDecimal("price");
        Integer quantity = resultSet.getInt("quantity");

        Product product = new Product(name, category, price, quantity);
        product.setId(id);
        return product;
    }


    private void setProduct(Product product, PreparedStatement statement) throws SQLException {
        statement.setString(1, product.getName());
        statement.setString(2, product.getCategory());
        statement.setBigDecimal(3, product.getPrice());
        statement.setInt(4, product.getQuantity());
    }


    private static void affectedRowsVerify(PreparedStatement statement,String message) throws SQLException {
        int affectedRows = statement.executeUpdate();
        if (affectedRows <= 0) {
            throw new SQLException(message);
        }
    }

}
