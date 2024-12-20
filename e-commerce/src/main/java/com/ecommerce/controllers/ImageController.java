package com.ecommerce.controllers;

import com.ecommerce.dto.ImageDto;
import com.ecommerce.exceptions.ImageNotFoundException;
import com.ecommerce.models.Image;
import com.ecommerce.response.ApiResponse;
import com.ecommerce.services.image.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.sql.SQLException;
import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("${api.prefix}/image")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @PostMapping("/upload-image")
    public ResponseEntity<ApiResponse> saveImages(@RequestParam List<MultipartFile> files, @RequestParam Long productId) {

        try {
            List<ImageDto> imageDtos = imageService.saveImages(files, productId);
            return ResponseEntity.ok(new ApiResponse("Upload Success.", imageDtos));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Upload failed.", e.getMessage()));

        }
    }

    @GetMapping("/download-image/{imageId}")
    public ResponseEntity<Resource> downloadImage(@PathVariable Long imageId) {

        try {
            Image image = imageService.getImageById(imageId);
            ByteArrayResource resource = new ByteArrayResource(image.getImage().getBytes(1, (int) image.getImage().length()));

            return ResponseEntity.ok().contentType(MediaType.parseMediaType(image.getFileType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment: filename=\"" + image.getFileName() + "\"")
                    .body(resource);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/update-image-by-id")
    public ResponseEntity<ApiResponse> updateImage(@RequestParam Long imageId, @RequestParam MultipartFile file) {
        try {
            Image image = imageService.getImageById(imageId);
            if (image == null) {
                imageService.updateImageByImageId(file, imageId);
                return ResponseEntity.ok(new ApiResponse("Update Success", null));
            }
        } catch (ImageNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
        return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                .body(new ApiResponse("Update failed", INTERNAL_SERVER_ERROR));
    }

    @DeleteMapping("/delete-image-by-id")
    public ResponseEntity<ApiResponse> deleteImage(@RequestParam Long imageId) {
        try {
            Image image = imageService.getImageById(imageId);
            if (image == null) {
                imageService.deleteImageById(imageId);
                return ResponseEntity.ok(new ApiResponse("Delete Success", null));
            }
        } catch (ImageNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
        return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                .body(new ApiResponse("Delete failed", INTERNAL_SERVER_ERROR));
    }

}
