package repositories;

import Entities.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends GenericRepository<Product> {
    Product save(Product product);
    List<Product> findAll();
    Optional<Product> getById(Integer id);
    Product update(Product product);
    void deleteById(Integer id);
}
