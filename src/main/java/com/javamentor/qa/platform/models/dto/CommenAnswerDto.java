package com.javamentor.qa.platform.models.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "Коменнтарий к ответу")
public class CommenAnswerDto {
    @Schema(description = "id комментария")
    private Long id;
    @Schema(description = "id ответа")
    private Long answerId;
    @Schema(description = "дата редактирования")
    private LocalDateTime lastRedactionDate;
    @Schema(description = "дата создания ответа")
    private LocalDateTime persistDate;
    @NotNull
    @NotEmpty
    @Schema(description = "текст комментария")
    private String text;
    @Schema(description = "id пользователя")
    private Long userId;
    @Schema(description = "ссылка на картинку пользователя")
    private String imageLink;
    @Schema(description = "репутация")
    private Long reputation;
}
