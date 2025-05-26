package com.examplea.form;

/**
 * コメントのフォーム.
 */
public class CommentForm {
    /** 記事id */
    private Integer articleId;

    /** 投稿者名 */
    private String name;

    /** 内容 */
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
