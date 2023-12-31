package com.example.ms.movie.services.impl;

import com.example.ms.movie.controllers.MovieController;
import com.example.ms.movie.controllers.dtos.requests.CreateMovieRequest;
import com.example.ms.movie.controllers.dtos.requests.UpdateMovieRequest;
import com.example.ms.movie.entities.Movie;
import com.example.ms.movie.repositories.MovieRepository;
import com.example.ms.movie.services.MovieService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Optional;

class MovieServiceImplTest {

    @Mock
    private MovieRepository movieRepository;

    private MovieService movieService;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);
        movieService = new MovieServiceImpl(movieRepository);
    }

    @Test
    void createMovie() {

        Movie movie = Movie.builder().movieId(null).movieName("Movie Test").movieAvailable(true).clientNumber(null).build();

        Mockito.when(movieRepository.save(movie)).thenReturn(movie);
        Movie result = movieService.createMovie(CreateMovieRequest.builder().movieName("Movie Test").build());

        Assertions.assertEquals(movie, result);
    }

    @Test
    void getAllMovies() {

        List<Movie> movieList = List.of(Movie.builder().movieId(null).movieName("Movie Test").movieAvailable(true).clientNumber(null).build(),
        Movie.builder().movieId(null).movieName("Movie Test 2: Payback").movieAvailable(true).clientNumber(null).build());

        Mockito.when(movieRepository.findAll()).thenReturn(movieList);
        List<Movie> result = movieService.getAllMovies();

        Assertions.assertEquals(movieList, result);
    }

    @Test
    void getMovieById() {

        Movie movie = Movie.builder().movieId(1L).movieName("Movie Test").movieAvailable(true).clientNumber(null).build();

        Mockito.when(movieRepository.findById(1L)).thenReturn(Optional.ofNullable(movie));
        Movie result = movieService.getMovieById(1L);

        Assertions.assertEquals(movie, result);
    }

    @Test
    void deleteMovie() {

        Movie movie = Movie.builder().movieId(1L).movieName("Movie Test").movieAvailable(true).clientNumber(null).build();

        Mockito.when(movieRepository.findById(1L)).thenReturn(Optional.ofNullable(movie));
        movieService.deleteMovie(1L);

        Mockito.verify(movieRepository,Mockito.times(1)).deleteById(1L);
    }

    @Test
    void updateMovie() {

        Movie movie = Movie.builder().movieId(1L).movieName("Movie Test").movieAvailable(true).clientNumber(null).build();

        Mockito.when(movieRepository.findById(1L)).thenReturn(Optional.ofNullable(movie));
        Mockito.when(movieRepository.save(movie)).thenReturn(movie);
        Movie result = movieService.updateMovie(1L, UpdateMovieRequest.builder().movieName("New ").build());

        Assertions.assertEquals(movie, result);
    }

    @Test
    void getMovieNotFoundException() {

        Mockito.when(movieRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(ResponseStatusException.class,()->movieService.getMovieById(1L));
    }

      @Test
    void deleteMovieNotFoundException() {

        Mockito.when(movieRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(ResponseStatusException.class,()->movieService.deleteMovie(1L));
    }

    @Test
    void updateMovieNotFoundException() {

        Mockito.when(movieRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(ResponseStatusException.class,()->movieService.updateMovie(1L, UpdateMovieRequest.builder().movieName("New Movie Name").build()));
    }
}