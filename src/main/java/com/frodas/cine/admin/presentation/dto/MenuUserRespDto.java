package com.frodas.cine.admin.presentation.dto;

import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MenuUserRespDto implements Serializable {

    private String label;

    private List<MenuUserItemDto> items = new ArrayList<>();

}
