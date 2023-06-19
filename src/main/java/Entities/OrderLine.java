package Entities;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class OrderLine {
    private Integer id;
    private final Product product;
    private Integer quantity;
    private BigDecimal finalPrice;
    private final Integer customerId;
    private final Boolean ordered;

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
    public Boolean getOrdered() {
        return ordered;
    }
    public Product getProduct() {
        return product;
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
       return String.format(
               "| order line id: %s " +
                        "| product: %s  [id: %s]" +
                       "| unit price: %s " +
                        "| quantity: %s " +
                        "| line price: %s |%n",
                id, product.getName(), product.getId(), product.getPrice(), quantity, finalPrice.setScale(2, RoundingMode.HALF_UP));

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
