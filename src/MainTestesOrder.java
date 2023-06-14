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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainTestesOrder {
    public static void main(String[] args) {
        OrderRepository repository = new OrderRepositoryImpl(new DatabaseConfig());
        OrderLineRepository orderLineRepository = new OrderLineRepositoryImpl(new DatabaseConfig());
        ProductRepository productRepository = new ProductRepositoryImpl(new DatabaseConfig());
//
//        Product product = new Product("Cellphone", "Eletronic", BigDecimal.valueOf(1000.00), 10);
//        product.setId(1);
        Product product2 = new Product("Smart TV", "Eletronic", BigDecimal.valueOf(2000.00), 10);
        product2.setId(3);
        Product product3 =  new Product("Smart Watch", "Eletronic", BigDecimal.valueOf(500.00), 10);
        product3.setId(4);
        Product product4 =  new Product("Xbox", "Eletronic", BigDecimal.valueOf(1200.00), 10);
        product4.setId(6);

        OrderLine orderLine = new OrderLine(product3, 5, 1);
        orderLine.setId(12);
        OrderLine orderLine2 = new OrderLine(product4, 10, 1);
        orderLine2.setId(11);
        //        OrderLine orderLine1 = new OrderLine(product3, 2, 1);

//        System.out.println(orderLineRepository.insert(orderLine2));
//        System.out.println(orderLineRepository.insert(orderLine));
        Set<OrderLine> orderLineList = new HashSet<>();
//        orderLineRepository.selectAll().forEach(System.out::println);
orderLineList.add(orderLine);
orderLineList.add(orderLine2);
//
        Order order = new Order(orderLineList, Payment.PIX, 1);
        order.setId(18);
//        System.out.println(repository.insert(order));
//        System.out.println("\n");
        repository.selectAll().forEach(System.out::println);

//        OrderLine orderLine3 = new OrderLine(product2, 1, 1);
//        orderLineList.
//            order.setCustomerId(10);
//            order.setPayment(Payment.CASH);
////        orderLineRepository.insert(orderLine3);
////        order.setOrderLineList();
////        repository.update(order)
//        System.out.println(repository.update(order)+"\n");
        System.out.println(repository.selectById(18));
    }
}
