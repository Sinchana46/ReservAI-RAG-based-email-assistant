package com.sinchana.RetrievAI.dto;

import jakarta.validation.constraints.NotBlank;

public record EmailRequest(
    String sender,
    String subject,
    @NotBlank(message = "Email content cannot be blank")
    String content
) {
}
