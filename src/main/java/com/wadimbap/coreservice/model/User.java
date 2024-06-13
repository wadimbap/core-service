package com.wadimbap.coreservice.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

/**
 * Класс, представляющий пользователя приложения.
 */
@Data
@Entity
public class User {

    /**
     * Уникальный идентификатор пользователя.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Имя пользователя.
     */
    private String username;

    /**
     * Пароль пользователя.
     */
    private String password;

    /**
     * Электронная почта пользователя.
     */
    private String email;

    /**
     * Роль пользователя (например, USER или MODERATOR).
     */
    private String role;

    /**
     * Статус блокировки пользователя.
     */
    private boolean blocked;

    /**
     * Список изображений, загруженных пользователем.
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Image> images;
}
