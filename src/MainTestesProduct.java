import Entities.Product;
import config.DatabaseConfig;
import repositories.ProductRepository;
import repositories.impl.ProductRepositoryImpl;

import java.math.BigDecimal;

public class MainTestesProduct {
    public static void main(String[] args) {
        ProductRepository repository = new ProductRepositoryImpl(new DatabaseConfig());

        Product product = new Product("Cellphone", "Eletronic", BigDecimal.TEN, 10);
        Product product2 = new Product("Smart TV", "Eletronic", BigDecimal.valueOf(2000.00), 10);

        repository.save(product2);

        var list = repository.findAll();
        System.out.println(list);
//        product.setId(1);
//        product.setPrice(BigDecimal.valueOf(1000.00));
//        repository.update(product);
        repository.deleteById(2);
         list = repository.findAll();
        System.out.println(list);



    }

}
