package org.example.urlshortener.controller;

import org.example.urlshortener.dto.ShortUrlRequest;
import org.example.urlshortener.dto.ShortUrlResponse;
import org.example.urlshortener.service.UrlService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/url/shorten")
public class UrlController {


    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }


    //из метода мы должны получить лонгюрл и отправить его сервису
    @PostMapping()
    public ResponseEntity<ShortUrlResponse> shortenUrl(@RequestBody ShortUrlRequest request){


        // метод сервиса который должен проверить наличие юрл в бд и потом или вернуть шортюрл или создать а потом вернуть
        ShortUrlResponse shortUrlResponse = urlService.getUrl(request.getUrl());
        return ResponseEntity.ok(shortUrlResponse);

    }

    @GetMapping("/{shortUrl}")
    public ResponseEntity<Void> redirectToLongUrl(@PathVariable String shortUrl){
        String longUrl = urlService.getLongUrl(shortUrl);

        //перенавправляем на страницу лонгюрл
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(longUrl))
                .build();

    }

}
