package com.vmate.vmatebe.domain.post.post.dto;

import com.vmate.vmatebe.domain.post.post.entity.Post;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public class PostDto {
    private long id;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
    private long authorId;
    private String authorName;
    private String title;
    private String body;

    public PostDto(Post post) {
        this.id = post.getId();
        this.createDate = post.getCreateDate();
        this.modifyDate = post.getModifyDate();
        this.authorId = post.getAuthor().getId();
        this.authorName = post.getAuthor().getName();
        this.title = post.getTitle();
        this.body = post.getBody();
    }

    public PostDto(long id, LocalDateTime createDate, LocalDateTime modifyDate, long authorId, String authorName, String title, String body) {
        this.id = id;
        this.createDate = createDate;
        this.modifyDate = modifyDate;
        this.authorId = authorId;
        this.authorName = authorName;
        this.title = title;
        this.body = body;
    }
}