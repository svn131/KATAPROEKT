package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.exception.ApiRequestException;
import com.javamentor.qa.platform.service.abstracts.model.AnswerService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/user/question/{questionId}/answer/{answerId}")
public class ResourceAnswerController {

    private final AnswerService answerService;

    public ResourceAnswerController(AnswerService answerService) {
        this.answerService = answerService;
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
}
