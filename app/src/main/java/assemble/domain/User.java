package assemble.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 사용자 정보.
 */
@Entity
@Getter
@Builder
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue
    private Long id;

    @Builder.Default
    private String email = "";

    @Builder.Default
    private String name = "";

    @Builder.Default
    private String password = "";

    @Builder.Default
    private boolean deleted = false;

    public User (Long id, String email, String name, String password, boolean deleted) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
        this.deleted = deleted;
    }

    public User (Long id, String email, String name, String password) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
    }

    /**
     * 사용자 정보를 변경한다.
     *
     * @param source 변경할 사용자 정보
     */
    public void changeWith(User source) {
        name = source.name;
    }

    /**
     * 비밀번호를 변경한다.
     *
     * @param password 변경할 비밀번호
     * @param passwordEncoder 비밀번호 인코더
     */
    public void changePassword(String password,
                               PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(password);
    }

    /**
     * 사용자를 탈퇴 처리한다.
     */
    public void destroy() {
        deleted = true;
    }

    /**
     * 사용자 비밀번호 일치여부를 확인하여, 인증한다.
     *
     * @param password 사용자가 입력한 비밀번호
     * @param passwordEncoder 비밀번호 인코더
     * @return
     */
    public boolean authenticate(String password,
                                PasswordEncoder passwordEncoder) {
        return !deleted && passwordEncoder.matches(password, this.password);
    }
}
