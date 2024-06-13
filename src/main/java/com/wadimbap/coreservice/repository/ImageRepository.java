package com.wadimbap.coreservice.repository;

import com.wadimbap.coreservice.model.Image;
import com.wadimbap.coreservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Репозиторий для работы с сущностью Image.
 */
@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    /**
     * Находит все изображения пользователя и сортирует их по дате загрузки в порядке убывания.
     *
     * @param user пользователь, чьи изображения необходимо найти
     * @return список изображений пользователя, отсортированных по дате в порядке убывания
     */
    List<Image> findByUserOrderByDateDesc(User user);

    /**
     * Находит все изображения пользователя и сортирует их по идентификатору (id) в порядке возрастания.
     *
     * @param user пользователь, чьи изображения необходимо найти
     * @return список изображений пользователя, отсортированных по id в порядке возрастания
     */
    List<Image> findByUserOrderById(User user);

    /**
     * Находит все изображения пользователя и сортирует их по размеру в порядке убывания.
     *
     * @param user пользователь, чьи изображения необходимо найти
     * @return список изображений пользователя, отсортированных по размеру в порядке убывания
     */
    List<Image> findByUserOrderBySizeDesc(User user);
}
