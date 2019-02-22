package com.timecoder.web;

import com.timecoder.model.Theme;
import com.timecoder.service.SseService;
import com.timecoder.service.ThemeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static java.util.Collections.singletonMap;
import static org.springframework.http.HttpStatus.OK;

@RestController
@EnableSpringDataWebSupport
@RequiredArgsConstructor
public class ThemeController {

    private final ThemeService themeService;
    private final SseService sseService;

    @RequestMapping(value = "/episodes/{id}/theme", method = RequestMethod.POST)
    public ResponseEntity createTheme(@PathVariable("id") Long id, @Valid @RequestBody Theme theme) {
        Long themeId = themeService.createTheme(id, theme);
        sseService.emitNotification(themeId);
        return new ResponseEntity<>(singletonMap("id", id), OK);
    }

    @RequestMapping(value = "/theme", method = RequestMethod.POST)
    public ResponseEntity addFreeTheme(@Valid @RequestBody Theme theme) {
        Long id = themeService.createTheme(theme);
        return new ResponseEntity<>(singletonMap("id", id), OK);
    }

    @RequestMapping(value = "/theme/{id}/delete", method = RequestMethod.DELETE)
    public ResponseEntity deleteFreeTheme(@PathVariable("id") Long id) {
        boolean result = themeService.deleteFreeTheme(id);
        return new ResponseEntity<>(singletonMap("changed", result), OK);
    }

    @RequestMapping(value = "/episodes/{id}/theme/{themeId}/timestamp", method = RequestMethod.POST)
    public Theme setThemeTimestamp(@PathVariable("id") Long id, @PathVariable("themeId") Long themeId) {
        Theme theme = themeService.updateTimeStamp(id, themeId);
        sseService.emitNotification(theme);
        return theme;
    }

    @RequestMapping(value = "/theme", method = RequestMethod.GET)
    public Iterable<Theme> getAllThemes(@RequestParam("episode") String episode) {
        return themeService.getAllThemes(episode);
    }

    @RequestMapping(value = "/episodes/{id}", method = RequestMethod.POST)
    public ResponseEntity linkThemes(@PathVariable("id") Long id, @RequestBody List<Long> themeList) {
        return themeService.linkThemes(id, themeList);
    }
}
