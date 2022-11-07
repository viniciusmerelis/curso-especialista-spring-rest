package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.model.PedidoDTO;
import com.algaworks.algafood.api.model.PedidoResumoDTO;
import com.algaworks.algafood.api.model.input.PedidoInputDTO;
import com.algaworks.algafood.domain.filter.PedidoFilter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

@Api(tags = "Pedidos")
public interface PedidoControllerOpenApi {
    @ApiOperation("Pesquisa os pedidos")
    @ApiImplicitParams(@ApiImplicitParam(
            value = "Nomes das propriedades para filtrar na resposta, separados por vírgula",
            name = "campos", paramType = "query", type = "string"))
    PagedModel<PedidoResumoDTO> pesquisar(PedidoFilter filtro, Pageable pageable);

    @ApiOperation("Busca um pedido por código")
    @ApiImplicitParams(@ApiImplicitParam(
            value = "Nomes das propriedades para filtrar na resposta, separados por vírgula",
            name = "campos", paramType = "query", type = "string"))
    PedidoDTO buscar(@ApiParam(value = "Código de um pedido", example = "f9981ca4-5a5e-4da3-af04-933861df3e55", required = true) String codigoPedido);

    @ApiOperation("Registra um pedido")
    @ApiResponses(@ApiResponse(code = 201, message = "Pedido registrado"))
    PedidoDTO adicionar(@ApiParam(name = "corpo", value = "Representação de um novo pedido", required = true) PedidoInputDTO pedidoInputDTO);
}
