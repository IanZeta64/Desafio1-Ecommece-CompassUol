package controller;

import Entities.Product;

import java.util.List;

public interface ProductController extends GenericController<Product> {
   void searchByNameContainsString();

}
