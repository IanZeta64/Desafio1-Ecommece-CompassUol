package application;

import Services.*;
import Services.impl.*;
import config.DatabaseConfig;
import controller.CartController;
import controller.CustomerController;
import controller.ProductController;
import controller.impl.CartControllerImpl;
import controller.impl.CustomerControllerImpl;
import controller.impl.ProductControllerImpl;
import repositories.CustomerRepository;
import repositories.OrderLineRepository;
import repositories.OrderRepository;
import repositories.ProductRepository;
import repositories.impl.CustomerRepositoryImpl;
import repositories.impl.OrderLineRepositoryImpl;
import repositories.impl.OrderRepositoryImpl;
import repositories.impl.ProductRepositoryImpl;
import view.AppMenuView;
import view.impl.AppMenuViewImpl;

public class Application {
    private final CartController cartController;
    private final ProductController productController;
    private final CustomerController customerController;

    public Application(
            OrderRepository orderRepository,
            OrderLineRepository orderLineRepository,
            ProductRepository productRepository,
            CustomerRepository customerRepository
    ) {
        OrderService orderService = new OrderServiceImpl(orderRepository);
        OrderLineService orderLineService = new OrderLineServiceImpl(orderLineRepository);
        ProductService productService = new ProductServiceImpl(productRepository);
        CustomerService customerService = new CustomerServiceImpl(customerRepository);
        CartService cartService = new CartServiceImpl(orderService, orderLineService, productService);

        this.productController = new ProductControllerImpl(productService);
        this.customerController = new CustomerControllerImpl(customerService);
        this.cartController = new CartControllerImpl(cartService);
    }

    public void run() {
        AppMenuView appMenuView = new AppMenuViewImpl(cartController, productController, customerController);
        appMenuView.menu();
    }

    public static void main(String[] args) {
        DatabaseConfig databaseConfig = new DatabaseConfig();
        OrderRepository orderRepository = new OrderRepositoryImpl(databaseConfig);
        OrderLineRepository orderLineRepository = new OrderLineRepositoryImpl(databaseConfig);
        ProductRepository productRepository = new ProductRepositoryImpl(databaseConfig);
        CustomerRepository customerRepository = new CustomerRepositoryImpl(databaseConfig);

        Application application = new Application(orderRepository, orderLineRepository, productRepository, customerRepository);
        application.run();
    }
}