package com.neueda.shorty.stats.service;

import com.neueda.shorty.stats.model.Url;

import java.util.Optional;

public interface UrlService {

    String addUrl(String longUrl);
    Optional<Url> getUrl(String shortUrl);
    String getCleanLongUrl(Url url);
}
