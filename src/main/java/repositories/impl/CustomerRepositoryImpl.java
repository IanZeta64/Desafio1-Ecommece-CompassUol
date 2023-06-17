package repositories.impl;

import Entities.Customer;
import config.DatabaseConfig;
import repositories.CustomerRepository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerRepositoryImpl implements CustomerRepository {
    private final DatabaseConfig databaseConfig;

    public CustomerRepositoryImpl(DatabaseConfig databaseConfig) {
        this.databaseConfig = databaseConfig;
    }

    @Override
    public Customer insert(Customer customer) {
        String sql_insert = "INSERT INTO customers (name, birth_date, document) VALUES (?, ?, ?)";
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql_insert, Statement.RETURN_GENERATED_KEYS)){
            setCustomer(customer, statement);
            affectedRowsVerify(statement, "Failed to save customer, no rows affected.");
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int generatedId = generatedKeys.getInt(1);
                customer.setId(generatedId);
            } else {
                throw new SQLException("Failed to save customer, no ID obtained.");
            }
            generatedKeys.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return customer;
    }

    @Override
    public List<Customer> selectAll() {
        List<Customer> customers = new ArrayList<>();
        String sql_selectAll = "SELECT * FROM customers";
        try (Connection connection = databaseConfig.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql_selectAll)) {
            while (resultSet.next()) {
                Customer customer = getCustomer(resultSet);
                customers.add(customer);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return customers;
    }

    @Override
    public Optional<Customer> selectById(Integer id) {
        String sql_selectById = "SELECT * FROM customers WHERE id = ?";
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql_selectById)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
               Customer customer = getCustomer(resultSet);
                return Optional.of(customer);
            }
            resultSet.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return Optional.empty();
    }

    @Override
    public Customer update(Customer customer) {
        String sql_update = "UPDATE customers SET name = ?, birth_date = ?, document = ? WHERE id = ?";
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql_update)) {
            setCustomer(customer, statement);
            statement.setInt(4, customer.getId());
            affectedRowsVerify(statement, "Failed to update customer, no rows affected.");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return customer;
    }

    @Override
    public void deleteById(Integer id) {
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM customers WHERE id = ?")) {
            statement.setInt(1, id);
            affectedRowsVerify(statement, "Failed to remove customer, no rows affected.");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    private Customer getCustomer(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        LocalDate birthDate = resultSet.getDate("birth_date").toLocalDate();
        String document = resultSet.getString("document");

        Customer customer = new Customer(name, birthDate, document);
        customer.setId(id);
        return customer;
    }

    private void setCustomer(Customer customer, PreparedStatement statement) throws SQLException {
        statement.setString(1, customer.getName());
        statement.setDate(2, Date.valueOf(customer.getBirthDate()));
        statement.setString(3, customer.getDocument());
    }

    private  void affectedRowsVerify(PreparedStatement statement, String message) throws SQLException {
        int affectedRows = statement.executeUpdate();
        if (affectedRows == 0) {
            throw new SQLException(message);
        }
    }
}
