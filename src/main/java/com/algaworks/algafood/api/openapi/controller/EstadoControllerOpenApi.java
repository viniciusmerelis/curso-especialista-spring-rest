package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.EstadoDTO;
import com.algaworks.algafood.api.model.input.EstadoInputDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Estados")
public interface EstadoControllerOpenApi {
    @ApiOperation("Lista os estados")
    List<EstadoDTO> listar();

    @ApiOperation("Busca um estado por ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID do estado inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Estado não encontrado", response = Problem.class)
    })
    EstadoDTO buscar(@ApiParam(value = "ID de um estado", example = "1", required = true) Long estadoId);

    @ApiOperation("Cadastra um estado")
    @ApiResponses(@ApiResponse(code = 201, message = "Estado cadastrado"))
    EstadoDTO adicionar(@ApiParam(name = "corpo", value = "Representação de um novo estado", required = true) EstadoInputDTO estadoInputDTO);

    @ApiOperation("Atualiza um estado por ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Estado atualizado"),
            @ApiResponse(code = 404, message = "Estado não encontrado", response = Problem.class)
    })
    EstadoDTO atualizar(
            @ApiParam(value = "ID de um estado", example = "1", required = true) Long estadoId,
            @ApiParam(name = "corpo", value = "Representação de um estado com os novos dados", required = true) EstadoInputDTO estadoInputDTO);

    @ApiOperation("Exclui um estado por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Estado excluído"),
            @ApiResponse(code = 404, message = "Estado não encontrado", response = Problem.class)
    })
    void remover(@ApiParam(value = "ID de um estado", example = "1", required = true) Long estadoId);
}
