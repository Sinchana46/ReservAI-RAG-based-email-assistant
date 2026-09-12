package com.sinchana.RetrievAI.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class ExplainService {
    private final ChatClient chatClient;
    public ExplainService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }
    public String explain(String text) {
        String prompt = """
                You are an AI email assistant.

                Explain the following text in simple language.

                Preserve the original meaning, but make technical,
                complicated, or ambiguous language easier to understand.

                Text:
                %s

                Give a concise explanation.
                """.formatted(text);

        return chatClient
                .prompt()
                .user(prompt)
                .call()
                .content();
    }
}
