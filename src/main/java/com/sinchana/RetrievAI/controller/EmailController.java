package com.sinchana.RetrievAI.controller;

import com.sinchana.RetrievAI.dto.EmailRequest;
import com.sinchana.RetrievAI.dto.ExplainRequest;
import com.sinchana.RetrievAI.dto.ExplainResponse;
import com.sinchana.RetrievAI.dto.SummaryResponse;
import com.sinchana.RetrievAI.service.ExplainService;
import com.sinchana.RetrievAI.service.IndexingService;
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

    public EmailController(SummarizationService summarizationService, ExplainService explainService, IndexingService indexingService) {
        this.summarizationService=summarizationService;
        this.explainService = explainService;
        this.indexingService = indexingService;
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
}
