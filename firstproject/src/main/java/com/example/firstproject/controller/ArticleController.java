package com.example.firstproject.controller;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.dto.CommentDto;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import com.example.firstproject.repository.CommentRepository;
import com.example.firstproject.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Objects;

@Slf4j
@Controller
public class ArticleController {
    @Autowired      //스프링 부트가 미리 생성해 놓은 리파지터리 객체 주입(DI)
    private ArticleRepository articleRepository;
    @Autowired
    private CommentService commentService;

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
        return "redirect:/articles/" + saved.getId();
    }

    //데이터 조회 후 출력 (단일 데이터)
    @GetMapping("/articles/{id}")
    public String show(@PathVariable Long id, Model model){
        log.info ("id = " + id);
        //1.id를 조회해 데이터 가져오기
        Article articleEntity = articleRepository.findById(id).orElse(null);
        List<CommentDto> commentsDtos = commentService.comments(id);
        //2.모델에 데이터 등록하기
        model.addAttribute("article",articleEntity);
        model.addAttribute("commentDtos", commentsDtos);
        //3.뷰페이지 반환하기
        return "articles/show";
    }

    //데이터 목록 조회
    @GetMapping("/articles")
    public String index(Model model){
        //1. DB에서 모든 데이터 가져오기
        List<Article> articleEntityList = articleRepository.findAll();

        //2. 모델에 데이터 등록하기
        model.addAttribute("articleList",articleEntityList);

        //3. 뷰 페이지 설정하기
        return "/articles/index";
    }

    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id,Model model){
        Article articleEntity = articleRepository.findById(id).orElse(null);
        model.addAttribute("article",articleEntity);
        return "/articles/edit";
    }

    @PostMapping("/articles/update")
    public String update(ArticleForm form){
        //1. DTO를 엔티티로 변환
        Article articleEntity = form.toEntity();
        log.info (form.toString());

        //2. 엔티티를 DB에 저장
        //2-1. DB에서 기존 데이터 가져오기
        Article target = articleRepository.findById(articleEntity.getId()).orElse(null);
        //2-2. 기존 데이터 값 갱신
        if(target!=null){
            articleRepository.save(articleEntity);
        }

        //3. 수정 결과 페이지로 리다이렉트 하기
        return "redirect:/articles/"+articleEntity.getId();
    }

    @GetMapping("/articles/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr) {
        log.info("삭제 요청이 들어왔습니다");

        //1. 삭제할 대상 가져오기
        Article target = articleRepository.findById(id).orElse(null);
        log.info (target.toString());

        //2. 대상 엔티티 삭제하기
        if (target != null){
            articleRepository.delete(target);
            rttr.addFlashAttribute("msg","삭제했습니다");
        }
        return "redirect:/articles";
    }

}
