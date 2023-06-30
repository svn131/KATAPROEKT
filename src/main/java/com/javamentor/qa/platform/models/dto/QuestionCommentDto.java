package com.javamentor.qa.platform.models.dto;


import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Schema(description = "комментарий к вопросу")
public class QuestionCommentDto {

    @Parameter(description = "comment id")
    private Long id;
    @Parameter(description = "question id")
    private Long questionId;
    @Parameter(description = "last reduction date")
    private LocalDateTime lastRedactionDate;
    @Parameter(description = "date of creation")
    private LocalDateTime persistDate;
    @NotNull
    @NotEmpty
    @Parameter(description = "comment text")
    private String text;
    @NotNull
    @Parameter(description = "creator user id")
    private Long userId;
    @Parameter(description = "creator user id image link")
    private String imageLink;
    @Parameter(description = "reputation of creator user id")
    private Integer reputation;
}

