package Entities;
import Enums.Payment;
import java.time.Instant;
import java.util.Set;

public class Order {

    private Integer id;
    private Set<OrderLine> orderLineList;
    private Payment payment;
    private Integer customerId;
    private Instant createdOn;

    public Order(Set<OrderLine> orderLineList, Payment payment, Integer customerId, Instant createdOn) {
        this.orderLineList = orderLineList;
        this.payment = payment;
        this.customerId = customerId;
        this.createdOn = createdOn;
    }

    public Integer getId() {
        return id;
    }

    public Set<OrderLine> getOrderLineList() {
        return orderLineList;
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

}
