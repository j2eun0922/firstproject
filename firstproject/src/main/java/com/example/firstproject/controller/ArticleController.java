package com.example.firstproject.controller;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class ArticleController {
    @Autowired      //스프링 부트가 미리 생성해 놓은 리파지터리 객체 주입(DI)
    private ArticleRepository articleRepository;

    @GetMapping("/articles/new")
    public String newArticleForm(){
        return "articles/new";
    }

    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form){
        //System.out.println(form.toString());
        log.info (form.toString());
        //1. DTO를 엔티티로 변환
        Article article = form.toEntity();
        //System.out.println(article.toString());
        log.info (article.toString());

        //2. 리파지터리로 엔티티를 db에 저장
        Article saved = articleRepository.save(article);
        //System.out.println(saved.toString());
        log.info (saved.toString());
        return "";
    }

    //데이터 조회 후 출력 (단일 데이터)
    @GetMapping("/articles/{id}")
    public String show(@PathVariable Long id, Model model){
        log.info ("id = " + id);
        //1.id를 조회해 데이터 가져오기
        Article articleEntity = articleRepository.findById(id).orElse(null);

        //2.모델에 데이터 등록하기
        model.addAttribute("article",articleEntity);

        //3.뷰페이지 반환하기
        return "articles/show";
    }

    //데이터 목록 조회
    @GetMapping("/articles")
    public String index(){
        //1. DB에서 모든 데이터 가져오기
        List<Article> articleEntityList = articleRepository.findAll();

        //2. 모델에 데이터 등록하기


        //3. 뷰 페이지 설정하기
        return "/article/index";
    }
}
