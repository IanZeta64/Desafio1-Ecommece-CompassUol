package Services.impl;
import Entities.Employee;
import Services.EmployeeService;
import exceptions.DuplicatedEmployeeException;
import exceptions.EmployeeNotFoundException;
import repositories.EmployeeRepository;
import java.util.List;

public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee save(Employee employee) {
        try {
            if (employeeRepository.selectAll().stream().anyMatch(empl -> empl.equals(employee))) {
                throw new DuplicatedEmployeeException("Employee already registered, not saved in database.");
            }
            return employeeRepository.insert(employee);
        } catch (DuplicatedEmployeeException e) {
            System.err.println(e.getMessage());
        }
        return employee;
    }

    @Override
    public List<Employee> getAll() {
        return employeeRepository.selectAll();
    }

    @Override
    public Employee getById(Integer id) throws EmployeeNotFoundException{
        return employeeRepository.selectById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(String.format("Employee not found by id: %s", id)));
    }

    @Override
    public Employee update(Employee employee) {
        try {
            if (!existById(employee.getId())) throw new EmployeeNotFoundException("Employee not found, can't update.");
            return employeeRepository.update(employee);
        } catch (EmployeeNotFoundException e) {
            System.err.println(e.getMessage());
        }
        return employee;
    }

    @Override
    public void delete(Integer id) {
        try {
            if (!existById(id)) throw new EmployeeNotFoundException("Employee not found, can't delete.");
            employeeRepository.deleteById(id);
        } catch (EmployeeNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public Boolean existById(Integer id) {
        long cont = employeeRepository.selectById(id).stream().count();
        return cont > 0;
    }

    @Override
    public List<Employee> searchByNameContainsString(String name) {
        return employeeRepository.selectAll().stream().filter(employee -> employee.getName().contains(name)).toList();
    }

    @Override
    public Boolean verifyRegister(String name, String register) {
        return employeeRepository.selectAll().stream()
                .anyMatch(employee -> employee.getName().equalsIgnoreCase(name) && employee.getRegister().equals(register));
    }
}
