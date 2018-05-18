package com.neueda.shorty.repository;

import com.neueda.shorty.model.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UrlRepository extends JpaRepository<Url, Long> {
    Optional<Url> findByShortIdAndStatusNot(Long shortId, String status);
    Optional<Url> findByShortUrl(String shortUrl);
    Optional<Url> findFirstByOrderByIdDesc();
}
