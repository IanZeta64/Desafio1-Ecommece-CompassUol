package Services;
import Entities.Employee;
import java.util.List;

public interface EmployeeService extends GenericService<Employee> {
    List<Employee> searchByNameContainsString(String name);

    Boolean verifyRegister(String name, String document);
}
