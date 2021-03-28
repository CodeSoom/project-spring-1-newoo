package assemble.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 모임.
 */
@Entity
@Getter
@Builder
@NoArgsConstructor
public class Meeting {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String description;

    private String imageUrl;

    private Long ownerId;

    public Meeting(Long id, String name, String description, String imageUrl, Long ownerId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.ownerId = ownerId;
    }

    public Meeting(Long id, String name, String description, Long ownerId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.ownerId = ownerId;
    }

    /**
     * 모임 객체 내부 필드 값들을 변경한다.
     *
     * @param source 수정할 모임 내용
     */
    public void changeWith(Meeting source) {
        this.name = source.name;
        this.description = source.description;
    }
}
