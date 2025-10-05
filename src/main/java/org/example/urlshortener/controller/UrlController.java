package org.example.urlshortener.controller;

import org.example.urlshortener.dto.ShortUrlRequest;
import org.example.urlshortener.dto.ShortUrlResponse;
import org.example.urlshortener.service.UrlService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UrlController {


    private UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }


    //из метода мы должны получить лонгюрл и отправить его сервису
    @PostMapping("/shorten")
    public ResponseEntity<ShortUrlResponse> shortenUrl(@RequestBody ShortUrlRequest request){


        // метод сервиса который должен проверить наличие юрл в бд и потом или вернуть шортюрл или создать а потом вернуть
        ShortUrlResponse shortUrlResponse = urlService.getUrl(request.getUrl());
        return ResponseEntity.ok(shortUrlResponse);

    }

    @GetMapping("/{shorten}")
    public ResponseEntity<Void> redirectToLongUrl(@PathVariable String shorten){
        urlService.
    }

}
