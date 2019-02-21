package com.timecoder.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long episodeId;
    @ApiModelProperty(hidden = true)
    private Date createdAt = new Date();
    @ApiModelProperty(example = "Hello world")
    private String name;
    @ApiModelProperty(example = "Test post")
    private String shortDescription;
    @ApiModelProperty(example = "Test post description")
    private String description;
    private String link;
    @ElementCollection(fetch = FetchType.LAZY)
    @ApiModelProperty(dataType="List", example = "[One, Two, Three]")
    private List<String> guests = new ArrayList<>();
}
