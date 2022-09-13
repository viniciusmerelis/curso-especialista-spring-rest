package com.algaworks.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.RestauranteDto;
import com.algaworks.algafood.domain.model.Restaurante;

@Component
public class RestauranteAssemblerDTO {
	
	@Autowired
	private ModelMapper modelMapper;

	public RestauranteDto toDto(Restaurante restaurante) {
		return modelMapper.map(restaurante, RestauranteDto.class);
	}
	
	public List<RestauranteDto> toCollectionDto(List<Restaurante> restaurantes) {
		return restaurantes.stream()
				.map(restaurante -> toDto(restaurante))
				.collect(Collectors.toList());
	}
	
}
