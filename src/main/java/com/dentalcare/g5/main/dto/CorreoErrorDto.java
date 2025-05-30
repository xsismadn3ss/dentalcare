package com.dentalcare.g5.main.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.core.io.ByteArrayResource;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CorreoErrorDto {
    private String to;
    private String subject;
    private String body;
    private String attachmentName;
    private ByteArrayResource attachmentContent;
}
