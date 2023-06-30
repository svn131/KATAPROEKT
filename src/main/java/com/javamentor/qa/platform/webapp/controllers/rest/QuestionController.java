package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.exception.ApiRequestException;
import com.javamentor.qa.platform.service.abstracts.dto.CommentDtoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/user/question")
public class QuestionController {

    private final CommentDtoService commentDtoService;

    public QuestionController(CommentDtoService commentDtoService) {
        this.commentDtoService = commentDtoService;
    }

    @GetMapping("{id}/comment")
    @ApiOperation("return all comments of question")
    public ResponseEntity<?> getAllCommentsOnQuestion(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(commentDtoService.getAllQuestionCommentDtoById(id), HttpStatus.OK) ;
        } catch (ApiRequestException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}

