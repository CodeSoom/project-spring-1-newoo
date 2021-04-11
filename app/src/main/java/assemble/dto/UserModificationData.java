package assemble.dto;

import com.github.dozermapper.core.Mapping;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 사용자 정보 수정 데이터.
 */
@Getter
@Builder
public class UserModificationData {
    @NotBlank
    @Mapping("name")
    private String name;

    @NotBlank
    @Size(min = 4, max = 1024)
    @Mapping("password")
    private String password;

    public UserModificationData(String name, String password) {
        this.name = name;
        this.password = password;
    }
}
