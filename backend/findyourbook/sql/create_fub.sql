CREATE SCHEMA fub

-- пользователи сайта
CREATE TABLE fub.fub_user
(
  id SERIAL PRIMARY KEY,
  login VARCHAR(30),
  email VARCHAR(50),
  hashpass VARCHAR(32),
  country VARCHAR(30),
  gender VARCHAR(20),
  born DATE,
  news BOOLEAN
);
CREATE UNIQUE INDEX fub_user_nick_uindex ON fub.fub_user (email);

-- книги пользователя
CREATE TABLE fub.user_and_book
(
  fub_user INTEGER REFERENCES fub.fub_user(id),
  book INTEGER REFERENCES library.book(id)
);

-- жанры пользователя
CREATE TABLE fub.user_and_genre
(
  fub_user INTEGER REFERENCES fub.fub_user(id),
  genre INTEGER REFERENCES library.genre(id)
);

CREATE TABLE fub.user__favorite_book
(
  fub_user INTEGER REFERENCES fub.fub_user(id),
  book INTEGER REFERENCES library.book(id)
);

CREATE TABLE fub.user__favorite_author
(
  fub_user INTEGER REFERENCES fub.fub_user(id),
  author INTEGER REFERENCES library.author(id)
);