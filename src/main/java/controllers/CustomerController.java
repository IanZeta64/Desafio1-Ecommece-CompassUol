package controllers;
import Entities.Customer;
import domain.Login;

public interface CustomerController extends GenericController{
    void searchByNameContainsString();
    Login verifyRegister();
    Customer getByNameAndDocument(String name, String document);
}
