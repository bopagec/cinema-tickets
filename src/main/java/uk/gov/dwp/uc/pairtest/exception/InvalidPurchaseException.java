package uk.gov.dwp.uc.pairtest.exception;

public class InvalidPurchaseException extends RuntimeException {
    public InvalidPurchaseException(String msg){
        super(msg);
    }
    public InvalidPurchaseException(String msg, Throwable e){
        super(msg, e);
    }

    public InvalidPurchaseException(Throwable e){
        super(e);
    }
}
