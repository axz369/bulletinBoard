package com.examplea.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * 記事のフォーム.
 */
public class ArticleForm {
    /** 投稿者名 */
    @NotBlank(message = "投稿者名を入力してください")
    @Size(max = 50, message = "名前は50文字以内で入力してください")
    private String articleName;

    /** 内容 */
    @NotBlank(message = "投稿内容を入力してください")
    @Size(max = 50, message = "投稿内容は1000文字以内で入力してください")
    private String articleContent;



    // getter setter
    public String getArticleName() {
        return articleName;
    }

    public void setArticleName(String articleName) {
        this.articleName = articleName;
    }

    public String getArticleContent() {
        return articleContent;
    }

    public void setArticleContent(String articleContent) {
        this.articleContent = articleContent;
    }



    @Override
    public String toString() {
        return "ArticleForm{" +
                "articleName='" + articleName + '\'' +
                ", articleContent='" + articleContent + '\'' +
                '}';
    }
}
