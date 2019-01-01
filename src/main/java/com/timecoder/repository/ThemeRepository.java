package com.timecoder.repository;

import com.timecoder.model.Theme;
import org.springframework.data.repository.CrudRepository;

public interface ThemeRepository extends CrudRepository<Theme, Long> {
    Theme findFirstByEpisodeIdOrderByIdAsc(Long episodeId);

    Theme findByIdAndEpisodeId(Long themeId, Long episodeId);
}
