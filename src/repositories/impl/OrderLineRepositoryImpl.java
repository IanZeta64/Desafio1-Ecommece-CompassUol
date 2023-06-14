package repositories.impl;

import Entities.OrderLine;
import Entities.Product;
import config.DatabaseConfig;
import repositories.OrderLineRepository;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderLineRepositoryImpl implements OrderLineRepository {

    private final DatabaseConfig databaseConfig;

    public OrderLineRepositoryImpl(DatabaseConfig databaseConfig) {
        this.databaseConfig = databaseConfig;
    }
    @Override
    public OrderLine insert(OrderLine orderLine) {
        String sql_insert = "INSERT INTO order_lines (product_id, quantity, final_price, customer_id, ordered) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql_insert, Statement.RETURN_GENERATED_KEYS)){
            setOrderLine(orderLine, statement);
            affectedRowsVerify(statement, "Failed to save order line, no rows affected.");
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int generatedId = generatedKeys.getInt(1);
                orderLine.setId(generatedId);
            } else {
                throw new SQLException("Failed to save order line, no ID obtained.");
            }
            generatedKeys.close();
        } catch (SQLException e) {
            throw new RuntimeException(e); // TRATAR EXCESSAO
        }
        return orderLine;
    }


    @Override
    public List<OrderLine> selectAll() {
        List<OrderLine> orderLineList = new ArrayList<>();
        String sql_selectAll = "SELECT ol.id, p.id AS product_id, p.name AS product_name, p.category AS Product_category," +
                " p.price AS product_price, p.quantity AS product_quantity, ol.quantity, ol.final_price, ol.customer_id, ol.ordered " +
                "FROM order_lines ol " +
                "JOIN products p ON ol.product_id = p.id ";
        try (Connection connection = databaseConfig.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql_selectAll)) {
            while (resultSet.next()) {
                OrderLine orderLine = getOrderLine(resultSet);
                orderLineList.add(orderLine);
            }
        } catch (SQLException e) {
            e.printStackTrace();// TRATAR EXCESSAO
        }
        return orderLineList;
    }



    @Override
    public Optional<OrderLine> selectById(Integer id) {
        String sql_selectById = "SELECT * FROM order_lines WHERE id = ? ";
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql_selectById)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                OrderLine orderLine = getOrderLine(resultSet);
                return Optional.of(orderLine);
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();// TRATAR EXCESSAO
        }
        return Optional.empty();
    }

    @Override
    public OrderLine update(OrderLine orderLine) {
        String sql_update = "UPDATE order_lines SET product_id = ?, quantity = ?, final_price = ?, customer_id = ?," +
                " ordered = ? WHERE id = ?";
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql_update)) {
            setOrderLine(orderLine, statement);
            statement.setInt(6, orderLine.getId());
            affectedRowsVerify(statement, "Failed to update order line, no rows affected.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderLine;
    }

    @Override
    public void deleteById(Integer id) {
        String sql_delete =  "DELETE FROM order_lines WHERE id = ?";
    try (Connection connection = databaseConfig.getConnection();
    PreparedStatement statement = connection.prepareStatement(sql_delete)) {
        statement.setInt(1, id);
        affectedRowsVerify(statement, "Failed to remove order line, no rows affected.");
    } catch (SQLException e) {
        e.printStackTrace();/// TRATAR EXCEÇÃO
    }
}


    private void affectedRowsVerify(PreparedStatement statement, String message) throws SQLException {
        int affectedRows = statement.executeUpdate();
        if (affectedRows == 0) {
            throw new SQLException(message);
        }
    }
    private  void setOrderLine(OrderLine orderLine, PreparedStatement statement) throws SQLException {
        statement.setInt(1, orderLine.getProduct().getId());
        statement.setInt(2, orderLine.getQuantity());
        statement.setBigDecimal(3, orderLine.getFinalPrice());
        statement.setInt(4, orderLine.getCustomerId());
        statement.setBoolean(5, orderLine.getOrdered());
    }

    private OrderLine getOrderLine(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        int productId = resultSet.getInt("product_id");
        String productName = resultSet.getString("product_name");
        String productCategory = resultSet.getString("product_category");
        BigDecimal productPrice = resultSet.getBigDecimal("product_price");
        int productQuantity = resultSet.getInt("product_quantity");
        int quantity = resultSet.getInt("quantity");
        BigDecimal finalPrice = resultSet.getBigDecimal("final_price");
        int customerId = resultSet.getInt("customer_id");
        boolean ordered = resultSet.getBoolean("ordered");

        Product product = new Product(productName, productCategory, productPrice, productQuantity);
        product.setId(productId);
        OrderLine orderLine = new OrderLine(id, product, quantity, finalPrice, customerId, ordered);
        orderLine.setId(id);
        return orderLine;
    }
}
