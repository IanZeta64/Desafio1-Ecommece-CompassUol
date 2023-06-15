package Services.impl;

import Entities.Customer;
import Entities.Order;
import Entities.OrderLine;
import Entities.Product;
import Enums.Payment;
import Services.CartService;
import Services.OrderLineService;
import Services.OrderService;
import Services.ProductService;
import exceptions.OrderLineNotFoundException;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class CartServiceImpl implements CartService {

    private final Customer customer;

    private final OrderService orderService;
    private final OrderLineService orderLineService;
    private final ProductService productService;

    public CartServiceImpl(Customer customer, OrderService orderService, OrderLineService orderLineService, ProductService productService) {
        this.customer = customer;
        this.orderService = orderService;
        this.orderLineService = orderLineService;
        this.productService = productService;
    }

    @Override
    public OrderLine addProduct(Integer productId, Integer quantity) {
            Product product = productService.getById(productId);
            OrderLine orderLine = new OrderLine(product, quantity, customer.getId());
            if (orderLineService.existByProductId(productId)){
                Integer quantityFromOrderLineSearched = getOrderLineByProductId(productId).getQuantity();
                orderLine.setQuantity(orderLine.getQuantity() + quantityFromOrderLineSearched);
              return orderLineService.update(orderLine);
            }else{
                return orderLineService.save(orderLine);
            }
    }

    @Override
    public Set<OrderLine> getCart() {
        return getOrderLinesByCustomerIdAndNotOrdered();
    }

    @Override
    public OrderLine updateCartProduct(Integer productId, Integer quantity) {
        OrderLine orderLine = getOrderLineByProductId(productId);
        orderLine.setQuantity(quantity);
        return orderLineService.update(orderLine);
    }

    @Override
    public void removeProduct(Integer productId) {
        Integer quantityFromOrderLineSearched = getOrderLineByProductId(productId).getQuantity();
        orderLineService.delete(quantityFromOrderLineSearched);
    }

    @Override
    public void clearCart() {
        var orderLineList = getOrderLinesByCustomerIdAndNotOrdered();
        for (OrderLine orderLine: orderLineList) {
           removeProduct(orderLine.getProduct().getId());
        }
    }
    @Override
    public Order placeOrder(Payment payment) {
        Set<OrderLine> orderLineSet =  getOrderLinesByCustomerIdAndNotOrdered();
        return orderService.save(new Order(orderLineSet, payment, customer.getId()));
    }

    @Override
    public List<Order> getAllOrders() {
        return orderService.getAll().stream().filter(order -> order.getCustomerId().equals(customer.getId())).toList();
    }

    private Set<OrderLine> getOrderLinesByCustomerIdAndNotOrdered(){
        return orderLineService.getAll().stream().filter(orderLine ->
                orderLine.getOrdered().equals(false)  && orderLine.getCustomerId().equals(customer.getId()))
                .collect(Collectors.toSet());
    }
    private OrderLine getOrderLineByProductId(Integer productId) {
        return getOrderLinesByCustomerIdAndNotOrdered().stream().filter(ordLn -> Objects.equals(ordLn.getProduct().getId(), productId))
                .findFirst().orElseThrow(() -> new OrderLineNotFoundException("Product not registered in cart"));
    }
}
