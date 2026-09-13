package com.sinchana.RetrievAI.controller;

import com.sinchana.RetrievAI.dto.*;
import com.sinchana.RetrievAI.service.ExplainService;
import com.sinchana.RetrievAI.service.IndexingService;
import com.sinchana.RetrievAI.service.RagQaService;
import com.sinchana.RetrievAI.service.SummarizationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/emails")
@CrossOrigin(origins = "*")
public class EmailController {
    private final SummarizationService summarizationService;
    private final ExplainService explainService;
    private final IndexingService indexingService;
    private final RagQaService ragQaService;

    public EmailController(SummarizationService summarizationService, ExplainService explainService, IndexingService indexingService, RagQaService ragQaService) {
        this.summarizationService=summarizationService;
        this.explainService = explainService;
        this.indexingService = indexingService;
        this.ragQaService = ragQaService;
    }

    @PostMapping("/summarize")
    public ResponseEntity<SummaryResponse> summarize(@Valid @RequestBody EmailRequest email) {
        String summary = summarizationService.summarize(email);
        return new ResponseEntity<>(new SummaryResponse(summary), HttpStatus.OK);
    }

    @PostMapping("/explain")
    public ResponseEntity<ExplainResponse> explain(@Valid @RequestBody ExplainRequest request) {
        String explanation = explainService.explain(request.text());
        return new ResponseEntity<>(new ExplainResponse(explanation), HttpStatus.OK);
    }

    @PostMapping("/index")
    public ResponseEntity<String> indexEmail(@Valid @RequestBody EmailRequest email) {
        indexingService.indexEmail(email.sender(), email.subject(), email.content());
        return new ResponseEntity<>("Email indexed successfully", HttpStatus.OK);
    }

    @PostMapping("/ask")
    public ResponseEntity<RagQaResponse> ask(@Valid @RequestBody RagQaRequest request) {
        String answer = ragQaService.ask(request.question());
        return new ResponseEntity<>(new RagQaResponse(answer), HttpStatus.OK);
    }
}
