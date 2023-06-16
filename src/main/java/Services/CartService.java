package Services;
import Entities.Order;
import Entities.OrderLine;
import Entities.Product;
import Enums.Payment;

import java.util.List;

public interface CartService {

    OrderLine addProduct(Integer productId, Integer quantity, Integer customerId);
    List<OrderLine> getCart(Integer customerId);
    OrderLine updateCartProduct(Integer productId, Integer quantity, Integer customerId);
    void removeProduct(Integer productId, Integer customerId);
    void clearCart(Integer customerId);
    Order placeOrder(Payment payment, Integer customerId);

    List<Order> getAllOrders(Integer customerId);

}
