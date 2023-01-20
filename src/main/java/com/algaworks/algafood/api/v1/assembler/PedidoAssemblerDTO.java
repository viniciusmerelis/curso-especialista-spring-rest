package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.LinkFactory;
import com.algaworks.algafood.api.v1.controller.PedidoController;
import com.algaworks.algafood.api.v1.model.PedidoDTO;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class PedidoAssemblerDTO extends RepresentationModelAssemblerSupport<Pedido, PedidoDTO> {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private AlgaSecurity algaSecurity;

    public PedidoAssemblerDTO() {
        super(PedidoController.class, PedidoDTO.class);
    }

    @Override
    public PedidoDTO toModel(Pedido pedido) {
        PedidoDTO pedidoDTO = createModelWithId(pedido.getCodigo(), pedido);
        mapper.map(pedido, pedidoDTO);
        if (algaSecurity.podePesquisarPedidos()) {
            pedidoDTO.add(LinkFactory.linkToPedidos("pedidos"));
        }
        pedido.getStatus().statusDisponiveisParaAlteracao()
                .forEach(status -> {
                    if (algaSecurity.podeGerenciarPedidos(pedido.getCodigo())) {
                        pedidoDTO.add(LinkFactory.linkToStatusPedido(status, pedidoDTO.getCodigo()));
                    }
                });
        if (algaSecurity.podeConsultarRestaurantes()) {
            pedidoDTO.getRestaurante().add(LinkFactory.linkToRestaurante(pedidoDTO.getRestaurante().getId()));
        }
        if (algaSecurity.podeConsultarUsuariosGruposPermissoes()) {
            pedidoDTO.getCliente().add(LinkFactory.linkToUsuario(pedidoDTO.getCliente().getId()));
        }
        if (algaSecurity.podeConsultarFormasPagamento()) {
            pedidoDTO.getFormaPagamento().add(LinkFactory.linkToFormaPagamento(pedidoDTO.getFormaPagamento().getId()));
        }
        if (algaSecurity.podeConsultarCidades()) {
            pedidoDTO.getEnderecoEntrega().getCidade().add(LinkFactory.linkToCidade(pedidoDTO.getEnderecoEntrega().getCidade().getId()));
        }
        if (algaSecurity.podeConsultarRestaurantes()) {
            pedidoDTO.getItens().forEach(item -> item.add(LinkFactory.linkToProduto(pedidoDTO.getRestaurante().getId(), item.getProdutoId(), "produto")));
        }
        return pedidoDTO;
    }
}
