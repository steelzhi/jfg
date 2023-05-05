package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface FilmStorage {
    Collection<Film> get();

    List<Film> search(String query, Boolean director, Boolean film);

    public Optional<Film> getById(int id);

    public Film create(Film film);

    public Optional<Film> update(Film film);

    public void addLike(Film film, User user);

    public void removeLike(Film film, User user);

    List<Film> getFilmsByDirector(int directorId);

    public Collection<Film> getPopularByGenreAndYear(int count, int genreId, int year);

    public void removeFilm(int filmId);

    Collection<Film> getFilmRecommendations(int userId);

    public Collection<Film> getCommonFilms(int userId, int friendId);
}