package com.shortening.shorteningurl.controller;

import com.shortening.shorteningurl.domain.ShortenedUrlDomain;
import com.shortening.shorteningurl.service.UrlShorteningService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Optional;

/**
 * URL Shortening
 * Created by HJB
 */
@Slf4j
@Controller
public class ShorteningUrlController {
    private static final String HOST = "http://localhost:8080/";
    private final UrlValidator urlValidator;
    private final UrlShorteningService urlShorteningService;

    public ShorteningUrlController(UrlShorteningService urlShorteningService) {
        this.urlShorteningService = urlShorteningService;
        this.urlValidator = new UrlValidator();

    }

    @GetMapping(value = "/")
    public String test(){
        return "shortening";
    }

    /**
     * URL 입력
     *
     * @param url
     * @return
     */
    @PostMapping(value = "/shortening/url")
    public String shorteningUrl(@RequestParam("targetUrl") String url
    , Map<String, String> model) {

        // 유효성 체크
        if (!urlValidator.isValid(url)) {
            log.info("msg: {}, targetUrl : {}", "유효하지 않은 URL요청", url);
            model.put("isValidUrl", "잘못된 URL입니다. 다시 입력해주세요");
            return "shortening";
        }

        Optional<ShortenedUrlDomain> domain = urlShorteningService.getShortenedUrlKey(removeEndDelimiter(url));
        domain.ifPresent(elem -> {
            model.put("shortenedUrl", HOST + elem.getShortenedUrlKey());
            model.put("originalUrl", elem.getOriginalUrl());
        });
        return "shortening";
    }

    @GetMapping(value = "/**")
    public String redirectService(HttpServletRequest request) {
        final Optional<ShortenedUrlDomain> url = urlShorteningService.getOriginalUrl("http://localhost:8080" + request.getRequestURI());
        return url.map(elem -> "redirect:" + elem.getOriginalUrl())
                .orElseThrow(RuntimeException::new);
    }

    private String removeEndDelimiter(String url) {
        if (url.endsWith("/")) {
            return url.substring(0, url.length() - 1);
        } else {
            return url;
        }
    }
}
