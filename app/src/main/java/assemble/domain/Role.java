package assemble.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 사용자 권한.
 */
@Entity
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue
    private Long id;

    private Long userId;

    @Getter
    private String name;

    public Role(Long userId, String name) {
        this.userId = userId;
        this.name = name;
    }

    public Role(String name) {
        this(null, name);
    }
}