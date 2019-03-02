package com.timecoder.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UpdateThemeDto {

    @JsonProperty
    private String title;
    @JsonProperty
    private String timecode;
}
