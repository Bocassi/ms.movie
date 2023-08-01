package com.example.ms.movie.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Value(value = "${spring.kafka.topic-rent}")
    private String topicNameRentMovie;

    @Value(value = "${spring.kafka.topic-return}")
    private String topicNameReturnMovie;

    @Bean
    public NewTopic rentMovieTopic() {
        return TopicBuilder.name(topicNameRentMovie).build();
    }

    @Bean
    public NewTopic returnMovieTopic() {
        return TopicBuilder.name(topicNameReturnMovie).build();
    }
}
