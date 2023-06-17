package Services;
import Entities.Order;
import Entities.OrderLine;
import Enums.Payment;

import java.util.List;

public interface CartService {

    OrderLine addCartProduct(Integer productId, Integer quantity, Integer customerId);
    List<OrderLine> getCart(Integer customerId);
    OrderLine updateCartProduct(Integer productId, Integer quantity, Integer customerId);
    void removeCartProduct(Integer productId, Integer customerId);
    void clearCart(Integer customerId);
    Order placeOrder(Payment payment, Integer customerId);

    List<Order> getAllOrders(Integer customerId);

    Order getOrderById(Integer orderId, Integer customerId);
    OrderLine getOrderLineById(Integer orderLineId, Integer customerId);
}
