package assemble.dto;

import com.github.dozermapper.core.Mapping;

import javax.validation.constraints.NotBlank;

/**
 * 모임 DTO.
 */
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
