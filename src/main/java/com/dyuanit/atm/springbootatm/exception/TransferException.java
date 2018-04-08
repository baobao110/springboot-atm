package com.dyuanit.atm.springbootatm.exception;

public class TransferException extends RuntimeException {
    public TransferException() {
        super();
    }

    public TransferException(String message) {
        super(message);
    }
}
