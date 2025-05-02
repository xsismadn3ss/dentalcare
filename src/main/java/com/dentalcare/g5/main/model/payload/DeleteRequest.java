package com.dentalcare.g5.main.model.payload;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Generic DTO for delete operations.
 * Used across all controllers that perform entity deletion.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteRequest {
    @NotNull(message = "El ID es obligatorio")
    private Integer id;
}

