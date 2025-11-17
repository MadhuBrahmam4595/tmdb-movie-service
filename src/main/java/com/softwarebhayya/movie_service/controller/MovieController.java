package com.softwarebhayya.movie_service.controller;


import com.softwarebhayya.movie_service.model.Movie;
import com.softwarebhayya.movie_service.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovie(@PathVariable String id){
        Movie read = movieService.read(Long.valueOf(id));
        return ResponseEntity.ok(read);
    }

    @PostMapping
    public ResponseEntity<Movie> create(@RequestBody Movie movie){
        Movie movie1 = movieService.create(movie);
        return ResponseEntity.ok(movie1);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable String id, @RequestBody Movie movie){
        movieService.update(Long.valueOf(id), movie);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id){
        movieService.delete(Long.valueOf(id));
    }
}
