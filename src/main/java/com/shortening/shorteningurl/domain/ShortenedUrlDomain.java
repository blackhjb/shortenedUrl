package com.shortening.shorteningurl.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "TB_SHORTENING_URL")
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
@EqualsAndHashCode
public class ShortenedUrlDomain {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "shortened_url_key", unique = true, nullable = false)
    private String shortenedUrlKey;

    @Column(name = "original_url", nullable = false)
    private String originalUrl;

    @Column(name = "requestCnt")
    @ColumnDefault("0")

    private Long requestCnt;

    public void increaseCount(){
        this.requestCnt = this.requestCnt+1;
    }


}
