package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import com.algaworks.algafood.api.openapi.controller.UsuarioControllerOpenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
@RequestMapping(path = "/usuarios", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuarioController implements UsuarioControllerOpenApi {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private UsuarioAssemblerDTO usuarioAssemblerDTO;
	
	@Autowired
	private UsuarioInputDisassemblerDTO usuarioInputDisassemblerDTO;
	
	@Override
	@GetMapping
	public List<UsuarioDTO> listar() {
		List<Usuario> todosUsuarios = usuarioRepository.findAll();
		return usuarioAssemblerDTO.toCollectionDto(todosUsuarios);
	}
	
	@Override
	@GetMapping("/{usuarioId}")
	public UsuarioDTO buscar(@PathVariable Long usuarioId) {
		Usuario usuario = usuarioService.buscarOuFalhar(usuarioId);
		return usuarioAssemblerDTO.toDto(usuario);
	}
	
	@Override
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UsuarioDTO adicionar(@RequestBody UsuarioComSenhaInputDTO usuarioInputDTO) {
		Usuario usuario = usuarioInputDisassemblerDTO.toDomainObject(usuarioInputDTO);
		usuario = usuarioService.salvar(usuario);
		return usuarioAssemblerDTO.toDto(usuario);
	}
	
	@Override
	@PutMapping("/{usuarioId}")
	public UsuarioDTO atualizar(@PathVariable Long usuarioId, @RequestBody @Valid UsuarioInputDTO usuarioInputDTO) {
		Usuario usuarioAtual = usuarioService.buscarOuFalhar(usuarioId);
		usuarioInputDisassemblerDTO.copyToDomainObject(usuarioInputDTO, usuarioAtual);
		usuarioAtual = usuarioService.salvar(usuarioAtual);
		return usuarioAssemblerDTO.toDto(usuarioAtual);
	}
	
	@Override
	@PutMapping("/{usuarioId}/senha")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void alterarSenha(@PathVariable Long usuarioId, @RequestBody @Valid SenhaInputDTO senha) {
		usuarioService.alterarSenha(usuarioId, senha.getSenhaAtual(), senha.getNovaSenha());
	}
	
}
