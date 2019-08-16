package com.timecoder.repository;

import com.timecoder.model.Episode;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EpisodeRepository extends CrudRepository<Episode, Long> {
    List<Episode> findAllByOrderByIdDesc();

    Episode findByName(String name);
}
