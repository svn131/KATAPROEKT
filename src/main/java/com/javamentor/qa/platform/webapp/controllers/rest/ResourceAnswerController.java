package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.exception.ApiRequestException;
import com.javamentor.qa.platform.models.dto.AnswerDto;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.dto.AnswerDtoService;
import com.javamentor.qa.platform.models.dto.CommenAnswerDto;
import com.javamentor.qa.platform.models.entity.question.answer.CommentAnswer;
import com.javamentor.qa.platform.service.abstracts.model.CommentAnswerService;
import com.javamentor.qa.platform.service.abstracts.model.AnswerService;
import com.javamentor.qa.platform.service.abstracts.model.VoteAnswerService;
import io.swagger.annotations.Api;
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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.RequestBody;

import java.security.*;
import java.util.List;
import java.util.Optional;

@Api(tags = "Возвращает ответ за положительное голосование за ответ на вопрос")
@RestController
@RequestMapping("api/user/question/{questionId}/answer/{answerId}")
public class ResourceAnswerController {

    private final CommentAnswerService commentAnswerService;
    private final AnswerService answerService;
    private final AnswerDtoService answerDtoService;
    private final VoteAnswerService voteAnswerService;
    private final UserDetailsService userDetailsService;


    public ResourceAnswerController(CommentAnswerService commentAnswerService, AnswerService answerService, AnswerDtoService answerDtoService, VoteAnswerService voteAnswerService, UserDetailsService userDetailsService) {
        this.commentAnswerService = commentAnswerService;
        this.answerService = answerService;
        this.answerDtoService = answerDtoService;
        this.voteAnswerService = voteAnswerService;
        this.userDetailsService = userDetailsService;
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
    @Operation(
            summary = "Получения списка ответов по id вопроса",
            description = "Возвращает список DTO ответов по id вопроса"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Ответы пользователя найдены",
                            content = {@Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = AnswerDto.class)
                            )}
                    ),
                    @ApiResponse(responseCode = "400", description = "По данному id не найдено вопроса"),
                    @ApiResponse(responseCode = "401", description = "Отказ в доступе"),
                    @ApiResponse(responseCode = "403", description = "Доступ запрещён"),
                    @ApiResponse(responseCode = "404", description = "Ответ не найден")
            }
    )
    public ResponseEntity<List<AnswerDto>> getAllAnswers(
            @Parameter(description = "id вопроса по которому получим ответы")
            @PathVariable Long questionId,
            @AuthenticationPrincipal
            @Parameter(description = "Авторизованный пользователь") User user
    ) {
        List<AnswerDto> list = answerDtoService.getAllAnswersDtoByQuestionId(questionId, user.getId());
        return list.isEmpty()
                ? ResponseEntity.badRequest().build()
                : ResponseEntity.ok(list);
    }

    @ApiOperation(value = "Управление баллами репутации, обновление таблиц Reputation и Votes_on_answers", notes = "Возвращает различные ResponseEntity<?>" +
            " в зависимости от результата")
    @io.swagger.annotations.ApiResponses(value = {
            @io.swagger.annotations.ApiResponse(code = 400, message = "Нельзя голосовать за свои ответы"),
            @io.swagger.annotations.ApiResponse(code = 208, message = "Вы уже проголосовали за этот ответ"),
            @io.swagger.annotations.ApiResponse(code = 200, message = "Вы успешно проголосовали. Внесена новая запись в таблицу Votes_on_answers " +
                    "(В зависимости от результата метода addReputation также выполнено одно связанное действие: " +
                    "'Обновление записи в таблице Reputation с увеличением баллов репутации на 10' или " +
                    "'Новая запись в таблицу Reputation со значением баллов репутации равным 10'")
    })
    @PostMapping("/{id}/upVote")
    public ResponseEntity<Long> answerUpVoteCount(@AuthenticationPrincipal @Parameter
            (description = "Достаём текущего пользователя из SpringSecurity") Principal principal,
                                                  @PathVariable @Parameter(description = "Номер ответа в БД в таблице Answer") Long id) {
        // todo удалить после реализации авторизации
        User user = (User) userDetailsService.loadUserByUsername(principal.getName());
        Optional<Answer> answer = answerService.getAnswerById(id, user.getId());
        if (answer.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            voteAnswerService.addNewVoteOnAnswer(user, answer.orElseThrow());
        }
        return new ResponseEntity<>(voteAnswerService.getAllCurrentUserVotesOfAnswer(answer.get().getId(), user.getId()), HttpStatus.OK);
    }

    @PostMapping("/comment")
    @ApiOperation(value = "Добавление комментария к ответу")
    @ApiResponses(value = {
            @ApiResponse (responseCode = "200", description = "Комментарий успешно добавлен"),
            @ApiResponse (responseCode = "400", description = "Не удалось добавить комментарий к ответу")
    })
    public ResponseEntity<CommenAnswerDto> addAnswerComment(@PathVariable("questionId") Long questionId,
                                                            @PathVariable("answerId") Long answerId,
                                                            @RequestBody String comment) {
        //Проверяем пустой ли комментарий
        if (comment == null || comment.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        //Получаем наш комментраий к ответу
        Optional<CommentAnswer> commentAnswerOptional = commentAnswerService.addCommentAnswer(answerId, questionId, comment);
        //Проверяем не вернулся ли пустой Optional
        if (commentAnswerOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        //Получаем наш DTO
        CommenAnswerDto commenAnswerDto = commentAnswerService.toDto(commentAnswerOptional.get());
        return new ResponseEntity<>(commenAnswerDto, HttpStatus.OK);
    }
}
