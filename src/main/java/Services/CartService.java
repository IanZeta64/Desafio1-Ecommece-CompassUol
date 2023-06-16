package Services;
import Entities.Order;
import Entities.OrderLine;
import Entities.Product;
import Enums.Payment;

import java.util.List;

public interface CartService {

    OrderLine addProduct(Integer productId, Integer quantity);
    List<OrderLine> getCart();
    OrderLine updateCartProduct(Integer productId, Integer quantity);
    void removeProduct(Integer productId);
    void clearCart();
    Order placeOrder(Payment payment);

    List<Order> getAllOrders();

    List<Product> searchProduct(String name);

    List<Product> getAllProducts();

}
