package com.example.ms.movie.controllers;

import com.example.ms.movie.controllers.dtos.requests.CreateMovieRequest;
import com.example.ms.movie.controllers.dtos.requests.UpdateMovieRequest;
import com.example.ms.movie.entities.Movie;
import com.example.ms.movie.services.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MovieControllerTest {

    @Mock
    private MovieService movieService;

    private MovieController movieController;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);
        movieController = new MovieController(movieService);
    }

    @Test
    void createMovie() {

        CreateMovieRequest createMovieRequest = CreateMovieRequest.builder().movieName("Test Movie").build();
        Movie movie = Movie.builder().movieId(1L).movieName("Test Movie").movieAvailable(true).clientNumber(null).build();

        Mockito.when(movieService.createMovie(createMovieRequest)).thenReturn(movie);
        ResponseEntity<Movie> result = movieController.createMovie(createMovieRequest);

        Mockito.verify(movieService).createMovie(createMovieRequest);
        assert result.getStatusCode() == HttpStatus.CREATED;
        assert result.getBody().equals(movie);
    }

    @Test
    void getAllMovies() {

        List<Movie> movieList = List.of(Movie.builder().movieId(null).movieName("Movie Test").movieAvailable(true).clientNumber(null).build(),
                Movie.builder().movieId(null).movieName("Movie Test 2: Payback").movieAvailable(true).clientNumber(null).build());

        Mockito.when(movieService.getAllMovies()).thenReturn(movieList);
        ResponseEntity<List<Movie>> result = movieController.getAllMovies();

        Mockito.verify(movieService).getAllMovies();
        assert result.getStatusCode() == HttpStatus.OK;
        assert result.getBody().equals(movieList);
    }

    @Test
    void getMovieById() {

        Movie movie = Movie.builder().movieId(1L).movieName("Test Movie").movieAvailable(true).clientNumber(null).build();

        Mockito.when(movieService.getMovieById(1L)).thenReturn(movie);
        ResponseEntity<Movie> result = movieController.getMovieById(1L);

        Mockito.verify(movieService).getMovieById(1L);
        assert result.getStatusCode() == HttpStatus.OK;
        assert result.getBody().equals(movie);
    }


    @Test
    void updateMovie() {

        Movie movie = Movie.builder().movieId(1L).movieName("Test Movie").movieAvailable(false).clientNumber("Test01").build();
        UpdateMovieRequest updateMovieRequest = UpdateMovieRequest.builder().movieName("New test movie").build();

        Mockito.when(movieService.updateMovie(1L,updateMovieRequest)).thenReturn(movie);
        ResponseEntity<Movie> result = movieController.updateMovie(1L,updateMovieRequest);

        Mockito.verify(movieService).updateMovie(1L,updateMovieRequest);
        assert result.getStatusCode() == HttpStatus.OK;
        assert result.getBody().equals(movie);
    }

    @Test
    void deleteMovie() {

        Mockito.doNothing().when(movieService).deleteMovie(1L);

        movieController.deleteMovie(1L);
        Mockito.verify(movieService,Mockito.times(1)).deleteMovie(1L);
    }
}