package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.LinkFactory;
import com.algaworks.algafood.api.controller.PedidoController;
import com.algaworks.algafood.api.model.PedidoDTO;
import com.algaworks.algafood.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class PedidoAssemblerDTO extends RepresentationModelAssemblerSupport<Pedido, PedidoDTO> {

    @Autowired
    private ModelMapper mapper;

    public PedidoAssemblerDTO() {
        super(PedidoController.class, PedidoDTO.class);
    }

    @Override
    public PedidoDTO toModel(Pedido pedido) {
        PedidoDTO pedidoDTO = createModelWithId(pedido.getCodigo(), pedido);
        mapper.map(pedido, pedidoDTO);
        pedidoDTO.add(LinkFactory.linkToPedidos("pedidos"));
        pedido.getStatus().statusDisponiveisParaAlteracao().forEach(status -> pedidoDTO.add(LinkFactory.linkToStatusPedido(status, pedidoDTO.getCodigo())));
        pedidoDTO.getRestaurante().add(LinkFactory.linkToRestaurante(pedidoDTO.getRestaurante().getId()));
        pedidoDTO.getCliente().add(LinkFactory.linkToUsuario(pedidoDTO.getCliente().getId()));
        pedidoDTO.getFormaPagamento().add(LinkFactory.linkToFormaPagamento(pedidoDTO.getFormaPagamento().getId()));
        pedidoDTO.getEnderecoEntrega().getCidade().add(LinkFactory.linkToCidade(pedidoDTO.getEnderecoEntrega().getCidade().getId()));
        pedidoDTO.getItens().forEach(item -> item.add(LinkFactory.linkToProduto(pedidoDTO.getRestaurante().getId(), item.getProdutoId(), "produto")));
        return pedidoDTO;
    }
}
