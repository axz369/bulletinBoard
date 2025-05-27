package com.examplea.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * コメントのフォーム.
 */
public class CommentForm {
    /** 記事id */
    private Integer articleId;

    /** 投稿者名 */
    @NotBlank(message = "名前を入力してください")
    @Size(max = 50, message = "名前は50文字以内で入力してください")
    private String name;

    /** 内容 */
    @NotBlank(message = "コメント内容を入力してください")
    @Size(max = 50, message = "コメント内容は1000文字以内で入力してください")
    private String content;



    // getter setter
    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }



    @Override
    public String toString() {
        return "CommentForm{" +
                "articleId=" + articleId +
                ", name='" + name + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
