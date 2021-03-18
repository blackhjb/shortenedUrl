package com.shortening.shorteningurl;

import org.apache.commons.validator.routines.UrlValidator;
import org.junit.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShorteningurlApplication.class)
public class ShorteningurlApplicationTests {

    @Autowired
    private ResourceLoader loader;

    private List<String> urlList;
    private List<String> mixedInvalidUrlList;
    @Before
    public void setUp() {
        urlList = new ArrayList<>();
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(loader.getResource("classpath:static/urlListForTest").getInputStream()));
            urlList = bufferedReader.lines().collect(Collectors.toList());

            bufferedReader = new BufferedReader(new InputStreamReader(loader.getResource("classpath:static/mixedWrongUrlListForTest").getInputStream()));
            mixedInvalidUrlList = bufferedReader.lines().collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Optional.ofNullable(bufferedReader).ifPresent(reader -> {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    /**
     * mixedWrongUrlListForTest.txt파일에는 유효하지않은 Url 5개 존재한다.
     * 유효하지않은 Url의 갯수를 비교하여 테스트
     */
    @Test
    public void isValidUrlTest() {
        UrlValidator urlValidator = new UrlValidator();
        long count = mixedInvalidUrlList.stream()
                .filter(url -> !urlValidator.isValid(url))
                .count();

        Assert.assertEquals(5, count);
    }


    /**
     * 싱글톤 객체가 가지고 있는 인스턴스 변수도 싱글톤 유지가 되는지 테스트
     */
    @Test
    public void singleTonObjTest() {
        SingleTonObj singleTonObj = SingleTonObj.getInstance();
        List<String> list = singleTonObj.getList();
        list.add("add");
        list.add("musinsa");

        List<String> listB = singleTonObj.getList();
        listB.forEach(System.out::println);

        Assert.assertEquals(list, listB);
    }
}
