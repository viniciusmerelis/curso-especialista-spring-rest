package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.LinkFactory;
import com.algaworks.algafood.api.v1.controller.PedidoController;
import com.algaworks.algafood.api.v1.model.PedidoResumoDTO;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class PedidoResumoAssemblerDTO extends RepresentationModelAssemblerSupport<Pedido, PedidoResumoDTO> {

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private AlgaSecurity algaSecurity;
	
	public PedidoResumoAssemblerDTO() {
		super(PedidoController.class, PedidoResumoDTO.class);
	}

	@Override
	public PedidoResumoDTO toModel(Pedido pedido) {
		PedidoResumoDTO pedidoResumoDTO = createModelWithId(pedido.getId(), pedido);
		mapper.map(pedido, pedidoResumoDTO);
		if (algaSecurity.podePesquisarPedidos()) {
			pedidoResumoDTO.add(LinkFactory.linkToPedidos("pedidos"));
		}
		if (algaSecurity.podeConsultarRestaurantes()) {
			pedidoResumoDTO.getRestaurante().add(LinkFactory.linkToRestaurante(pedidoResumoDTO.getRestaurante().getId()));
		}
		if (algaSecurity.podeConsultarUsuariosGruposPermissoes()) {
			pedidoResumoDTO.getCliente().add(LinkFactory.linkToUsuario(pedidoResumoDTO.getCliente().getId()));
		}
		return pedidoResumoDTO;
	}
}
