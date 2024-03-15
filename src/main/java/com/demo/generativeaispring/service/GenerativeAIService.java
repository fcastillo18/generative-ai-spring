package com.demo.generativeaispring.service;

import com.demo.generativeaispring.domain.MovieDetails;
import org.springframework.ai.client.AiClient;
import org.springframework.ai.client.AiResponse;
import org.springframework.ai.parser.BeanOutputParser;
import org.springframework.ai.prompt.Prompt;
import org.springframework.ai.prompt.PromptTemplate;
import org.springframework.stereotype.Service;

@Service
public class GenerativeAIService {

    private final AiClient aiClient;

    public GenerativeAIService(AiClient aiClient) {
        this.aiClient = aiClient;
    }

    public String getJoke(String topic){
        PromptTemplate promptTemplate = new PromptTemplate("""
            Imitate a stand-up comedian and craft a joke related to the specified {topic}.
            Please ensure the content is considerate and respectful.
           """);
        promptTemplate.add("topic", topic);

        Prompt prompt = promptTemplate.create();

        return this.aiClient.generate(prompt).getGeneration().getText();
    }

    // Produce output in JSON format through Prompt-based generation.
    public String getMovies(String category, String year){
        PromptTemplate promptTemplate = new PromptTemplate("""
            Kindly recommend the top movie from the {category} category for the year {year}.
            Additionally, include a concise summary of the movie. The details should be brief
            and presented in JSON format. The required information includes: category, title, year,
            review, director, and summary.
            """);
        promptTemplate.add("category", category);
        promptTemplate.add("year", year);
        AiResponse generate = this.aiClient.generate(promptTemplate.create());
        return generate.getGeneration().getText();
    }


    // Create JSON-formatted output by converting it into a Dto.
    public MovieDetails getMoviesInJson(String category, String year) {
        BeanOutputParser<MovieDetails> movieBeanOutputParser = new BeanOutputParser<>(MovieDetails.class);
        PromptTemplate promptTemplate = new PromptTemplate("""
                Kindly identify the most outstanding movie in the {category} category from {year}.
                Include a succinct summary of the movie as well; the provided details should be concise
                and not overly detailed. Ensure the response includes the following elements in {format}:
                category, title, year, review, director, summary.
                """);
        promptTemplate.add("category", category);
        promptTemplate.add("year", year);
        promptTemplate.add("format", movieBeanOutputParser.getFormat());
        promptTemplate.setOutputParser(movieBeanOutputParser);

        AiResponse response = aiClient.generate(promptTemplate.create());
        return movieBeanOutputParser.parse(response.getGeneration().getText());
    }
}
