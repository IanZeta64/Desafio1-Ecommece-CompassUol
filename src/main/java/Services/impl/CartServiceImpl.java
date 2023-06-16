package Services.impl;

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
import exceptions.InsufficientStockException;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class CartServiceImpl implements CartService {

    private final OrderService orderService;
    private final OrderLineService orderLineService;
    private final ProductService productService;

    public CartServiceImpl(OrderService orderService, OrderLineService orderLineService, ProductService productService) {
        this.orderService = orderService;
        this.orderLineService = orderLineService;
        this.productService = productService;
    }

    @Override
    public OrderLine addCartProduct(Integer productId, Integer quantity, Integer customerId) throws OrderLineNotFoundException {
        Product product = productService.getById(productId);
        OrderLine orderLine = new OrderLine(product, quantity, customerId);
        if (orderLineService.existByProductIdAndCustomerIdAndNotOrdered(productId, customerId)) {
            OrderLine orderLineSearched = getOrderLineByProductIdAndCustomerIdAndNotOrdered(productId, customerId);
            orderLine.setId(orderLineSearched.getId());
            orderLine.setQuantity(orderLine.getQuantity() + orderLineSearched.getQuantity());
            return orderLineService.update(orderLine);
        } else {
            return orderLineService.save(orderLine);
        }
    }

    @Override
    public List<OrderLine> getCart(Integer customerid) {
        return getOrderLinesByCustomerIdAndNotOrdered(customerid).stream().toList();
    }

    @Override
    public OrderLine updateCartProduct(Integer productId, Integer quantity, Integer customerId) throws OrderLineNotFoundException {
        if (!orderLineService.existByProductIdAndCustomerIdAndNotOrdered(productId, customerId)) {
            throw new OrderLineNotFoundException(String.format("Order line not found by product id %s, can't update qauntity %s", productId, quantity));
        }
        OrderLine orderLine = getOrderLineByProductIdAndCustomerIdAndNotOrdered(productId, customerId);
        orderLine.setQuantity(quantity);
        return orderLineService.update(orderLine);
    }

    @Override
    public void removeCartProduct(Integer productId, Integer customerId) throws OrderLineNotFoundException {
        try {
            if (!orderLineService.existByProductIdAndCustomerIdAndNotOrdered(productId, customerId)) {
                throw new OrderLineNotFoundException(String.format("Order line not found by product id %s.", productId));
            }
            Integer orderLineId = getOrderLineByProductIdAndCustomerIdAndNotOrdered(productId, customerId).getId();
            orderLineService.delete(orderLineId);
        } catch (OrderLineNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void clearCart(Integer customerId) {
        Set<OrderLine> orderLineList = getOrderLinesByCustomerIdAndNotOrdered(customerId);
        for (OrderLine orderLine : orderLineList) {
            removeCartProduct(orderLine.getProduct().getId(), customerId);
        }
    }

    @Override
    public Order placeOrder(Payment payment, Integer customerId) throws InsufficientStockException, EmptyCartException {
        Set<OrderLine> orderLineSet = getOrderLinesByCustomerIdAndNotOrdered(customerId);
        if (!orderLineSet.isEmpty()) {
            orderLineSet.forEach(orderLine -> {
                if (!productService.quantityInSotckAvaliable(orderLine.getProduct().getId(), orderLine.getQuantity())) {
                    throw new InsufficientStockException(String.format("Product: %s with id: %s is low in stock. Please adjust quantity in order line with id: %s.",
                            orderLine.getProduct().getName(), orderLine.getProduct().getId(), orderLine.getId()));
                }
            });
            return orderService.save(new Order(orderLineSet, payment, customerId));
        } else {
            throw new EmptyCartException("Cart is empty, can't place order.");
        }
    }

    @Override
    public List<Order> getAllOrders(Integer customerId) {
        return orderService.getAll().stream().filter(order -> order.getCustomerId().equals(customerId)).toList();
    }


    private Set<OrderLine> getOrderLinesByCustomerIdAndNotOrdered(Integer customerId) {
        return orderLineService.getAll().stream().filter(orderLine ->
                        orderLine.getOrdered().equals(false) && orderLine.getCustomerId().equals(customerId))
                .collect(Collectors.toSet());
    }

    private OrderLine getOrderLineByProductIdAndCustomerIdAndNotOrdered(Integer productId, Integer customerId) throws OrderLineNotFoundException {
        return getOrderLinesByCustomerIdAndNotOrdered(customerId).stream()
                .filter(ordLn -> Objects.equals(ordLn.getProduct().getId(), productId))
                .findFirst().orElseThrow(() -> new OrderLineNotFoundException("Product not registered in cart"));
    }
}
