package com.examplea.repository;

import com.examplea.domain.Article;
import com.examplea.domain.Comment;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
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



    /**
     * Commentオブジェクト付きのArticleオブジェクトを生成するエクストラクター.
     */
    private static final ResultSetExtractor<List<Article>> ARTICLE_WITH_COMMENTS_EXTRACTOR = rs -> {
        List<Article> articleList = new ArrayList<>();

        //検索結果を一行ずつ見ていく
        while (rs.next()) {
            //注目行の記事idを取得
            int articleId = rs.getInt("a_id");

            Article article = null;
            // 注目行のarticleIdと同じ記事がarticleListに存在するかをチェック
            // 既に同じ記事があるならそれを入れる
            for (Article a : articleList) {
                if (a.getId() == articleId) {
                    article = a;
                    break;
                }
            }

            // 注目行の記事が初めて出てきた記事なら新しくArticleオブジェクトを作成
            if (article == null) {
                article = new Article();
                article.setId(articleId);
                article.setName(rs.getString("a_name"));
                article.setContent(rs.getString("a_content"));
                article.setCommentList(new ArrayList<>());
                articleList.add(article);
            }

            // コメントがあれば追加
            // LEFT OUTER JOINしているので記事に対するコメントがないならコメントのカラムは歯抜け(null)になる
            Integer commentId = (Integer)rs.getObject("c_id");
            if (commentId != null) {
                Comment comment = new Comment();
                comment.setId(commentId);
                comment.setName(rs.getString("c_name"));
                comment.setContent(rs.getString("c_content"));
                comment.setArticleId(rs.getInt("c_article_id"));
                article.getCommentList().add(comment);
            }
        }

        return articleList;
    };




    private final NamedParameterJdbcTemplate template;
    private final CommentRepository commentRepository;
    public ArticleRepository(NamedParameterJdbcTemplate template, CommentRepository commentRepository) {
        this.template = template;
        this.commentRepository = commentRepository;
    }



    /**
     * 記事一覧をidの降順で取得する.
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
        List<Article> articleList = template.query(sql,ARTICLE_ROW_MAPPER);

        //記事のコメントを取得
        for(Article article : articleList){
            List<Comment> commentList = commentRepository.findByArticleId(article.getId());
            article.setCommentList(commentList);
        }
        return articleList;
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
        //記事を削除
        //ON DELETE CASCADE制約により付随するコメントも削除される
        String sql = """
                DELETE FROM articles
                WHERE id = :id
                ;
                """;
        SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
        template.update(sql,param);
    }



    /**
     * 記事一覧を取得する.
     *
     * @return 記事一覧
     */
    public List<Article> findAllWithComments(){
        String sql = """
                SELECT
                 a.id as a_id
                ,a.name as a_name
                ,a.content as a_content
                ,c.id as c_id
                ,c.name as c_name
                ,c.content as c_content
                ,c.article_id as c_article_id
                FROM
                articles as a
                LEFT OUTER JOIN
                comments as c
                on a.id = c.article_id
                ORDER BY a.id, c.id
                ;
                """;
        return template.query(sql, ARTICLE_WITH_COMMENTS_EXTRACTOR);
    }
}
