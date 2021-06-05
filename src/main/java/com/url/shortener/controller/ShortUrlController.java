package com.url.shortener.controller;

import com.url.shortener.dto.ShortUrlRequest;
import com.url.shortener.entity.ShortURL;
import com.url.shortener.service.UrlShorterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class ShortUrlController {
    private final UrlShorterService urlShorterService;

    @GetMapping("/{slug}")
    public ShortURL getUrlsBySlug(@PathVariable("slug") String slug) {
        return urlShorterService.getBySlug(slug);
    }

    @PostMapping(value = "create", consumes = "application/json", produces = "application/json")
    public ShortURL createShortURL(@RequestBody @Valid ShortUrlRequest shortUrlRequest) {
        return urlShorterService.createShortUrl(
                shortUrlRequest.getFullUrl(), shortUrlRequest.getSlug());
    }

}
