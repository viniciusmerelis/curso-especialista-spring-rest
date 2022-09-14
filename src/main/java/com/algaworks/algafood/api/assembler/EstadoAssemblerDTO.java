package com.algaworks.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.EstadoDTO;
import com.algaworks.algafood.domain.model.Estado;

@Component
public class EstadoAssemblerDTO {

	private ModelMapper modelMapper;
	
	public EstadoDTO toDto(Estado estado) {
		return modelMapper.map(estado, EstadoDTO.class);
	}
	
	public List<EstadoDTO> toCollectionDto(List<Estado> estados) {
		return estados.stream()
				.map(estado -> toDto(estado))
				.collect(Collectors.toList());
	}
	
}
