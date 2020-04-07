package ro.msg.learning.shop.exceptions;

public class OrderPlacingException extends Exception {
    public OrderPlacingException(String errorMessage) {
        super(errorMessage);
    }
}
