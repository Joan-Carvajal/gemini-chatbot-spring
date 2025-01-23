package com.ai.spring.gemini_chat.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
public class QnAService {
    @Value("${gemini.api.url}")
    private String geminiApiUlr;
    @Value("${gemini.api.key}")
    private String geminiApiKey;

    private final WebClient webClient;

    public QnAService(WebClient.Builder webClient) {
        this.webClient = webClient.build();
    }

    public String getAnswer(String question) {
        Map<String,Object> requestBody= Map.of(
                "contents", new Object[]{
                        Map.of("parts", new Object[]{
                                 Map.of("text",question)
                        })
                });
//{   "contents": [{     "parts":[{"text": "Explain how AI works"}]     }]    }
        //make api call
        String response=webClient.post()
                .uri(geminiApiUlr + geminiApiKey)
                .header("Content-Type","application/json")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return response;
    }
}
