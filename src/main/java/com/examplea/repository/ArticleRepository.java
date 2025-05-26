package com.examplea.repository;

import com.examplea.domain.Article;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * articlesテーブルを操作するリポジトリ.
 */
@Repository
public class ArticleRepository {
    /**
     * Articleオブジェクトを生成するローマッパー.
     */
    private static final RowMapper<Article> ARTICLE_ROW_MAPPER = (rs, i) -> {
        Article article = new Article();
        article.setId(rs.getInt("id"));
        article.setName(rs.getString("name"));
        article.setContent(rs.getString("content"));
        return article;
    };

    private final NamedParameterJdbcTemplate template;
    public ArticleRepository(NamedParameterJdbcTemplate template) {
        this.template = template;
    }


    /**
     * 記事一覧をidの降順で取得.
     *
     * @return 記事一覧 記事が存在しない場合はサイズ0の記事一覧を返す
     */
    public List<Article> findAll(){
        String sql = """
                select
                id,name,content
                from articles
                order by id desc
                ;
                """;
        return template.query(sql,ARTICLE_ROW_MAPPER);
    }

    /**
     * 記事を投稿する.
     *
     * @param article 記事情報
     */
    public void insert(Article article){

        SqlParameterSource param = new BeanPropertySqlParameterSource(article);

        String sql = """
                INSERT INTO articles(name,content)
                VALUES(:name, :content)
                ;
                """;
        template.update(sql,param);
    }


    /**
     * 記事を削除する.
     *
     * @param id 削除したい記事のid
     */
    public void deleteById(int id){
        String sql = """
                DELETE FROM articles
                WHERE id = :id
                ;
                """;
        SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
        template.update(sql,param);
    }
}
