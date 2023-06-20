package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.exception.ApiRequestException;
import com.javamentor.qa.platform.models.dto.AnswerDto;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.dto.AnswerDtoService;
import com.javamentor.qa.platform.service.abstracts.model.AnswerService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("api/user/question/{questionId}/answer/{answerId}")
public class ResourceAnswerController {

    private final AnswerService answerService;
    private final AnswerDtoService answerDtoService;

    public ResourceAnswerController(AnswerService answerService, AnswerDtoService answerDtoService) {
        this.answerService = answerService;
        this.answerDtoService = answerDtoService;
    }

    @DeleteMapping
    @ApiOperation("mark answer as deleted")
    public ResponseEntity<HttpStatus> markAsDeleted(@PathVariable("answerId") Long answerId) {
        try {
            answerService.markAsDeleted(answerId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ApiRequestException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    @Operation(summary = "Получения списка ответов по id вопроса", description = "Возвращает список DTO ответов по id вопроса")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ответы пользователя найдены",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AnswerDto.class))}),
            @ApiResponse(responseCode = "400", description = "По данному id не найдено вопроса"),
            @ApiResponse(responseCode = "401", description = "Отказ в доступе"),
            @ApiResponse(responseCode = "403", description = "Доступ запрещён"),
            @ApiResponse(responseCode = "404", description = "Ответ не найден")})
    public ResponseEntity<List<AnswerDto>> getAllAnswers(@Parameter(description = "id вопроса по которому получим ответы") @PathVariable Long questionId,
                                                         @AuthenticationPrincipal @Parameter(description = "Авторизованный пользователь") User user) {
        List<AnswerDto> list = answerDtoService.getAllAnswersDtoByQuestionId(questionId, user.getId());
        return list.isEmpty()
                ? new ResponseEntity<>(HttpStatus.BAD_REQUEST)
                : new ResponseEntity<>(list, HttpStatus.OK);

    }
}
