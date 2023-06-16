package controller;

import Entities.Customer;
import domain.Login;

public interface CustomerController extends GenericController<Customer>{
    void searchByNameContainsString();
    Login verifyRegister();

    Customer getByNameAndDocument(String name, String document);
}
