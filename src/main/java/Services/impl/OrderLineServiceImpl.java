package Services.impl;

import Entities.OrderLine;
import Services.OrderLineService;
import exceptions.CustomerNotFoundException;
import exceptions.DuplicatedCustomerException;
import exceptions.OrderLineNotFoundException;
import repositories.OrderLineRepository;

import java.util.List;

public class OrderLineServiceImpl implements OrderLineService {

    private  final OrderLineRepository orderLineRepository;

    public OrderLineServiceImpl(OrderLineRepository orderLineRepository) {
        this.orderLineRepository = orderLineRepository;
    }

    @Override
    public OrderLine save(OrderLine orderLine) {
        if (orderLineRepository.selectAll().stream().anyMatch(ordLn -> ordLn.equals(orderLine))) {
            throw new DuplicatedCustomerException("Order line already registered");
        } else {
            return orderLineRepository.insert(orderLine);
        }
    }

    @Override
    public List<OrderLine> getAll() {
        return orderLineRepository.selectAll();
    }

    @Override
    public OrderLine getById(Integer id) {
        return orderLineRepository.selectById(id).orElseThrow(() -> new OrderLineNotFoundException(String.format("Order line not found by id: %s", id)));
    }

    @Override
    public OrderLine update(OrderLine orderLine) {
        if (existById(orderLine.getId())){
            return orderLineRepository.update(orderLine);
        } else{
            throw new CustomerNotFoundException("Customer not found,can't update.");
        }
    }

    @Override
    public void delete(Integer id) {
        if (existById(id)){
            orderLineRepository.deleteById(id);
        }else{
            throw new CustomerNotFoundException("Customer not found,can't delete.");
        }
    }

    @Override
    public Boolean existById(Integer id) {
        long cont = orderLineRepository.selectById(id).stream().count();
        return cont > 0;
    }

}