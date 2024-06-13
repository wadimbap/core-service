package com.wadimbap.coreservice.service;

import com.wadimbap.coreservice.model.User;
import com.wadimbap.coreservice.repository.UserRepository;
import lombok.Data;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.wadimbap.coreservice.util.Event;


@Service
@Data
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RabbitTemplate rabbitTemplate;

    public void register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("USER");
        userRepository.save(user);
        rabbitTemplate.convertAndSend("register", user);
        sendWelcomeEmail(user.getEmail());
    }

    private void sendWelcomeEmail(String email) {
        Event event = new Event(email, "Welcome to service");
        rabbitTemplate.convertAndSend("mail-queue", event);
    }
}
