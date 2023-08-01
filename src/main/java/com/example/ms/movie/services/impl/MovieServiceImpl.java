package com.example.ms.movie.services.impl;

import com.example.ms.movie.controllers.dtos.requests.CreateMovieRequest;
import com.example.ms.movie.controllers.dtos.requests.RentAndReturnMovieRequest;
import com.example.ms.movie.controllers.dtos.requests.UpdateMovieRequest;
import com.example.ms.movie.controllers.dtos.responses.GetClientByMovieIdResponse;
import com.example.ms.movie.entities.Movie;
import com.example.ms.movie.repositories.MovieRepository;
import com.example.ms.movie.services.ClientFeignClient;
import com.example.ms.movie.services.EmailService;
import com.example.ms.movie.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ClientFeignClient clientFeignClient;

    @Override
    public Movie createMovie(CreateMovieRequest createMovieRequest) {

        Movie movie = Movie.builder()
                .movieName(createMovieRequest.getMovieName())
                .movieAvailable(true)
                .clientNumber(null)
                .build();

        return movieRepository.save(movie);
    }

    @Override
    public List<Movie> getAllMovies() {

        List<Movie> movies  = movieRepository.findAll();

        return movies;
    }

    @Override
    public Movie getMovieById(Long id) {
        Optional<Movie> movie = movieRepository.findById(id);

        if(movie.isPresent()) {

            return movie.get();
        }   else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found");
    }

    @Override
    public void deleteMovie(Long id) {

        Optional<Movie> client = movieRepository.findById(id);

        if(client.isPresent()) {

            movieRepository.deleteById(id);
        }   else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found");
    }

    @Override
    public Movie updateMovie(Long id, UpdateMovieRequest updateMovieRequest) {

        Optional<Movie> optionalMovie = movieRepository.findById(id);

        if (optionalMovie.isPresent()) {

            Movie movie = optionalMovie.get();

            if (updateMovieRequest.getMovieName() != null) {

                movie.setMovieName(updateMovieRequest.getMovieName());
            }

            return movieRepository.save(movie);
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found");
    }

    @Override
    public void rentMovie(RentAndReturnMovieRequest rentAndReturnMovieRequest) {

        Long movieId = rentAndReturnMovieRequest.getMovieId();
        Optional<Movie> optionalMovie = movieRepository.findById(movieId);

        if (optionalMovie.isPresent()) {

            Movie movie = optionalMovie.get();
            String recipient = clientFeignClient.getClientByClientNumber(rentAndReturnMovieRequest.getClientNumber()).getMail();

            if (movie.getMovieAvailable()) {

                movie.setMovieAvailable(false);
                movie.setClientNumber(rentAndReturnMovieRequest.getClientNumber());
                movieRepository.save(movie);

                String subject = "Movie rented: " + movie.getMovieName();
                String content = "Dear costumer,\n\nYou've rented the movie '" + movie.getMovieName() + "'. Enjoy it!\n\nRegards.";
                emailService.sendEmail(recipient, subject, content);
            } else {

                String subject = "Movie not available: " + movie.getMovieName();
                String content = "Dear costumer,\n\nUnfortunately, the title '" + movie.getMovieName() + "' is not available at the moment.\nFeel free to check our other available movies.\n\nRegards.";
                emailService.sendEmail(recipient, subject, content);
            }
        }
    }

    @Override
    public void returnMovie(RentAndReturnMovieRequest rentAndReturnMovieRequest) {

        Long movieId = rentAndReturnMovieRequest.getMovieId();
        Optional<Movie> optionalMovie = movieRepository.findById(movieId);

        if (optionalMovie.isPresent()) {

            Movie movie = optionalMovie.get();
            String recipient = (clientFeignClient.getClientByClientNumber(rentAndReturnMovieRequest.getClientNumber())).getMail();

            if (!movie.getMovieAvailable()) {

                movie.setMovieAvailable(true);
                movie.setClientNumber(null);
                movieRepository.save(movie);

                String subject = "Movie returned: " + movie.getMovieName();
                String content = "Dear costumer,\n\nYou've successfully returned the movie '" + movie.getMovieName() + "'. We hope you've enjoyed it!\n\nRegards.";
                emailService.sendEmail(recipient, subject, content);
            } else {

                String subject = "Movie not currently on your possession.";
                String content = "Dear costumer,\n\nThe title '" + movie.getMovieName() + "' is not currently in your possession.\nPlease check the information provided.\n\nRegards.";
                emailService.sendEmail(recipient, subject, content);
            }
        }
    }

    @Override
    public List<Movie> getMoviesByClientNumber(String clientNumber) {

        List<Movie> movieList = movieRepository.findByClientNumber(clientNumber);

        if(movieList.size() > 0) {

            return movieList;
        }   else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This client doesn't have any rented movies");
    }

    @Override
    public GetClientByMovieIdResponse getClientByMovieId(Long movieId) {

        Optional<Movie> optionalMovie = movieRepository.findById(movieId);

        if (optionalMovie.isPresent()) {

            Movie movie = optionalMovie.get();

            if (!movie.getMovieAvailable()) {

                return clientFeignClient.getClientByClientNumber(movie.getClientNumber());
            } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The movie is not currently rented");
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The provided movie ID doesn't exist");
    }
}
