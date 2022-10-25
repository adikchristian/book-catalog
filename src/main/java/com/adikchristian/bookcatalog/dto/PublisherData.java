package com.adikchristian.bookcatalog.dto;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class PublisherData {
    
    private Long id;

    @NotEmpty(message = "Name is required")
    private String name;

    @NotEmpty(message = "phone is required")
    private String phone;

    private String address;

}
