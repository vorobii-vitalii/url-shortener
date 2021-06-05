package com.url.shortener.service;


import com.url.shortener.entity.ShortURL;
import com.url.shortener.exception.NotUniqueSlugMatchException;
import com.url.shortener.exception.SlugNotFoundException;
import com.url.shortener.exception.SlugWasTakenException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoOperations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UrlShorterServiceImplTest {

    private static final String INPUT_URL = "inputUrl";
    private static final String SLUG = "slug";

    @InjectMocks
    private UrlShorterServiceImpl urlShorterService;

    @Mock
    private MongoOperations mongoOperations;

    @Test
    public void testCreateShortUrlGivenTakenSlug() {

        ShortURL shortURL = mock(ShortURL.class);

        when(mongoOperations.find(any(), any()))
                .thenReturn(List.of(shortURL));

        assertThrows(
                SlugWasTakenException.class,
                () -> urlShorterService.createShortUrl(INPUT_URL, SLUG));
    }

    @Test
    public void testCreateShortUrlGivenSlugNotTaken() {

        when(mongoOperations.find(any(), eq(ShortURL.class)))
                .thenReturn(Collections.emptyList());

        urlShorterService.createShortUrl(INPUT_URL, SLUG);

        verify(mongoOperations, times(1)).insert(any(ShortURL.class));
    }

    @Test
    public void testGetBySlugGivenMultiResult() {

        List<ShortURL> shortUrls =
                List.of(mock(ShortURL.class), mock(ShortURL.class));

        when(mongoOperations.find(any(), eq(ShortURL.class)))
                .thenReturn(shortUrls);

        assertThrows(NotUniqueSlugMatchException.class,
                () -> urlShorterService.getBySlug(SLUG));
    }


    @Test
    public void testGetBySlugGivenNoResult() {

        when(mongoOperations.find(any(), eq(ShortURL.class)))
                .thenReturn(Collections.emptyList());

        assertThrows(SlugNotFoundException.class,
                () -> urlShorterService.getBySlug(SLUG));
    }

    @Test
    public void testGetBySlugGivenExactlyOneMatch() {

        ShortURL shortURL = mock(ShortURL.class);

        when(mongoOperations.find(any(), eq(ShortURL.class)))
                .thenReturn(List.of(shortURL));

        ShortURL foundShortURL = urlShorterService.getBySlug(SLUG);

        assertEquals(shortURL, foundShortURL);

        verify(mongoOperations, times(1))
                .find(any(), eq(ShortURL.class));
    }

}
