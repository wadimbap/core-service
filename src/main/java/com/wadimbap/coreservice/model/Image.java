package com.wadimbap.coreservice.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

/**
 * Класс, представляющий изображение, загруженное пользователем.
 */
@Data
@Entity
public class Image {

    /**
     * Уникальный идентификатор изображения.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Дата и время загрузки изображения.
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date date = new Date();

    /**
     * Размер изображения в байтах.
     */
    private long size;

    /**
     * Имя файла изображения.
     */
    private String name;

    /**
     * URL изображения в облачном хранилище.
     */
    private String url;

    /**
     * Пользователь, загрузивший изображение.
     */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
