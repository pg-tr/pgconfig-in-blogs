CREATE DATABASE postgresqlBlogCrawling;

CREATE TABLE blog(
	param varchar(60) NOT NULL,
	blog_url text NOT NULL,
	blog_title text NOT NULL,
	PRIMARY KEY( param, blog_url)
);

