package com.timecoder.service;

import com.timecoder.model.Episode;
import com.timecoder.model.Theme;
import com.timecoder.repository.EpisodeRepository;
import com.timecoder.repository.ThemeRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

import static com.google.common.primitives.Longs.asList;
import static java.util.Collections.singletonMap;
import static org.springframework.http.HttpStatus.OK;

@Service
@Transactional
@RequiredArgsConstructor
public class ThemeService {

    private final ThemeRepository themeRepository;
    private final EpisodeRepository episodeRepository;
    private final EpisodeService episodeService;

    public Long createTheme(Long id, Theme theme) {
        long themeId = themeRepository.save(theme).getId();

        linkThemes(id, asList(themeId));

        return id;
    }

    public Long createTheme(Theme theme) {
        return themeRepository.save(theme).getId();
    }

    public Theme updateTimeStamp(Long episodeId, Long themeId) {
        Instant startTime = episodeService.getEpisodeById(episodeId).getStartTime();
        Theme currentTheme = themeRepository.findByIdAndEpisodeId(themeId, episodeId);

        Instant currentThemeTime = Instant.now();

        Duration duration = Duration.between(startTime, currentThemeTime);
        String timeCode = DurationFormatUtils.formatDuration(duration.toMillis(), "HH:mm:ss", true);

        currentTheme.setTimecode(timeCode);
        currentTheme.setTimestamp(currentThemeTime);
        currentTheme.setPassed(true);

        themeRepository.save(currentTheme);
        return currentTheme;
    }

    public Iterable<Theme> getAllThemes(String episode) {
        Long term = null;
        if(NumberUtils.isCreatable(episode)) {
            term = NumberUtils.createLong(episode);
        }

        return themeRepository.findByEpisodeId(term);
    }

    public ResponseEntity linkThemes(Long id, List<Long> themeList) {
        Episode episode = episodeRepository.findById(id).orElseThrow(() -> new RuntimeException());
        themeRepository.findAllById(themeList).forEach(episode::addTheme);
        episodeRepository.save(episode);
        return new ResponseEntity<>(singletonMap("changed", true), OK);
    }
}
