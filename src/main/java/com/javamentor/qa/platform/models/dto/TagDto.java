package com.javamentor.qa.platform.models.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "информация о тэге")
public class TagDto {

    @Schema(description = "id тэга")
    private Long id;

    @Schema(description = "название тэга")
    private String name;

    @Schema(description = "описание тэга")
    private String description;
}
