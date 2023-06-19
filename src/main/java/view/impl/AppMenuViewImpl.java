package view.impl;

import Entities.Customer;
import Entities.Employee;
import Enums.Role;
import controller.CartController;
import controller.CustomerController;
import controller.EmployeeController;
import controller.ProductController;
import domain.Login;
import exceptions.CustomerNotFoundException;
import exceptions.EmployeeNotFoundException;
import exceptions.NotAllowedException;
import utils.ConsoleUiHelper;
import view.AppMenuView;

public class AppMenuViewImpl implements AppMenuView {

    private final CartController cartController;
    private final ProductController productController;
    private final CustomerController customerController;
    private final EmployeeController employeeController;
    private char characterMenuStyle;

    public AppMenuViewImpl(CartController cartController, ProductController productController, CustomerController customerController, EmployeeController employeeController) {
        this.cartController = cartController;
        this.productController = productController;
        this.customerController = customerController;
        this.employeeController = employeeController;
    }

    @Override
    public void menu() {
        this.characterMenuStyle = ConsoleUiHelper.askCharacterInput("Enter the character to be used in the menu style");
            mainMenu();
    }

    private void mainMenu() {
        ConsoleUiHelper.drawHeader("WELCOME TO ECOMMERCE iJAVA", 100, characterMenuStyle);
        int chooseRole = ConsoleUiHelper.askChooseOption("You're a Customer or Employee?", "Customer", "Employee", "System Exit");
        switch (chooseRole) {
            case 1 -> cartMenu();
            case 2 -> employeeMenu();
            case 3 -> System.exit(0);
        }
    }

    public void cartMenu() {
        ConsoleUiHelper.drawHeader("CART", 100, characterMenuStyle);
        int choose = ConsoleUiHelper.askChooseOption("You're a new here?", "Yes, i want to register", "No, sign in", "Return to previous menu", "System Exit");
        switch (choose) {
            case 1 -> customerRegister();
            case 2 -> customerSignIn();
            case 3 -> mainMenu();
            case 4 -> System.exit(0);
        }
    }

    private void customerSignIn() {
        ConsoleUiHelper.drawWithPadding("SIGN IN - CUSTOMER", 100, characterMenuStyle);
        Login login = customerController.verifyRegister();
        if (login.verify()) {
            try {
                shopMenu(customerController.getByNameAndDocument(login.username(), login.password()));
            }catch (CustomerNotFoundException e){
                System.err.println(e.getMessage());
            }
        } else {
            System.out.println("Error validating information.");
            cartMenu();
        }
    }

    private void customerRegister() {
        ConsoleUiHelper.drawWithPadding("REGISTER - CUSTOMER", 100, characterMenuStyle);
        customerController.save();
        customerSignIn();
    }

    private void shopMenu(Customer customer) {
        ConsoleUiHelper.drawHeader("SHOP", 100, characterMenuStyle);
        int choose = ConsoleUiHelper.askChooseOption(String.format("What do you want to do, Mr./Ms %s?", customer.getName()),
                "View all products", "Search product by name", "Add product in cart", "Remove product in cart",
                "Update product in cart", "View cart", "Clear cart", "Place order", "View order line by id",
                "View order by id", "View all my orders", "Return to main menu", "System exit");
        switch (choose) {
            case 1 -> productController.getAll();
            case 2 -> productController.searchByNameContainsString();
            case 3 -> cartController.addProduct(customer.getId());
            case 4 -> cartController.removeProduct(customer.getId());
            case 5 -> cartController.updateCartProduct(customer.getId());
            case 6 -> cartController.getCart(customer.getId());
            case 7 -> cartController.clearCart(customer.getId());
            case 8 -> cartController.placeOrder(customer.getId());
            case 9 -> cartController.getOrderLineById(customer.getId());
            case 10 -> cartController.getOrderById(customer.getId());
            case 11 -> cartController.getAllOrders(customer.getId());
            case 12 -> mainMenu();
            case 13 -> System.exit(0);
        }
        shopMenu(customer);
    }
    public void employeeMenu() {
        ConsoleUiHelper.drawHeader("EMPLOYEE", 100, characterMenuStyle);
        int choose = ConsoleUiHelper.askChooseOption("Please, authenticate yourself as an employee","Sign in", "Return to previous menu", "System Exit");
        switch (choose) {
            case 1 -> employeeSignIn();
            case 2 -> mainMenu();
            case 3 -> System.exit(0);
        }
    }

    private void employeeSignIn() {
        ConsoleUiHelper.drawWithPadding("SIGN IN - EMPLOYEE", 100, characterMenuStyle);
        Login login = employeeController.verifyRegister();
        if (login.verify()) {
            try {
                managementMenu(employeeController.getByNameAndRegister(login.username(), login.password()));
            }catch (EmployeeNotFoundException e){
                System.err.println(e.getMessage());
            }
        } else {
            System.out.println("Error validating information.");
            employeeMenu();
        }
    }


    public void managementMenu(Employee employee) {
        ConsoleUiHelper.drawHeader("MAMAGEMENT", 100, characterMenuStyle);
        int chooseRole = ConsoleUiHelper.askChooseOption("What do you want to do?", "Management Customers",
                "Management Products", "Management employes", "Return to previous menu", "System exit");
        switch (chooseRole) {
            case 1 -> managementCustomersMenu(employee);
            case 2 -> managementProductsMenu(employee);
            case 3 -> managementEmployeeMenu(employee);
            case 4 -> mainMenu();
            case 5 -> System.exit(0);
        }
        managementMenu(employee);
    }

    private void managementEmployeeMenu(Employee employee) {
        try {
            if (employee.getRole().equals(Role.MANAGER)) {
                ConsoleUiHelper.drawWithPadding("PRODUCTS MANAGEMENT", 100, characterMenuStyle);
                int choose = ConsoleUiHelper.askChooseOption(String.format("What do you want to do, Mr./Ms %s?", employee.getName()),
                        "View all employees", "Search employee by name", "Register employee in database", "Remove employee in database",
                        "Update employee in database", "View employee by id",
                        "Return to previous menu", "System exit");
                switch (choose) {
                    case 1 -> employeeController.getAll();
                    case 2 -> employeeController.searchByNameContainsString();
                    case 3 -> employeeController.save();
                    case 4 -> employeeController.delete();
                    case 5 -> employeeController.update();
                    case 6 -> employeeController.getById();
                    case 7 -> managementMenu(employee);
                    case 8 -> System.exit(0);
                }
                managementEmployeeMenu(employee);
            } else {
                throw new NotAllowedException("Function not allowed for your role");

            }
        }catch (NotAllowedException e){
            System.err.println(e.getMessage());
        }

    }

    private void managementProductsMenu(Employee employee) {
        ConsoleUiHelper.drawWithPadding("PRODUCTS MANAGEMENT", 100, characterMenuStyle);
        int choose = ConsoleUiHelper.askChooseOption(String.format("What do you want to do, Mr./Ms %s?", employee.getName()),
                "View all products", "Search product by name", "Add product in stock", "Remove product in stock",
                "Update product in stock", "View product by id",
                "Return to previous menu", "System exit");
        switch (choose) {
            case 1 -> productController.getAll();
            case 2 -> productController.searchByNameContainsString();
            case 3 -> productController.save();
            case 4 -> productController.delete();
            case 5 -> productController.update();
            case 6 -> productController.getById();
            case 7 -> managementMenu(employee);
            case 8 -> System.exit(0);
        }
        managementProductsMenu(employee);
    }

    private void managementCustomersMenu(Employee employee) {
        ConsoleUiHelper.drawWithPadding("PRODUCTS MANAGEMENT", 100, characterMenuStyle);
        int choose = ConsoleUiHelper.askChooseOption(String.format("What do you want to do, Mr./Ms %s?", employee.getName()),
                "View all customers", "Search customer by name", "Add customer in database", "Remove customer in database",
                "Update customer in database", "View customer by id",
                "Return to previous menu", "System exit");
        switch (choose) {
            case 1 -> customerController.getAll();
            case 2 -> customerController.searchByNameContainsString();
            case 3 -> customerController.save();
            case 4 -> customerController.delete();
            case 5 -> customerController.update();
            case 6 -> customerController.getById();
            case 7 -> managementMenu(employee);
            case 8 -> System.exit(0);
        }
        managementCustomersMenu(employee);
    }
}

