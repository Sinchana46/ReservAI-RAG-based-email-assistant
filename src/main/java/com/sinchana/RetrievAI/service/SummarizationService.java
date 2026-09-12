package com.sinchana.RetrievAI.service;

import com.sinchana.RetrievAI.dto.EmailRequest;
import jakarta.validation.Valid;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class SummarizationService {
    private final ChatClient chatClient;
    public SummarizationService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    public String summarize(@Valid EmailRequest email) {
        String prompt = """
                You are an AI email assistant.
                Summarize the following clearly and concisely.
                Include:
                - Main purpose of the email
                - Important information
                - Deadlines or dates, if any
                - Action items for the recipient, if any
                
                Sender: %s
                Subject: %s
                
                Email:
                %s
                
                Keep the summary concise and easy to understand.
                """.formatted(
                email.sender(),
                email.subject(),
                email.content()
        );
        return chatClient
                .prompt()
                .user(prompt)
                .call()
                .content();
    }
}
