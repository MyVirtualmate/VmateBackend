package com.vmate.vmatebe.domain.post.post.service;

import com.vmate.vmatebe.domain.member.member.entity.Member;
import com.vmate.vmatebe.domain.post.post.entity.Post;
import com.vmate.vmatebe.domain.post.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {
    private final PostRepository postRepository;
    @Transactional
    public void write(Member author, String title, String body, boolean published) {
        Post post = Post.builder()
                .author(author)
                .title(title)
                .body(body)
                .published(published)
                .build();
        postRepository.save(post);
    }
}