package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.LinkFactory;
import com.algaworks.algafood.api.controller.PedidoController;
import com.algaworks.algafood.api.model.PedidoResumoDTO;
import com.algaworks.algafood.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class PedidoResumoAssemblerDTO extends RepresentationModelAssemblerSupport<Pedido, PedidoResumoDTO> {

	@Autowired
	private ModelMapper mapper;
	
	public PedidoResumoAssemblerDTO() {
		super(PedidoController.class, PedidoResumoDTO.class);
	}

	@Override
	public PedidoResumoDTO toModel(Pedido pedido) {
		PedidoResumoDTO pedidoResumoDTO = createModelWithId(pedido.getId(), pedido);
		mapper.map(pedido, pedidoResumoDTO);
		pedidoResumoDTO.add(LinkFactory.linkToPedidos("pedidos"));
		pedidoResumoDTO.getRestaurante().add(LinkFactory.linkToRestaurante(pedidoResumoDTO.getRestaurante().getId()));
		pedidoResumoDTO.getCliente().add(LinkFactory.linkToUsuario(pedidoResumoDTO.getCliente().getId()));
		return pedidoResumoDTO;
	}
}
