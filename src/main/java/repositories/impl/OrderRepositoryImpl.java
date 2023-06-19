package repositories.impl;
import Entities.Order;
import Entities.OrderLine;
import Entities.Product;
import Enums.Payment;
import config.DatabaseConfig;
import repositories.OrderRepository;
import java.math.BigDecimal;
import java.sql.*;
import java.time.Instant;
import java.util.*;


public class OrderRepositoryImpl implements OrderRepository {

    private final DatabaseConfig databaseConfig;

    public OrderRepositoryImpl(DatabaseConfig databaseConfig) {
        this.databaseConfig = databaseConfig;
    }
    @Override
    public Order insert(Order order) {
        int generatedId;
        String sql_insert = "INSERT INTO orders (payment, customer_id, created_on, final_price) " +
                "VALUES (?, ?, ?, ?)";
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql_insert, Statement.RETURN_GENERATED_KEYS)) {
            setOrder(order, statement);
            affectedRowsVerify(statement, "Failed to save order, no rows affected.");
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                generatedId = generatedKeys.getInt(1);
                order.setId(generatedId);
            } else {
                throw new SQLException("Failed to save order, no ID obtained.");
            }

            try (PreparedStatement updateStatement = connection.prepareStatement("UPDATE order_lines SET order_id = ?," +
                    " ordered = true WHERE customer_id = ? AND ordered = false")) {
                updateStatement.setInt(1, generatedId);
                updateStatement.setInt(2, order.getCustomerId());
                updateStatement.executeUpdate();
            }
            try (PreparedStatement updateStatement = connection.prepareStatement("UPDATE products SET quantity = quantity - ?" +
                    " WHERE id = ? ")) {

                for (OrderLine orderline: order.getOrderLineList()) {
                updateStatement.setInt(1, orderline.getQuantity());
                updateStatement.setInt(2, orderline.getProduct().getId());
                updateStatement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return order;
    }
    @Override
    public List<Order> selectAll() {
        List<Order> orders = new ArrayList<>();
        Map<Integer, Order> orderMap = new HashMap<>();
        Map<Integer, Set<OrderLine>> orderLineListMap = new HashMap<>();
        String sql_selectAll = "SELECT o.id AS order_id, o.payment, o.customer_id, o.created_on, o.final_price, " +
                "ol.id AS order_line_id, ol.product_id, ol.quantity, ol.final_price AS orderline_price, ol.ordered, " +
                "p.id AS product_id, p.name AS product_name, p.category AS product_category, p.price AS product_price, p.quantity AS product_quantity " +
                "FROM orders o " +
                "JOIN order_lines ol ON o.id = ol.order_id " +
                "JOIN products p ON ol.product_id = p.id" ;
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql_selectAll); ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Order order = getOrder(resultSet);
                int orderId = order.getId();

                if (orderMap.containsKey(orderId)) {
                    Set<OrderLine> existingOrderLineList = orderLineListMap.get(orderId);
                    Set<OrderLine> updatedOrderLineList = new HashSet<>(existingOrderLineList);
                    updatedOrderLineList.addAll(order.getOrderLineList());
                    orderLineListMap.put(orderId, updatedOrderLineList);
                } else {
                    orderMap.put(orderId, order);
                    orderLineListMap.put(orderId, new HashSet<>(order.getOrderLineList()));
                }
            }
            orders = orderMap.entrySet().stream().map( entryMap -> {
                 entryMap.getValue().setOrderLineList(orderLineListMap.get(entryMap.getKey()));
                 return entryMap.getValue();
            }).toList();


        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return orders;
    }

    @Override
    public Optional<Order> selectById(Integer id) {
        Map<Integer, Order> orderMap = new HashMap<>();
        Map<Integer, Set<OrderLine>> orderLineListMap = new HashMap<>();
        String sql_selectById =  "SELECT o.id AS order_id, o.payment, o.customer_id, o.created_on, o.final_price, " +
                "ol.id AS order_line_id, ol.product_id, ol.quantity, ol.final_price AS orderline_price, ol.ordered, " +
                "p.id AS product_id, p.name AS product_name, p.category AS product_category, p.price AS product_price, p.quantity AS product_quantity " +
                "FROM orders o " +
                "JOIN order_lines ol ON o.id = ol.order_id " +
                "JOIN products p ON ol.product_id = p.id " +
                "WHERE o.id = ?";

        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql_selectById)) {
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    Order order = getOrder(resultSet);
                    int orderId = order.getId();

                    if (orderMap.containsKey(orderId)) {
                        Set<OrderLine> existingOrderLineList = orderLineListMap.get(orderId);
                        Set<OrderLine> updatedOrderLineList = new HashSet<>(existingOrderLineList);
                        updatedOrderLineList.addAll(order.getOrderLineList());
                        orderLineListMap.put(orderId, updatedOrderLineList);
                    } else {
                        orderMap.put(orderId, order);
                        orderLineListMap.put(orderId, new HashSet<>(order.getOrderLineList()));
                    }
                }
                resultSet.close();
                return orderMap.entrySet().stream().map( entryMap -> {
                    entryMap.getValue().setOrderLineList(orderLineListMap.get(entryMap.getKey()));
                    return entryMap.getValue();
                }).findFirst();

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return Optional.empty();
    }


    @Override
    public Order update(Order order) {
        String sql_update = "UPDATE orders SET payment = ?, customer_id = ?, created_on = ?, final_price = ? WHERE id = ?";
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql_update)) {
            setOrder(order, statement);
            statement.setInt(5, order.getId());
            affectedRowsVerify(statement, "Failed to update order line, no rows affected.");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return order;

    }

    @Override
    public void deleteById(Integer id) {
        String sql_delete =  "DELETE FROM orders WHERE id = ?";
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql_delete)) {
            statement.setInt(1, id);
            affectedRowsVerify(statement, "Failed to remove order, no rows affected.");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }


    private void affectedRowsVerify(PreparedStatement statement, String message) throws SQLException {
        int affectedRows = statement.executeUpdate();
        if (affectedRows == 0) {
            throw new SQLException(message);
        }
    }
    private  void setOrder(Order order, PreparedStatement statement) throws SQLException {
        statement.setString(1, order.getPayment().toString());
        statement.setInt(2, order.getCustomerId());
        statement.setTimestamp(3, Timestamp.from(order.getCreatedOn()));
        statement.setBigDecimal(4, order.getFinalPrice());
    }

    private Order getOrder(ResultSet resultSet) throws SQLException {
       int orderId = resultSet.getInt("order_id");
                    int orderLineId = resultSet.getInt("order_line_id");
                    int productId = resultSet.getInt("product_id");

                    String productName = resultSet.getString("product_name");
                    String produtCategory = resultSet.getString("product_category");
                    BigDecimal productPrice= resultSet.getBigDecimal("product_price");
                    Integer productQuantity = resultSet.getInt("product_quantity");
                    Product product = new Product(productName, produtCategory, productPrice, productQuantity);
                    product.setId(productId);


                    Integer customerId = resultSet.getInt("customer_id");
                    Integer quantity = resultSet.getInt("quantity");
                    BigDecimal lineFinalPrice = resultSet.getBigDecimal("orderline_price");
                    Boolean ordered = resultSet.getBoolean("ordered");
                    OrderLine orderLine = new OrderLine(orderLineId, product ,quantity, lineFinalPrice, customerId, ordered);


                    Payment payment = Payment.valueOf(resultSet.getString("payment"));
                    Instant createdOn = resultSet.getTimestamp("created_on").toInstant();
                    BigDecimal orderFinalPrice = resultSet.getBigDecimal("final_price");
                    return new Order(orderId, Set.of(orderLine), payment, customerId, createdOn, orderFinalPrice);

    }
}

