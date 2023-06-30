package com.javamentor.qa.platform.webapp.converters;

import com.javamentor.qa.platform.models.dto.UserRegistrationDto;
import com.javamentor.qa.platform.models.entity.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;


@Mapper
public abstract class UserConverter {

    public static UserConverter INSTANCE = Mappers.getMapper(UserConverter.class);


    @Mappings({
            @Mapping(target="fullName", expression = "java(userRegistrationDto.getFirstName() + \" \" + userRegistrationDto.getLastName())"),
    })
    public abstract User userRegistrationDtoToUser(UserRegistrationDto userRegistrationDto);

    @Mappings({
            @Mapping(target="firstName", expression = "java(user.getFullName().split(\" \")[0])"),
            @Mapping(target="lastName", expression = "java(user.getFullName().split(\" \")[1])")
    })
    public abstract UserRegistrationDto userToUserRegistrationDto(User user);

}

