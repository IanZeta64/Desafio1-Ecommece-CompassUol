import Entities.Customer;
import Enums.Payment;
import Services.*;
import Services.impl.*;
import config.DatabaseConfig;
import repositories.CustomerRepository;
import repositories.OrderLineRepository;
import repositories.OrderRepository;
import repositories.ProductRepository;
import repositories.impl.CustomerRepositoryImpl;
import repositories.impl.OrderLineRepositoryImpl;
import repositories.impl.OrderRepositoryImpl;
import repositories.impl.ProductRepositoryImpl;

public class MainTestesCartService {
    public static void main(String[] args) {
        DatabaseConfig dbc = new DatabaseConfig();
        OrderRepository orderRepository = new OrderRepositoryImpl(dbc);
        OrderLineRepository orderLineRepository = new OrderLineRepositoryImpl(dbc);
        ProductRepository productRepository = new ProductRepositoryImpl(dbc);
        CustomerRepository customerRepository = new CustomerRepositoryImpl(dbc);

        OrderService orderService = new OrderServiceImpl(orderRepository);
        OrderLineService orderLineService = new OrderLineServiceImpl(orderLineRepository);
        ProductService productService = new ProductServiceImpl(productRepository);
        CustomerService customerService = new CustomerServiceImpl(customerRepository);

        Customer customer = customerService.getById(1);
        CartService cartService = new CartServiceImpl(customer, orderService, orderLineService, productService);
        System.out.println(cartService.getCart());
//        cartService.addProduct(3, 1);
//        cartService.addProduct(4, 1);
//        System.out.println(cartService.getCart());
//        System.out.println(cartService.updateCartProduct(4, 5));
////        cartService.removeProduct(6, 2);
//        cartService.removeProduct(3, 1);
//        System.out.println(cartService.getCart());
////        cartService.clearCart();
        cartService.placeOrder(Payment.PIX);
//        System.out.println(cartService.getCart());

        cartService.getAllOrders().forEach(System.out::println);


    }
}
