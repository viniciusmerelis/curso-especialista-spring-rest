package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.model.PermissaoDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.hateoas.CollectionModel;

@Api(tags = "permissoes")
public interface PermissaoControllerOpenApi {

    @ApiOperation("Lista as permissões")
    CollectionModel<PermissaoDTO> listar();
}
