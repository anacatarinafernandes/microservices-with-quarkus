package com.anafernandes.catalog.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class BookRequest {

    private String title;
    private String originalTitle;
    private Integer isbn;
    private String language;
    private Integer stockAvailable;
    private Double price;
    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;
    private String availability;
    private String authors;
    private String category;
}
