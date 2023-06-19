package Services.impl;
import Entities.OrderLine;
import Services.OrderLineService;
import exceptions.*;
import repositories.OrderLineRepository;
import java.util.List;

public class OrderLineServiceImpl implements OrderLineService {

    private final OrderLineRepository orderLineRepository;


    public OrderLineServiceImpl(OrderLineRepository orderLineRepository) {
        this.orderLineRepository = orderLineRepository;
    }

    @Override
    public OrderLine save(OrderLine orderLine) {
        try {
            if (orderLineRepository.selectAll().stream().anyMatch(ordLn -> ordLn.equals(orderLine))) {
                throw new DuplicatedOrderLineException("Order line already registered. can't save duplicated order line.");
            }
            return orderLineRepository.insert(orderLine);
        } catch (DuplicatedOrderLineException e) {
            System.err.println(e.getMessage());
        }
        return orderLine;
    }

    @Override
    public List<OrderLine> getAll() {
        return orderLineRepository.selectAll();
    }

    @Override
    public OrderLine getById(Integer id) throws OrderLineNotFoundException {
        return orderLineRepository.selectById(id)
                .orElseThrow(() -> new OrderLineNotFoundException(String.format("Order line not found by id: %s", id)));
    }

    @Override
    public OrderLine update(OrderLine orderLine) {
        try {
            if (!existById(orderLine.getId()))
                throw new OrderLineNotFoundException("Order line not found,can't update.");
            return orderLineRepository.update(orderLine);
        } catch (OrderLineNotFoundException e) {
            System.err.println(e.getMessage());
        }
        return orderLine;
    }

    @Override
    public void delete(Integer id) {
        try {
            if (!existById(id)) throw new OrderLineNotFoundException("Order line not found,can't delete.");
            orderLineRepository.deleteById(id);

        } catch (OrderLineNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public Boolean existById(Integer id) {
        long cont = orderLineRepository.selectById(id).stream().count();
        return cont > 0;
    }


    @Override
    public Boolean existByProductIdAndCustomerId(Integer productId, Integer customerId) {
        return orderLineRepository.selectAll().stream()
//                .filter(orderLine -> orderLine.getOrdered().equals(false))
                .anyMatch(orderLine -> orderLine.getProduct().getId().equals(productId) && orderLine.getCustomerId().equals(customerId));
    }

    @Override
    public OrderLine findByProductIdAndCustomerId(Integer productId, Integer cutomerId) {
        return orderLineRepository.selectAll().stream().filter(orderLine -> orderLine.getProduct().getId().equals(productId)
                        && orderLine.getCustomerId().equals(cutomerId)).findFirst()
                .orElseThrow(() -> new OrderLineNotFoundException(String.format("Order line not found by product_id: %s", productId)));
    }
}