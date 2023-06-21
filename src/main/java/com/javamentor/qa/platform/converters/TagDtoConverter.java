package com.javamentor.qa.platform.converters;

import com.javamentor.qa.platform.models.dto.TagDto;
import com.javamentor.qa.platform.models.entity.question.Tag;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public abstract class TagDtoConverter {
    public static final TagDtoConverter INSTANCE = Mappers.getMapper(TagDtoConverter.class);

    @Mapping(source = "tagDto.description", target = "description", defaultValue = "placeholder description")
    public abstract Tag tagDtoToTag(TagDto tagDto);

    public abstract List<Tag> tagDtoToTagList(List<TagDto> dtoList);

    public abstract List<TagDto> tagToTagDtoList(List<Tag> dtoList);

    public abstract List<Tag> tagDtoListToTagList(List<TagDto> tags);
}
