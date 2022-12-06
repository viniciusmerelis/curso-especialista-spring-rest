package com.algaworks.algafood.api.v1.assembler.disassembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.model.input.UsuarioInputDTO;
import com.algaworks.algafood.domain.model.Usuario;

@Component
public class UsuarioInputDisassemblerDTO {

	@Autowired
    private ModelMapper modelMapper;
    
    public Usuario toDomainObject(UsuarioInputDTO usuarioInputDTO) {
        return modelMapper.map(usuarioInputDTO, Usuario.class);
    }
    
    public void copyToDomainObject(UsuarioInputDTO usuarioInput, Usuario usuario) {
        modelMapper.map(usuarioInput, usuario);
    } 
	
}
