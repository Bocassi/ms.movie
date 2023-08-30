package com.example.ms.movie.repositories;

import com.example.ms.movie.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    List<Movie> findByClientNumber(String clientNumber);
}
