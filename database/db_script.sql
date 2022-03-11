CREATE DATABASE postgresqlBlogCrawling;

CREATE TABLE blog(
	param varchar(60) NOT NULL,
	blog_url text NOT NULL,
	blog_title text NOT NULL,
	PRIMARY KEY( param, blog_url)
);

CREATE TABLE jwt_token(
	user_name text UNIQUE NOT NULL,
	jwt_token text UNIQUE NOT NULL,
	PRIMARY KEY(user_name, jwt_token)
);

CREATE TABLE cron_conf(
	id serial NOT NULL PRIMARY KEY,
	cron_exp text NOT NULL
);

CREATE INDEX on blog USING btree (param);
CREATE INDEX on jwt_token USING btree (jwt_token);

