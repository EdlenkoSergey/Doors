package doors.exceptions;

public class ForbiddenEntranceException extends RuntimeException {
    public ForbiddenEntranceException(String message) {
        super(message);
    }
}
