package com.neueda.shorty.stats.repository;

import com.neueda.shorty.stats.model.Url;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UrlRepository extends JpaRepository<Url, Long> {
    Optional<Url> findByShortId(Long id);
    Optional<Url> findByShortUrl(String shortUrl);
    Optional<Url> findFirstByOrderByIdDesc();

}
