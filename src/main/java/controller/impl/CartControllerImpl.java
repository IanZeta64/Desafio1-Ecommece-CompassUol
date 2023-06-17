package controller.impl;
import Entities.OrderLine;
import Enums.Payment;
import Services.CartService;
import controller.CartController;
import exceptions.EmptyCartException;
import exceptions.InsufficientStockException;
import exceptions.OrderLineNotFoundException;
import exceptions.OrderNotFoundException;
import utils.ConsoleUiHelper;
import java.math.BigDecimal;

public class CartControllerImpl implements CartController {

    private final CartService cartService;

    public CartControllerImpl(CartService cartService) {
        this.cartService = cartService;
    }

    @Override
    public void addProduct(Integer customerId) {
        try {
            Integer productId = ConsoleUiHelper.askNumber("Enter the product id to be added to the cart");
            Integer quantity = ConsoleUiHelper.askNumber("Enter the product quantity to be added to the cart");
            System.out.println(cartService.addCartProduct(productId, quantity, customerId));
        } catch (OrderLineNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void getCart(Integer customerId) {
        ConsoleUiHelper.listOrderLinesPages(cartService.getCart(customerId), 5);

    }

    @Override
    public void updateCartProduct(Integer customerId) {
        try {
            Integer productId = ConsoleUiHelper.askNumber("Enter the product id to be updated to the cart: ");
            Integer quantity = ConsoleUiHelper.askNumber("Enter the product quantity to be updated to the cart: ");
            System.out.println(cartService.updateCartProduct(productId, quantity, customerId));
        } catch (OrderLineNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void removeProduct(Integer customerId) {
        try {
            Integer productId = ConsoleUiHelper.askNumber("Enter the product id to be added to the cart: ");
            cartService.removeCartProduct(productId, customerId);
            System.out.printf("Product %s removed from cart.%n", productId);
        } catch (OrderLineNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void clearCart(Integer customerId) {
        cartService.clearCart(customerId);
    }

    @Override
    public void placeOrder(Integer customerId) {
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
        } while (payment == null);
        try {
            if (confirmOrder(customerId, payment)) System.out.println(cartService.placeOrder(payment, customerId));
            else System.out.println("Order not placed.");
        } catch (InsufficientStockException | EmptyCartException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void getAllOrders(Integer customerId) {
        ConsoleUiHelper.listOrdersPages(cartService.getAllOrders(customerId), 2);
    }

    @Override
    public void getOrderLineById(Integer customerId) {
        try {
            Integer orderLineId = ConsoleUiHelper.askNumber("Enter a order line id:");
            System.out.println(cartService.getOrderLineById(orderLineId, customerId));
        }catch (OrderLineNotFoundException e){
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void getOrderById(Integer customerId) {
        try {
            Integer orderLineId = ConsoleUiHelper.askNumber("Enter a order line id:");
            System.out.println(cartService.getOrderById(orderLineId, customerId));
        }catch (OrderNotFoundException e){
            System.err.println(e.getMessage());
        }

    }

    private Boolean confirmOrder(Integer customerId, Payment payment) {
        var orderLineList = cartService.getCart(customerId);
        BigDecimal finalPriceOrder = orderLineList.stream().map(OrderLine::getFinalPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("\nYour Cart: ");
        ConsoleUiHelper.listOrderLinesPages(orderLineList, 99);
        System.out.println("final price: " + finalPriceOrder + "\nPayment: " + payment + "\n");
        return ConsoleUiHelper.askConfirm("Do you want to place this order?", "Yes, confirm.", "No, cancel.");
    }
}

