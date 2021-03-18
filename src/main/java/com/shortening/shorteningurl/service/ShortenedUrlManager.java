package com.shortening.shorteningurl.service;

import com.shortening.shorteningurl.domain.ShortenedUrlDomain;

import java.util.Optional;

public interface ShortenedUrlManager {
    Optional<ShortenedUrlDomain> addNewShortenedUrlKey(String url);
    //로깅용 메서드
    void loggingShortenedUrlManagerHashMap();
    void clearHashMap();
}
