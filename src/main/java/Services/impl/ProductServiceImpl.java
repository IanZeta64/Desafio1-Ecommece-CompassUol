package Services.impl;

import Entities.Product;
import Services.ProductService;
import exceptions.DuplicatedProductException;
import exceptions.ProductNotFoundException;
import repositories.ProductRepository;

import java.util.List;

public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product save(Product product) {
        if (productRepository.selectAll().stream().anyMatch(prd -> prd.equals(product))) {
            throw new DuplicatedProductException("Product already saved in stock");
        }else{
            return productRepository.insert(product);
        }
    }

    @Override
    public List<Product> getAll() {
        return productRepository.selectAll();
    }

    @Override
    public Product getById(Integer id) {
        return productRepository.selectById(id).orElseThrow( () ->  new ProductNotFoundException(String.format("Product not found by id: %s", id)));
    }

    @Override
    public List<Product> searchByNameContainsString(String name) {
        return productRepository.selectAll().stream().filter(product -> product.getName().toLowerCase().contains(name.toLowerCase())).toList();
    }

    @Override
    public Product update(Product product) {
        if (existById(product.getId())) {
            return productRepository.update(product);
        }else {
            throw new ProductNotFoundException("Product not found, can't update");
        }
    }

    @Override
    public void delete(Integer id) {
        if (existById(id)) {
             productRepository.deleteById(id);
        }else {
            throw new ProductNotFoundException("Product not found, can't delete");
        }
    }
    @Override
    public Boolean existById(Integer id){
        long cont = productRepository.selectById(id).stream().count();
        return cont > 0;
        }
    public Boolean quantityInSotckAvaliable(Integer id, Integer quantity){
        return productRepository.selectById(id).stream().anyMatch(product -> product.getQuantity() >= quantity);
    }
}
