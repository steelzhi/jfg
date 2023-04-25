DROP TABLE IF EXISTS friend;
DROP TABLE IF EXISTS film_like;
DROP TABLE IF EXISTS film_x_genre;
DROP TABLE IF EXISTS review_like;
DROP TABLE IF EXISTS film_x_director;
DROP TABLE IF EXISTS event;
DROP TABLE IF EXISTS review;
DROP TABLE IF EXISTS director;
DROP TABLE IF EXISTS film;
DROP TABLE IF EXISTS rating;
DROP TABLE IF EXISTS genre;
DROP TABLE IF EXISTS "user";

CREATE TABLE IF NOT EXISTS rating (
  rating_id integer GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  name varchar
);

CREATE TABLE IF NOT EXISTS genre (
  genre_id integer GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  name varchar
);

CREATE TABLE IF NOT EXISTS film (
  film_id integer GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  name varchar,
  description varchar,
  release_dt date,
  duration integer,
  rating_id integer REFERENCES rating (rating_id)
);

CREATE TABLE IF NOT EXISTS film_x_genre (
  film_id integer REFERENCES film (film_id),
  genre_id integer REFERENCES genre (genre_id),
  PRIMARY KEY (film_id, genre_id)
);

CREATE TABLE IF NOT EXISTS "user" (
  user_id integer GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  email varchar,
  login varchar,
  name varchar,
  birth_dt date
);

CREATE TABLE IF NOT EXISTS film_like (
  film_id integer REFERENCES film (film_id),
  user_id integer REFERENCES "user" (user_id),
  PRIMARY KEY (film_id, user_id)
);

CREATE TABLE IF NOT EXISTS friend (
  user_id integer REFERENCES "user" (user_id),
  friend_user_id integer REFERENCES "user" (user_id),
  status varchar,
  PRIMARY KEY (user_id, friend_user_id)
);


CREATE TABLE review (
  review_id integer GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  creator_user_id integer REFERENCES "user" (user_id),
  reviewed_film_id integer REFERENCES film (film_id),
  content varchar,
  is_positive boolean
);

CREATE TABLE review_like (
  review_id integer REFERENCES review (review_id),
  user_id integer REFERENCES "user" (user_id),
  score integer,
  PRIMARY KEY (review_id, user_id)
);

CREATE TABLE director (
  director_id integer GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  name varchar
);

CREATE TABLE film_x_director (
  film_id integer REFERENCES film (film_id),
  director_id integer REFERENCES director (director_id),
  PRIMARY KEY (film_id, director_id)
);

CREATE TABLE event (
  event_id integer GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  user_id integer REFERENCES "user" (user_id),
  event_type varchar,
  action_type varchar,
  entity_id integer,
  event_dttm timestamp
);
