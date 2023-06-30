package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.models.dto.RelatedTagDto;
import com.javamentor.qa.platform.service.abstracts.dto.TagDtoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/user/tag/")
@Api(tags = "Related Tag Api")
public class ResourceTagController {

    private final TagDtoService tagDtoService;

    public ResourceTagController(TagDtoService tagDtoService) {
        this.tagDtoService = tagDtoService;
    }

    @GetMapping("related")
    @ApiOperation(value = "Запросить список популярных тегов", notes = "Возвращает 10 самых часто используемых тегов")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "список самых популярных тегов вопрос успешно получен"),
            @ApiResponse(code = 400, message = "плохой запрос")
    })
    public ResponseEntity<List<RelatedTagDto>> getTop10Tags() {
        return new ResponseEntity<>(tagDtoService.getTopTags(), HttpStatus.OK);
    }
}

