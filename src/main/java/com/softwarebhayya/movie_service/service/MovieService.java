package com.softwarebhayya.movie_service.service;

import com.softwarebhayya.movie_service.exception.InvalidDataException;
import com.softwarebhayya.movie_service.exception.NotFoundException;
import com.softwarebhayya.movie_service.model.Movie;
import com.softwarebhayya.movie_service.repo.MovieRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class MovieService {

    private final MovieRepo movieRepo;

    public Movie create(Movie movie){
        if (movie == null){
            throw new InvalidDataException("Invalid movie null");
        }
        return movieRepo.save(movie);
    }

    public Movie read(Long id){
        return movieRepo.findById(id)
                .orElseThrow(()->new NotFoundException("Movie is not found id"+id));
    }

    public void update(Long id, Movie movie){
        if (id == null || movie.getId() == null){
            throw new InvalidDataException("Movie id should not be null");
        }
        if (movieRepo.existsById(id)){
            Movie referenceById = movieRepo.getReferenceById(id);
            referenceById.setActors(movie.getActors());
            referenceById.setName(movie.getName());
            referenceById.setDirector(movie.getDirector());
            movieRepo.save(referenceById);
        }else{
            throw new NotFoundException("Movie is not found to update");
        }
    }

    public void delete(Long id){
        if (id == null){
            throw new InvalidDataException("Movie id should not be null");
        }
        if (movieRepo.existsById(id)){
            movieRepo.deleteById(id);
        }else{
            throw new NotFoundException("Movie is not found to delete");
        }
    }

}
