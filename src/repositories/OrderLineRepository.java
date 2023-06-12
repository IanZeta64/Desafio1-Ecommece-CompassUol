package repositories;

import Entities.Order;
import Entities.OrderLine;

import java.util.List;
import java.util.Optional;

public interface OrderLineRepository extends GenericRepository<OrderLine> {
    OrderLine save(OrderLine orderLine);

    List<OrderLine> findAll();

    Optional<OrderLine> getById(Integer id);
}
