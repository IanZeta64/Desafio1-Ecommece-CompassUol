package repositories;

import Entities.Order;
import Entities.Product;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends GenericRepository<Order> {
    Order save(Order order);

    List<Order> findAll();

    Optional<Order> getById(Integer id);
}
