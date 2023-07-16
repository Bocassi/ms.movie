package com.example.ms.movie.controllers.dtos.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Entity used to get the data of the client that currently holds a movie")
public class GetClientByMovieIdResponse {

    @Schema(description = "The client's ID")
    private Long id;

    @Schema(description = "The client's First Name")
    private String firstName;

    @Schema(description = "The client's Last Name")
    private String lastName;

    @Schema(description = "The client's DNI")
    private String dni;

    @Schema(description = "The client's Email")
    private String mail;

    @Schema(description = "The client's Phone number")
    private String phone;

    @Schema(description = "The client's client number")
    private String clientNumber;
}
