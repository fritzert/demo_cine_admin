package com.frodas.cine.admin.presentation.controller;

import com.frodas.cine.admin.presentation.advice.SuccesResponse;
import com.frodas.cine.admin.presentation.advice.SuccesResponseHandler;
import com.frodas.cine.admin.presentation.doc.ClienteDoc;
import com.frodas.cine.admin.presentation.dto.*;
import com.frodas.cine.admin.service.exception.BusinessException;
import com.frodas.cine.admin.service.http.cloudinary.CloudinaryServiceImpl;
import com.frodas.cine.admin.service.interfaces.ClienteService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.cloudinary.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import static com.frodas.cine.admin.util.constants.ConstantsMessage.MSG_NAME_FAKE_FILE;
import static com.frodas.cine.admin.util.constants.ConstantsPath.PATH_CLIENTES;

@RestController
@RequestMapping(PATH_CLIENTES)
@RequiredArgsConstructor
public class ClienteController implements ClienteDoc {

    private final ClienteService clienteService;
    private final CloudinaryServiceImpl cloudinaryService;

    @Override
    @GetMapping
    public ResponseEntity<SuccesResponse<List<ClienteResDto>>> listAll() {
        List<ClienteResDto> list = clienteService.listar();
        return SuccesResponseHandler.SUCCESS(list);
    }

    @Override
    @PostMapping(value = "/buscar")
    public ResponseEntity<SuccesResponse<Page<ClienteResDto>>> search(@RequestBody FiltroNombreDto dto, Pageable pageable) {
        Page<ClienteResDto> list;
        if (dto.getNombre().isBlank())
            list = clienteService.listarPageable(pageable);
        else
            list = clienteService.listarPageableNombre(dto.getNombre(), pageable);
        return SuccesResponseHandler.SUCCESS(list);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<SuccesResponse<ClienteResDto>> findById(@PathVariable("id") UUID id) {
        ClienteResDto dto = clienteService.buscarPorId(id);
        return SuccesResponseHandler.SUCCESS(dto);
    }

    @GetMapping("/attr/{dni}")
    public ResponseEntity<SuccesResponse<ClienteResDto>> findByDni(@PathVariable("dni") String dni) {
        ClienteResDto dto = clienteService.buscarPorDni(dni);
        return SuccesResponseHandler.SUCCESS(dto);
    }

    @Override
    @PostMapping
    public ResponseEntity<SuccesResponse<ClienteResDto>> create(@Valid @RequestPart("cliente") ClienteReqDto dto, @RequestPart("file") MultipartFile file) {
        if (file == null) {
            throw new BusinessException("Archivo obligatorio");
        }
        ImagenDto imagenDto = getImageService(file);
        imagenDto.setImagenName(file.getOriginalFilename());
        ClienteResDto newDto = clienteService.registrar(dto, imagenDto);
        return SuccesResponseHandler.SUCCESS(201, newDto);
    }

    @Override
    @PutMapping
    public ResponseEntity<SuccesResponse<ClienteResDto>> update(@Valid @RequestPart("cliente") ClienteReqDto dto, @RequestPart("file") MultipartFile file) {
        ClienteResDto newDto = clienteService.buscarPorId(dto.getIdCliente());
        if (Objects.equals(file.getOriginalFilename(), MSG_NAME_FAKE_FILE)) {
            newDto = clienteService.actualizar(dto, null);
        } else {
            cloudinaryService.delete(newDto.getUsuario().getImagenId());
            ImagenDto imagenDto = getImageService(file);
            newDto = clienteService.actualizar(dto, imagenDto);
        }
        return SuccesResponseHandler.SUCCESS(newDto);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<SuccesResponse<Void>> updateEstado(@PathVariable("id") UUID id) {
        clienteService.updateEliminar(id);
        return SuccesResponseHandler.SUCCESS();
    }

    @SneakyThrows
    private ImagenDto getImageService(MultipartFile file) {
//        File fileSend = Files.createTempFile("", file.getOriginalFilename()).toFile();
//        file.transferTo(fileSend);
        File fileSend = convert(file);
        Map result = cloudinaryService.upload(fileSend, "clientes");
        JSONObject json = new JSONObject(result);
        return new ImagenDto(json.getString("original_filename"), json.getString("url"),
                json.getString("public_id"));
    }

    private File convert(MultipartFile multipartFile) throws IOException {
        File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        FileOutputStream fo = new FileOutputStream(file);
        fo.write(multipartFile.getBytes());
        fo.close();
        return file;
    }

    @Override
    @GetMapping("/usuariorol/{idCliente}")
    public ResponseEntity<SuccesResponse<List<UsuarioRolDto>>> listUsuarioRolByMenu(@PathVariable("idCliente") UUID idCliente) {
        List<UsuarioRolDto> list = clienteService.listUsuarioRolByCliente(idCliente);
        return SuccesResponseHandler.SUCCESS(list);
    }

    @Override
    @PutMapping("/usuariorol/{idCliente}")
    public ResponseEntity<SuccesResponse<Void>> createUsuarioRol(@PathVariable("idCliente") UUID idCliente,
                                                                 @RequestBody
                                                                 @NotEmpty(message = "La lista de elementos no puede estar vacía.")
                                                                 List<@Valid UsuarioRolDto> dto) {
        clienteService.procesarUsuarioRol(idCliente, dto);
        return SuccesResponseHandler.SUCCESS();
    }

}