package com.wadimbap.coreservice.controller;

import com.wadimbap.coreservice.model.User;
import com.wadimbap.coreservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллер для обработки запросов, связанных с пользователями.
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * Метод для регистрации нового пользователя.
     *
     * @param user данные пользователя для регистрации
     */
    @PostMapping("/register")
    public void register(@RequestBody User user) {
        userService.register(user);
    }
}
