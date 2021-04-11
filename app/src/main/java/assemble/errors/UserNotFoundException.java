package assemble.errors;

/**
 * 유저를 찾을 수 없다는 예외.
 */
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super("해당 유저를 찾을 수 없음: " + id);
    }
}
