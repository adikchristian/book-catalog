package com.adikchristian.bookcatalog.dto;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class BookData {
    private Long id;

    @NotEmpty(message = "Name is Required")
    private String name;

    @DecimalMin(value = "1.0", message = "Please Enter a valid stock")
    private double stock;

    private String description;
}
