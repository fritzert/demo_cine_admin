package com.frodas.cine.admin.presentation.advice;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HttpErrorInfo implements Serializable {

    private String message;
    private String excepcionMessage;
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();
    private List<String> errors;
    private HttpStatus httpStatus;
    private Integer statusCode;
    private String path;

    public HttpErrorInfo(HttpStatus httpStatus, String path, String excepcionMessage, String message) {
        timestamp = LocalDateTime.now();
        this.httpStatus = httpStatus;
        this.statusCode = httpStatus.value();
        this.path = path;
        this.excepcionMessage = excepcionMessage;
        this.message = message;
    }

}
