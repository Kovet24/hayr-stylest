package salon.ekat.hairStylist.exception;

public class ConflictingAppointmentsException extends RuntimeException {
    public ConflictingAppointmentsException() {
    }

    public ConflictingAppointmentsException(String message) {
        super(message);
    }
}
