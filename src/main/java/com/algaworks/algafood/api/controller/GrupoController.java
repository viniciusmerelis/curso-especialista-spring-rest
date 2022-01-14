package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.GrupoDtoAssembler;
import com.algaworks.algafood.api.disassembler.GrupoDtoInputDisassembler;
import com.algaworks.algafood.api.model.GrupoDto;
import com.algaworks.algafood.api.model.input.GrupoDtoInput;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.repository.GrupoRepository;
import com.algaworks.algafood.domain.service.GrupoService;

@RestController
@RequestMapping("/grupos")
public class GrupoController {

	@Autowired
	private GrupoRepository grupoRepository;
	
	@Autowired
	private GrupoService grupoService;
	
	@Autowired
	private GrupoDtoAssembler grupoDtoAssembler;
	
	@Autowired
	private GrupoDtoInputDisassembler grupoDtoDisassembler;
	
	@GetMapping
	public List<GrupoDto> listar() {
		List<Grupo> todosGrupos = grupoRepository.findAll();
		return grupoDtoAssembler.toCollectionDto(todosGrupos);
	}
	
	@GetMapping("/{grupoId}")
	public GrupoDto buscar(@PathVariable Long grupoId) {
		Grupo grupo = grupoService.buscarOuFalhar(grupoId);
		return grupoDtoAssembler.toDto(grupo);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public GrupoDto adicionar(@RequestBody GrupoDtoInput grupoDtoInput) {
		Grupo grupo = grupoDtoDisassembler.toDomainObject(grupoDtoInput);
		grupo = grupoService.salvar(grupo);
		return grupoDtoAssembler.toDto(grupo);
	}
	
	@PutMapping("/{grupoId}")
	public GrupoDto atualizar(@PathVariable Long grupoId, @RequestBody @Valid GrupoDtoInput grupoDtoInput ) {
		Grupo grupoAtual = grupoService.buscarOuFalhar(grupoId);
		grupoDtoDisassembler.copyToDomainObject(grupoDtoInput, grupoAtual);
		grupoAtual = grupoService.salvar(grupoAtual);
		return grupoDtoAssembler.toDto(grupoAtual);
	}
	
	@DeleteMapping("/{grupoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluir(@PathVariable Long grupoId) {
		grupoService.excluir(grupoId);
	}
	
}
