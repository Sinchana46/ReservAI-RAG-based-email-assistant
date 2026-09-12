package com.sinchana.RetrievAI.dto;

import jakarta.validation.constraints.NotBlank;

public record IndexEmailRequest(
    @NotBlank(message = "Sender cannot be empty")
    String sender,
    @NotBlank(message = "Subject cannot be empty")
    String subject,
    @NotBlank(message = "Email content cannot be empty")
    String content
) {
}
