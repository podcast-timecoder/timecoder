package com.timecoder.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Episode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Size(min = 1)
    @Column(unique = true)
    @ApiModelProperty(example = "#1")
    String name;

    @ApiModelProperty(example = "false")
    boolean isStarted = false;

    Instant startTime;

    @OneToMany(cascade = {CascadeType.ALL}, orphanRemoval = true)
    @JoinColumn(name = "episodeId")
    @OrderBy("createdAt")
    List<Theme> themeList = new ArrayList<>();
}
