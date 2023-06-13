package Entities;
import Enums.Payment;
import java.time.Instant;
import java.util.Set;

public class Order {

    private Integer id;
    private Set<OrderLine> orderLineList;
    private Payment payment;
    private Customer customer;
    private Instant createdOn;

    public Order(Set<OrderLine> orderLineList, Payment payment, Customer customer, Instant createdOn) {
        this.orderLineList = orderLineList;
        this.payment = payment;
        this.customer = customer;
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

    public Customer getCustomerId() {
        return customer;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

}
