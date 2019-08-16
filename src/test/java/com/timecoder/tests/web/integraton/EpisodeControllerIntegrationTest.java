package com.timecoder.tests.web.integraton;

import com.timecoder.TimecoderApplication;
import com.timecoder.dto.EpisodeDto;
import com.timecoder.model.Episode;
import com.timecoder.repository.EpisodeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = TimecoderApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class EpisodeControllerIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private EpisodeRepository repository;

    @Test
    public void testCanCreateEpisode() throws Exception {
        EpisodeDto episode = new EpisodeDto();
        episode.setName("Demo");

        mvc.perform(MockMvcRequestBuilders.post("/episodes")
                .content(asJsonString(episode))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.created").exists());
    }

    @Test
    public void testCanNotCreateEpisodeWithNullName() throws Exception {
        EpisodeDto episode = new EpisodeDto();

        mvc.perform(MockMvcRequestBuilders.post("/episodes")
                .content(asJsonString(episode))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    public void testCanNotCreateEpisodeWithBlankName() throws Exception {
        EpisodeDto episode = new EpisodeDto();
        episode.setName("");

        mvc.perform(MockMvcRequestBuilders.post("/episodes")
                .content(asJsonString(episode))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    public void testCanGetAllEpisodes() throws Exception {
        long id = createTestEpisode("#1: Python").getId();

        mvc.perform(get("/episodes"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.content().json("[{\"id\":" + id + ",\"name\":\"#1: Python\",\"startTime\":null,\"themeList\":[],\"started\":false}]"));
    }

    @Test
    public void testCanGetEpisodeById() throws Exception {
        long id = createTestEpisode("#2: .Net").getId();

        mvc.perform(get("/episodes/{id}", id))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.content().json("{\"id\":" + id + ",\"name\":\"#2: .Net\",\"startTime\":null,\"themeList\":[],\"started\":false}"));
    }

    @Test
    public void testCanDeleteEpisode() throws Exception {
        long id = createTestEpisode("#3: Java").getId();

        mvc.perform(delete("/episodes/{id}/remove", id))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().json("{\"changed\":true}"));
    }

    @Test
    public void testCanStartEpisode() throws Exception {
        long id = createTestEpisode("#4: JS").getId();

        mvc.perform(post("/episodes/{id}/start", id))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().json("{\"changed\":true}"));
    }

    private Episode createTestEpisode(String name) {
        Episode episode = new Episode();
        episode.setName(name);

        return repository.save(episode);
    }

    @Before
    public void deleteAllEpisodes() {
        repository.deleteAll();
    }
}
