package com.timecoder.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class PostDto {

    private long episodeId;
    @ApiModelProperty(hidden = true)
    private Date createdAt = new Date();
    @ApiModelProperty(example = "Hello world")
    @NotBlank
    @Size(min = 1)
    private String name;
    @ApiModelProperty(example = "Test post")
    private String shortDescription;
    @ApiModelProperty(example = "Test post description")
    private String description;
    @Length(max=10000)
    private String link;
    @ApiModelProperty(dataType="List")
    private List<String> guests = new ArrayList<>();
}
