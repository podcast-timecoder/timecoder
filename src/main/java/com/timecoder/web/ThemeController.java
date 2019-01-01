package com.timecoder.web;

import com.timecoder.model.Theme;
import com.timecoder.service.ThemeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class ThemeController {

    private final ThemeService themeService;

    @RequestMapping(value = "/theme", method = RequestMethod.POST)
    public ResponseEntity createTheme(@Valid @RequestBody Theme theme) {
        return themeService.createTheme(theme);
    }

    @RequestMapping(value = "/episodes/{id}/theme/{themeId}/timestamp", method = RequestMethod.POST)
    public Theme setThemeTimestamp(@PathVariable("id") Long id, @PathVariable("themeId") Long themeId){
       return themeService.updateTimeStamp(id, themeId);
    }

}
