package com.sinchana.RetrievAI.dto;

import jakarta.validation.constraints.NotBlank;

public record ExplainRequest(@NotBlank(message = "Selected text cannot be empty") String text) {
}
