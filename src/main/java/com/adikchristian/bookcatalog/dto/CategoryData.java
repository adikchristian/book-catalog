package com.adikchristian.bookcatalog.dto;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class CategoryData {

    private Long id;

    @NotEmpty(message = "Name is required")
    private String name;
}
