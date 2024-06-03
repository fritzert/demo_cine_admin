package com.frodas.cine.admin.util.enums;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public enum GroupMenuType {

    Negocio("b2c"),

    Administracion("admin"),

    Seguridad("sec"),

    Reportes("report");

    private final Object objeto;

    GroupMenuType(Object objeto) {
        this.objeto = objeto;
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

    public static final List<GroupMenu> gruposMenu = new ArrayList<>();

    static {
        gruposMenu.add(new GroupMenu(GroupMenuType.Negocio.name(), GroupMenuType.Negocio.getString()));
        gruposMenu.add(new GroupMenu(GroupMenuType.Administracion.name(), GroupMenuType.Administracion.getString()));
        gruposMenu.add(new GroupMenu(GroupMenuType.Seguridad.name(), GroupMenuType.Seguridad.getString()));
        gruposMenu.add(new GroupMenu(GroupMenuType.Reportes.name(), GroupMenuType.Reportes.getString()));
    }

}
