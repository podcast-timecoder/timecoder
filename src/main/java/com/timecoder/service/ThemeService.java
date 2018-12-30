package com.timecoder.service;

import com.timecoder.model.Theme;
import com.timecoder.repository.ThemeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;

import static java.util.Collections.singletonMap;
import static org.springframework.http.HttpStatus.OK;

@Service
@RequiredArgsConstructor
public class ThemeService {

    private final ThemeRepository themeRepository;

    public ResponseEntity<Map<String, Long>> createTheme(Theme theme) {
        long id = themeRepository.save(theme).getId();
        return new ResponseEntity<>(singletonMap("id", id), OK);
    }

    public ResponseEntity<Theme> updateTimeStamp(Long themeId) {
        Theme theme = themeRepository.findById(themeId).get();
        theme.setTimestamp(Instant.now());
        themeRepository.save(theme);
        return new ResponseEntity<>(theme, OK);
    }
}
