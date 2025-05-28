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
    private String commentName;

    /** 内容 */
    @NotBlank(message = "コメント内容を入力してください")
    @Size(max = 50, message = "コメント内容は1000文字以内で入力してください")
    private String commentContent;



    // getter setter
    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public String getCommentName() {
        return commentName;
    }

    public void setCommentName(String commentName) {
        this.commentName = commentName;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }



    @Override
    public String toString() {
        return "CommentForm{" +
                "articleId=" + articleId +
                ", commentName='" + commentName + '\'' +
                ", commentContent='" + commentContent + '\'' +
                '}';
    }
}
