package com.frodas.cine.admin.presentation.dto;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MenuUserItemDto implements Serializable {

    private String label;

    private String icon;

    private String routerLink;

    private List<MenuUserItemDto> items;

    public MenuUserItemDto(String label, String icon, String routerLink) {
        this.label = label;
        this.icon = icon;
        this.routerLink = routerLink;
    }

    public MenuUserItemDto(String label, String icon, List<MenuUserItemDto> items) {
        this.label = label;
        this.icon = icon;
        this.items = items;
    }

}
