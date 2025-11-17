package com.softwarebhayya.movie_service.repo;

import com.softwarebhayya.movie_service.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepo extends JpaRepository<Movie, Long> {
}
