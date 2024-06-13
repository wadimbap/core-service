package com.wadimbap.coreservice.controller;

import com.wadimbap.coreservice.model.Image;
import com.wadimbap.coreservice.service.ImageService;
import com.wadimbap.coreservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Контроллер для обработки запросов, связанных с действиями модератора.
 */
@RestController
@RequestMapping("/moderator")
@RequiredArgsConstructor
public class ModeratorController {

    private final ImageService imageService;
    private final UserService userService;

    /**
     * Метод для получения списка всех изображений от всех пользователей.
     *
     * @return список всех изображений
     */
    @GetMapping("/all-images")
    public List<Image> findAllImages() {
        return imageService.findAllImages();
    }

    /**
     * Метод для блокировки пользователя модератором.
     *
     * @param userId идентификатор пользователя для блокировки
     */
    @PostMapping("/block-user")
    public void blockUser(@RequestParam("userId") Long userId) {
        userService.blockUser(userId);
    }

    /**
     * Метод для разблокировки пользователя модератором.
     *
     * @param userId идентификатор пользователя для разблокировки
     */
    @PostMapping("/unblock-user")
    public void unblockUser(@RequestParam("userId") Long userId) {
        userService.unblockUser(userId);
    }
}
