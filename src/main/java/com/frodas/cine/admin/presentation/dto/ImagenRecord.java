package com.frodas.cine.admin.presentation.dto;

import jakarta.validation.constraints.NotNull;

public record ImagenRecord(
        @NotNull String imagenName,
        @NotNull String imagenUrl,
        String imagenId) {

    public static String DEFAULT_MESSAGE = "Hola";

    public void printName() {
        System.out.printf("Name: " + imagenName);
    }

    public static void printMessage() {
        System.out.printf(DEFAULT_MESSAGE);
    }
}
