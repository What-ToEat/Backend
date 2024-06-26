package capstone.restaurant.dto.tag;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor

public class TagResponse {
    @Schema(example = "양식")
    private String name;
    @Schema(example = "음식 종류")
    private String category;

}
