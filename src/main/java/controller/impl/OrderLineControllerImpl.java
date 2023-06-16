//package controller.impl;
//
//import Entities.OrderLine;
//import Entities.Product;
//import Services.OrderLineService;
//import Services.ProductService;
//import controller.OrderLineController;
//import utils.ConsoleUiHelper;
//
//import java.time.LocalDate;
//
//public class OrderLineControllerImpl implements OrderLineController {
//
//    private final OrderLineService orderLineService;
//    private final ProductService productService;
//
//    public OrderLineControllerImpl(OrderLineService orderLineService, ProductService productService) {
//        this.orderLineService = orderLineService;
//        this.productService = productService;
//    }
//
//    @Override
//    public void save() {
//        Integer productId = ConsoleUiHelper.askNumber("Enter a product id in order line");
//        Product product = productService.getById(productId);
//        Integer quantity = ConsoleUiHelper.askNumber("Enter a product quantity in order line");
//        Integer customerId = ConsoleUiHelper.askNumber("Enter a customer id in order line");
//        orderLineService.save(new OrderLine(product, quantity,customerId));
//    }
//
//    @Override
//    public void getAll() {
//
//    }
//
//    @Override
//    public void getById() {
//
//    }
//
//    @Override
//    public void update() {
//
//    }
//
//    @Override
//    public void delete() {
//
//    }
//
//    @Override
//    public void existById() {
//
//    }
//}
