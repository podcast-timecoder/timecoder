package com.timecoder.repository;

import com.timecoder.model.Post;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface PostRepository extends PagingAndSortingRepository<Post, Long> {

}
