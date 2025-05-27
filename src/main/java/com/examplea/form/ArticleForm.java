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
    private String name;

    /** 内容 */
    @NotBlank(message = "投稿内容を入力してください")
    @Size(max = 50, message = "投稿内容は1000文字以内で入力してください")
    private String content;



    // getter setter
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
        return "ArticleForm{" +
                "name='" + name + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
