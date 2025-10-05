package org.example.urlshortener.service;

import jakarta.transaction.Transactional;
import org.example.urlshortener.base62.Base62Converter;
import org.example.urlshortener.dto.ShortUrlResponse;
import org.example.urlshortener.entity.Url;
import org.example.urlshortener.repository.UrlRepository;
import org.springframework.stereotype.Service;

@Service
public class UrlService {
    private UrlRepository urlRepository;
    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    //обьединили в одну транзакцию чтобы операция по добавлению сущности не была раздроблена как-либо
    @Transactional
    public ShortUrlResponse getUrl(String longUrl) {
        // дто для передачи в контроллер
        ShortUrlResponse shortUrlResponse;

        //сущность для работы с бд
        Url newUrl;

        //проверяем есть ли уже в бд лонгюрл, если да то возвращаем сущность, если нет, то создаем
        newUrl = urlRepository.findByLongUrl(longUrl)
                .orElseGet(()->{

                    // создаем локальную сущность
                    Url localNewUrl = new Url(longUrl);
                    // сохраняем чтобы потом взять оттуда ид
                    Url savedUrl = urlRepository.save(localNewUrl);

                    //генерируем короткую ссылку
                    String shortUrl = createShortUrl(savedUrl.getId());

                    //вставляем короткую ссылку в нашу сущность
                    localNewUrl.setShortUrl(shortUrl);
                    // сохраняем ее
                    savedUrl = urlRepository.save(localNewUrl);
                    //возвращаем
                    return savedUrl;

                });
        //вставляем в дто для контроллера данные
        shortUrlResponse = new ShortUrlResponse(newUrl.getShortUrl());
        return shortUrlResponse;
    }

    // метод генерации короткой ссылки, обращаемся к алгоритму басе62
    private String createShortUrl(long id) {
        Base62Converter converter = new Base62Converter();
        return converter.encode(id);

    }
}
