package assemble.application.errors;

/**
 * 모임을 찾을 수 없다는 예외
 */
public class MeetingNotFoundException extends RuntimeException {
    public MeetingNotFoundException(Long id) {
        super("Meeting not found: " + id);
    }
}
