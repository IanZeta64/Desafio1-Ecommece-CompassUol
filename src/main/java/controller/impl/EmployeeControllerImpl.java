package controller.impl;
import Entities.Employee;
import Enums.Role;
import Services.EmployeeService;
import controller.EmployeeController;
import domain.Login;
import exceptions.EmployeeNotFoundException;
import utils.ConsoleUiHelper;
import java.util.UUID;

public class EmployeeControllerImpl implements EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeControllerImpl(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public void save() {
        String name = ConsoleUiHelper.askSimpleInput("Enter a employee name ");
        Role role = getRole();
        employeeService.save(new Employee(name, role));
    }



    @Override
    public void getAll() {
        ConsoleUiHelper.listEmployeesPages(employeeService.getAll(), 5);

    }

    @Override
    public void getById() {
        try {
            Integer id = ConsoleUiHelper.askNumber("Enter a id to search ");
            System.out.println(employeeService.getById(id));
        }catch (EmployeeNotFoundException e){
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void update() {
        Integer id = ConsoleUiHelper.askNumber("Enter a id to be updated ");
        String name = ConsoleUiHelper.askSimpleInput("Enter a new Employee name ");
        Role role = getRole();
        employeeService.update(new Employee(id, name, UUID.randomUUID().toString(), role));
    }

    @Override
    public void delete() {
        Integer id = ConsoleUiHelper.askNumber("Enter a id to be deleted ");
        if (id > 1) {
            employeeService.delete(id);
            System.out.printf("Employee %s deleted.%n", id);
        }
    }

    private Role getRole() {
        int choose = ConsoleUiHelper.askChooseOption("Enter a employee role", "Manager", "Seller");
        Role role = null;
        switch (choose){
            case 1 ->  role = Role.MANAGER;
            case 2 -> role = Role.SELLER;
        }
        return role;
    }

    @Override
    public void searchByNameContainsString() {
        String name = ConsoleUiHelper.askSimpleInput("Enter a employee name to search ");
        ConsoleUiHelper.listEmployeesPages(employeeService.searchByNameContainsString(name), 5);
    }

    @Override
    public Login verifyRegister() {
        String name = ConsoleUiHelper.askSimpleInput("Enter a employee name to sign in");
        String register = ConsoleUiHelper.askSimpleInput("Enter a employee register value to sign in");
        return new Login(employeeService.verifyRegister(name, register), name, register);
    }

    @Override
    public Employee getByNameAndRegister(String name, String register) throws EmployeeNotFoundException{
        return employeeService.getAll().stream().filter(employee ->  employee.getRegister().equals(register) && employee.getName().equals(name))
                .findFirst().orElseThrow( () -> new EmployeeNotFoundException("Employee not found in database."));
    }
}
