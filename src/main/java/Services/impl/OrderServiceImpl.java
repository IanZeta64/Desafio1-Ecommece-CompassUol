package Services.impl;
import Entities.Order;
import Services.OrderService;
import exceptions.DuplicatedOrderException;
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
        try {
            if (orderRepository.selectAll().stream().anyMatch(odr -> odr.equals(order))) {
                throw new DuplicatedOrderException("Order already registered");
            }
            return orderRepository.insert(order);
        } catch (DuplicatedOrderException e) {
            System.err.println(e.getMessage());
        }
        return order;
    }

    @Override
    public List<Order> getAll() {
        return orderRepository.selectAll();
    }

    @Override
    public Order getById(Integer id) throws OrderNotFoundException {
        return orderRepository.selectById(id)
                .orElseThrow(() -> new OrderNotFoundException(String.format("Order not found bi id: %s", id)));
    }

    @Override
    public Order update(Order order) {
        try {
            if (!existById(order.getId())) throw new OrderNotFoundException("Order not found, can't update.");
            return orderRepository.update(order);
        } catch (OrderNotFoundException e) {
            System.err.println(e.getMessage());
        }
        return order;
    }

    @Override
    public void delete(Integer id) {
        try {
            if (!existById(id)) throw new OrderNotFoundException("Order not found, can't delete.");
            orderRepository.deleteById(id);
        }catch (OrderNotFoundException e){
            System.err.println(e.getMessage());
        }
    }

    @Override
    public Boolean existById(Integer id) {
        long count = orderRepository.selectById(id).stream().count();
        return count > 0;
    }
}
