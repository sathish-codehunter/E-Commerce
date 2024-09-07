package com.sathish.shop.service.image;

import java.io.IOException;
import java.sql.SQLException;

import javax.sql.rowset.serial.SerialBlob;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sathish.shop.exceptions.ImageNotFoundException;
import com.sathish.shop.model.Image;
import com.sathish.shop.repository.ImageRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageService{

    private final ImageRepository imageRepository;

    @Override
    public void deleteImageById(Long id) {
        imageRepository.findById(id).ifPresentOrElse(imageRepository::delete, ()-> {throw new ImageNotFoundException("Image Not Found");});
    }

    @Override
    public Image getImageById(Long id) {
        return imageRepository.findById(id).orElseThrow(()-> new ImageNotFoundException("Image Not Found"));
    }

    @Override
    public Image saveImage(MultipartFile file, Long productId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void updateImage(MultipartFile file, Long productId) {
        Image image = getImageById(productId);
        try {
            image.setFileName(image.getFileName());
            image.setImage(new SerialBlob(file.getBytes()));
            imageRepository.save(image);
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    
}
