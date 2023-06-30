package com.javamentor.qa.platform.converters;

import com.javamentor.qa.platform.models.dto.QuestionCreateDto;
import com.javamentor.qa.platform.models.dto.QuestionDto;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.user.User;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class QuestionConverter {
    public static final QuestionConverter INSTANCE = Mappers.getMapper(QuestionConverter.class);


    @Mapping(source = "questionCreateDto.tags", target = "tags") // listTagDto --> Tag
    @Mapping(source = "author", target = "user") // id --> author
    public abstract Question questionCreateDtoToQuestion(QuestionCreateDto questionCreateDto, User author);


    @Mapping(source = "question.tags", target = "listTagDto") //Tag --> listTagDto
    @Mapping(source = "question.user.id", target = "authorId") // author --> id
    @Mapping(source = "question.user.fullName", target = "authorName")
    public abstract QuestionDto questionToQuestionDto(Question question);

    @Mapping(source = "questionDto.listTagDto", target = "tags")
    @Mapping(source = "author", target = "user")
    @Mapping(source = "questionDto.id", target = "id")
    @Mapping(source = "questionDto.persistDateTime", target = "persistDateTime")
    @Mapping(source = "questionDto.lastUpdateDateTime", target = "lastUpdateDateTime")
    public abstract Question questionDtoToQuestion(QuestionDto questionDto, User author);

}




