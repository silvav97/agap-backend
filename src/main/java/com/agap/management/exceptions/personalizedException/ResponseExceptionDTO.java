package com.agap.management.exceptions.personalizedException;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseExceptionDTO {

    private String message;
    private String description;
    private int errorCode;
}
