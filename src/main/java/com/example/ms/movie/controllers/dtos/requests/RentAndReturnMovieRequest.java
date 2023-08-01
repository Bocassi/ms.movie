package com.example.ms.movie.controllers.dtos.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
@JsonDeserialize
public class RentAndReturnMovieRequest {

    @NotBlank(message = "Movie ID must be a positive number")
    @Positive(message = "Movie ID must be a positive number")
    private Long movieId;

    @NotBlank (message = "Client number must contain a valid client number")
    private String clientNumber;

    @JsonCreator
    public RentAndReturnMovieRequest(@JsonProperty("movieId") Long movieId, @JsonProperty("clientNumber") String clientNumber) {
        this.movieId = movieId;
        this.clientNumber = clientNumber;
    }
}