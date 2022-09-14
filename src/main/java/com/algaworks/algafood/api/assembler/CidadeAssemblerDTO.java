package com.algaworks.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.CidadeDTO;
import com.algaworks.algafood.domain.model.Cidade;

@Component
public class CidadeAssemblerDTO {

	@Autowired
    private ModelMapper modelMapper;
    
    public CidadeDTO toDto(Cidade cidade) {
        return modelMapper.map(cidade, CidadeDTO.class);
    }
    
    public List<CidadeDTO> toCollectionDto(List<Cidade> cidades) {
        return cidades.stream()
                .map(cidade -> toDto(cidade))
                .collect(Collectors.toList());
    }
	
}
