package com.test.rohlik.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JsonResponseDTO {
    private Object response;
    private String error;
}
