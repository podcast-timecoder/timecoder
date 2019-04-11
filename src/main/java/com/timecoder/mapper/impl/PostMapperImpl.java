package com.timecoder.mapper.impl;

import com.timecoder.dto.PostDto;
import com.timecoder.mapper.PostMapper;
import com.timecoder.model.Post;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2019-04-11T13:20:38+0300",
    comments = "version: 1.3.0.Final, compiler: javac, environment: Java 1.8.0_191 (Oracle Corporation)"
)
@Component
public class PostMapperImpl implements PostMapper {

    @Override
    public Post toPost(PostDto postDto) {
        if ( postDto == null ) {
            return null;
        }

        Post post = new Post();

        post.setCreatedAt( postDto.getCreatedAt() );
        post.setName( postDto.getName() );
        post.setShortDescription( postDto.getShortDescription() );
        post.setDescription( postDto.getDescription() );
        post.setLink( postDto.getLink() );
        List<String> list = postDto.getGuests();
        if ( list != null ) {
            post.setGuests( new ArrayList<String>( list ) );
        }

        return post;
    }
}
