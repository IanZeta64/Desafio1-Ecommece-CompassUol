package controllers;
import Entities.Employee;
import domain.Login;

public interface EmployeeController extends GenericController {
    void searchByNameContainsString();
    Login verifyRegister();
    Employee getByNameAndRegister(String name, String register);
}
