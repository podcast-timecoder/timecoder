package com.timecoder.mapper.impl;

import com.timecoder.dto.EpisodeDto;
import com.timecoder.mapper.EpisodeMapper;
import com.timecoder.model.Episode;
import org.springframework.stereotype.Component;

import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2019-04-11T13:13:27+0300",
    comments = "version: 1.3.0.Final, compiler: javac, environment: Java 1.8.0_191 (Oracle Corporation)"
)
@Component
public class EpisodeMapperImpl implements EpisodeMapper {

    @Override
    public Episode toEpisodeWithout(EpisodeDto episodeDto) {
        if ( episodeDto == null ) {
            return null;
        }

        Episode episode = new Episode();

        episode.setName( episodeDto.getName() );

        return episode;
    }
}
