package salon.ekat.hairStylist.exception;

public class ValidationAppointmentException extends HairValidationException {
    public ValidationAppointmentException() {
    }

    public ValidationAppointmentException(String message) {
        super(message);
    }

    public ValidationAppointmentException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidationAppointmentException(Throwable cause) {
        super(cause);
    }
}
