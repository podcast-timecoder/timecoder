package com.timecoder.service;

import com.timecoder.aspect.Logger;
import com.timecoder.dto.EpisodeDto;
import com.timecoder.mapper.EpisodeMapper;
import com.timecoder.model.Episode;
import com.timecoder.repository.EpisodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;

@Service
@Transactional
@RequiredArgsConstructor
public class EpisodeService {

    private final EpisodeRepository episodeRepository;
    private final EpisodeMapper episodeMapper;

    @Logger
    public long createEpisode(EpisodeDto episodeDto) {
        Episode episode = episodeMapper.toEpisodeWithout(episodeDto);

        if (episodeRepository.findByName(episodeDto.getName()) != null) {
            throw new RuntimeException("Episode with name " + episodeDto.getName() + " already exist");
        }

        return episodeRepository.save(episode).getId();
    }

    public Iterable<Episode> getAllEpisodes() {
        return episodeRepository.findAllByOrderByIdDesc();
    }

    public Episode getEpisodeById(Long id) {
        return episodeRepository.findById(id).orElseThrow(() -> new RuntimeException("Episode id " + id + " not found"));
    }

    public Episode startEpisode(Long id) {
        Episode episode = getEpisodeById(id);
        episode.setStarted(true);
        episode.setStartTime(Instant.now());
        return episodeRepository.save(episode);
    }

    public Episode stopEpisode(Long id) {
        Episode episode = getEpisodeById(id);
        episode.setStarted(false);
        return episodeRepository.save(episode);
    }

    public boolean deleteEpisode(Long id) {
        episodeRepository.deleteById(id);
        return true;
    }
}
