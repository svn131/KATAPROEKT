package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.converters.QuestionConverter;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.QuestionService;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.javamentor.qa.platform.models.dto.QuestionCreateDto;
import com.javamentor.qa.platform.models.dto.QuestionDto;

import javax.validation.Valid;

@RestController
@RequestMapping("api/user/question")
@ApiOperation("Question Api")
public class ResourceQuestionController {
    //TODO поменять заглушечного пользователя на секьюрного
    private final UserService testUserService;
    private final QuestionService questionService;

    public ResourceQuestionController(UserService testUserService, QuestionService questionService) {
        this.testUserService = testUserService;
        this.questionService = questionService;
    }

    @PostMapping
    @ApiOperation(value = "Создать новый вопрос", notes = "Собирает Dto нового вопроса")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "новый вопрос успешно сохранен"),
            @ApiResponse(code = 400, message = "плохой запрос")
    })
    public ResponseEntity<QuestionDto> addQuestion(@ApiParam(name = "questionCreateDto", value = "Объект создания вопроса") @Valid @RequestBody QuestionCreateDto questionCreateDto) {
        User testUser = testUserService.getAll().get(0);
        Question newQuestion = QuestionConverter.INSTANCE.questionCreateDtoToQuestion(questionCreateDto, testUser);
        questionService.persist(newQuestion);
        return new ResponseEntity<>(QuestionConverter.INSTANCE.questionToQuestionDto(newQuestion), HttpStatus.OK);
    }
}
