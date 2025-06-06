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
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public String index(ArticleForm articleForm, CommentForm commentForm, Model model){
        //表示するたびに最新の記事一覧を取得
        List<Article> articleList = articleRepository.findAllWithComments();
        Map<Integer, Integer> goodCountMap = new HashMap<>();
        for (Article article : articleList) {
            String key = "goodCount_" + article.getId();
            Integer count = (Integer) application.getAttribute(key);
            goodCountMap.put(article.getId(), count != null ? count : 0);
        }
        model.addAttribute("articleList",articleList);
        model.addAttribute("goodCountMap", goodCountMap);
        return "index";
    }



    /**
     * 記事を投稿する.
     *
     * @param articleForm 記事フォーム
     * @return 記事一覧画面
     */
    @PostMapping("/insert-article")
    public String insertArticle(@Validated ArticleForm articleForm, BindingResult articleResult, CommentForm commentForm,Model model){

        //一つでもエラーがあれば戻る
        if(articleResult.hasErrors()){
            return index(articleForm,commentForm,model);
        }

        //記事投稿者と内容を手動でマッピング
        Article article = new Article();
        article.setName(articleForm.getArticleName());
        article.setContent(articleForm.getArticleContent());

        //実行
        articleRepository.insert(article);
        return "redirect:/article";
    }



    /**
     * コメントを投稿する.
     *
     * @param commentForm コメントフォーム
     * @return 記事一覧画面
     */
    @PostMapping("/insert-comment")
    public String insertComment(ArticleForm articleForm, @Validated CommentForm commentForm,BindingResult commentResult,Model model){

        //一つでもエラーがあれば戻る
        if(commentResult.hasErrors()){
            return index(articleForm,commentForm,model);
        }

        Comment comment = new Comment();

        //articleIdのみ自動でマッピング
        BeanUtils.copyProperties(commentForm,comment);
        //コメント投稿者と内容を手動でマッピング
        comment.setArticleId(Integer.parseInt(commentForm.getArticleId()));
        comment.setName(commentForm.getCommentName());
        comment.setContent(commentForm.getCommentContent());

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


    /**
     * いいね追加
     */
    @PostMapping("/add-good")
    @ResponseBody
    public Map<String, Object> addGood(Integer articleId){
        String key = "goodCount_" + articleId;

        //既にある場合は取り出して+1,ないなら1から
        Integer goodCount = (Integer) application.getAttribute(key);
        if(goodCount == null) {
            goodCount = 1;
        }else{
            goodCount++;
        }

        //applicationスコープに格納
        application.setAttribute(key,goodCount);

        //レスポンス
        Map<String, Object> response = new HashMap<>();
        response.put("articleId", articleId);
        response.put("goodCount" ,goodCount);
        return response;
    }
}
