package com.wadimbap.coreservice.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import com.wadimbap.coreservice.util.Event;
import com.wadimbap.coreservice.model.Image;
import com.wadimbap.coreservice.model.User;
import com.wadimbap.coreservice.repository.ImageRepository;
import com.wadimbap.coreservice.repository.UserRepository;
import lombok.Data;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Data
public class ImageService {

    private final ImageRepository imageRepository;
    private final UserRepository userRepository;
    private final RabbitTemplate rabbitTemplate;
    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket-name}")
    private String bucketName;

    public void uploadImages(Long userId, List<MultipartFile> files) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        long totalSize = 0;
        for (MultipartFile file : files) {
            if (file.getContentType().equals("image/jpeg") || file.getContentType().equals("image/png")) {
                totalSize += file.getSize();

                String fileName = saveImageToYandexCloud(file);

                Image image = new Image();
                image.setName(fileName);
                image.setUrl("https://storage.googleapis.com/" + bucketName + "/" + fileName);
                image.setSize(file.getSize());
                image.setUser(user);
                image.setDate(new Date());
                imageRepository.save(image);
            } else {
                throw new IllegalArgumentException("Invalid file type");
            }
        }
        sendUploadNotification(user.getEmail(), totalSize);
    }

    private String saveImageToYandexCloud(MultipartFile file) {
        String key = UUID.randomUUID() + "-" + file.getOriginalFilename();
        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            metadata.setContentLength(file.getSize());
            amazonS3.putObject(new PutObjectRequest(bucketName, key, file.getInputStream(), metadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file to Google Cloud Storage", e);
        }
        return key;
    }

    public List<Image> findAllUsersImages(Long userId) {
        return new ArrayList<>(userRepository
                .findById(userId)
                .map(User::getImages)
                .orElseThrow(() -> new IllegalArgumentException("User Not Found")));
    }

    public byte[] downloadImage(Long userId, Long imageId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Image image = imageRepository.findById(imageId)
                .orElseThrow(() -> new IllegalArgumentException("Image not found"));

        if (!image.getUser().equals(user)) {
            throw new IllegalArgumentException("Image does not belong to user");
        }

        byte[] imageData = loadImageFromYandexCloud(image.getName());
        sendDownloadNotification(user.getEmail(), image);
        return imageData;
    }

    private byte[] loadImageFromYandexCloud(String key) {
        S3Object s3object = amazonS3.getObject(bucketName, key);
        try (S3ObjectInputStream inputStream = s3object.getObjectContent()) {
            return IOUtils.toByteArray(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Failed to download file from Yandex Cloud Storage", e);
        }
    }

    private void sendUploadNotification(String email, long totalSize) {
        Event event = new Event(email,
                "Images uploaded, total size: " + totalSize + " bytes");
        rabbitTemplate.convertAndSend("mail-queue", event);
    }

    private void sendDownloadNotification(String email, Image image) {
        Event event = new Event(email,
                "Image downloaded: " + image.getName() + ", size: " + image.getSize() + " bytes");
        rabbitTemplate.convertAndSend("mail-queue", event);
    }

    @PreAuthorize("hasRole('MODERATOR')")
    public List<Image> findAllImages() {
        return imageRepository.findAll();
    }
}
