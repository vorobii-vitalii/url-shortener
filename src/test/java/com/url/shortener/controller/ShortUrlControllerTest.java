package com.url.shortener.controller;

import com.url.shortener.entity.ShortURL;
import com.url.shortener.service.UrlShorterService;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
class ShortUrlControllerTest {

    private static final String VALID_SLUG = "validSlug";
    private static final String WRONG_SLUG = "wrongSlug";
    private static final String FULL_URL = "fullURL";
    private static final LocalDateTime NOW = LocalDateTime.MAX;
    private static final ObjectId OBJECT_ID = ObjectId.get();

    @Mock
    private UrlShorterService urlShorterService;

    private MockMvc mockMvc;

    @BeforeEach
    public void beforeEach() {

        ShortUrlController shortUrlController =
                new ShortUrlController(urlShorterService);

        mockMvc = MockMvcBuilders.standaloneSetup(shortUrlController).build();
    }

    @Test
    public void testGetUrlBySlugGivenValidSlug() throws Exception {

        ShortURL shortURL = ShortURL.builder()
                .slug(VALID_SLUG)
                .fullUrl(FULL_URL)
                .createdAt(NOW)
                .id(OBJECT_ID)
                .build();

        when(urlShorterService.getBySlug(VALID_SLUG))
                .thenReturn(shortURL);

        mockMvc.perform(get("/" + VALID_SLUG))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().string(containsString(VALID_SLUG)))
                .andExpect(content().string(containsString(VALID_SLUG)));
    }




}