DROP TABLE IF EXISTS comments;
DROP TABLE IF EXISTS articles;

CREATE TABLE articles(
	id serial PRIMARY KEY, --投稿ID
	name text NOT NULL, --名前
	content text NOT NULL --記事内容
);

CREATE TABLE comments(
	id serial PRIMARY KEY, --コメントID
	name text NOT NULL, --名前
	content text NOT NULL, --コメント内容
	article_id integer REFERENCES articles(id) --投稿ID
);

INSERT INTO articles(name,content) VALUES('伊賀','この掲示板について');
INSERT INTO articles(name,content) VALUES('山田','新たな投稿です');

INSERT INTO comments(name,content,article_id) VALUES('佐藤1','伊賀さんの書き込みコメント1',1);
INSERT INTO comments(name,content,article_id) VALUES('佐藤2','伊賀さんの書き込みコメント2',1);
INSERT INTO comments(name,content,article_id) VALUES('佐藤3','伊賀さんの書き込みコメント3',1);
INSERT INTO comments(name,content,article_id) VALUES('吉田1','山田さんの書き込みコメント1',2);
INSERT INTO comments(name,content,article_id) VALUES('吉田2','山田さんの書き込みコメント2',2);