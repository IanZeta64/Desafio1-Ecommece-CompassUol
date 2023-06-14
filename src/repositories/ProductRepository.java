package repositories;

import Entities.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends GenericRepository<Product> {
    Product insert(Product product);
    List<Product> selectAll();
    Optional<Product> selectById(Integer id);
    Product update(Product product);
    void deleteById(Integer id);
}
