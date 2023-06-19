package Entities;
import java.math.BigDecimal;
import java.util.Objects;

public class Product {
    private Integer id;
    private final String name;
    private final String category;
    private final BigDecimal price;
    private final Integer quantity;

    public Product(String name, String category, BigDecimal price, Integer quantity) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
    }
    public Product(Integer id, String name, String category, BigDecimal price, Integer quantity) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
    }
    public Integer getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getCategory() {
        return category;
    }
    public BigDecimal getPrice() {
        return price;
    }
    public Integer getQuantity() {
        return quantity;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {

        return String.format("Product:%n" +
                "id:  %s%n" +
                "name= %s%n" +
                "category= %s%n" +
                "price= %s%n" +
                "quantity= %s%n", id, name, category, price,quantity);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product product)) return false;
        return getName().equals(product.getName()) && getCategory().equals(product.getCategory());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getCategory());
    }
}
