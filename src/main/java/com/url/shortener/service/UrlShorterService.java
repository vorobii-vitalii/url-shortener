package com.url.shortener.service;

import com.url.shortener.entity.ShortURL;

public interface UrlShorterService {
    ShortURL createShortUrl(String inputUrl, String slug);
    ShortURL getBySlug(String slug);
}
