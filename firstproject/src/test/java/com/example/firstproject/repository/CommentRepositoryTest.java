package com.example.firstproject.repository;

import com.example.firstproject.entity.Article;
import com.example.firstproject.entity.Comment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CommentRepositoryTest {
    @Autowired
    CommentRepository commentRepository;

    @Test
    @DisplayName("특정 게시글의 모든 댓글 조회")

    void findByArticleId() {
        //case1: 4번 게시글의 모든 댓글 조회
        {
            //1. 입력 데이터 준비
            Long articleId = 4L;
            //2. 실제 데이터
            List<Comment> comments = commentRepository.findByArticleId(articleId);
            //3. 예상 데이터
            Article article = new Article(4L, "당신의 인생 영화는?", "댓글");
            Comment a = new Comment(1L, article, "park", "히어로");
            Comment b = new Comment(2L, article, "kim", "영웅");
            Comment c = new Comment(3L, article, "choi", "이영웅");
            List<Comment> expected = Arrays.asList(a, b, c);

            //4. 비교 및 검증
            assertEquals(expected.toString(), comments.toString(), "4번 글의 모든 댓글 출력");
        }
        //case2: 1번 게시글의 모든 댓글 조회
        //1. 입력 데이터 준비
        Long articleId = 1L;
        //2. 실제 데이터
        List<Comment> comments = commentRepository.findByArticleId(articleId);
        //3. 예상 데이터
        Article article = new Article(1L, "가가가", "111");
        List<Comment> expected = Arrays.asList();
        //4. 비교 및 검증
        assertEquals(expected.toString(), comments.toString(), "1번 글은 댓글이 없음");
    }

    @Test
    @DisplayName("특정 닉네임의 모든 댓글 조회")
    void findByNickname() {
        String nickname = "park";

        List<Comment> comments = commentRepository.findByNickname(nickname);

        Comment a = new Comment(1L, new Article(4L, "당신의 인생 영화는?", "댓글"),nickname,"히어로");
        Comment b = new Comment(4L, new Article(5L, "소울 푸드?", "머노"),nickname,"치킨");
        Comment c = new Comment(7L, new Article(6L, "취미?", "머하노"),nickname,"산책");
        List<Comment> expected = Arrays.asList(a,b,c);

        assertEquals(expected.toString(),comments.toString(), "park의 모든 댓글을 출력");
    }
}