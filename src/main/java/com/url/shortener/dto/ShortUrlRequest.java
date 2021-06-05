package com.url.shortener.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ShortUrlRequest {

    @NotBlank
    private String slug;

    @NotBlank
    private String fullUrl;

}
