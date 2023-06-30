//package com.javamentor.qa.platform.initdb;
//
//import com.javamentor.qa.platform.models.entity.Comment;
//import com.javamentor.qa.platform.models.entity.CommentType;
//import com.javamentor.qa.platform.models.entity.question.Question;
//import com.javamentor.qa.platform.models.entity.question.Tag;
//import com.javamentor.qa.platform.models.entity.question.answer.Answer;
//import com.javamentor.qa.platform.models.entity.user.Role;
//import com.javamentor.qa.platform.models.entity.user.User;
//import com.javamentor.qa.platform.models.entity.user.reputation.Reputation;
//import com.javamentor.qa.platform.models.entity.user.reputation.ReputationType;
//import com.javamentor.qa.platform.service.abstracts.model.CommentService;
//import com.javamentor.qa.platform.service.abstracts.model.QuestionService;
//import com.javamentor.qa.platform.service.abstracts.model.RoleService;
//import com.javamentor.qa.platform.service.abstracts.model.TagService;
//import com.javamentor.qa.platform.service.abstracts.model.UserService;
//import com.javamentor.qa.platform.service.abstracts.model.AnswerService;
//import com.javamentor.qa.platform.service.abstracts.model.ReputationService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.annotation.PostConstruct;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//public class TestDataInitService {
//
//    private final RoleService roleService;
//
//    private final UserService userService;
//
//    private final TagService tagService;
//
//    private final QuestionService questionService;
//
//    private final AnswerService answerService;
//
//    private final ReputationService reputationService;
//
//    private final CommentService commentService;
//
//
//    public TestDataInitService(RoleService roleService, UserService userService, TagService tagService,
//                               QuestionService questionService, AnswerService answerService, ReputationService reputationService,
//                               CommentService commentService) {
//        this.roleService = roleService;
//        this.userService = userService;
//        this.tagService = tagService;
//        this.questionService = questionService;
//        this.answerService = answerService;
//        this.reputationService = reputationService;
//        this.commentService = commentService;
//    }
//
//
//    @Transactional
//    @PostConstruct
//    public void createEntity() {
//        createRoles();
//        createUsers();
//        createTags();
//        createQuestions();
//        createAnswers();
//        createReputations();
//        createComments();
//    }
//
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    List<Role> roles = new ArrayList<>();
//    List<Role> rolesList = new ArrayList<>();
//
//    private void createRoles() {
//        System.out.println("Хочу создать роль");
//        roles.add(new Role("ADMIN"));
//        roles.add(new Role("USER"));
//        rolesList = roleService.getAll();
//        if (!rolesList.isEmpty()) {
//            for (Role role : rolesList) {
//                roles.removeIf(role1 -> role.getName().equalsIgnoreCase(role1.getName()));
//            }
//        } else {
//            rolesList = roles;
//        }
//    }
//
//    List<User> users = new ArrayList<>();
//    List<User> userList = new ArrayList<>();
//
//    private void createUsers() {
//        User user01 = new User();
//        User user02 = new User();
//        User user03 = new User();
//
//        user01.setEmail("test@test.com");
//        user01.setPassword(passwordEncoder().encode("test"));
//        user01.setFullName("Ivan Ivanov Ivanovich");
//        user01.setPersistDateTime(LocalDateTime.now());
//        user01.setIsEnabled(true);
//        user01.setIsDeleted(false);
//        user01.setCity("Moscow");
//        user01.setLinkSite("https://testsite.com");
//        user01.setLinkGitHub("https://github.com/javaivan");
//        user01.setLinkVk("https://vk.com/javaivan");
//        user01.setAbout("junior-java-developer");
//        user01.setImageLink("test-image");
//        user01.setLastUpdateDateTime(LocalDateTime.now());
//        user01.setNickname("javaivan");
//        user01.setRole(rolesList.get(1));
//        users.add(user01);
//
//        user02.setEmail("petr@mail.ru");
//        user02.setPassword(passwordEncoder().encode("protest"));
//        user02.setFullName("Petrov Petr Petrovich");
//        user02.setPersistDateTime(LocalDateTime.now());
//        user02.setIsEnabled(true);
//        user02.setIsDeleted(false);
//        user02.setCity("Sochi");
//        user02.setLinkSite("https://petrpro.com");
//        user02.setLinkGitHub("https://github.com/petrpro");
//        user02.setLinkVk("https://vk.com/petrpro");
//        user02.setAbout("middle-java-developer");
//        user02.setImageLink("pro-test-image");
//        user02.setLastUpdateDateTime(LocalDateTime.now());
//        user02.setNickname("petrpro");
//        user02.setRole(rolesList.get(0));
//        users.add(user02);
//
//        user03.setEmail("akulinna@mail.ru");
//        user03.setPassword(passwordEncoder().encode("akulinna12345"));
//        user03.setFullName("Akulina Akulinna Akulinovna");
//        user03.setPersistDateTime(LocalDateTime.now());
//        user03.setIsEnabled(true);
//        user03.setIsDeleted(false);
//        user03.setCity("Ufa");
//        user03.setLinkSite("https://akulinna.com");
//        user03.setLinkGitHub("https://github.com/akulinna");
//        user03.setLinkVk("https://vk.com/akulinna");
//        user03.setAbout("senior-java-developer");
//        user03.setImageLink("akulinna-test-image");
//        user03.setLastUpdateDateTime(LocalDateTime.now());
//        user03.setNickname("akulinna");
//        user03.setRole(rolesList.get(0));
//        users.add(user03);
//
//        userList = userService.getAll();
//
//        for (User user : userList) {
//            users.removeIf(user1 -> user.getEmail().equalsIgnoreCase(user1.getEmail()));
//        }
//        if (!users.isEmpty()) {
//            userService.persistAll(users);
//        }
//        userList.addAll(users);
//    }
//
//    List<Tag> tags = new ArrayList<>();
//    List<Tag> tagsList = new ArrayList<>();
//
//    private void createTags() {
//        Tag tag01 = new Tag();
//        Tag tag02 = new Tag();
//        Tag tag03 = new Tag();
//        Tag tag04 = new Tag();
//
//        tag01.setName("java");
//        tag01.setDescription("Java is a high-level object-oriented programming language.");
//        tag01.setPersistDateTime(LocalDateTime.now());
//        tag01.setQuestions(questionsList);
//        tags.add(tag01);
//
//        tag02.setName("spring");
//        tag02.setDescription("The Spring Framework is an open-source framework for application " +
//                "development on the Java platform.");
//        tag02.setPersistDateTime(LocalDateTime.now());
//        tag02.setQuestions(questionsList);
//        tags.add(tag02);
//
//        tag03.setName("datasource");
//        tag03.setDescription("Datasource is a name given to the connection set up to a database " +
//                "from a server.");
//        tag03.setPersistDateTime(LocalDateTime.now());
//        tag03.setQuestions(questionsList);
//        tags.add(tag03);
//
//        tag04.setName("spring boot");
//        tag04.setDescription("Spring Boot is a framework that allows to easily create " +
//                "Spring-powered, production-grade applications and services with the absolute minimum fuss.");
//        tag04.setPersistDateTime(LocalDateTime.now());
//        tag04.setQuestions(questionsList);
//        tags.add(tag04);
//
//        tagsList = tagService.getAll();
//
//        for (Tag tag : tagsList) {
//            tags.removeIf(tag1 -> tag.getName().equalsIgnoreCase(tag1.getName()));
//        }
//        if (!tags.isEmpty()) {
//            tagService.persistAll(tags);
//        }
//        tagsList.addAll(tags);
//    }
//
//    List<Question> questions = new ArrayList<>();
//    List<Question> questionsList = new ArrayList<>();
//
//    private void createQuestions() {
//        Question question01 = new Question();
//        Question question02 = new Question();
//        Question question03 = new Question();
//        Question question04 = new Question();
//
//        question01.setTitle("question number one");
//        question01.setDescription("how to create db in spring boot");
//        question01.setPersistDateTime(LocalDateTime.now());
//        question01.setUser(userList.get(0));
//        question01.setTags(tagsList);
//        question01.setLastUpdateDateTime(LocalDateTime.now());
//        question01.setIsDeleted(false);
//        question01.setAnswers(answersList);
//        questions.add(question01);
//
//        question02.setTitle("question number two");
//        question02.setDescription("top programming languages in 2023");
//        question02.setPersistDateTime(LocalDateTime.now());
//        question02.setUser(userList.get(2));
//        question02.setTags(tagsList);
//        question02.setLastUpdateDateTime(LocalDateTime.now());
//        question02.setIsDeleted(false);
//        question02.setAnswers(answersList);
//        questions.add(question02);
//
//        question03.setTitle("question number three");
//        question03.setDescription("help me to start spring boot application");
//        question03.setPersistDateTime(LocalDateTime.now());
//        question03.setUser(userList.get(0));
//        question03.setTags(tagsList);
//        question03.setLastUpdateDateTime(LocalDateTime.now());
//        question03.setIsDeleted(false);
//        question03.setAnswers(answersList);
//        questions.add(question03);
//
//        question04.setTitle("question number four");
//        question04.setDescription("persist or merge JPA + Hibernate spring");
//        question04.setPersistDateTime(LocalDateTime.now());
//        question04.setUser(userList.get(1));
//        question04.setTags(tagsList);
//        question04.setLastUpdateDateTime(LocalDateTime.now());
//        question04.setIsDeleted(false);
//        question04.setAnswers(answersList);
//        questions.add(question04);
//
//        questionsList = questionService.getAll();
//        for (Question question : questionsList) {
//            questions.removeIf(question1 -> (question.getTitle().equalsIgnoreCase(question1.getTitle())
//                    & question.getDescription().equalsIgnoreCase(question1.getDescription())));
//        }
//        if (!questions.isEmpty()) {
//            questionService.persistAll(questions);
//        }
//        questionsList.addAll(questions);
//    }
//
//    List<Answer> answers = new ArrayList<>();
//    List<Answer> answersList = new ArrayList<>();
//
//    private void createAnswers() {
//        Answer answer01 = new Answer();
//        Answer answer02 = new Answer();
//        Answer answer03 = new Answer();
//
//        answer01.setPersistDateTime(LocalDateTime.now());
//        answer01.setUpdateDateTime(LocalDateTime.now());
//        answer01.setQuestion(questionsList.get(0));
//        answer01.setUser(userList.get(1));
//        answer01.setHtmlBody("answer number one");
//        answer01.setIsHelpful(false);
//        answer01.setIsDeleted(false);
//        answer01.setIsDeletedByModerator(false);
//        answers.add(answer01);
//
//        answer02.setPersistDateTime(LocalDateTime.now());
//        answer02.setUpdateDateTime(LocalDateTime.now());
//        answer02.setQuestion(questionsList.get(2));
//        answer02.setUser(userList.get(1));
//        answer02.setHtmlBody("answer number two");
//        answer02.setIsHelpful(false);
//        answer02.setIsDeleted(false);
//        answer02.setIsDeletedByModerator(false);
//        answers.add(answer02);
//
//        answer03.setPersistDateTime(LocalDateTime.now());
//        answer03.setUpdateDateTime(LocalDateTime.now());
//        answer03.setQuestion(questionsList.get(3));
//        answer03.setUser(userList.get(2));
//        answer03.setHtmlBody("answer number three");
//        answer03.setIsHelpful(false);
//        answer03.setIsDeleted(false);
//        answer03.setIsDeletedByModerator(false);
//        answers.add(answer03);
//
//        answersList = answerService.getAll();
//        for (Answer answer : answersList) {
//            answers.removeIf(answer1 -> answer.getHtmlBody().equalsIgnoreCase(answer1.getHtmlBody()));
//        }
//        if (!answers.isEmpty()) {
//            answerService.persistAll(answers);
//        }
//        answersList.addAll(answers);
//    }
//
//    List<Reputation> reputations = new ArrayList<>();
//    List<Reputation> reputationsList = new ArrayList<>();
//
//    private void createReputations() {
//        Reputation reputation01 = new Reputation();
//        Reputation reputation02 = new Reputation();
//
//        reputation01.setPersistDate(LocalDateTime.now());
//        reputation01.setAuthor(userList.get(2));
//        reputation01.setSender(userList.get(2));
//        reputation01.setCount(1000);
//        reputation01.setType(ReputationType.Question);
//        reputation01.setQuestion(questionsList.get(0));
//        reputations.add(reputation01);
//
//        reputation02.setPersistDate(LocalDateTime.now());
//        reputation02.setAuthor(userList.get(1));
//        reputation02.setSender(userList.get(1));
//        reputation02.setCount(70);
//        reputation02.setType(ReputationType.Question);
//        reputation02.setQuestion(questionsList.get(0));
//        reputations.add(reputation02);
//
//        reputationsList = reputationService.getAll();
//
//        for (Reputation reputation : reputationsList) {
//            reputations.removeIf(reputation1 -> reputation.getAuthor().equals(reputation1.getAuthor()));
//        }
//        if (!reputations.isEmpty()) {
//            reputationService.persistAll(reputations);
//        }
//    }
//
//    List<Comment> comments = new ArrayList<>();
//
//    List<Comment> commentsList = new ArrayList<>();
//
//    private void createComments() {
//        Comment comment01 = new Comment();
//        Comment comment02 = new Comment();
//        Comment comment03 = new Comment();
//
//        comment01.setCommentType(CommentType.QUESTION);
//        comment01.setText("Тестовый текст #1 для коментария под вопросом");
//        comment01.setUser(userList.get(1));
//        comment01.setPersistDateTime(LocalDateTime.now());
//        comment01.setLastUpdateDateTime(LocalDateTime.now());
//        comments.add(comment01);
//
//        comment02.setCommentType(CommentType.QUESTION);
//        comment02.setText("Тестовый текст #2 для коментария под вопросом");
//        comment02.setUser(userList.get(2));
//        comment02.setPersistDateTime(LocalDateTime.now());
//        comment02.setLastUpdateDateTime(LocalDateTime.now());
//        comments.add(comment02);
//
//        comment03.setCommentType(CommentType.QUESTION);
//        comment03.setText("Тестовый текст #3 для коментария под вопросом");
//        comment03.setUser(userList.get(0));
//        comment03.setPersistDateTime(LocalDateTime.now());
//        comment03.setPersistDateTime(LocalDateTime.now());
//        comments.add(comment03);
//
//        commentsList = commentService.getAll();
//        for (Comment comment: commentsList) {
//            comments.removeIf(comment1 -> comment.getText().equalsIgnoreCase(comment1.getText()));
//        }
//        if (!comments.isEmpty()) {
//            commentService.persistAll(comments);
//        }
//    }
//
//}

