package com.restaurant.restaurantvote.util.exception;

public class ToLateToVoteException extends RuntimeException {
    public ToLateToVoteException(String message) {
        super(message);
    }
}
