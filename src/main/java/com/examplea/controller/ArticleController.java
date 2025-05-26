package com.examplea.controller;

import com.examplea.domain.Article;
import com.examplea.repository.ArticleRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 記事情報を操作するコントローラ.
 */
@Controller
@RequestMapping("/article")
public class ArticleController {

    private final ArticleRepository articleRepository;
    public ArticleController(ArticleRepository articleRepository){
        this.articleRepository = articleRepository;
    }



    @GetMapping("")
    public String index(Model model){
        List<Article> articleList = articleRepository.findAll();
        model.addAttribute("articleList",articleList);
        System.out.println(articleList);
        return "index";
    }
}
