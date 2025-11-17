package com.softwarebhayya.movie_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softwarebhayya.movie_service.config.MovieControllerTestConfig;
import com.softwarebhayya.movie_service.model.Movie;
import com.softwarebhayya.movie_service.service.MovieService;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MovieController.class)
@Import(MovieControllerTestConfig.class)
class MovieControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MovieService movieService;

    // ============= GET =============
    @Test
    void testGetMovie() throws Exception {

        Movie movie = new Movie(
                1L,
                "Pokiri",
                "Puri",
                Arrays.asList("Mahesh", "Ileana")
        );

        when(movieService.read(1L)).thenReturn(movie);

        mockMvc.perform(get("/movies/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Pokiri"))
                .andExpect(jsonPath("$.director").value("Puri"))
                .andExpect(jsonPath("$.actors[0]").value("Mahesh"));
    }

    @Test
    void testGetMovie_NotFound() throws Exception {
        when(movieService.read(99L))
                .thenThrow(new RuntimeException("Movie is not found"));

        mockMvc.perform(get("/movies/99"))
                .andExpect(status().isInternalServerError());
    }

    // ============= POST =============
    @Test
    void testCreateMovie() throws Exception {

        Movie movie = new Movie(
                1L,
                "Pokiri",
                "Puri",
                Arrays.asList("Mahesh", "Ileana")
        );

        when(movieService.create(any(Movie.class))).thenReturn(movie);

        mockMvc.perform(
                        post("/movies")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(movie))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Pokiri"));
    }

    // ============= PUT =============
    @Test
    void testUpdateMovie() throws Exception {
        Movie updated = new Movie(
                1L,
                "New Title",
                "New Director",
                Arrays.asList("New Actor")
        );

        doNothing().when(movieService).update(1L, updated);

        mockMvc.perform(
                        put("/movies/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(updated))
                )
                .andExpect(status().isOk());
    }

    // ============= DELETE =============
    @Test
    void testDeleteMovie() throws Exception {

        doNothing().when(movieService).delete(1L);

        mockMvc.perform(delete("/movies/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteMovie_NotFound() throws Exception {
        doThrow(new RuntimeException("Movie is not found to delete"))
                .when(movieService).delete(99L);

        mockMvc.perform(delete("/movies/99"))
                .andExpect(status().isInternalServerError());
    }
}
