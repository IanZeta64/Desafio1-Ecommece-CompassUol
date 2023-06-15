package Services.impl;

import Entities.OrderLine;
import Services.OrderLineService;
import exceptions.*;
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
            throw new DuplicatedOrderLineException("Order line already registered");
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
            throw new OrderLineNotFoundException("Order line not found,can't update.");
        }
    }

    @Override
    public void delete(Integer id) {
        if (existById(id)){
            orderLineRepository.deleteById(id);
        }else{
            throw new OrderLineNotFoundException("Order not found,can't delete.");
        }
    }

    @Override
    public Boolean existById(Integer id) {
        long cont = orderLineRepository.selectById(id).stream().count();
        return cont > 0;
    }


    @Override
    public Boolean existByProductId(Integer productId){
        return orderLineRepository.selectAll().stream().anyMatch( orderLine ->  orderLine.getProduct().getId().equals(productId));
    }

    @Override
    public OrderLine findByProductId(Integer productId) {
        return orderLineRepository.selectAll().stream().filter(product -> product.getProduct().getId().equals(productId))
                .findFirst().orElseThrow(() -> new OrderLineNotFoundException(String.format("Order line not found by product_id: %s", productId)));
    }
}