package com.wadimbap.coreservice.repository;

import com.wadimbap.coreservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Репозиторий для доступа к данным пользователей в базе данных.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Метод для поиска пользователя по его имени пользователя.
     *
     * @param username имя пользователя для поиска
     * @return Optional с найденным пользователем, если такой существует
     */
    Optional<User> findByUsername(String username);
}
