package com.timecoder.service;

import com.timecoder.model.Episode;
import com.timecoder.model.Post;
import com.timecoder.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final EpisodeService episodeService;

    public Long createPost(Post post) {
        Episode episode = episodeService.getEpisodeById(post.getEpisodeId());
        if (episode != null) {
            return postRepository.save(post).getId();
        }
        return null;
    }

    public Iterable<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Optional<Post> getPostById(long id) {
        return Optional.of(postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post id " + id + " not found")));
    }

    public Long updatePost(Post post) {
        return postRepository.save(post).getId();
    }

    public boolean deletePostById(long id) {
        postRepository.deleteById(id);
        return true;
    }
}
