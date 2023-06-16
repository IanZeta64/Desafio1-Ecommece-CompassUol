package controller;

public interface CartController {
    void addProduct();
    void getCart();
    void updateCartProduct();
    void removeProduct();
    void clearCart();
    void placeOrder();

    void getAllOrders();

    void searchProduct();

    void getAllProducts();
}
