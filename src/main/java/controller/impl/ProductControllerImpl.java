package controller.impl;

import Entities.Product;
import Services.ProductService;
import controller.ProductController;
import exceptions.ProductNotFoundException;
import utils.ConsoleUiHelper;

import java.math.BigDecimal;
import java.util.List;

public class ProductControllerImpl implements ProductController {

    private final ProductService productService;

    public ProductControllerImpl(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public void save() {
        String name = ConsoleUiHelper.askSimpleInput("Enter a product name ");
        String category = ConsoleUiHelper.askSimpleInput("Enter a product category ");
        BigDecimal price = BigDecimal.valueOf(ConsoleUiHelper.askNumber("Enter a product price "));
        Integer quantity = ConsoleUiHelper.askNumber("Enter a product quantity in stock ");
         productService.save(new Product(name, category, price, quantity));
    }

    @Override
    public void getAll() {
       ConsoleUiHelper.listProductPages(productService.getAll(), 5);
    }

    @Override
    public void getById() {
        try {
            Integer id = ConsoleUiHelper.askNumber("Enter product id ");
            System.out.println(productService.getById(id));
        }catch (ProductNotFoundException e){
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void update() {
        Integer id = ConsoleUiHelper.askNumber("Enter a product ID to be updated ");
        String name = ConsoleUiHelper.askSimpleInput("Enter a new product name ");
        String category = ConsoleUiHelper.askSimpleInput("Enter a new product category ");
        BigDecimal price = BigDecimal.valueOf(ConsoleUiHelper.askNumber("Enter a new product price "));
        Integer quantity = ConsoleUiHelper.askNumber("Enter a  new product quantity in stock ");
        productService.update(new Product(id, name, category, price, quantity));
    }

    @Override
    public void delete() {
        Integer id = ConsoleUiHelper.askNumber("Enter a product ID to be deleted ");
        productService.delete(id);
        System.out.printf("Product %s deleted.%n", id);
    }

    @Override
    public void existById() {
        Integer id = ConsoleUiHelper.askNumber("Enter a product ID to be checked ");
        if (productService.existById(id)) System.out.printf("Product %s exists in stock.%n", id);
        else  System.out.printf("Product %s doesn't exists in stock.%n", id);

    }

    @Override
    public void searchByNameContainsString() {
        String name = ConsoleUiHelper.askSimpleInput("Enter a product name to search ");
        ConsoleUiHelper.listProductPages(productService.searchByNameContainsString(name), 5);
    }

}
