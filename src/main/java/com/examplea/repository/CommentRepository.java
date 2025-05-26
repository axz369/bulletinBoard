package com.examplea.repository;

import com.examplea.domain.Article;
import com.examplea.domain.Comment;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * commentsテーブルを操作するリポジトリ.
 */
@Repository
public class CommentRepository {
    /**
     * Commentオブジェクトを生成するローマッパー.
     */
    private static final RowMapper<Comment> COMMENT_ROW_MAPPER = (rs, i) -> {
        Comment comment = new Comment();
        comment.setId(rs.getInt("id"));
        comment.setName(rs.getString("name"));
        comment.setContent(rs.getString("content"));
        comment.setArticleId(rs.getInt("article_id"));
        return comment;
    };

    private final NamedParameterJdbcTemplate template;
    public CommentRepository(NamedParameterJdbcTemplate template) {
        this.template = template;
    }


    /**
     * 記事idからコメントを取得.
     *
     * @param articleId 記事id
     * @return コメントリスト
     */
    public List<Comment> findByArticleId(int articleId){
        String sql = """
                select
                id,name,content,articleId
                from comments
                where article_id = :articleId
                order by id
                ;
                """;
        SqlParameterSource param = new MapSqlParameterSource().addValue("articleId", articleId);
        return template.query(sql,COMMENT_ROW_MAPPER);
    }


    /**
     * コメントを投稿する.
     *
     * @param comment コメント情報
     */
    public void insert(Comment comment){

        SqlParameterSource param = new BeanPropertySqlParameterSource(comment);

        String sql = """
                INSERT INTO comments(name,content,article_id)
                VALUES(:name, :content, :article_id)
                ;
                """;
        template.update(sql,param);
    }


    /**
     * コメントを削除する.
     *
     * @param articleId 記事id
     */
    public void deleteById(int articleId){
        String sql = """
                DELETE FROM comments
                WHERE article_id = :articleId
                ;
                """;
        SqlParameterSource param = new MapSqlParameterSource().addValue("articleId", articleId);
        template.update(sql,param);
    }
}
