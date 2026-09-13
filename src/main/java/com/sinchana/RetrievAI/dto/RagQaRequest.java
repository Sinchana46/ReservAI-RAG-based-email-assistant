package com.sinchana.RetrievAI.dto;

import jakarta.validation.constraints.NotBlank;

public record RagQaRequest(@NotBlank(message = "Question cannot be empty") String question) { }
