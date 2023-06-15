package Services;
import Entities.Order;
import Entities.OrderLine;
import Enums.Payment;

import java.util.List;
import java.util.Set;

public interface CartService {

    OrderLine addProduct(Integer productId, Integer quantity);
    Set<OrderLine> getCart();
    OrderLine updateCartProduct(Integer productId, Integer quantity);
    void removeProduct(Integer productId);
    void clearCart();
    Order placeOrder(Payment payment);

    List<Order> getAllOrders();

}
