CREATE TABLE IF NOT EXISTS "rating" (
  "rating_id" integer GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  "rating_cd" varchar
);

CREATE TABLE IF NOT EXISTS "genre" (
  "genre_id" integer GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  "genre_nm" varchar
);

CREATE TABLE IF NOT EXISTS "film" (
  "film_id" integer GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  "film_nm" varchar,
  "film_desc" varchar,
  "release_dt" date,
  "duration_min" integer,
  "rating_id" integer REFERENCES "rating" ("rating_id")
);

CREATE TABLE IF NOT EXISTS "film_x_genre" (
  "film_id" integer REFERENCES "film" ("film_id"),
  "genre_id" integer REFERENCES "genre" ("genre_id"),
  PRIMARY KEY ("film_id", "genre_id")
);

CREATE TABLE IF NOT EXISTS "user" (
  "user_id" integer GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  "email_txt" varchar,
  "login_nm" varchar,
  "user_nm" varchar,
  "birth_dt" date
);

CREATE TABLE IF NOT EXISTS "like" (
  "film_id" integer REFERENCES "film" ("film_id"),
  "user_id" integer REFERENCES "user" ("user_id"),
  PRIMARY KEY ("film_id", "user_id")
);

CREATE TABLE IF NOT EXISTS "friend" (
  "user_id" integer REFERENCES "user" ("user_id"),
  "friend_user_id" integer REFERENCES "user" ("user_id"),
  "status_cd" varchar,
  PRIMARY KEY ("user_id", "friend_user_id")
);
