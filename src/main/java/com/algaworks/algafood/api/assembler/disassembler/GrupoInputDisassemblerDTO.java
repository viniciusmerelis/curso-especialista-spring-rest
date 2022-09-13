package com.algaworks.algafood.api.assembler.disassembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.input.GrupoDtoInput;
import com.algaworks.algafood.domain.model.Grupo;

@Component
public class GrupoInputDisassemblerDTO {

	@Autowired
    private ModelMapper modelMapper;
    
    public Grupo toDomainObject(GrupoDtoInput grupoInput) {
        return modelMapper.map(grupoInput, Grupo.class);
    }
    
    public void copyToDomainObject(GrupoDtoInput grupoInput, Grupo grupo) {
        modelMapper.map(grupoInput, grupo);
    } 
	
}
