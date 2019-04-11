package com.timecoder.test;

import com.timecoder.TimecoderApplication;
import com.timecoder.dto.EpisodeDto;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@ContextConfiguration(classes = TimecoderApplication.class)
@Testcontainers
@TestPropertySource(value={"classpath:application.properties"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EpisodeControllerTest {

    @Value("${server.port}")
    int port;

    @Container
    private final PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer()
            .withDatabaseName("timecoder")
            .withUsername("root")
            .withPassword("admin");

    @BeforeEach
    void setUp() {
        int port = postgreSQLContainer.getFirstMappedPort();
        System.setProperty("spring.datasource.url", String.format("jdbc:postgresql://loca:%d/postgres", postgreSQLContainer.getFirstMappedPort()));
        RestAssured.port = port;
    }

    @Test
    void testCanCreateEpisode() {
        EpisodeDto episodeDto = new EpisodeDto();
        episodeDto.setName("#1");

        RestAssured.given()
                .body(episodeDto)
                .when()
                .post("/episodes")
                .then()
                .statusCode(200)
                .body("status", Matchers.equalTo(""));
    }
}
