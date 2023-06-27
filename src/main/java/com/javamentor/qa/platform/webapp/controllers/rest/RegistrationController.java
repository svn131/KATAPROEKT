package com.javamentor.qa.platform.webapp.controllers.rest;

import com.javamentor.qa.platform.dao.abstracts.model.RoleDao;
import com.javamentor.qa.platform.models.dto.UserRegistrationDto;
import com.javamentor.qa.platform.models.entity.user.Role;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import com.javamentor.qa.platform.webapp.converters.UserConverter;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.Optional;

@Api(tags = "Регистрация нового пользователя", description = "Регистрация и верификация по email")
@RestController
@RequestMapping("/api/user/registration")
public class RegistrationController {

    private final UserService userService;
    private final JavaMailSender javaMailSender;
    private final RoleDao roleService;

    @Value("${spring.mail.properties.time}")
    private int EXPIRATION_TIME_IN_MINUTES;
    @Value("${spring.mail.username}")
    private String fromAddress;
    @Value("${spring.mail.properties.sender.name}")
    private String senderName;

    @Autowired
    public RegistrationController(UserService userService, JavaMailSender javaMailSender, RoleDao roleService) {
        this.userService = userService;
        this.javaMailSender = javaMailSender;
        this.roleService = roleService;
    }

    @PostMapping
    @Operation(summary = "Oтправляет сообщение юзеру, содержащее ссылку с подтверждением email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Сообщение отправлено",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserRegistrationDto.class))}),
            @ApiResponse(responseCode = "400", description = "Указан неверный идентификатор",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден",
                    content = @Content)})
    public ResponseEntity<UserRegistrationDto> sendMessage(@RequestBody UserRegistrationDto registrationDto, HttpServletRequest request) throws MessagingException, UnsupportedEncodingException {
        if (userService.getByEmail(registrationDto.getEmail()).isPresent()) {
            return new ResponseEntity<>(registrationDto, HttpStatus.valueOf("Error: Email is already in use!"));
        }
        User user = UserConverter.INSTANCE.userRegistrationDtoToUser(registrationDto);
        user.setRole(roleService.getByName("USER").orElse(new Role("USER")));
        user.setIsEnabled(false);
        user.setPassword(user.getPassword());
        userService.persist(user);

        String subject = "Please verify your registration";
        String content = "Dear [[name]],<br>"
                         + "Please go to the link below to verify your registration:<br>"
                         + "Expiration time [[time]] minutes<br>"
                         + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                         + "Thank you,<br>"
                         + "Your stackoverflow";

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(registrationDto.getEmail());
        helper.setSubject(subject);
        content = content
                .replace("[[name]]", registrationDto.getFirstName() + " " + registrationDto.getLastName())
                .replace("[[time]]", String.valueOf(EXPIRATION_TIME_IN_MINUTES));
        String email = user.getEmail();
        int code = email.hashCode();
        String siteURL = request.getRequestURL().toString().replace(request.getServletPath(), "");
        String verifyURL = siteURL + "/api/user/registration/verify?email="+ email + "&code=" + code;
        content = content.replace("[[URL]]", verifyURL);
        helper.setText(content, true);

        try {
            javaMailSender.send(message);
            System.out.println("Email sent successfully to: " + registrationDto.getEmail());
        } catch (MailSendException e) {
            System.out.println("Failed to send email to: " + registrationDto.getEmail() + ". Error message: " + e.getMessage());
        }

        return new ResponseEntity<>(registrationDto, HttpStatus.OK);
    }

    @Operation(summary = "Подтверждение пользователя через email")
    @GetMapping("/verify")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное подтверждение",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserRegistrationDto.class))}),
            @ApiResponse(responseCode = "400", description = "Указан неверный идентификатор",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден",
                    content = @Content)})
    public ResponseEntity<String> verify(@Param("email") String email,
                                         @Param("code") Integer code) {
        if (code == null) {
            return new ResponseEntity<>("Код подтверждения не был указан", HttpStatus.BAD_REQUEST);
        }

        Optional<User> optionalUser = userService.getByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            LocalDateTime linkExpirationTime = user.getPersistDateTime().plusMinutes(EXPIRATION_TIME_IN_MINUTES);
            if ((code.equals(user.getEmail().hashCode())) &&
                (LocalDateTime.now().isBefore(linkExpirationTime))) {
                user.setIsEnabled(true);
                userService.update(user);
                return new ResponseEntity<>("Вы успешно зарегистрировались!", HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("Ссылка недействительна", HttpStatus.FORBIDDEN);
    }

}

