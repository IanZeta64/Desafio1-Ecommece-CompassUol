package controller.impl;
import Entities.Customer;
import Services.CustomerService;
import controller.CustomerController;
import domain.CustomerDto;
import domain.Login;
import exceptions.CustomerNotFoundException;
import utils.ConsoleUiHelper;
import java.time.LocalDate;

public class CustomerControllerImpl implements CustomerController {
    private final CustomerService customerService;

    public CustomerControllerImpl(CustomerService customerService) {
        this.customerService = customerService;
    }


    @Override
    public void save() {
        String name = ConsoleUiHelper.askSimpleInput("Enter a customer name ");
        String document = ConsoleUiHelper.askSimpleInput("Enter a customer document ");
        LocalDate birthDate = ConsoleUiHelper.askLocalDate("Enter a customer birth date", "dd/MM/yyyy");
        customerService.save(new Customer(name, birthDate,document));
    }

    @Override
    public void getAll() {
        ConsoleUiHelper.listCustomersPages(customerService.getAll(), 5);
    }

    @Override
    public void getById() {
        Integer id = ConsoleUiHelper.askNumber("Enter a id to search ");
        System.out.println(customerService.getById(id));
    }

    @Override
    public void update() {
        Integer id = ConsoleUiHelper.askNumber("Enter a id to be updated ");
        String name = ConsoleUiHelper.askSimpleInput("Enter a new customer name ");
        String document = ConsoleUiHelper.askSimpleInput("Enter a new customer document ");
        LocalDate birthDate = ConsoleUiHelper.askLocalDate("Enter a new customer birth date", "dd/MM/yyyy");
        customerService.update(new Customer(id, name, birthDate,document));
    }

    @Override
    public void delete() {
        Integer id = ConsoleUiHelper.askNumber("Enter a id to be deleted ");
        customerService.delete(id);
        System.out.printf("Customer %s deleted.%n", id);
    }

    @Override
    public void existById() {
        Integer id = ConsoleUiHelper.askNumber("Enter a customer id to be checked ");
        if (customerService.existById(id)) System.out.printf("Customer %s exists in database.%n", id);
        else  System.out.printf("Customer %s doesn't exists in database.%n", id);
    }

    @Override
    public void searchByNameContainsString() {
        String name = ConsoleUiHelper.askSimpleInput("Enter a customer name to search ");
        ConsoleUiHelper.listCustomersPages(customerService.searchByNameContainsString(name), 5);
    }

    @Override
    public Login verifyRegister() {
        String name = ConsoleUiHelper.askSimpleInput("Enter a customer name to sign in");
        String document = ConsoleUiHelper.askSimpleInput("Enter a customer document to sign in");
        return new Login(customerService.verifyRegister(name, document), name, document);
    }

    @Override
    public Customer getByNameAndDocument(String name, String document) {
        return customerService.getAll().stream().filter(customer ->  customer.getDocument().equals(document) && customer.getName().equals(name))
                .findFirst().orElseThrow( () -> new CustomerNotFoundException("Customer not found in database."));
    }
}
