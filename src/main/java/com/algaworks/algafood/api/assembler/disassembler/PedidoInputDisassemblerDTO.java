package com.algaworks.algafood.api.assembler.disassembler;

import com.algaworks.algafood.api.model.input.PedidoInputDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.domain.model.Pedido;

@Component
public class PedidoInputDisassemblerDTO {

	@Autowired
	private ModelMapper modelMapper;
	
	public Pedido toDomainObject(PedidoInputDTO pedidoInputDTO) {
		return modelMapper.map(pedidoInputDTO, Pedido.class);
	}
	
	public void copyToDomainObject(PedidoInputDTO pedidoInputDTO, Pedido pedido) {
		modelMapper.map(pedidoInputDTO, pedido);
	}
}
