package assemble.dto;

import lombok.Builder;
import lombok.Getter;

/**
 * 사용자 정보 생성 또는 수정 결과 데이터.
 */
@Getter
@Builder
public class UserResultData {
    private Long id;

    private String email;

    private String name;

    public UserResultData(Long id, String email, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }
}
