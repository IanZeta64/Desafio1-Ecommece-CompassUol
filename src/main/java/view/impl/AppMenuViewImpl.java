package view.impl;

import Entities.Customer;
import controller.CartController;
import controller.CustomerController;
import controller.ProductController;
import domain.Login;
import utils.ConsoleUiHelper;
import view.AppMenuView;

public class AppMenuViewImpl implements AppMenuView {

    private final CartController cartController;

    private final ProductController productController;
    private final CustomerController customerController;

    private char characterMenuStyle;

    public AppMenuViewImpl(CartController cartController, ProductController productController, CustomerController customerController) {
        this.cartController = cartController;
        this.productController = productController;
        this.customerController = customerController;
    }

    @Override
    public void menu() {
        this.characterMenuStyle = ConsoleUiHelper.askCharacterInput("Enter the character to be used in the menu style");
        do {
            mainMenu();
        }while(true);
    }

    private void mainMenu() {
        ConsoleUiHelper.drawHeader("WELCOME TO ECOMMERCE iJAVA", 100, characterMenuStyle);
        int chooseRole = ConsoleUiHelper.askChooseOption("You're a Customer or Employee?", "Customer", "Employee", "System Exit");
        switch (chooseRole){
            case 1 -> cartMenu();
            case 2-> managementMenu();
            case 3 -> System.exit(0);
        }
    }

    public void cartMenu() {
        ConsoleUiHelper.drawHeader("SIGN IN", 100, characterMenuStyle);
        int choose = ConsoleUiHelper.askChooseOption("You're a new here?", "Yes", "No", "Return to previous menu", "System Exit");
        switch (choose){
            case 1 -> customerRegister();
            case 2 -> customerSignIn();
            case 3 -> mainMenu();
            case 4 -> System.exit(0);
        }
    }

    private void customerSignIn() {
        ConsoleUiHelper.drawWithPadding("SIGN IN", 100, characterMenuStyle);
       Login login = customerController.verifyRegister();
        if (login.verify()) {
            do {
                shopMenu(customerController.getByNameAndDocument(login.customerName(), login.customerDocument()));
            }while (true);
        }
        else {
            System.out.println("Error validating information.");
            cartMenu();
        }

    }
    private void customerRegister() {
        customerController.save();
    }

    private void shopMenu(Customer customer) {
        ConsoleUiHelper.drawHeader("CART", 100, characterMenuStyle);
        int choose =ConsoleUiHelper.askChooseOption(String.format("What do you want to do, Mr./Ms %s?", customer.getName()),
                "View all products", "Search product by name", "Add product in cart", "Remove product in cart",
                "Update product in cart", "View cart", "Clear cart", "Place order", "View all my orders",
                "Return to main menu", "System exit");
        switch (choose){
            case 1 -> productController.getAll();
            case 2 -> productController.searchByNameContainsString();
            case 3 -> cartController.addProduct(customer.getId());
            case 4 -> cartController.removeProduct(customer.getId());
            case 5 -> cartController.updateCartProduct(customer.getId());
            case 6 -> cartController.getCart(customer.getId());
            case 7 -> cartController.clearCart(customer.getId());
            case 8 -> cartController.placeOrder(customer.getId());
            case 9 -> cartController.getAllOrders(customer.getId());
            case 10 -> menu();
            case 11 -> System.exit(0);

        }
    }



    public void managementMenu() {
        ConsoleUiHelper.drawHeader("MAMAGEMENT", 100, characterMenuStyle);
        int chooseRole = ConsoleUiHelper.askChooseOption("What do you want to do?", "Management Customers",
                "Management Products", "Return to previous menu", "System exit");
        switch (chooseRole){
            case 1 -> managementCustomersMenu();
            case 2 -> managementProductsMenu();
            case 3 -> menu();
            case 4 -> System.exit(0);
        }
    }

    private void managementProductsMenu() {
        ConsoleUiHelper.drawWithPadding("PRODUCTS MANAGEMENT", 100, characterMenuStyle);
        String employee = "employee";
        int choose =ConsoleUiHelper.askChooseOption(String.format("What do you want to do, Mr./Ms %s?", employee),
                "View all products", "Search product by name", "Add product in stock", "Remove product in stock",
                "Update product in stock", "View product by id",
                "Return to previous menu", "System exit");
        switch (choose){
            case 1 -> productController.getAll();
            case 2 -> productController.searchByNameContainsString();
            case 3 -> productController.save();
            case 4 -> productController.delete();
            case 5 -> productController.update();
            case 6 -> productController.getById();
            case 7 -> managementMenu();
            case 8 -> System.exit(0);
        }
    }

    private void managementCustomersMenu() {
        ConsoleUiHelper.drawWithPadding("PRODUCTS MANAGEMENT", 100, characterMenuStyle);
        String employee = "employee";
        int choose =ConsoleUiHelper.askChooseOption(String.format("What do you want to do, Mr./Ms %s?", employee),
                "View all customers", "Search customer by name", "Add customer in database", "Remove customer in database",
                "Update customer in database", "View customer by id",
                "Return to previous menu", "System exit");
        switch (choose){
            case 1 -> customerController.getAll();
            case 2 -> customerController.searchByNameContainsString();
            case 3 -> customerController.save();
            case 4 -> customerController.delete();
            case 5 -> customerController.update();
            case 6 -> customerController.getById();
            case 7 -> managementMenu();
            case 8 -> System.exit(0);
        }

    }
}
