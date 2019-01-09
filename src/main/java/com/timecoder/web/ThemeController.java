package com.timecoder.web;

import com.timecoder.model.Theme;
import com.timecoder.service.ThemeService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ThemeController {

    private final ThemeService themeService;

    @RequestMapping(value = "/episodes/{id}/theme", method = RequestMethod.POST)
    public ResponseEntity createTheme(@PathVariable("id") Long id, @Valid @RequestBody Theme theme) {
        return themeService.createTheme(id, theme);
    }

    @RequestMapping(value = "/episodes/{id}/theme/{themeId}/timestamp", method = RequestMethod.POST)
    public Theme setThemeTimestamp(@PathVariable("id") Long id, @PathVariable("themeId") Long themeId){
       return themeService.updateTimeStamp(id, themeId);
    }

    @RequestMapping(value = "/theme", method = RequestMethod.GET)
    public Iterable<Theme> getAllThemes(@RequestParam("episode") String episode){
        return themeService.getAllThemes(episode);
    }

    @RequestMapping(value = "/episodes/{id}", method = RequestMethod.POST)
    public ResponseEntity linkThemes(@PathVariable("id") Long id, @RequestBody List<Long> themeList){
        return themeService.linkThemes(id, themeList);
    }
}
