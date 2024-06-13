package com.wadimbap.coreservice.controller;

import com.wadimbap.coreservice.model.Image;
import com.wadimbap.coreservice.service.ImageService;
import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/moderator")
@Data
public class ModeratorController {

    private final ImageService imageService;

    @GetMapping("/all-images")
    public List<Image> findAllImages() {
        return imageService.findAllImages();
    }
}
