package assemble.dto;

/**
 * 메시지를 가지고 있는 에러 응답 객체.
 */
public class ErrorResponse {
    private String message;

    public ErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
