package com.timecoder.repository;

import com.timecoder.model.Episode;
import org.springframework.data.repository.CrudRepository;

public interface EpisodeRepository extends CrudRepository<Episode, Long> { }
