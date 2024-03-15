package com.demo.generativeaispring.controller;

import com.demo.generativeaispring.domain.MovieDetails;
import com.demo.generativeaispring.service.GenerativeAIService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class BaseController {

    private final GenerativeAIService generativeAiService;

    public BaseController(GenerativeAIService generativeAiService) {
        this.generativeAiService = generativeAiService;
    }

    @GetMapping("/joke")
    public String getJoke(@RequestParam String topic){
        return generativeAiService.getJoke(topic);
    }

    @GetMapping("/movies")
    public String getBooks(@RequestParam String category, @RequestParam String year){
        return generativeAiService.getMovies(category, year);
    }

    @GetMapping("/moviesInJSON")
    public MovieDetails getBooksInJSON(@RequestParam String category, @RequestParam String year){
        return generativeAiService.getMoviesInJson(category, year);
    }
}
