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
@Schema(description = "Информация об одном из самых часто используемых тегов")
public class RelatedTagDto {
    @Schema(description = "id тэга")
    Long id;
    @Schema(description = "Название тэга")
    String title;
    @Schema(description = "Количество вхождений")
    Long countQuestion;
}

