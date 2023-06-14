import Entities.Order;
import Entities.OrderLine;
import Enums.Payment;
import config.DatabaseConfig;
import repositories.OrderLineRepository;
import repositories.OrderRepository;
import repositories.impl.OrderLineRepositoryImpl;
import repositories.impl.OrderRepositoryImpl;
import java.util.ArrayList;
import java.util.List;

public class MainTestesOrder {
    public static void main(String[] args) {
        OrderRepository repository = new OrderRepositoryImpl(new DatabaseConfig());
        OrderLineRepository orderLineRepository = new OrderLineRepositoryImpl(new DatabaseConfig());
//
//        Product product = new Product("Cellphone", "Eletronic", BigDecimal.valueOf(1000.00), 10);
//        product.setId(1);
//        Product product2 = new Product("Smart TV", "Eletronic", BigDecimal.valueOf(2000.00), 10);
//        product2.setId(3);
//        Product product3 =  new Product("Smart Watch", "Eletronic", BigDecimal.valueOf(500.00), 10);
//        product3.setId(4);
//        OrderLine orderLine = new OrderLine(product, 5, 1);
//        OrderLine orderLine1 = new OrderLine(product3, 2, 1);
//        OrderLine orderLine2 = new OrderLine(product2, 10, 1);
//        orderLineRepository.insert(orderLine2);
        List<OrderLine> orderLineList = new ArrayList<>();
//        orderLineList.add(orderLine);
//        orderLineList.add(orderLine1);
//        orderLineList.add(orderLine2);
        orderLineList = orderLineRepository.selectAll();
        Order order = new Order(orderLineList, Payment.CREDIT_CARD, 1);

        repository.insert(order);
        repository.selectAll().forEach(System.out::println);
    }
}
