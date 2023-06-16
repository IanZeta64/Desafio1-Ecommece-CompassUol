import Entities.Order;
import Entities.OrderLine;
import Entities.Product;
import Enums.Payment;
import config.DatabaseConfig;
import repositories.OrderLineRepository;
import repositories.OrderRepository;
import repositories.ProductRepository;
import repositories.impl.OrderLineRepositoryImpl;
import repositories.impl.OrderRepositoryImpl;
import repositories.impl.ProductRepositoryImpl;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class MainTestesRepositories {
    public static void main(String[] args) {
        DatabaseConfig databaseConfig = new DatabaseConfig();
        OrderLineRepository orderLineRepository = new OrderLineRepositoryImpl(databaseConfig);
        ProductRepository productRepository = new ProductRepositoryImpl((databaseConfig));
        OrderRepository orderRepository = new OrderRepositoryImpl(databaseConfig);

        System.out.println("PRODUTOS");
        Product product1 = new Product("Office Chair", "Office", BigDecimal.valueOf(750.00), 10);
        Product product2 = new Product("Office Desk", "Office", BigDecimal.valueOf(450.00), 10);
//        productRepository.insert(product1);
//        productRepository.insert(product2);
        var productList = productRepository.selectAll();
        product1.setId(7);
        product2.setId(8);
        productList.forEach(System.out::println);
        System.out.println("\n\n\n");

        System.out.println("ORDERLINE");
        OrderLine orderLine1 = new OrderLine(product1, 2, 1);
        OrderLine orderLine2 = new OrderLine(product2, 1, 1);
//        orderLineRepository.insert(orderLine1);
//        orderLineRepository.insert(orderLine2);
        var orderLineList = orderLineRepository.selectAll();
        int ol1Id = orderLineList.get(0).getId();
        orderLine1.setId(ol1Id);
        int ol2Id = orderLineList.get(1).getId();
        orderLine2.setId(ol2Id);
        orderLineList.forEach(System.out::println);
        System.out.println("\n\n\n");

        System.out.println("ORDER");
        Set<OrderLine> orderLineSet = new HashSet<>();
        orderLineSet.add(orderLine1);
        orderLineSet.add(orderLine2);
        Order order = new Order(orderLineSet, Payment.BANK_SLIP, 1);
        System.out.println(orderRepository.insert(order));
        System.out.println("\n\n\n");

        System.out.println("CONFERINDO ORDERLINE");
        orderLineRepository.selectAll().forEach(System.out::println);
        System.out.println("\n\n\n");

        System.out.println("CONFERINDO PRODUCTS");
        productRepository.selectAll().forEach(System.out::println);








    }
}
