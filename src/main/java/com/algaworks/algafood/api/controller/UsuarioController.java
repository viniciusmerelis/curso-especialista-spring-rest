package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.UsuarioAssemblerDTO;
import com.algaworks.algafood.api.assembler.disassembler.UsuarioInputDisassemblerDTO;
import com.algaworks.algafood.api.model.UsuarioDTO;
import com.algaworks.algafood.api.model.input.SenhaInputDTO;
import com.algaworks.algafood.api.model.input.UsuarioComSenhaInputDTO;
import com.algaworks.algafood.api.model.input.UsuarioInputDTO;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.UsuarioRepository;
import com.algaworks.algafood.domain.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private UsuarioAssemblerDTO usuarioAssemblerDTO;
	
	@Autowired
	private UsuarioInputDisassemblerDTO usuarioInputDisassemblerDTO;
	
	@GetMapping
	public List<UsuarioDTO> listar() {
		List<Usuario> todosUsuarios = usuarioRepository.findAll();
		return usuarioAssemblerDTO.toCollectionDto(todosUsuarios);
	}
	
	@GetMapping("/{usuarioId}")
	public UsuarioDTO buscar(@PathVariable Long usuarioId) {
		Usuario usuario = usuarioService.buscarOuFalhar(usuarioId);
		return usuarioAssemblerDTO.toDto(usuario);
	}
	
	@PostMapping	
	@ResponseStatus(HttpStatus.CREATED)
	public UsuarioDTO adicionar(@RequestBody UsuarioComSenhaInputDTO usuarioDtoInput) {
		Usuario usuario = usuarioInputDisassemblerDTO.toDomainObject(usuarioDtoInput);
		usuario = usuarioService.salvar(usuario);
		return usuarioAssemblerDTO.toDto(usuario);
	}
	
	@PutMapping("/{usuarioId}")
	public UsuarioDTO atualizar(@PathVariable Long usuarioId, @RequestBody @Valid UsuarioInputDTO usuarioDtoinputDTO) {
		Usuario usuarioAtual = usuarioService.buscarOuFalhar(usuarioId);
		usuarioInputDisassemblerDTO.copyToDomainObject(usuarioDtoinputDTO, usuarioAtual);
		usuarioAtual = usuarioService.salvar(usuarioAtual);
		return usuarioAssemblerDTO.toDto(usuarioAtual);
	}
	
	@PutMapping("/{usuarioId}/senha")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void alterarSenha(@PathVariable Long usuarioId, @RequestBody @Valid SenhaInputDTO senha) {
		usuarioService.alterarSenha(usuarioId, senha.getSenhaAtual(), senha.getNovaSenha());
	}
	
}
