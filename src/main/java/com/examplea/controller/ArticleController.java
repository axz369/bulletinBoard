package com.examplea.controller;

import com.examplea.repository.ArticleRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 記事情報を操作するコントローラ.
 */
@Controller
@RequestMapping("article")
public class ArticleController {

    private final ArticleRepository articleRepository;
    public ArticleController(ArticleRepository articleRepository){
        this.articleRepository = articleRepository;
    }



    @GetMapping("")
    public String index(){
        return "index";
    }
}
