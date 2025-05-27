package com.examplea.controller;

import com.examplea.domain.Article;
import com.examplea.domain.Comment;
import com.examplea.form.ArticleForm;
import com.examplea.form.CommentForm;
import com.examplea.repository.ArticleRepository;
import com.examplea.repository.CommentRepository;
import jakarta.servlet.ServletContext;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 記事情報を操作するコントローラ.
 */
@Controller
@RequestMapping("/article")
public class ArticleController {

    //Applicationスコープを使うための設定
    @Autowired
    private ServletContext application;


    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;
    public ArticleController(ArticleRepository articleRepository, CommentRepository commentRepository){
        this.articleRepository = articleRepository;
        this.commentRepository = commentRepository;
    }


    /**
     * 記事一覧画面を表示する.
     *
     * @return 記事一覧と投稿画面
     */
    @GetMapping("")
    public String index(){
        //表示するたびに最新の記事一覧を取得
        List<Article> articleList = articleRepository.findByArticleWithComments();
        application.setAttribute("articleList",articleList);
        return "index";
    }


    /**
     * 記事を投稿する.
     *
     * @param form 記事フォーム
     * @return 記事一覧画面
     */
    @PostMapping("/insert-article")
    public String insertArticle(ArticleForm form){
        Article article = new Article();
        BeanUtils.copyProperties(form,article);
        //実行
        articleRepository.insert(article);
        return "redirect:/article";
    }


    /**
     * コメントを投稿する.
     *
     * @param form コメントフォーム
     * @return 記事一覧画面
     */
    @PostMapping("/insert-comment")
    public String insertComment(CommentForm form){
        Comment comment = new Comment();
        BeanUtils.copyProperties(form, comment);
        //実行
        commentRepository.insert(comment);
        return "redirect:/article";
    }


    /**
     * 記事を削除する.
     *
     * @param articleId 記事id
     * @return 記事一覧画面
     */
    @PostMapping("/delete-article")
    public String deleteArticle(Integer articleId){
        articleRepository.deleteById(articleId);
        return "redirect:/article";
    }
}
