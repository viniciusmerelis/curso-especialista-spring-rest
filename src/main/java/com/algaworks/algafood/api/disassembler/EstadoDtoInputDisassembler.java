package com.algaworks.algafood.api.disassembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.input.EstadoDtoInput;
import com.algaworks.algafood.domain.model.Estado;

@Component
public class EstadoDtoInputDisassembler {

	@Autowired
    private ModelMapper modelMapper;
    
    public Estado toDomainObject(EstadoDtoInput estadoInput) {
        return modelMapper.map(estadoInput, Estado.class);
    }
    
    public void copyToDomainObject(EstadoDtoInput estadoInput, Estado estado) {
        modelMapper.map(estadoInput, estado);
    }   
	
}
