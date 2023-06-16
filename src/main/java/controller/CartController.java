package controller;

public interface CartController {
    void addProduct(Integer customerId);
    void getCart(Integer customerId);
    void updateCartProduct(Integer customerId);
    void removeProduct(Integer customerId);
    void clearCart(Integer customerId);
    void placeOrder(Integer customerId);

    void getAllOrders(Integer customerId);

}
