package com.algaworks.algafood.api.assembler.disassembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.input.EstadoInputDTO;
import com.algaworks.algafood.domain.model.Estado;

@Component
public class EstadoInputDisassemblerDTO {

	@Autowired
    private ModelMapper modelMapper;
    
    public Estado toDomainObject(EstadoInputDTO estadoInput) {
        return modelMapper.map(estadoInput, Estado.class);
    }
    
    public void copyToDomainObject(EstadoInputDTO estadoInput, Estado estado) {
        modelMapper.map(estadoInput, estado);
    }   
	
}
