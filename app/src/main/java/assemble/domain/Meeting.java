package assemble.domain;

import lombok.Builder;
import lombok.Getter;

/**
 * 모임.
 */
@Getter
@Builder
public class Meeting {
    private Long id;

    private String name;

    private String description;

    private String imageUrl;

    private Long ownerId;

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
