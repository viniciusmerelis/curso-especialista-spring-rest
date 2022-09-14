package com.algaworks.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.PedidoResumoDTO;
import com.algaworks.algafood.domain.model.Pedido;

@Component
public class PedidoResumoAssemblerDTO {

	@Autowired
	private ModelMapper modelMapper;
	
	public PedidoResumoDTO toDto(Pedido pedido) {
		return modelMapper.map(pedido, PedidoResumoDTO.class);
	}
	
	public List<PedidoResumoDTO> toCollectionDto(List<Pedido> pedidos) {
		return pedidos.stream()
				.map(pedido -> toDto(pedido))
				.collect(Collectors.toList());
	}
}
