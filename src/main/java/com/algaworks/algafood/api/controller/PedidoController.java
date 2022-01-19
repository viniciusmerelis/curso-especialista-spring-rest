package com.algaworks.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.PedidoDtoAssembler;
import com.algaworks.algafood.api.model.PedidoDto;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import com.algaworks.algafood.domain.service.EmissaoPedidoService;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private EmissaoPedidoService emissaoService;
	
	@Autowired
	private PedidoDtoAssembler pedidoDtoAssembler;
	
	@GetMapping
	public List<PedidoDto> listar() {
		List<Pedido> todosPedidos = pedidoRepository.findAll();
		return pedidoDtoAssembler.toCollectionDto(todosPedidos);
	}
	
	@GetMapping("/{pedidoId}")
	public PedidoDto buscar(@PathVariable Long pedidoId) {
		Pedido pedido = emissaoService.buscarOuFalhar(pedidoId);
		return pedidoDtoAssembler.toDto(pedido);
	}
}
