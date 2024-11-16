package com.ecommerce.services.image;

import com.ecommerce.dto.ImageDto;
import com.ecommerce.models.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {

    List<ImageDto> saveImages(List<MultipartFile> files, Long productId);

    Image getImageById(Long imageId);

    void updateImageByImageId(MultipartFile file, Long imageId);

    void deleteImageById(Long imageId);
}
