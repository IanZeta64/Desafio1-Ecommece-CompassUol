package repositories;

import Entities.OrderLine;

import java.util.List;
import java.util.Optional;

public interface OrderLineRepository extends GenericRepository<OrderLine> {
    OrderLine save(OrderLine orderLine);
    List<OrderLine> findAll();
    Optional<OrderLine> getById(Integer id);
    OrderLine update(OrderLine orderLine);
    void deleteById(Integer id);
}
