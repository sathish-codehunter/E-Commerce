package com.sathish.shop.request;

import java.util.List;
import com.sathish.shop.model.Category;
import com.sathish.shop.model.Image;

import lombok.Data;


@Data
public class AddProductRequest {
    private Long id;
    private String name;
    private String brand;
    private Double price;
    private int inventory;
    private String description;
    private Category category;
    private List<Image> images;
}
