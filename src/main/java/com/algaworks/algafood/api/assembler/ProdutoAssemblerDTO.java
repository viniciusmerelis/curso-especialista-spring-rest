package com.algaworks.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.ProdutoDTO;
import com.algaworks.algafood.domain.model.Produto;

@Component
public class ProdutoAssemblerDTO {

	@Autowired
	private ModelMapper modelMapper;
	
	public ProdutoDTO toDto(Produto produto) {
		return modelMapper.map(produto, ProdutoDTO.class);
	}
	
	public List<ProdutoDTO> toCollectionDto(List<Produto> produtos) {
		return produtos.stream()
				.map(produto -> toDto(produto))
				.collect(Collectors.toList());
	}
}