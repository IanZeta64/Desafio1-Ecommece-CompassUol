import Entities.Customer;
import Services.*;
import Services.impl.*;
import config.DatabaseConfig;
import controller.CartController;
import controller.impl.CartControllerImpl;
import repositories.CustomerRepository;
import repositories.OrderLineRepository;
import repositories.OrderRepository;
import repositories.ProductRepository;
import repositories.impl.CustomerRepositoryImpl;
import repositories.impl.OrderLineRepositoryImpl;
import repositories.impl.OrderRepositoryImpl;
import repositories.impl.ProductRepositoryImpl;

public class MainTesteCarController {
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

        CartController cartController = new CartControllerImpl(cartService);

        cartController.getAllOrders();
//        System.out.println();
        cartController.getAllProducts();
//        cartController.searchProduct();
//        cartController.addProduct();
//        cartController.updateCartProduct();
//        cartController.placeOrder();
        cartController.getCart();

    }
}
