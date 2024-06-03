package com.frodas.cine.admin.config.middleware.loghandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoggerHandler {
    private String nombreMetodo;
    private long tiempoInicio;

    public LoggerHandler(String nombreMetodo) {
        this.nombreMetodo = nombreMetodo;
    }

    public void startMethod() {
        this.tiempoInicio = System.currentTimeMillis();
        log.info("---------------------------------------------");
        log.info("[INICIO MÉTODO " + nombreMetodo + "] - Service");
    }

    public void infoMethod(String infoMessage) {
        this.tiempoInicio = System.currentTimeMillis();
        log.info("---------------------------------------------");
        log.info("[INFO MÉTODO " + nombreMetodo + "] - Service");
        log.info(infoMessage);
        log.info("---------------------------------------------");
    }

    public void errorMethod(String errorMessage) {
        this.tiempoInicio = System.currentTimeMillis();
        log.error("---------------------------------------------");
        log.error("[ERROR MÉTODO " + nombreMetodo + "] - Service");
        log.error(errorMessage);
        log.error("---------------------------------------------");
    }

    public void endMethod() {
        long tiempoEjecucion = System.currentTimeMillis() - tiempoInicio;
        log.info("[FIN MÉTODO - " + nombreMetodo + "] - Service");
        log.info("Tiempo de ejecución: " + tiempoEjecucion + "ms");
        log.info("---------------------------------------------");
    }
}
