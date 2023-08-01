package com.example.ms.movie.messaging;

import com.example.ms.movie.controllers.dtos.requests.RentAndReturnMovieRequest;
import com.example.ms.movie.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    @Autowired
    private MovieService movieService;

    @KafkaListener(topics = "${spring.kafka.topic-rent}",
            groupId = "${spring.kafka.group-rent}",
            containerFactory = "rentAndReturnKafkaListenerContainerFactory")
    public void consumeRentMovieRequest(RentAndReturnMovieRequest rentAndReturnMovieRequest) {

        movieService.rentMovie(rentAndReturnMovieRequest);
    }

    @KafkaListener(topics = "${spring.kafka.topic-return}",
            groupId = "${spring.kafka.group-return}",
            containerFactory = "rentAndReturnKafkaListenerContainerFactory")
    public void consumeReturnMovieRequest(RentAndReturnMovieRequest rentAndReturnMovieRequest) {

        movieService.returnMovie(rentAndReturnMovieRequest);
    }
}
