package Entities;

import java.math.BigDecimal;
import java.util.Objects;

public class OrderLine {

    private Integer id;
    private Product product;
    private Integer quantity;
    private BigDecimal finalPrice;
    private Integer customerId;
    private Boolean ordered;

    public OrderLine(Product product, Integer quantity, Integer customerId) {
        this.product = product;
        this.quantity = quantity;
        this.customerId = customerId;
        this.finalPrice = product.getPrice().multiply(BigDecimal.valueOf(quantity));
        this.ordered = false;
    }

    public OrderLine(Integer id, Product product, Integer quantity, BigDecimal finalPrice, Integer customerId, Boolean ordered) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
        this.finalPrice = finalPrice;
        this.customerId = customerId;
        this.ordered = ordered;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Boolean getOrdered() {
        return ordered;
    }


    public void setOrdered(Boolean ordered) {
        this.ordered = ordered;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
        setFinalPrice();
    }

    public BigDecimal getFinalPrice() {
        return finalPrice;
    }

    private void setFinalPrice() {
        this.finalPrice = product.getPrice().multiply(BigDecimal.valueOf(quantity));
    }

    @Override
    public String toString() {
        return "OrderLine{" +
                "id=" + id +
                ", product=" + product.getName() +
                ", quantity=" + quantity +
                ", finalPrice=" + finalPrice +
                ", customerId=" + customerId +
                ", ordered=" + ordered +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderLine orderLine)) return false;
        return getProduct().equals(orderLine.getProduct()) && getQuantity().equals(orderLine.getQuantity()) && getFinalPrice().equals(orderLine.getFinalPrice()) && getCustomerId().equals(orderLine.getCustomerId()) && getOrdered().equals(orderLine.getOrdered());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProduct(), getQuantity(), getFinalPrice(), getCustomerId(), getOrdered());
    }
}
