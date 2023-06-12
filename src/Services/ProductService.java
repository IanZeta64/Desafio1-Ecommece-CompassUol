package Services;

import Entities.Product;

import java.util.List;

public interface ProductService {

    Product save(Product product);
    List<Product> getAll();
    Product getById(Integer id);
    List<Product> searchByNameContainsString(String name);
    Product update(Product product);
    void delete(Integer id);
}
