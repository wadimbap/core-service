package com.wadimbap.coreservice.controller;

import com.wadimbap.coreservice.model.Image;
import com.wadimbap.coreservice.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Контроллер для обработки запросов, связанных с изображениями.
 */
@RestController
@RequestMapping("/image")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    /**
     * Метод для загрузки изображений пользователем.
     *
     * @param userId идентификатор пользователя, загружающего изображения
     * @param files  список файлов (изображений) для загрузки
     */
    @PostMapping("/upload")
    public void uploadImage(@RequestParam("userId") Long userId,
                            @RequestParam("files") List<MultipartFile> files) {
        imageService.uploadImages(userId, files);
    }

    /**
     * Метод для получения списка изображений, загруженных пользователем.
     *
     * @param userId идентификатор пользователя
     * @return список изображений пользователя
     */
    @GetMapping("/list")
    public List<Image> listImages(@RequestParam("userId") Long userId) {
        return imageService.findAllUserImages(userId);
    }

    /**
     * Метод для получения отсортированного списка изображений, загруженных пользователем.
     *
     * @param userId идентификатор пользователя
     * @param sortBy поле для сортировки (доступны: "date", "id", "size")
     * @return отсортированный список изображений пользователя
     */
    @GetMapping("/list")
    public List<Image> listImages(
            @RequestParam("userId") Long userId,
            @RequestParam(value = "sortBy", defaultValue = "date") String sortBy
    ) {
        return imageService.findAllUserImagesSorted(userId, sortBy);
    }

    /**
     * Метод для скачивания изображения пользователем.
     *
     * @param userId  идентификатор пользователя
     * @param imageId идентификатор изображения для скачивания
     * @return байтовый массив с данными изображения
     */
    @GetMapping("/{imageId}")
    public byte[] downloadImage(@RequestParam("userId") Long userId,
                                @PathVariable("imageId") Long imageId) {
        return imageService.downloadImage(userId, imageId);
    }
}
