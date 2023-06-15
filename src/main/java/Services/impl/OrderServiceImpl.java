package Services.impl;

import Entities.Order;
import Services.OrderService;
import exceptions.CustomerNotFoundException;
import exceptions.DUplicatedOrderException;
import exceptions.DuplicatedCustomerException;
import exceptions.OrderNotFoundException;
import repositories.OrderRepository;

import java.util.List;

public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order save(Order order) {
        if (orderRepository.selectAll().stream().anyMatch(odr -> odr.equals(order))) {
            throw new DUplicatedOrderException("Order already registered");
        } else {
            return orderRepository.insert(order);
        }
    }

    @Override
    public List<Order> getAll() {
        return orderRepository.selectAll();
    }

    @Override
    public Order getById(Integer id) {
        return orderRepository.selectById(id).orElseThrow(() -> new OrderNotFoundException(String.format("Order not found bi id: %s", id)));
    }

    @Override
    public Order update(Order order) {
        if (existById(order.getId())){
            return orderRepository.update(order);
        } else{
            throw new OrderNotFoundException("Order not found, can't update.");
        }
    }

    @Override
    public void delete(Integer id) {
        if (existById(id)){
             orderRepository.deleteById(id);
        } else{
            throw new OrderNotFoundException("Order not found, can't delete.");
        }
    }

    @Override
    public Boolean existById(Integer id) {
        long count = orderRepository.selectById(id).stream().count();
        return count > 0;
    }
}
