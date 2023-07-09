package com.example.ms.movie.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "movies")
@Schema(description = "Movie entity")
public class Movie {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Schema(description = "Auto-generated movie ID")
    private Long movieId;

    @Column
    @Schema(description = "Movie name")
    private String movieName;

    @Column
    @Schema(description = "States if the movie is available for rental. Default: true")
    private Boolean movieAvailable;

    @Column
    @Schema(description = "States the client that currently has the movie. Default: null")
    private String clientNumber;
}
