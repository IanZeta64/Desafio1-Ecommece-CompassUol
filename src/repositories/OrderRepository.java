package repositories;

import Entities.Order;
import repositories.GenericRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends GenericRepository<Order> {
    Order insert(Order order);
    List<Order> selectAll();
    Optional<Order> selectById(Integer id);
    Order update(Order order);
    void deleteById(Integer id);
}
