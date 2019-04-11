package com.timecoder.mapper;

import com.timecoder.dto.EpisodeDto;
import com.timecoder.model.Episode;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EpisodeMapper {

    @Mapping(target = "themeList", ignore = true)
    Episode toEpisodeWithout(EpisodeDto episodeDto);
}
