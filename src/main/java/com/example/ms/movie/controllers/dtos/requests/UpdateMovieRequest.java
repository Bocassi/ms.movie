package com.example.ms.movie.controllers.dtos.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Entity used to update an existing movie")
public class UpdateMovieRequest {

    @Pattern(regexp = "^(?!\\s*$).+", message = "'movieName' can't consist only of white spaces")
    @Schema(description = "The new name of the movie")
    private String movieName;
}
