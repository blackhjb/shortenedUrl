# shortenedUrl
URL 줄이기

## Spec
- **언어 : JAVA (JDK 13)/thymeleaf**
- **프레임워크 : SpringBoot 2.4.3.RELEASE, JPA & Hibernate)**
- **저장소 : H2(in-memory db)**
- **의존관리 : Maven**

## 기능
- **URL 입력폼 제공 및 결과 출력**
- **URL Shortening Key는 8 Character 이내로 생성되어야 합니다.**
- **동일한 URL에 대한 요청은 동일한 Shortening Key로 응답해야 합니다.**
- **동일한 URL에 대한 요청 수 정보를 가져야 한다.(DB로 확인가능)**
- **Shortening된 URL을 요청받으면 원래 URL로 리다이렉트 합니다**

# 리눅스 환경
* https://sdkman.io/install 참조 사이트
1.--- sdk 설치
$ curl -s "https://get.sdkman.io" | bash
$ source "$HOME/.sdkman/bin/sdkman-init.sh"
2. sdk install maven
3. java -jar *.jar

# DB
http://localhost:8080/h2-console
JDBC URL:jdbc:h2:mem:testdb
![스크린샷 2021-03-18 오후 5 19 49](https://user-images.githubusercontent.com/60101005/111593745-9fb00f00-880d-11eb-8f78-996fb3e3b3a2.png)

# 단축 URL 생성 알고리즘
Generate shortening URL : within 8 charactors
Base64인코딩 사용
java API에 url safe한 인코딩을 지원

# 화면
![스크린샷 2021-03-18 오후 5 23 06](https://user-images.githubusercontent.com/60101005/111594188-13521c00-880e-11eb-9ac9-e7585c06602b.png)
![스크린샷 2021-03-18 오후 5 23 24](https://user-images.githubusercontent.com/60101005/111594205-1c42ed80-880e-11eb-8d6f-310b6d9f4925.png)
링크 클릭시 원본 url로 리다이렉팅후 이동
![스크린샷 2021-03-18 오후 5 23 41](https://user-images.githubusercontent.com/60101005/111594229-27961900-880e-11eb-89ac-b16dab31e5c6.png)
![스크린샷 2021-03-18 오후 5 24 14](https://user-images.githubusercontent.com/60101005/111594306-3f6d9d00-880e-11eb-9dde-dffe5510273d.png)

같은 URL 입력시 카운트 증가 단축 URL 같음
![스크린샷 2021-03-18 오후 5 25 33](https://user-images.githubusercontent.com/60101005/111594468-72b02c00-880e-11eb-82ae-503555128c92.png)
![스크린샷 2021-03-18 오후 5 26 00](https://user-images.githubusercontent.com/60101005/111594499-7ba0fd80-880e-11eb-8427-535a3affef32.png)

