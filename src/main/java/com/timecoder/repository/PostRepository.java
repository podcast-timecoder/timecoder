package com.timecoder.repository;

import com.timecoder.model.Post;
import org.springframework.data.repository.CrudRepository;


public interface PostRepository extends CrudRepository<Post, Long> {

}
