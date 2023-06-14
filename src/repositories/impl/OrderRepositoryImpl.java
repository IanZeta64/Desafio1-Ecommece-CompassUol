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
import java.util.stream.Collectors;

public class OrderRepositoryImpl implements OrderRepository {

    private final DatabaseConfig databaseConfig;

    public OrderRepositoryImpl(DatabaseConfig databaseConfig) {
        this.databaseConfig = databaseConfig;
    }
    @Override
    public Order insert(Order order) {
        int generatedId = 0;
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
            System.out.println();
            try (PreparedStatement updateStatement = connection.prepareStatement("UPDATE order_lines SET order_id = ?, ordered = true WHERE customer_id = ? AND ordered = false")) {
                updateStatement.setInt(1, generatedId);
                updateStatement.setInt(2, order.getCustomerId());
                updateStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return order;
    }

    @Override
    public List<Order> selectAll() {
        List<Order> orders = new ArrayList<>();
        String sql_selectAll = "SELECT o.id AS order_id, o.payment, o.customer_id, o.created_on, o.final_price, " +
                "ol.id AS order_line_id, ol.product_id, ol.quantity, ol.final_price AS orderline_price, ol.ordered, " +
                "p.id AS product_id, p.name AS product_name, p.category AS product_category, p.price AS product_price, p.quantity AS product_quantity " +
                "FROM orders o " +
                "JOIN order_lines ol ON o.id = ol.order_id " +
                "JOIN products p ON ol.product_id = p.id" ;
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql_selectAll)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                Map<Integer, Order> orderMap = new HashMap<>();
                List<OrderLine> orderLineList = new ArrayList<>();
                Queue<List<OrderLine>> queueOrderLineList = new LinkedList<>();
                while (resultSet.next()) {
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
                    Order order = new Order(orderId, List.of(orderLine), payment, customerId, createdOn, orderFinalPrice);

                    if (orderMap.containsKey(orderId)) {
                        orderLineList.addAll(order.getOrderLineList());
                    } else {
                        queueOrderLineList.add(orderLineList);
                       orderLineList.clear();
                        orderMap.put(orderId, order);
                    }
                }
               orders = orderMap.values().stream().map(order -> new Order(order.getId(), queueOrderLineList.poll(), order.getPayment(), order.getCustomerId(), order.getCreatedOn(), order.getFinalPrice())).toList();
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Trate a exceção adequadamente
        }

        return orders;
    }

    @Override
    public Optional<Order> selectById(Integer id) {
        return Optional.empty();
    }

    @Override
    public Order update(Order order) {
        return null;
    }

    @Override
    public void deleteById(Integer id) {

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

//    private OrderLine getOrder(ResultSet resultSet) throws SQLException {
//        int id = resultSet.getInt("id");
//        int productId = resultSet.getInt("product_id");
//        String productName = resultSet.getString("product_name");
//        String productCategory = resultSet.getString("product_category");
//        BigDecimal productPrice = resultSet.getBigDecimal("product_price");
//        int productQuantity = resultSet.getInt("product_quantity");
//        int quantity = resultSet.getInt("quantity");
//        BigDecimal finalPrice = resultSet.getBigDecimal("final_price");
//        int customerId = resultSet.getInt("customer_id");
//        boolean ordered = resultSet.getBoolean("ordered");
//
//        Product product = new Product(productName, productCategory, productPrice, productQuantity);
//        product.setId(productId);
//        OrderLine orderLine = new OrderLine(id, product, quantity, finalPrice, customerId, ordered);
//        orderLine.setId(id);
//        return orderLine;
//    }
}

