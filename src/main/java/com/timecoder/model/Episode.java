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

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = false)
    @JoinTable(name = "episodeToTheme",
            joinColumns = {@JoinColumn(name = "id")},
            inverseJoinColumns = {@JoinColumn(name = "episodeId")}
    )
    @OrderBy("timeCode, createdAt")
    List<Theme> themeList = new ArrayList<>();

    public void addTheme(Theme theme) {
        themeList.add(theme);
        theme.setEpisode(this);
    }

    public void removeTheme(Long themeId) {
        Theme theme = themeList
                .stream()
                .filter(t -> t.getId().equals(themeId))
                .findFirst().get();
        themeList.remove(theme);
        theme.setEpisode(null);
    }
}
