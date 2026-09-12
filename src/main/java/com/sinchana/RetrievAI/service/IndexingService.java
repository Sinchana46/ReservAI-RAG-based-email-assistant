package com.sinchana.RetrievAI.service;

import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IndexingService {
    private final VectorStore vectorStore;

    public IndexingService(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }
    public void indexEmail(String sender, String subject, String content) {
        Document document = new Document(content,
                java.util.Map.of("sender", sender, "subject", subject));
        TokenTextSplitter splitter = TokenTextSplitter.builder().build();
        List<Document> chunks = splitter.apply(List.of(document));
        vectorStore.add(chunks);
    }
}
