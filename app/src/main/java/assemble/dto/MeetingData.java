package assemble.dto;

import com.github.dozermapper.core.Mapping;
import lombok.Builder;

import javax.validation.constraints.NotBlank;

/**
 * 모임 DTO.
 */
@Builder
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

    @NotBlank
    @Mapping("ownerId")
    private Long ownerId;
}
