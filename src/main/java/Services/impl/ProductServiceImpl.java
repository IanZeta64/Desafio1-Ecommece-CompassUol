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
        try {
            if (productRepository.selectAll().stream().anyMatch(prd -> prd.equals(product))) {
                throw new DuplicatedProductException("Product already saved in stock");
            }
            return productRepository.insert(product);
        } catch (DuplicatedProductException e) {
            System.err.println(e.getMessage());
        }
        return product;
    }

    @Override
    public List<Product> getAll() {
        return productRepository.selectAll();
    }

    @Override
    public Product getById(Integer id) throws ProductNotFoundException {
        return productRepository.selectById(id)
                .orElseThrow(() -> new ProductNotFoundException(String.format("Product not found by id: %s", id)));
    }

    @Override
    public List<Product> searchByNameContainsString(String name) {
        return productRepository.selectAll().stream().filter(product -> product.getName().toLowerCase().contains(name.toLowerCase())).toList();
    }

    @Override
    public Product update(Product product) {
        try {
            if (!existById(product.getId())) throw new ProductNotFoundException("Product not found, can't update");
            return productRepository.update(product);
        } catch (ProductNotFoundException e) {
            System.err.println(e.getMessage());
        }
        return product;
    }

    @Override
    public void delete(Integer id) {
        try {
            if (!existById(id)) throw new ProductNotFoundException("Product not found, can't delete");
            productRepository.deleteById(id);
        } catch (ProductNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public Boolean existById(Integer id) {
        long cont = productRepository.selectById(id).stream().count();
        return cont > 0;
    }

    public Boolean quantityInSotckAvaliable(Integer id, Integer quantity) {
        return productRepository.selectById(id).stream().anyMatch(product -> product.getQuantity() >= quantity);
    }
}
