package Services.impl;

import Entities.Customer;
import Entities.Order;
import Entities.OrderLine;
import Enums.Payment;
import Services.CartService;
import exceptions.OrderLineNotFoundException;
import exceptions.ProductNotFoundException;
import repositories.OrderLineRepository;
import repositories.OrderRepository;
import repositories.ProductRepository;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class CartServiceImpl implements CartService {

    private final OrderLineRepository orderLineRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final Customer customer;

    public CartServiceImpl(OrderLineRepository orderLineRepository, OrderRepository orderRepository, ProductRepository productRepository, Customer customer) {
        this.orderLineRepository = orderLineRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.customer = customer;
    }

    @Override
    public OrderLine addProduct(Integer productId, Integer quantity) {
//        if (getOrderLinesByCustomerIdAndNotOrdered().stream()
//                .anyMatch(orderLine -> orderLine.getProduct().equals(productId))){
//            productRepository.selectById(productId).orElseThrow(() -> new ProductNotFoundException());
//            OrderLine orderLine = getOrderLinesByCustomerIdAndNotOrdered().stream()
//                    .filter(ordLn -> ordLn.getProduct().getId().equals(productId)).findFirst()
//                    .orElseGet(odrLn -> new OrderLine());
//        }
        return null;
    }

    @Override
    public Set<OrderLine> getCart() {
        return getOrderLinesByCustomerIdAndNotOrdered();
    }

    @Override
    public OrderLine updateCartProduct(Integer productId, Integer quantity) {
        OrderLine orderLine = getOrderLineByProductId(productId);
        orderLine.setQuantity(quantity);
        return orderLineRepository.update(orderLine);
    }



    @Override
    public void removeProduct(Integer productId) {
        OrderLine orderLine = getOrderLineByProductId(productId);
        orderLineRepository.deleteById(orderLine.getId());
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
        return orderRepository.insert(new Order(getOrderLinesByCustomerIdAndNotOrdered(), payment, customer.getId()));
    }

    private Set<OrderLine> getOrderLinesByCustomerIdAndNotOrdered(){
        return orderLineRepository.selectAll().stream().filter(orderLine ->
                orderLine.getOrdered().equals(false)  && orderLine.getCustomerId().equals(customer.getId()))
                .collect(Collectors.toSet());
    }
    private OrderLine getOrderLineByProductId(Integer productId) {
        return getOrderLinesByCustomerIdAndNotOrdered().stream().filter(ordLn -> Objects.equals(ordLn.getProduct().getId(), productId))
                .findFirst().orElseThrow(() -> new OrderLineNotFoundException("Product not registered in cart"));
    }
}
