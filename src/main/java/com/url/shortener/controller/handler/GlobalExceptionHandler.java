package com.url.shortener.controller.handler;

import com.url.shortener.exception.NotUniqueSlugMatchException;
import com.url.shortener.exception.SlugNotFoundException;
import com.url.shortener.exception.SlugWasTakenException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotUniqueSlugMatchException.class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public String handleNotUniqueSlug(NotUniqueSlugMatchException ignored) {
        return "Not unique URL was found by a given slug";
    }

    @ExceptionHandler(SlugNotFoundException.class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public String handleSlugWasNotFound(SlugNotFoundException ignored) {
        return "URL was not found by a given slug";
    }

    @ExceptionHandler(SlugWasTakenException.class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public String handleSlugWasTaken(SlugWasTakenException ignored) {
        return "Slug was already taken";
    }

}
