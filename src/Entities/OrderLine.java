package Entities;

import java.math.BigDecimal;

public class OrderLine {

    private Integer id;
    private Product product;
    private Integer quantity;
    private BigDecimal finalPrice;
    private Boolean ordered;

    public OrderLine(Product product, Integer quantity) {
        this.product = product;
        this.quantity = quantity;
        this.finalPrice = product.getPrice().multiply(BigDecimal.valueOf(quantity));
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
                " product=" + product +
                ", quantity=" + quantity +
                ", finalPrice=" + finalPrice +
                '}';
    }
}
