package com.wadimbap.coreservice.util;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class Event implements Serializable {

    private String email;
    private String message;
}