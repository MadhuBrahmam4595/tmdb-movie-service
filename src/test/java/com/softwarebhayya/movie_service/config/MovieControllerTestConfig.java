package com.softwarebhayya.movie_service.config;

import com.softwarebhayya.movie_service.service.MovieService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MovieControllerTestConfig {

    @Bean
    public MovieService movieService(){
        return Mockito.mock(MovieService.class);
    }

}
