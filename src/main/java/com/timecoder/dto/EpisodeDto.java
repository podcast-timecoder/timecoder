package com.timecoder.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class EpisodeDto {
    @Size(min = 1)
    @ApiModelProperty(example = "#1")
    @NotBlank
    String name;
}
