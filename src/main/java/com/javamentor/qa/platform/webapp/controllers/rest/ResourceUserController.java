package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.models.dto.UserDto;
import com.javamentor.qa.platform.service.abstracts.repository.ReadWriteService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class ResourceUserController {

    private ReadWriteService readWriteService;

    public ResourceUserController(@Qualifier("userDtoServiceImpl") ReadWriteService readWriteService) {
        this.readWriteService = readWriteService;
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get user by id", response = UserDto.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "User found"), @ApiResponse(code = 404, message = "User not found")
    })
    public ResponseEntity<UserDto> getUserDtoById(@PathVariable Long id) {
        Optional<UserDto> userDtoOptional = readWriteService.getById((id));
        if (userDtoOptional.isPresent()) {
            return new ResponseEntity<>(userDtoOptional.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}





