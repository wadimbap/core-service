package com.wadimbap.coreservice.service;

import com.wadimbap.coreservice.model.User;
import com.wadimbap.coreservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.wadimbap.coreservice.util.Event;

/**
 * Сервис для работы с пользователями приложения.
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RabbitTemplate rabbitTemplate;

    /**
     * Регистрация нового пользователя.
     *
     * @param user пользователь для регистрации
     */
    public void register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("USER");
        user.setBlocked(false);
        userRepository.save(user);
        rabbitTemplate.convertAndSend("register", user);
        sendWelcomeEmail(user.getEmail());
    }

    /**
     * Отправка приветственного сообщения на email пользователя.
     *
     * @param email адрес электронной почты пользователя
     */
    private void sendWelcomeEmail(String email) {
        Event event = new Event(email, "Welcome to the service!");
        rabbitTemplate.convertAndSend("mail-queue", event);
    }

    /**
     * Блокировка пользователя модератором.
     *
     * @param userId идентификатор пользователя для блокировки
     */
    @PreAuthorize("hasRole('MODERATOR')")
    public void blockUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.setBlocked(true);
        userRepository.save(user);
    }

    /**
     * Разблокировка пользователя модератором.
     *
     * @param userId идентификатор пользователя для разблокировки
     */
    @PreAuthorize("hasRole('MODERATOR')")
    public void unblockUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.setBlocked(false);
        userRepository.save(user);
    }
}
