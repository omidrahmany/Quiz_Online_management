package io.spring.quiz_online.excetion_handling;

public class InvalidAccountException extends Exception {
    public InvalidAccountException(String message) {
        super(message);
    }
}
