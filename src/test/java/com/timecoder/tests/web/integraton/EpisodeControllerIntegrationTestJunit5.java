package com.timecoder.tests.web.integraton;

import com.timecoder.TimecoderApplication;
import com.timecoder.dto.EpisodeDto;
import com.timecoder.model.Episode;
import com.timecoder.model.Guest;
import com.timecoder.repository.EpisodeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TimecoderApplication.class)
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class EpisodeControllerIntegrationTestJunit5 extends BaseIntegrationTest {

    @Autowired
    private EpisodeRepository repository;

    @Autowired
    private WebTestClient webClient;

    @Test
    public void testCanCreateEpisode() {
        EpisodeDto episode = new EpisodeDto();
        episode.setName("Demo");

        this.webClient.post()
                .uri("/episodes")
                .body(fromObject(episode))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.created").exists();
    }

    @Test
    public void testCanNotCreateEpisodeWithNullName() {
        EpisodeDto episode = new EpisodeDto();

        this.webClient.post()
                .uri("/episodes")
                .body(fromObject(episode))
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    public void testCanNotCreateEpisodeWithBlankName() {
        EpisodeDto episode = new EpisodeDto();
        episode.setName("");

        this.webClient.post()
                .uri("/episodes")
                .body(fromObject(episode))
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    public void testCanGetAllEpisodes() {
        long id = createTestEpisode("#1: Python").getId();

        this.webClient.get()
                .uri("/episodes")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .json("[{\"id\":" + id + ",\"name\":\"#1: Python\",\"startTime\":null,\"themeList\":[],\"started\":false}]");
    }

    @Test
    public void testCanGetEpisodeById() {
        long id = createTestEpisode("#2: .Net").getId();

        this.webClient.get()
                .uri("/episodes/" + id)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .json("{\"id\":" + id + ",\"name\":\"#2: .Net\",\"startTime\":null,\"themeList\":[],\"started\":false}");
    }

    @Test
    public void testCanDeleteEpisode() {
        long id = createTestEpisode("#3: Java").getId();
        this.webClient
                .delete()
                .uri("/episodes/{id}/remove", id)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .json("{\"changed\":true}");
    }

    @Test
    public void testCanStartEpisode() {
        long id = createTestEpisode("#4: JS").getId();
        this.webClient.post().uri("/episodes/{id}/start", id).exchange().expectStatus().isOk().expectBody()
                .json("{\"changed\":true}");
    }

    @Test
    public void shouldBeExceptionWhenGetNonExistingEpisode() {
        this.webClient.get().uri("/episodes/{id}", 1)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR)
                .expectBody()
                .jsonPath("$.message").isEqualTo("Episode id 1 not found");
    }

    @Test
    public void testCanNotCreateEpisodeWithTheSameName() {
        createTestEpisode("Episode 1");

        EpisodeDto episode = new EpisodeDto();
        episode.setName("Episode 1");

        this.webClient.post()
                .uri("/episodes")
                .body(fromObject(episode))
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.CONFLICT)
                .expectBody()
                .json("{\"status\":\"CONFLICT\",\"message\":\"Episode with name Episode 1 already exist\"}");
    }

    @Test
    public void testCanAddEpisodeGuest(){
        Episode episode = createTestEpisode("Episode 1");

        this.webClient.post()
                .uri("/episodes/{id}/guest", episode.getId())
                .body(fromObject("Ivan"))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK);


        this.webClient.get()
                .uri("/episodes/{id}", episode.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .json("{\"id\":1,\"name\":\"Episode 1\",\"startTime\":null,\"themeList\":[],\"guests\":[{\"id\":1,\"name\":\"Ivan\"}],\"started\":false}");
    }

    private Episode createTestEpisode(String name) {
        Episode episode = new Episode();
        episode.setName(name);

        return repository.save(episode);
    }

    @BeforeEach
    public void deleteAllEpisodes() {
        repository.deleteAll();
    }
}
