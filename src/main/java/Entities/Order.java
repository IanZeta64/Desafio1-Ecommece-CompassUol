package Entities;
import Enums.Payment;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class Order {

    private Integer id;
    private Set<OrderLine> orderLineList;
    private Payment payment;
    private Integer customerId;
    private Instant createdOn;

    private BigDecimal finalPrice;

    public Order(Integer id, Set<OrderLine> orderLineList, Payment payment, Integer customerId, Instant createdOn, BigDecimal finalPrice) {
        this.id = id;
        this.orderLineList = orderLineList;
        this.payment = payment;
        this.customerId = customerId;
        this.createdOn = createdOn;
        this.finalPrice = finalPrice;
    }

    public Order(Set<OrderLine> orderLineList, Payment payment, Integer customerId) {
        this.orderLineList = orderLineList;
        this.payment = payment;
        this.customerId = customerId;
        this.createdOn = Instant.now();
        this.finalPrice = orderLineList.stream().map(OrderLine::getFinalPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void setFinalPrice() {
        this.finalPrice = orderLineList.stream().map(OrderLine::getFinalPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getFinalPrice() {
        return finalPrice;
    }

    public Integer getId() {
        return id;
    }

    public Set<OrderLine> getOrderLineList() {
        return Collections.unmodifiableSet(orderLineList);
    }

    public Payment getPayment() {
        return payment;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderLineList=" + orderLineList +
                ", payment=" + payment +
                ", customerId=" + customerId +
                ", createdOn=" + createdOn +
                ", finalPrice=" + finalPrice +
                '}';
    }
    public void setOrderLineList(Set<OrderLine> orderLineList) {
        this.orderLineList = orderLineList;
        setFinalPrice();
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }
}
