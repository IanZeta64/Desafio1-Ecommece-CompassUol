package Services;

import Entities.Product;

import java.util.List;

public interface ProductService extends GenericService<Product> {
    List<Product> searchByNameContainsString(String name);
}
