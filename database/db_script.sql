CREATE TABLE company(
	id serial primary key,
	name varchar(50) not null
);

CREATE TABLE medicine(
	id serial PRIMARY KEY,
	name varchar(50) NOT NULL,
	desciption text,
	company_id integer NOT NULL REFERENCES company(id)
);

