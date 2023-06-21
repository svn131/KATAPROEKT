package com.javamentor.qa.platform.models.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "Информация о создаваемом вопросе")
public class QuestionCreateDto {

    @NotNull
    @NotBlank
    @Schema(description = "Заголовок создаваемого вопроса")
    private String title;

    @NotNull
    @NotBlank
    @Schema(description = "Описание создаваемого вопроса")
    private String description;

    @NotNull
    @NotEmpty
    @Schema(description = "Теги создаваемого вопроса")
    private List<TagDto> tags;

}
