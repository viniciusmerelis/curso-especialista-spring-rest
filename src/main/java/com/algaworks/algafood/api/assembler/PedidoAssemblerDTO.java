package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.controller.CidadeController;
import com.algaworks.algafood.api.controller.FormaPagamentoController;
import com.algaworks.algafood.api.controller.PedidoController;
import com.algaworks.algafood.api.controller.RestauranteController;
import com.algaworks.algafood.api.controller.RestauranteProdutoController;
import com.algaworks.algafood.api.controller.UsuarioController;
import com.algaworks.algafood.api.model.PedidoDTO;
import com.algaworks.algafood.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PedidoAssemblerDTO extends RepresentationModelAssemblerSupport<Pedido, PedidoDTO> {

    @Autowired
    private ModelMapper mapper;

    public PedidoAssemblerDTO() {
        super(PedidoController.class, PedidoDTO.class);
    }

    @Override
    public PedidoDTO toModel(Pedido pedido) {
        PedidoDTO pedidoDTO = createModelWithId(pedido.getId(), pedido);
        mapper.map(pedido, pedidoDTO);
        pedidoDTO.add(linkTo(PedidoController.class).withRel("pedidos"));
        pedidoDTO.getRestaurante().add(linkTo(
                methodOn(RestauranteController.class).buscar(pedido.getRestaurante().getId())).withSelfRel());
        pedidoDTO.getCliente().add(linkTo(
                methodOn(UsuarioController.class).buscar(pedido.getCliente().getId())).withSelfRel());
        pedidoDTO.getFormaPagamento().add(linkTo(
                methodOn(FormaPagamentoController.class).buscar(pedido.getFormaPagamento().getId())).withSelfRel());
        pedidoDTO.getEnderecoEntrega().getCidade().add(linkTo(
                methodOn(CidadeController.class).buscar(pedido.getEnderecoEntrega().getCidade().getId())).withSelfRel());
        pedidoDTO.getItens().forEach(item -> {
            item.add(linkTo(methodOn(RestauranteProdutoController.class)
                    .buscar(pedidoDTO.getRestaurante().getId(), item.getProdutoId())).withRel("produto"));
        });
        return pedidoDTO;
    }
}
