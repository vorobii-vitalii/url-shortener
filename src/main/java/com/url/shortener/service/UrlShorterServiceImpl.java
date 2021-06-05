package com.url.shortener.service;

import com.url.shortener.entity.ShortURL;
import com.url.shortener.exception.NotUniqueSlugMatchException;
import com.url.shortener.exception.SlugNotFoundException;
import com.url.shortener.exception.SlugWasTakenException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
@RequiredArgsConstructor
public class UrlShorterServiceImpl implements UrlShorterService {
    private static final String SLUG = "slug";

    private final MongoOperations mongoOperations;

    @Override
    public ShortURL createShortUrl(String inputUrl, String slug) {
        List<ShortURL> slugUrls =
                mongoOperations.find(constructSlugEquals(slug), ShortURL.class);

        if (!slugUrls.isEmpty()) {
            throw new SlugWasTakenException();
        }

        ShortURL shortURL = ShortURL.builder()
                .fullUrl(inputUrl)
                .slug(slug)
                .createdAt(LocalDateTime.now())
                .build();

        return mongoOperations.insert(shortURL);
    }

    @Override
    public ShortURL getBySlug(String slug) {
        List<ShortURL> slugUrls =
                mongoOperations.find(constructSlugEquals(slug), ShortURL.class);

        if (slugUrls.isEmpty()) {
            throw new SlugNotFoundException();
        }

        if (slugUrls.size() > 1) {
            throw new NotUniqueSlugMatchException();
        }

        return slugUrls.get(0);
    }

    private Query constructSlugEquals(String slug) {
        return query(where(SLUG).is(slug));
    }

}
