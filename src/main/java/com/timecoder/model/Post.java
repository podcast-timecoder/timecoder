package com.timecoder.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

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
    private Date createdAt = new Date();
    private String name;
    private String shortDescription;
    private String description;
    @Length(max=10000)
    private String link;
    @ElementCollection(fetch = FetchType.LAZY)
    private List<String> guests = new ArrayList<>();
}
