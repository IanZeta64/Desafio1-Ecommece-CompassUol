package Services;
import Entities.Order;
import Entities.OrderLine;
import java.util.Set;

public interface CartService {

    Set<OrderLine> addProduct(Integer productId, Integer quantity);
    Set<OrderLine> showCart();
    Set<OrderLine> updateProduct(Integer productId, Integer quantity);
    void removeProduct(Integer productId);
    void clearCart();
    Order placeOrder();

}
