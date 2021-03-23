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
}
