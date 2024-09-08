package com.sathish.shop.service.image;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.sathish.shop.dto.ImageDto;
import com.sathish.shop.model.Image;

public interface IImageService {
    Image getImageById(Long id);
    void deleteImageById(Long id);

    List<ImageDto> saveImage(List<MultipartFile> files, Long productId);
    void updateImage(MultipartFile file , Long productId);
}
