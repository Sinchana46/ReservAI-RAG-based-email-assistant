package com.sinchana.RetrievAI.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RagQaService {
    private final VectorStore vectorStore;
    private final ChatClient chatClient;

    public RagQaService(
            VectorStore vectorStore,
            ChatClient.Builder chatClientBuilder
    ) {
        this.vectorStore = vectorStore;
        this.chatClient = chatClientBuilder.build();
    }

    public String ask(String question) {

        List<Document> relevantDocuments = vectorStore.similaritySearch(
                SearchRequest.builder()
                        .query(question)
                        .topK(3)
                        .build()
        );

        String context = relevantDocuments.stream()
                .map(document -> {
                    String sender = String.valueOf(
                            document.getMetadata().getOrDefault("sender", "Unknown")
                    );

                    String subject = String.valueOf(
                            document.getMetadata().getOrDefault("subject", "Unknown")
                    );

                    return """
                            Sender: %s
                            Subject: %s

                            Email:
                            %s
                            """.formatted(
                            sender,
                            subject,
                            document.getText()
                    );
                })
                .reduce("", (a, b) -> a + "\n\n" + b);

        // 3. Give the retrieved context + question to Gemini
        String prompt = """
                You are an AI email assistant.

                Answer the user's question using ONLY the provided email context.

                Important rules:
                - Do not invent information.
                - If the context does not contain the answer, say:
                  "I could not find this information in the indexed emails."
                - If multiple emails contain relevant information, combine them carefully.
                - If the emails contain conflicting information, mention the conflict
                  instead of silently choosing one version.

                Email context:
                %s

                User question:
                %s

                Give a clear and concise answer.
                """.formatted(context, question);

        // 4. Ask Gemini to generate the final answer
        return chatClient
                .prompt()
                .user(prompt)
                .call()
                .content();
    }
}
