package Entities;
import Enums.Payment;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class Order {

    private Integer id;
    private Set<OrderLine> orderLineList;
    private Payment payment;
    private Integer customerId;
    private final Instant createdOn;

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

      return  String.format(
                "%n%n.:: ORDER - iJAVA ::. %n%n" +
                "Order id: %s%n" +
                        "ordered in: %s%n" +
                        "customer id: %s%n" +
                        "payment: %s%n" +
                        "%s" +
                        "final price: %s%n" +
                ".::THANKS FOR SHOPPING WITH US::.%n",
                id, createdOn, customerId, payment.toString(),
              orderLineList.toString().replace("[", "").replace("]","").replace(",", "").replaceFirst("\\|", " |"),
              finalPrice.setScale(2, RoundingMode.HALF_UP));

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
