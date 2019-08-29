package com.timecoder.service;

import com.timecoder.PostExistsException;
import com.timecoder.dto.PostDto;
import com.timecoder.mapper.PostMapper;
import com.timecoder.model.Post;
import com.timecoder.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;

    public Long createPost(PostDto postDto) {
        Post post = postMapper.toPost(postDto);
        return postRepository.save(post).getId();
    }

    public Iterable<Post> getAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    public Optional<Post> getPostById(long id) {
        return Optional.of(postRepository.findById(id).orElseThrow(() -> new PostExistsException("Post id " + id + " not found")));
    }

    public Long updatePost(Post post) {
        return postRepository.save(post).getId();
    }

    public boolean deletePostById(long id) {
        postRepository.deleteById(id);
        return true;
    }
}
