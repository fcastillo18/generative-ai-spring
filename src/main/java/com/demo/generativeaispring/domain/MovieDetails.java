package com.demo.generativeaispring.domain;

public record MovieDetails(
        String category,
        String title,
        String year,
        String review,
        String director,
        String summary
){}