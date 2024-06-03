package com.frodas.cine.admin.presentation.advice;

import org.springframework.http.ResponseEntity;

public class SuccesResponseHandler<T> {
    public static <T> ResponseEntity<SuccesResponse<T>> SUCCESS() {
        SuccesResponse<T> result = SuccesResponse.<T>builder()
                .build();
        return ResponseEntity.status(result.getStatusCode()).body(result);
    }

    public static <T> ResponseEntity<SuccesResponse<T>> SUCCESS(T data) {
        SuccesResponse<T> result = SuccesResponse.<T>builder()
                .data(data)
                .build();
        return ResponseEntity.status(result.getStatusCode()).body(result);
    }

    public static <T> ResponseEntity<SuccesResponse<T>> SUCCESS(Integer operationCode, T data) {
        SuccesResponse<T> result = SuccesResponse.<T>builder()
                .statusCode(operationCode)
                .data(data)
                .build();
        return ResponseEntity.status(result.getStatusCode()).body(result);
    }

    public static <T> ResponseEntity<SuccesResponse<T>> SUCCESS(Integer operationCode, String message) {
        SuccesResponse<T> result = SuccesResponse.<T>builder()
                .statusCode(operationCode)
                .message(message)
                .build();
        return ResponseEntity.status(result.getStatusCode()).body(result);
    }
}
