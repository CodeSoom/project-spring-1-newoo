package assemble.dto;

import com.github.dozermapper.core.Mapping;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

/**
 * 모임 DTO.
 */
@Builder
@Getter
public class MeetingData {
    private Long id;

    @NotBlank
    @Mapping("name")
    private String name;

    @NotBlank
    @Mapping("description")
    private String description;

    @Mapping("imageUrl")
    private String imageUrl;

    @Mapping("ownerId")
    private Long ownerId;
}
