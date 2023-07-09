package com.example.ms.movie.services;

import com.example.ms.movie.controllers.dtos.requests.CreateMovieRequest;
import com.example.ms.movie.controllers.dtos.requests.UpdateMovieRequest;
import com.example.ms.movie.entities.Movie;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MovieService {

    Movie createMovie(CreateMovieRequest createMovieRequest);

    List<Movie> getAllMovies();

    Movie getMovieById(Long id);

    void deleteMovie(Long id);

    Movie updateMovie(Long id, UpdateMovieRequest updateMovieRequest);

    String rentMovie(Long id, String clientNumber);

    String returnMovie(Long id);

}
