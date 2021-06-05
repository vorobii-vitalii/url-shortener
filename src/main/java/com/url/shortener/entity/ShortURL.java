package com.url.shortener.entity;

import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;

@Document(collection = "short_url")
@Data
@Builder
public class ShortURL {

    @MongoId
    private ObjectId id;

    private String slug;

    private String fullUrl;

    private LocalDateTime createdAt;

}
