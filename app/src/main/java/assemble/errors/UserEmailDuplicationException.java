package assemble.errors;

/**
 * 중복 이메일 예외.
 */
public class UserEmailDuplicationException extends RuntimeException {
    public UserEmailDuplicationException(String email) {
        super("이미 존재하는 이메일 입니다.: " + email);
    }
}
