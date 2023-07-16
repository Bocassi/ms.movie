package com.example.ms.movie.services;

import com.example.ms.movie.controllers.dtos.responses.GetClientByMovieIdResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "ms.client", url = "http://localhost:8081")
public interface ClientFeignClient {

    @GetMapping(value = "/client/client-by-clientNumber")
    GetClientByMovieIdResponse getClientByClientNumber(@RequestParam String clientNumber);
}
