package com.example.firstproject.dto;

import com.example.firstproject.entity.Article;
import com.example.firstproject.entity.Comment;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
@Getter
@NoArgsConstructor
public class CommentDto {
    private Long id;
    @JsonProperty("article_id")
    private Long articleId;       //해당 게시글의 부모 게시글 id
    private String nickname;        //댓글 단 사람
    private String body;            //댓글 본문

    //생성 메서드
    public static CommentDto createCommentDto(Comment comment) {
        return new CommentDto(comment.getId(),
                comment.getArticle().getId(),
                comment.getNickname(),
                comment.getBody()
        );
    }
}