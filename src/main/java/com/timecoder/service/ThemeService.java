package com.timecoder.service;

import com.timecoder.model.Episode;
import com.timecoder.model.Theme;
import com.timecoder.repository.ThemeRepository;
import io.swagger.models.auth.In;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Map;

import static java.util.Collections.singletonMap;
import static org.springframework.http.HttpStatus.OK;

@Service
@RequiredArgsConstructor
public class ThemeService {

    private final ThemeRepository themeRepository;
    private final EpisodeService episodeService;

    public ResponseEntity<Map<String, Long>> createTheme(Theme theme) {
        Episode episode = episodeService.getEpisodeById(theme.getEpisodeId());

        if (episode == null) {
            throw new RuntimeException("Wrong episode for theme " + theme.getTitle());
        }

        long id = themeRepository.save(theme).getId();
        return new ResponseEntity<>(singletonMap("id", id), OK);
    }

    public Theme updateTimeStamp(Long episodeId, Long themeId) {
        Instant startTime = episodeService.getEpisodeById(episodeId).getStartTime();
        Theme currentTheme = themeRepository.findByIdAndEpisodeId(themeId, episodeId);

        Instant currentThemeTime = Instant.now();

        Duration duration = Duration.between(startTime, currentThemeTime);
        String timeCode = DurationFormatUtils.formatDuration(duration.toMillis(), "H:mm:ss", true);

        currentTheme.setTimecode(timeCode);
        currentTheme.setTimestamp(currentThemeTime);
        currentTheme.setPassed(true);

        themeRepository.save(currentTheme);
        return currentTheme;
    }
}
