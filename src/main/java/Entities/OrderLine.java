package Entities;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class OrderLine {
    private Integer id;
    private final Product product;
    private Integer productQuantity;
    private BigDecimal orderLinePrice;
    private final Integer customerId;
    private final Boolean ordered;

    public OrderLine(Product product, Integer quantity, Integer customerId) {
        this.product = product;
        this.productQuantity = quantity;
        this.customerId = customerId;
        this.orderLinePrice = product.getPrice().multiply(BigDecimal.valueOf(quantity));
        this.ordered = false;
    }
    public OrderLine(Integer id, Product product, Integer quantity, BigDecimal orderLinePrice, Integer customerId, Boolean ordered) {
        this.id = id;
        this.product = product;
        this.productQuantity = quantity;
        this.orderLinePrice = orderLinePrice;
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
    public Integer getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(Integer productQuantity) {
        this.productQuantity = productQuantity;
        setFinalPrice();
    }

    public BigDecimal getOrderLinePrice() {
        return orderLinePrice;
    }

    private void setFinalPrice() {
        this.orderLinePrice = product.getPrice().multiply(BigDecimal.valueOf(productQuantity));
    }

    @Override
    public String toString() {
       return String.format(
               "| order line id: %s " +
                        "| product: %s  [id: %s]" +
                       "| unit price: %s " +
                        "| quantity: %s " +
                        "| line price: %s |%n",
                id, product.getName(), product.getId(), product.getPrice(), productQuantity, orderLinePrice.setScale(2, RoundingMode.HALF_UP));

    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderLine orderLine)) return false;
        return getProduct().equals(orderLine.getProduct()) && getProductQuantity().equals(orderLine.getProductQuantity()) && getOrderLinePrice().equals(orderLine.getOrderLinePrice()) && getCustomerId().equals(orderLine.getCustomerId()) && getOrdered().equals(orderLine.getOrdered());
    }
    @Override
    public int hashCode() {
        return Objects.hash(getProduct(), getProductQuantity(), getOrderLinePrice(), getCustomerId(), getOrdered());
    }
}
