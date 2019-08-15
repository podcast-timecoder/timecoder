package com.timecoder.tests.web.integraton;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.timecoder.dto.EpisodeDto;
import com.timecoder.repository.EpisodeRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


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
                .andExpect(MockMvcResultMatchers.content().string("{\"created\":1}"));
    }

    @Test
    public void testCanNotCreateEpisodeWithEmptyName() throws Exception {
        EpisodeDto episode = new EpisodeDto();

        mvc.perform(MockMvcRequestBuilders.post("/episodes")
                .content(asJsonString(episode))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
}
