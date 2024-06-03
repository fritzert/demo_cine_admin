package com.frodas.cine.admin.util.enums;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum StatusType {

    STATUS_CODE_OK(200),
    MESSAGE_STATUS_OK("OK"),

    STATUS_CODE_NO_CONTENT(204),
    MESSAGE_NO_CONTENT("Petición exitosa, pero sin resultados"),

    STATUS_CODE_ERROR(400),
    MESSAGE_ERROR("Ha ocurrido un error al procesar la información"),
    MESSAGE_ERROR_BUSINESS("Ha ocurrido un error, a nivel de negocio"),
    MESSAGE_ERROR_DATABASE("Ha ocurrido un error, a nivel de base de datos"),
    MESSAGE_ERROR_EXCEPTION_BAD_REQUEST("Datos ingresados incorrectamente"),
    MESSAGE_REQUEST_INVALID_MESSAGE("Solicitud incorrecta"),

    STATUS_CODE_NOT_FOUND(404),
    MESSAGE_NOT_FOUND("Ha ocurrido un error, dato no encontrado"),

    STATUS_CODE_CONFLICT(409),
    MESSAGE_ERROR_CONFLICT("Ha ocurrido un error, dato existente"),

    STATUS_CODE_NO_SERVICE(503),
    MESSAGE_SERVICIO_NO_DISPONIBLE("Estimado cliente el servicio no se encuentra disponible");

    private final Object objeto;

    StatusType(Object objeto) {
        this.objeto = objeto;
    }

    public Integer getNumber() {
        try {
            if (this.objeto instanceof String) {
                return Integer.parseInt((String) this.objeto);
            } else if (this.objeto instanceof Long) {
                return ((Long) this.objeto).intValue();
            } else {
                return (Integer) this.objeto;
            }
        } catch (Exception e) {
            log.error("No se podido convertir el objeto en alguna variable primitiva. : {}", e.getMessage());
            return 0;
        }
    }

    public String getString() {
        try {
            if (this.objeto instanceof Integer) {
                return String.valueOf(this.objeto);
            } else if (this.objeto instanceof Long) {
                return ((Long) this.objeto).toString();
            } else {
                return (String) this.objeto;
            }
        } catch (Exception e) {
            log.error("No se podido convertir el objeto en alguna variable primitiva. : {}", e.getMessage());
            return "";
        }
    }

}
