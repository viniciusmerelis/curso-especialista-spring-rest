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

import com.algaworks.algafood.api.assembler.UsuarioDtoAssembler;
import com.algaworks.algafood.api.disassembler.UsuarioDtoInputDisassembler;
import com.algaworks.algafood.api.model.UsuarioDto;
import com.algaworks.algafood.api.model.input.SenhaDtoInput;
import com.algaworks.algafood.api.model.input.UsuarioComSenhaDtoInput;
import com.algaworks.algafood.api.model.input.UsuarioDtoInput;
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
	private UsuarioDtoAssembler usuarioDtoAssembler;
	
	@Autowired
	private UsuarioDtoInputDisassembler usuarioDtoInputDisassembler;
	
	@GetMapping
	public List<UsuarioDto> listar() {
		List<Usuario> todosUsuarios = usuarioRepository.findAll();
		return usuarioDtoAssembler.toCollectionDto(todosUsuarios);
	}
	
	@GetMapping("/{usuarioId}")
	public UsuarioDto buscar(@PathVariable Long usuarioId) {
		Usuario usuario = usuarioService.buscarOuFalhar(usuarioId);
		return usuarioDtoAssembler.toDto(usuario);
	}
	
	@PostMapping	
	@ResponseStatus(HttpStatus.CREATED)
	public UsuarioDto adicionar(@RequestBody UsuarioComSenhaDtoInput usuarioDtoInput) {
		Usuario usuario = usuarioDtoInputDisassembler.toDomainObject(usuarioDtoInput);
		usuario = usuarioService.salvar(usuario);
		return usuarioDtoAssembler.toDto(usuario);
	}
	
	@PutMapping("/{usuarioId}")
	public UsuarioDto atualizar(@PathVariable Long usuarioId, @RequestBody @Valid UsuarioDtoInput usuarioDtoinput) {
		Usuario usuarioAtual = usuarioService.buscarOuFalhar(usuarioId);
		usuarioDtoInputDisassembler.copyToDomainObject(usuarioDtoinput, usuarioAtual);
		usuarioAtual = usuarioService.salvar(usuarioAtual);
		return usuarioDtoAssembler.toDto(usuarioAtual);
	}
	
	@PutMapping("/{usuarioId}/senha")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void alterarSenha(@PathVariable Long usuarioId, @RequestBody @Valid SenhaDtoInput senha) {
		usuarioService.alterarSenha(usuarioId, senha.getSenhaAtual(), senha.getNovaSenha());
	}
	
}
