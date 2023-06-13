import Entities.OrderLine;
import Entities.Product;
import config.DatabaseConfig;
import repositories.OrderLineRepository;
import repositories.impl.OrderLineRepositoryImpl;

import java.math.BigDecimal;

public class MainTestesOrderLine {
    public static void main(String[] args) {
        OrderLineRepository repository = new OrderLineRepositoryImpl(new DatabaseConfig());
        Product product =  new Product("Smart TV", "Eletronic", BigDecimal.valueOf(2000.00), 10);
        product.setId(3);
        OrderLine orderLine = new OrderLine(product, 2, 1);

//        System.out.println(repository.save(orderLine));
        repository.findAll().forEach(System.out::println);
//        orderLine.setQuantity(3);
//        System.out.println(repository.update(orderLine));
        repository.deleteById(4);
        repository.findAll().forEach(System.out::println);
    }
}
