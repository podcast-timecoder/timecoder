package com.timecoder.web;

import com.timecoder.dto.EpisodeDto;
import com.timecoder.model.Episode;
import com.timecoder.service.EpisodeService;
import com.timecoder.service.SseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static java.util.Collections.singletonMap;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
public class EpisodeController {

    private final EpisodeService episodeService;
    private final SseService sseService;

    @PostMapping(value = "/episodes")
    public ResponseEntity createEpisode(@Valid @RequestBody EpisodeDto episode) {
        long episodeId = episodeService.createEpisode(episode);
        return new ResponseEntity<>(singletonMap("created", episodeId), OK);
    }

    @GetMapping(value = "/episodes")
    public Iterable<Episode> getAllEpisodes() {
        return episodeService.getAllEpisodes();
    }

    @GetMapping(value = "/episodes/{id}")
    public Episode getEpisodeById(@PathVariable("id") Long id) {
        return episodeService.getEpisodeById(id);
    }

    @PostMapping(value = "/episodes/{id}/start")
    public ResponseEntity startEpisode(@PathVariable("id") Long id) {
        Episode episode = episodeService.startEpisode(id);
        sseService.emitNotification(episode.getId());
        return new ResponseEntity<>(singletonMap("changed", true), OK);
    }

    @PostMapping(value = "/episodes/{id}/stop")
    public ResponseEntity stopEpisode(@PathVariable("id") Long id) {
        Episode episode = episodeService.stopEpisode(id);
        sseService.emitNotification(episode.getId());
        return new ResponseEntity<>(singletonMap("changed", true), OK);
    }

    @DeleteMapping(value = "/episodes/{id}/remove")
    public ResponseEntity deleteEpisode(@PathVariable("id") Long id) {
        boolean deleted = episodeService.deleteEpisode(id);
        return new ResponseEntity<>(singletonMap("changed", deleted), OK);
    }

    @PostMapping("/episodes/{id}/guest")
    public ResponseEntity addGuest(@PathVariable("id") Long id, String guest){
        episodeService.addGuest(id, guest);
        return new ResponseEntity<>(singletonMap("changed", true), OK);
    }
}
