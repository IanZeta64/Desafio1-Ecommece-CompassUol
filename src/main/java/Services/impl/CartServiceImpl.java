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
import exceptions.EmptyCartException;
import exceptions.OrderLineNotFoundException;
import exceptions.LowStockException;

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
            if (orderLineService.existByProductIdAndNotOrdered(productId)){
                OrderLine orderLineSearched = getOrderLineByProductId(productId);
                orderLine.setId(orderLineSearched.getId());
                orderLine.setQuantity(orderLine.getQuantity() + orderLineSearched.getQuantity());
              return orderLineService.update(orderLine);
            }else{
                return orderLineService.save(orderLine);
            }
    }

    @Override
    public List<OrderLine> getCart() {
        return getOrderLinesByCustomerIdAndNotOrdered().stream().toList();
    }

    @Override
    public OrderLine updateCartProduct(Integer productId, Integer quantity) {
        OrderLine orderLine = getOrderLineByProductId(productId);
        orderLine.setQuantity(quantity);
        if (orderLineService.existByProductIdAndNotOrdered(productId)) return orderLineService.update(orderLine);
        else throw new OrderLineNotFoundException(String.format("Order line not found by product id %s, can't update qauntity %s", productId, quantity));
    }

    @Override
    public void removeProduct(Integer productId) {
        if (orderLineService.existByProductIdAndNotOrdered(productId)){
            Integer orderLineId = getOrderLineByProductId(productId).getId();
            orderLineService.delete(orderLineId);
        }else{
            throw new OrderLineNotFoundException(String.format("Order line not found by product id %s.", productId));
        }
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
        if (!orderLineSet.isEmpty()){
            orderLineSet.forEach(orderLine -> {
                if (!productService.quantityInSotckAvaliable(orderLine.getProduct().getId(), orderLine.getQuantity())){
                    throw new LowStockException(String.format("Product: %s with id: %s is low in stock. Please adjust quantity in order line with id: %s.",
                    orderLine.getProduct().getName(), orderLine.getProduct().getId(), orderLine.getId()));
                }
            });
            return orderService.save(new Order(orderLineSet, payment, customer.getId()));
        }else {
            throw new EmptyCartException("Cart is empty, can't place order.");
        }
    }

    @Override
    public List<Order> getAllOrders() {
        return orderService.getAll().stream().filter(order -> order.getCustomerId().equals(customer.getId())).toList();
    }

    @Override
    public List<Product> searchProduct(String name) {
        return productService.searchByNameContainsString(name);
    }

    @Override
    public List<Product> getAllProducts() {
        return productService.getAll();

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
