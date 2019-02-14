CREATE SCHEMA library;


CREATE TABLE library.poster (
  id SERIAL PRIMARY KEY,
  url VARCHAR(100) UNIQUE
);

CREATE TABLE library.author (
  id SERIAL PRIMARY KEY,
  name VARCHAR(100),
  url VARCHAR(200) UNIQUE ,
  year_begin VARCHAR(20),
  year_end VARCHAR(20),
  biography TEXT,
  img_url VARCHAR(200)
);

CREATE TABLE library.book (
  id SERIAL PRIMARY KEY,
  name VARCHAR(100),
  originalUrl VARCHAR(200) UNIQUE ,
  year INTEGER,
  description TEXT,
  poster INTEGER REFERENCES library.poster(id)
);

CREATE TABLE library.genre (
  id SERIAL PRIMARY KEY,
  name VARCHAR(100) UNIQUE
);

-- characteristics in mongo
CREATE TABLE library.tag (
  id SERIAL PRIMARY KEY,
  name VARCHAR(100) UNIQUE
);

CREATE TABLE library.protagonist (
  id SERIAL PRIMARY KEY,
  name VARCHAR(100) UNIQUE
);

CREATE TABLE library.place_of_event (
  id SERIAL PRIMARY KEY,
  name VARCHAR(100) UNIQUE
);

CREATE TABLE library.time_of_event (
  id SERIAL PRIMARY KEY,
  name VARCHAR(100) UNIQUE
);



CREATE TABLE library.book__genre (
  book INTEGER REFERENCES library.book(id),
  genre INTEGER REFERENCES library.genre(id)
);

-- characteristics in mongo
CREATE TABLE library.book__tag (
  book INTEGER REFERENCES library.book(id),
  tag INTEGER REFERENCES library.tag(id)
);

CREATE TABLE library.book__protagonist (
  book INTEGER REFERENCES library.book(id),
  protagonist INTEGER REFERENCES library.protagonist(id)
);

CREATE TABLE library.book__place_of_event (
  book INTEGER REFERENCES library.book(id),
  place_of_event INTEGER REFERENCES library.place_of_event(id)
);

CREATE TABLE library.book__time_of_event (
  book INTEGER REFERENCES library.book(id),
  time_of_event INTEGER REFERENCES library.time_of_event(id)
);

CREATE TABLE library.book__author (
  author INTEGER REFERENCES library.author(id),
  book INTEGER REFERENCES library.book(id)
)