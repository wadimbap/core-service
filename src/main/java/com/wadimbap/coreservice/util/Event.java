package com.wadimbap.coreservice.util;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * Класс, представляющий событие, содержащее информацию о получателе и сообщении.
 */
@Data
@AllArgsConstructor
public class Event implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Электронная почта получателя.
     */
    private String email;

    /**
     * Сообщение, которое будет отправлено.
     */
    private String message;
}
