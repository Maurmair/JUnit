package nl.maurmair.junit;

public class InvalidTemperatureException extends RuntimeException {

    public InvalidTemperatureException() {
        super();
    }

    public InvalidTemperatureException(String message,
                                       Throwable cause, boolean enableSuppression,
                                       boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public InvalidTemperatureException(String message,
                                       Throwable cause) {
        super(message, cause);
    }

    public InvalidTemperatureException(String message) {
        super(message);
    }

    public InvalidTemperatureException(Throwable cause) {
        super(cause);
    }

}
