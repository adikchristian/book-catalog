package com.adikchristian.bookcatalog.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class AuthorData {
    
    private Long id;

    @NotEmpty(message = "Name is required")
    private String name;

    @Past
    @NotNull(message = "Brith Date is reruired")
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDate brithDate;
}
