package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.controller.PedidoController;
import com.algaworks.algafood.api.controller.RestauranteController;
import com.algaworks.algafood.api.controller.UsuarioController;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.PedidoResumoDTO;
import com.algaworks.algafood.domain.model.Pedido;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

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
		pedidoResumoDTO.add(linkTo(PedidoController.class).withRel("pedidos"));
		pedidoResumoDTO.getRestaurante().add(linkTo(
				methodOn(RestauranteController.class).buscar(pedido.getRestaurante().getId())).withSelfRel());
		pedidoResumoDTO.getCliente().add(linkTo(
				methodOn(UsuarioController.class).buscar(pedido.getCliente().getId())).withSelfRel());
		return pedidoResumoDTO;
	}
}
