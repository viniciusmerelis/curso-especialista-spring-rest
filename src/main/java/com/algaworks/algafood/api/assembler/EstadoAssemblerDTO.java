package com.algaworks.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.EstadoDto;
import com.algaworks.algafood.domain.model.Estado;

@Component
public class EstadoAssemblerDTO {

	private ModelMapper modelMapper;
	
	public EstadoDto toDto(Estado estado) {
		return modelMapper.map(estado, EstadoDto.class);
	}
	
	public List<EstadoDto> toCollectionDto(List<Estado> estados) {
		return estados.stream()
				.map(estado -> toDto(estado))
				.collect(Collectors.toList());
	}
	
}
