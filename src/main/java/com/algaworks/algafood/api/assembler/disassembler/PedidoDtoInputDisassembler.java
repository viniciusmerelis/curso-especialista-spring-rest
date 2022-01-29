package com.algaworks.algafood.api.assembler.disassembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.input.PedidoDtoInput;
import com.algaworks.algafood.domain.model.Pedido;

@Component
public class PedidoDtoInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public Pedido toDomainObject(PedidoDtoInput pedidoDtoInput) {
		return modelMapper.map(pedidoDtoInput, Pedido.class);
	}
	
	public void copyToDomainObject(PedidoDtoInput pedidoDtoInput, Pedido pedido) {
		modelMapper.map(pedidoDtoInput, pedido);
	}
}
