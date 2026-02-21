package salon.ekat.hairStylist.exception;

public class HairValidationException extends RuntimeException {
    public HairValidationException() {
    }

    public HairValidationException(String message) {
        super(message);
    }

    public HairValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public HairValidationException(Throwable cause) {
        super(cause);
    }
}
