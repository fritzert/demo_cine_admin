package com.frodas.cine.admin.presentation.advice;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SuccesResponse<T> implements Serializable {
    @Builder.Default
//    private String message = "Process executed successfully";
    private String message = "Proceso ejecutado satisfactoriamente";
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();
    @Builder.Default
    private Integer statusCode = 200;
    private T data;

    public SuccesResponse(T body) {
        data = body;
    }

    public SuccesResponse(Integer operationCode, String message) {
        this.statusCode = operationCode;
        this.message = message;
    }
}
