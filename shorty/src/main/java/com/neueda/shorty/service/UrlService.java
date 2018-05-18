package com.neueda.shorty.service;

import com.neueda.shorty.model.Url;

import java.util.Optional;

public interface UrlService {

    String addUrl(String longUrl);
    Optional<Url> getUrl(String shortUrl);
}
