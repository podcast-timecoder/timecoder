package com.timecoder.service;

import com.timecoder.model.Episode;
import com.timecoder.repository.EpisodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static java.util.Collections.singletonMap;
import static org.springframework.http.HttpStatus.OK;

@Service
@Transactional
@RequiredArgsConstructor
public class EpisodeService {

    private final EpisodeRepository episodeRepository;

    public ResponseEntity createEpisode(Episode episode) {
        episodeRepository.save(episode);
        return new ResponseEntity<>(singletonMap("status", "CREATED"), OK);
    }

    public Iterable<Episode> getAllEpisodes() {
        return episodeRepository.findAllByOrderByIdDesc();
    }

    public Episode getEpisodeById(Long id) {
        return episodeRepository.findById(id).orElseThrow(() -> new RuntimeException("Episode id " + id + " not found"));
    }
}
