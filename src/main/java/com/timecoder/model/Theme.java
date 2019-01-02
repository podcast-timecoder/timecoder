package com.timecoder.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Theme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(hidden = true)
    Long id;
    Long episodeId;
    @ApiModelProperty(example = "Интро")
    String title;
    @ApiModelProperty(example = "-")
    String timecode;
    @ApiModelProperty(example = "false")
    boolean passed = false;
    @ApiModelProperty(hidden = true)
    Instant timestamp = Instant.now();
    @ApiModelProperty(hidden = true)
    Date createdAt = new Date();
}
