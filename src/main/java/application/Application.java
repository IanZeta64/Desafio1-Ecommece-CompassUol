package application;
import Services.*;
import Services.impl.*;
import config.DatabaseConfig;
import controllers.CartController;
import controllers.CustomerController;
import controllers.EmployeeController;
import controllers.ProductController;
import controllers.impl.CartControllerImpl;
import controllers.impl.CustomerControllerImpl;
import controllers.impl.EmployeeControllerImpl;
import controllers.impl.ProductControllerImpl;
import repositories.*;
import repositories.impl.*;
import view.AppMenuView;
import view.impl.AppMenuViewImpl;

public class Application {

    private final AppMenuView appMenuView;


    public Application(OrderRepository orderRepository, OrderLineRepository orderLineRepository,
                       ProductRepository productRepository, CustomerRepository customerRepository,
                       EmployeeRepository employeeRepository) {
        OrderService orderService = new OrderServiceImpl(orderRepository);
        OrderLineService orderLineService = new OrderLineServiceImpl(orderLineRepository);
        ProductService productService = new ProductServiceImpl(productRepository);
        CustomerService customerService = new CustomerServiceImpl(customerRepository);
        CartService cartService = new CartServiceImpl(orderService, orderLineService, productService);
        EmployeeService employeeService = new EmployeeServiceImpl(employeeRepository);

        ProductController productController = new ProductControllerImpl(productService);
        CustomerController customerController = new CustomerControllerImpl(customerService);
        CartController cartController = new CartControllerImpl(cartService);
        EmployeeController employeeController = new EmployeeControllerImpl(employeeService);
        this.appMenuView = new AppMenuViewImpl(cartController, productController, customerController, employeeController);
    }

    public void run() {
        this.appMenuView.menu();
    }

    public static void main(String[] args) {
        DatabaseConfig databaseConfig = new DatabaseConfig();
        OrderRepository orderRepository = new OrderRepositoryImpl(databaseConfig);
        OrderLineRepository orderLineRepository = new OrderLineRepositoryImpl(databaseConfig);
        ProductRepository productRepository = new ProductRepositoryImpl(databaseConfig);
        CustomerRepository customerRepository = new CustomerRepositoryImpl(databaseConfig);
        EmployeeRepository employeeRepository = new EmployeeRepositoryImpl(databaseConfig);
        databaseConfig.createTables();
        Application application = new Application(orderRepository, orderLineRepository, productRepository,
                customerRepository, employeeRepository);
        application.run();
    }
}