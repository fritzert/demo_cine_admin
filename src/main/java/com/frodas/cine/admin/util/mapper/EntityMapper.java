package com.frodas.cine.admin.util.mapper;

import com.frodas.cine.admin.persistence.entity.*;
import com.frodas.cine.admin.presentation.dto.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EntityMapper {

    @Mappings({})
    ConfigDto entityToApi(Config entity);

    @Mappings({
            @Mapping(target = "idConfig", ignore = true)
    })
    Config apiToEntity(ConfigDto api);

    List<ConfigDto> entityListToApiList(List<Config> entity);


    @Mappings({})
    ComidaDto entityToApi(Comida entity);

    @Mappings({
            @Mapping(target = "idComida", ignore = true)
    })
    Comida apiToEntity(ComidaDto api);

    List<ComidaDto> entityListToApiListComida(List<Comida> entity);


    @Mappings({})
    GeneroDto entityToApi(Genero entity);

    @Mappings({
            @Mapping(target = "idGenero", ignore = true)
    })
    Genero apiToEntity(GeneroDto api);

    List<GeneroDto> entityListToApiListGenero(List<Genero> entity);


    @Mappings({})
    MenuDto entityToApi(Menu entity);

    @Mappings({
            @Mapping(target = "idMenu", ignore = true)
    })
    Menu apiToEntity(MenuDto api);

    List<MenuDto> entityListToApiListMenu(List<Menu> entity);


    @Mappings({})
    PeliculaResDto entityToApi(Pelicula entity);

    @Mappings({
            @Mapping(target = "idPelicula", ignore = true)
    })
    Pelicula apiToEntity(PeliculaReqDto api);

    List<PeliculaResDto> entityListToApiListPelicula(List<Pelicula> entity);


    @Mappings({})
    ClienteResDto entityToApi(Cliente entity);

    @Mappings({
            @Mapping(target = "idCliente", ignore = true)
    })
    Cliente apiToEntity(ClienteReqDto api);

    List<ClienteResDto> entityListToApiListCliente(List<Cliente> entity);


    @Mappings({})
    RolDto entityToApi(Rol entity);

    @Mappings({
            @Mapping(target = "idRol", ignore = true)
    })
    Rol apiToEntity(RolDto api);

    List<RolDto> entityListToApiListRol(List<Rol> entity);
}
