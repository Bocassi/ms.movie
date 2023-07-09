package com.example.ms.movie.controllers.dtos.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Entity used to create a new movie")
public class CreateMovieRequest {

    @NotBlank(message = "'movieName' can't be null or blank")
    @Schema(description = "The name of the movie")
    private String movieName;
}
