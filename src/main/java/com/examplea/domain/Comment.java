package com.examplea.domain;

/**
 * コメント情報を表すドメイン.
 */
public class Comment {
    
    /** コメントid */
    private Integer id;

    /** コメントid */
    private String name;

    /** 投稿者名 */
    private String content;

    /** 記事id */
    private Integer articleId;



    // getter setter
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }



    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", content='" + content + '\'' +
                ", articleId=" + articleId +
                '}';
    }
}
