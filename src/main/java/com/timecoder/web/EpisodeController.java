package com.timecoder.web;

import com.timecoder.model.Episode;
import com.timecoder.service.EpisodeService;
import com.timecoder.service.SseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static java.util.Collections.singletonMap;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
public class EpisodeController {

    private final EpisodeService episodeService;
    private final SseService sseService;

    @RequestMapping(value = "/episodes", method = RequestMethod.POST)
    public ResponseEntity createEpisode(@Valid @RequestBody Episode episode) {
        long episodeId = episodeService.createEpisode(episode);
        return new ResponseEntity<>(singletonMap("created", episodeId), OK);
    }

    @RequestMapping(value = "/episodes", method = RequestMethod.GET)
    public Iterable<Episode> getAllEpisodes() {
        return episodeService.getAllEpisodes();
    }

    @RequestMapping(value = "/episodes/{id}", method = RequestMethod.GET)
    public Episode getEpisodeById(@PathVariable("id") Long id) {
        return episodeService.getEpisodeById(id);
    }

    @RequestMapping(value = "/episodes/{id}/start", method = RequestMethod.POST)
    public ResponseEntity startEpisode(@PathVariable("id") Long id) {
        Episode episode = episodeService.startEpisode(id);
        sseService.emitNotification(episode.getId());
        return new ResponseEntity<>(singletonMap("changed", true), OK);
    }

    @RequestMapping(value = "/episodes/{id}/stop", method = RequestMethod.POST)
    public ResponseEntity stopEpisode(@PathVariable("id") Long id) {
        Episode episode = episodeService.stopEpisode(id);
        sseService.emitNotification(episode.getId());
        return new ResponseEntity<>(singletonMap("changed", true), OK);
    }

    @RequestMapping(value = "/episodes/{id}/remove", method = RequestMethod.DELETE)
    public ResponseEntity deleteEpisode(@PathVariable("id") Long id) {
        boolean deleted = episodeService.deleteEpisode(id);
        return new ResponseEntity<>(singletonMap("changed", deleted), OK);
    }
}
