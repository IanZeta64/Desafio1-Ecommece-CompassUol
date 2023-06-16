package controller.impl;
import Entities.Product;
import Enums.Payment;
import Services.CartService;
import controller.CartController;
import utils.ConsoleUiHelper;
;

public class CartControllerImpl implements CartController {

    private final CartService cartService;



    public CartControllerImpl(CartService cartService) {
        this.cartService = cartService;
    }


    @Override
    public void addProduct() {
            Integer productId = ConsoleUiHelper.askNumber("Enter the product id to be added to the cart");
            Integer quantity = ConsoleUiHelper.askNumber("Enter the product quantity to be added to the cart");
            System.out.println(cartService.addProduct(productId, quantity));
    }

    @Override
    public void getCart() {
        ConsoleUiHelper.listOrderLinesWithPages(cartService.getCart());

    }

    @Override
    public void updateCartProduct() {
            Integer productId =  ConsoleUiHelper.askNumber("Enter the product id to be added to the cart: ");
            Integer quantity = ConsoleUiHelper.askNumber("Enter the product quantity to be added to the cart: ");
            System.out.println(cartService.updateCartProduct(productId, quantity));//UTIL PARA PRINT
    }

    @Override
    public void removeProduct() {
            Integer productId =  ConsoleUiHelper.askNumber("Enter the product id to be added to the cart: ");
            cartService.removeProduct(productId);
            System.out.println("Product removed from cart");
    }

    @Override
    public void clearCart() {
        cartService.clearCart();
    }

    @Override
    public void placeOrder() {
            Payment payment = null;
            var chooseOption = ConsoleUiHelper.askChooseOption("Choose the form of payment: ", "Cash", "Pix",
                    "Credit card", "Debit card", "Bank slip");
            do {
                switch (chooseOption) {
                    case 1 -> payment = Payment.CASH;
                    case 2 -> payment = Payment.PIX;
                    case 3 -> payment = Payment.CREDIT_CARD;
                    case 4 -> payment = Payment.DEBIT_CARD;
                    case 5 -> payment = Payment.BANK_SLIP;
                    default -> System.out.println("Select a valid number.");
                }
            }while (payment == null);
            System.out.println(cartService.placeOrder(payment));
    }

    @Override
    public void getAllOrders() {
        ConsoleUiHelper.listOrderWithPages(cartService.getAllOrders());
    }

    @Override
    public void searchProduct() {
            String name = ConsoleUiHelper.askSimpleInput("enter your product search");
        System.out.println(cartService.searchProduct(name));
    }

    @Override
    public void getAllProducts() {
        ConsoleUiHelper.listProductsWithPages(cartService.getAllProducts());
    }

}

