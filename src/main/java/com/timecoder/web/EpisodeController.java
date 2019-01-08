package com.timecoder.web;

import com.timecoder.model.Episode;
import com.timecoder.service.EpisodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class EpisodeController {

    private final EpisodeService episodeService;

    @RequestMapping(value = "/episodes", method = RequestMethod.POST)
    public ResponseEntity createEpisode(@Valid @RequestBody Episode episode) {
        return episodeService.createEpisode(episode);
    }

    @RequestMapping(value = "/episodes", method = RequestMethod.GET)
    public Iterable<Episode> getAllEpisodes() {
        return episodeService.getAllEpisodes();
    }

    @RequestMapping(value = "/episodes/{id}", method = RequestMethod.GET)
    public Episode getEpisodeById(@PathVariable("id") Long id){
        return episodeService.getEpisodeById(id);
    }

    @RequestMapping(value = "/episodes/{id}/start", method = RequestMethod.POST)
    public ResponseEntity startEpisode(@PathVariable("id") Long id){
        return episodeService.startEpisode(id);
    }

    @RequestMapping(value = "/episodes/{id}/stop", method = RequestMethod.POST)
    public ResponseEntity stopEpisode(@PathVariable("id") Long id){
        return episodeService.stopEpisode(id);
    }
}
