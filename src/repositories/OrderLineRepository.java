package repositories;

import Entities.OrderLine;

import java.util.List;
import java.util.Optional;

public interface OrderLineRepository extends GenericRepository<OrderLine> {
    OrderLine insert(OrderLine orderLine);
    List<OrderLine> selectAll();
    Optional<OrderLine> selectById(Integer id);
    OrderLine update(OrderLine orderLine);
    void deleteById(Integer id);
}
