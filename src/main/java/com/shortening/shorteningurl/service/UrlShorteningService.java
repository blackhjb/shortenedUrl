package com.shortening.shorteningurl.service;

import com.shortening.shorteningurl.domain.ShortenedUrlDomain;
import com.shortening.shorteningurl.repository.UrlRepository;
import com.shortening.shorteningurl.util.ShorteningKeyHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class UrlShorteningService {
    private final UrlRepository urlRepository;
    private final ShortenedUrlManager shortenedUrlManager;

    @Autowired
    public UrlShorteningService(UrlRepository urlRepository, ShortenedUrlManager shortenedUrlManager) {
        this.urlRepository = urlRepository;
        this.shortenedUrlManager = shortenedUrlManager;
    }

    @Deprecated
    public Optional<ShortenedUrlDomain> getShortenedUrl(String url) {
        Optional<ShortenedUrlDomain> existedUrlDomain = urlRepository.findByOriginalUrl(url);

        if (existedUrlDomain.isPresent()) {
            return existedUrlDomain;
        }

        ShortenedUrlDomain newUrlDomain = new ShortenedUrlDomain();
        String shortenedUrlKey = ShorteningKeyHelper.generateShortenedUrlKey(url);

        while (urlRepository.findByShortenedUrlKey(shortenedUrlKey).isPresent()) {
            shortenedUrlKey = ShorteningKeyHelper.generateShortenedUrlKey(url);
        }

        newUrlDomain.setShortenedUrlKey(shortenedUrlKey);
        newUrlDomain.setOriginalUrl(url);

        urlRepository.save(newUrlDomain);

        return Optional.of(newUrlDomain);
    }

    @Transactional
    public Optional<ShortenedUrlDomain> getShortenedUrlKey(String url) {
        Optional<ShortenedUrlDomain> existedUrlDomain = urlRepository.findByOriginalUrl(url);

        if (existedUrlDomain.isPresent()) {
            existedUrlDomain.get().increaseCount();
            return existedUrlDomain;
        }
        return shortenedUrlManager.addNewShortenedUrlKey(url);
    }


    public Optional<ShortenedUrlDomain> getOriginalUrl(String shortenedUrl) {
        urlRepository.findByShortenedUrlKey(shortenedUrl);
        return null;

    }
}
