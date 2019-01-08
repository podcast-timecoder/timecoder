package com.timecoder.service;

import com.timecoder.model.Episode;
import com.timecoder.repository.EpisodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.List;

import static java.util.Collections.singletonMap;
import static org.springframework.http.HttpStatus.OK;

@Service
@Transactional
@RequiredArgsConstructor
public class EpisodeService {

    private final EpisodeRepository episodeRepository;

    public ResponseEntity createEpisode(Episode episode) {
        episode.getThemeList().forEach(episode::addTheme);
        long id = episodeRepository.save(episode).getId();
        return new ResponseEntity<>(singletonMap("created", id), OK);
    }

    public Iterable<Episode> getAllEpisodes() {
        return episodeRepository.findAllByOrderByIdDesc();
    }

    public Episode getEpisodeById(Long id) {
        return episodeRepository.findById(id).orElseThrow(() -> new RuntimeException("Episode id " + id + " not found"));
    }

    public ResponseEntity startEpisode(Long id) {
        Episode episode = getEpisodeById(id);
        episode.setStarted(true);
        episode.setStartTime(Instant.now());
        episodeRepository.save(episode);
        return new ResponseEntity<>(singletonMap("changed", true), OK);
    }

    public ResponseEntity stopEpisode(Long id) {
        Episode episode = getEpisodeById(id);
        episode.setStarted(false);
        episodeRepository.save(episode);
        return new ResponseEntity<>(singletonMap("changed", true), OK);
    }
}
