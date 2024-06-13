package com.wadimbap.coreservice.controller;

import com.wadimbap.coreservice.model.Image;
import com.wadimbap.coreservice.service.ImageService;
import lombok.Data;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/image")
@Data
public class ImageController {

    private final ImageService imageService;

    @PostMapping("/upload")
    public void uploadImage(@RequestParam("userId") Long userId,
                            @RequestParam("files") List<MultipartFile> file) {
        imageService.uploadImages(userId, file);
    }

    @GetMapping("/list")
    public List<Image> listImages(@RequestParam("userId") Long userId) {
        return imageService.findAllUsersImages(userId);
    }

    @GetMapping("/{imageId}")
    public byte[] downloadImage(@RequestParam("userID") Long userId,
                                @PathVariable("imageId") Long imageId) {
        return imageService.downloadImage(userId, imageId);
    }

}
