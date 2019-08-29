package com.timecoder.tests.web.integraton;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.timecoder.TimecoderApplication;
import com.timecoder.dto.PostDto;
import com.timecoder.model.Post;
import com.timecoder.repository.PostRepository;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TimecoderApplication.class)
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class PostControllerIntegrationTestJunit5 {

    @Autowired
    PostRepository repository;

    @Autowired
    private WebTestClient webClient;

    @BeforeEach
    public void deleteAllPosts() {
        repository.deleteAll();
    }

    @Test
    public void testCanCreatePost(){
        PostDto post = new PostDto();
        post.setName("Post1");

        this.webClient.post()
                .uri("/posts")
                .body(fromObject(post))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.created").exists();
    }

    @Test
    public void cantCreatePostWithBlankName(){
        PostDto post = new PostDto();
        post.setName("");

        this.webClient.post()
                .uri("/posts")
                .body(fromObject(post))
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    public void cantCreatePostWithNullName(){
        PostDto post = new PostDto();

        this.webClient.post()
                .uri("/posts")
                .body(fromObject(post))
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    public void testCanGetAllPosts() {
        Post post = createPost("Post1");

        this.webClient.get()
            .uri("/posts?orderBy=DESC&pageNumber=0&pageSize=5&sortBy=id")
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .consumeWith(response -> {
                        Post receivedPost = responseToPost(response);
                        assertThat(post.getId()).isEqualTo(receivedPost.getId()).as("post.id");
                        assertThat(post.getName()).isEqualTo(receivedPost.getName()).as("post.name");
                        assertThat(post.getCreatedAt()).isEqualTo(receivedPost.getCreatedAt()).as("post.createdAt");
                        assertThat(post.getEpisodeId()).isEqualTo(receivedPost.getEpisodeId()).as("post.episodeId");
                        assertThat(post.getDescription()).isEqualTo(receivedPost.getDescription()).as("post.description");
                        assertThat(post.getShortDescription()).isEqualTo(receivedPost.getShortDescription()).as("post.shortDescription");
                        assertThat(post.getLink()).isEqualTo(receivedPost.getLink()).as("post.link");
                        assertThat(post.getGuests().size()).isEqualTo(post.getGuests().size()).as("post.guests");
            });
    }

    @Test
    public void testCanGetPostById() {
        Post post = createPost("Post1");

        this.webClient.get()
                .uri("/posts/" + post.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .json("{\"id\":" + post.getId() + ",\"episodeId\":0,\"createdAt\":\"" +
                        dateToCorrectView(post.getCreatedAt()) + "\",\"name\":\"" + post.getName() +
                        "\",\"shortDescription\":null,\"description\":null,\"link\":null,\"guests\":[]}");
    }

    @Test
    public void shouldBeExceptionWhenGetNonExistingPostById() {
        this.webClient.get()
                .uri("/posts/1")
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.NOT_FOUND)
                .expectBody()
                .jsonPath("$.message").isEqualTo("Post id 1 not found");
    }

    @Test
    public void testCanUpdatePostById(){
        Post post = createPost("Post1");

        post.setName("UpdatedName");
        this.webClient.put()
                .uri("/posts/" + post.getId())
                .body(fromObject(post))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .json("{\"updated\":" + post.getId() + "}");
    }

    @Test
    public void testCantUpdatePostByInvalidId(){
        Post post = createPost("Post1");
        post.setName("UpdatedName");

        this.webClient.put()
                .uri("/posts/" + 2)
                .body(fromObject(post))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.NOT_FOUND)
                .expectBody()
                .jsonPath("$.message").isEqualTo("Post id 2 not found");
    }

    @Test
    public void testCanDeletePostById(){
        Post post = createPost("Post1");

        this.webClient.delete()
                .uri("/posts/" + post.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .json("{\"changed\":true}");
    }

    private Post createPost(String name){
        Post post = new Post();
        post.setName(name);
        return repository.save(post);
    }

    private String dateToCorrectView(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return simpleDateFormat.format(date);
    }

    private Post responseToPost(EntityExchangeResult<byte[]> result){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(new JSONObject(new String(result.getResponseBody()))
                    .getJSONArray("content").get(0).toString(), Post.class);
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }

}
