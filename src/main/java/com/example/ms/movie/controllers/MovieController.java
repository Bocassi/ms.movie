package com.example.ms.movie.controllers;

import com.example.ms.movie.controllers.dtos.requests.CreateMovieRequest;
import com.example.ms.movie.controllers.dtos.requests.UpdateMovieRequest;
import com.example.ms.movie.controllers.dtos.responses.GetClientByMovieIdResponse;
import com.example.ms.movie.entities.Movie;
import com.example.ms.movie.services.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping (value = "/movie")
@Validated
@Tag(name = "Movies", description = "Endpoints for movie operations")
public class MovieController {

    private MovieService movieService;

    public MovieController(MovieService movieService) {this.movieService = movieService;}

    @PostMapping
    @Operation(summary = "Creates a new movie")
    @ApiResponse(responseCode = "201", description = "Movie created successfully")
    public ResponseEntity<Movie> createMovie(@Valid @RequestBody
                                                 @Parameter(description = "Class used to create a new movie", required = true) CreateMovieRequest createMovieRequest) {

        return new ResponseEntity<>(movieService.createMovie(createMovieRequest), HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get a list of all the movies")
    @ApiResponse(responseCode = "200", description = "List retrieved successfully")
    public ResponseEntity<List<Movie>> getAllMovies() {

        return new ResponseEntity<>(movieService.getAllMovies(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    @Operation(summary = "Get a movie by ID")
    @ApiResponse(responseCode = "200", description = "Movie found")
    @ApiResponse(responseCode = "404", description = "Movie not found")
    public ResponseEntity<Movie> getMovieById(@PathVariable @Positive(message = "'id' must be a positive value")
                                                  @Parameter(description = "Movie ID", required = true) Long id) {

        return new ResponseEntity<>(movieService.getMovieById(id), HttpStatus.OK);
    }


    @PatchMapping(value = "/{id}")
    @Operation(summary = "Updates an attribute of a movie")
    @ApiResponse(responseCode = "200", description = "Movie found")
    @ApiResponse(responseCode = "404", description = "Movie not found")
    public ResponseEntity<Movie> updateMovie(@PathVariable @Positive(message = "'id' must be a positive value") @Parameter(description = "Movie ID", required = true) Long id,
                                             @Valid @RequestBody @Parameter(description = "Class used to update an existing movie", required = true) UpdateMovieRequest updateMovieRequest) {

        return new ResponseEntity<>(movieService.updateMovie(id, updateMovieRequest), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Deletes a movie")
    @ApiResponse(responseCode = "404", description = "Movie not found")
    public void deleteMovie(@PathVariable @Positive(message = "'id' must be a positive value")
                                @Parameter(description = "Movie ID", required = true) Long id) {

        movieService.deleteMovie(id);
    }

    @GetMapping(value = "/movies")
    public ResponseEntity<List<Movie>> getMoviesByClientNumber(@RequestParam @Parameter(description = "Client Number of the renter", required = true) String clientNumber) {

        return new ResponseEntity<>(movieService.getMoviesByClientNumber(clientNumber), HttpStatus.OK);
    }

    @GetMapping(value = "/client/{movieId}")
    public ResponseEntity<GetClientByMovieIdResponse> getClientByMovieId(@PathVariable Long movieId) {

        return new ResponseEntity<>(movieService.getClientByMovieId(movieId), HttpStatus.OK);
    }
}
