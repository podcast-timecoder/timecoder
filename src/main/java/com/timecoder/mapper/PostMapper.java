package com.timecoder.mapper;

import com.timecoder.dto.PostDto;
import com.timecoder.model.Post;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostMapper {

    Post toPost(PostDto postDto);
}
