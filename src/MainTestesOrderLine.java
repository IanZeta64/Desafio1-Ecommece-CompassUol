import Entities.OrderLine;
import Entities.Product;
import config.DatabaseConfig;
import repositories.OrderLineRepository;
import repositories.impl.OrderLineRepositoryImpl;

import java.math.BigDecimal;

public class MainTestesOrderLine {
    public static void main(String[] args) {
        OrderLineRepository repository = new OrderLineRepositoryImpl(new DatabaseConfig());
        Product product =  new Product("Smart Watch", "Eletronic", BigDecimal.valueOf(500.00), 10);
        product.setId(4);
        OrderLine orderLine = new OrderLine(product, 2, 1);

        System.out.println(repository.insert(orderLine));
//        repository.selectAll().forEach(System.out::println);
//        orderLine.setQuantity(3);
//        System.out.println(repository.update(orderLine));
//        repository.deleteById(4);
        repository.selectAll().forEach(System.out::println);
    }
}
