package com.example.ms.movie.repositories;

import com.example.ms.movie.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MovieRepository extends JpaRepository<Movie, Long> {

}
