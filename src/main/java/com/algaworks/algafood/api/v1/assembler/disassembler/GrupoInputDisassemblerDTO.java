package com.algaworks.algafood.api.v1.assembler.disassembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.model.input.GrupoInputDTO;
import com.algaworks.algafood.domain.model.Grupo;

@Component
public class GrupoInputDisassemblerDTO {

	@Autowired
    private ModelMapper modelMapper;
    
    public Grupo toDomainObject(GrupoInputDTO grupoInput) {
        return modelMapper.map(grupoInput, Grupo.class);
    }
    
    public void copyToDomainObject(GrupoInputDTO grupoInput, Grupo grupo) {
        modelMapper.map(grupoInput, grupo);
    } 
	
}
