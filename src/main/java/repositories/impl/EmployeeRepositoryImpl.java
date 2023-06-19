package repositories.impl;
import Entities.Employee;
import Enums.Role;
import config.DatabaseConfig;
import repositories.EmployeeRepository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmployeeRepositoryImpl implements EmployeeRepository {

    private final DatabaseConfig databaseConfig;

    public EmployeeRepositoryImpl(DatabaseConfig databaseConfig) {
        this.databaseConfig = databaseConfig;
    }

    @Override
    public Employee insert(Employee employee) {
        String sql_insert = "INSERT INTO employees (name, register, role) VALUES (?, ?, ?)";
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql_insert, Statement.RETURN_GENERATED_KEYS)){
            setEmployee(employee, statement);
            affectedRowsVerify(statement, "Failed to save employee, no rows affected.");
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int generatedId = generatedKeys.getInt(1);
                System.out.println(generatedId);
                employee.setId(generatedId);
            } else {
                throw new SQLException("Failed to save employee, no ID obtained.");
            }
            generatedKeys.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return employee;
    }

    @Override
    public List<Employee> selectAll() {
         List<Employee> employees = new ArrayList<>();
        String sql_selectAll = "SELECT * FROM employees";
        try (Connection connection = databaseConfig.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql_selectAll)) {
            while (resultSet.next()) {
                Employee employee = getEmployee(resultSet);
                employees.add(employee);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return employees;
    }

    @Override
    public Optional<Employee> selectById(Integer id) {
        String sql_selectById = "SELECT * FROM employees WHERE id = ?";
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql_selectById)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Employee employee = getEmployee(resultSet);
                return Optional.of(employee);
            }
            resultSet.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public Employee update(Employee employee) {
        String sql_update = "UPDATE employees SET name = ?, register = ?, role = ? WHERE id = ?";
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql_update)) {
            setEmployee(employee, statement);
            statement.setInt(4, employee.getId());
            affectedRowsVerify(statement, "Failed to update employee, no rows affected.");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return employee;
    }

    @Override
    public void deleteById(Integer id) {
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM employees WHERE id = ?")) {
            statement.setInt(1, id);
            affectedRowsVerify(statement, "Failed to remove employee, no rows affected.");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
    private Employee getEmployee(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        String register = resultSet.getString("register");
        Role role = Role.valueOf(resultSet.getString("role"));
        return new Employee(id, name, register, role);
    }

    private void setEmployee(Employee employee, PreparedStatement statement) throws SQLException {
        statement.setString(1, employee.getName());
        statement.setString(2, employee.getRegister());
        statement.setString(3, employee.getRole().toString());
    }

    private  void affectedRowsVerify(PreparedStatement statement, String message) throws SQLException {
        int affectedRows = statement.executeUpdate();
        if (affectedRows == 0) {
            throw new SQLException(message);
        }
    }
}
