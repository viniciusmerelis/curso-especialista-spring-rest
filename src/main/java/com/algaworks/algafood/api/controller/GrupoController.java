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

import com.algaworks.algafood.api.assembler.GrupoAssemblerDTO;
import com.algaworks.algafood.api.assembler.disassembler.GrupoInputDisassemblerDTO;
import com.algaworks.algafood.api.model.GrupoDTO;
import com.algaworks.algafood.api.model.input.GrupoInputDTO;
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
	private GrupoAssemblerDTO grupoAssemblerDTO;
	
	@Autowired
	private GrupoInputDisassemblerDTO grupoDisassemblerDTO;
	
	@GetMapping
	public List<GrupoDTO> listar() {
		List<Grupo> todosGrupos = grupoRepository.findAll();
		return grupoAssemblerDTO.toCollectionDto(todosGrupos);
	}
	
	@GetMapping("/{grupoId}")
	public GrupoDTO buscar(@PathVariable Long grupoId) {
		Grupo grupo = grupoService.buscarOuFalhar(grupoId);
		return grupoAssemblerDTO.toDto(grupo);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public GrupoDTO adicionar(@RequestBody GrupoInputDTO grupoInputDTO) {
		Grupo grupo = grupoDisassemblerDTO.toDomainObject(grupoInputDTO);
		grupo = grupoService.salvar(grupo);
		return grupoAssemblerDTO.toDto(grupo);
	}
	
	@PutMapping("/{grupoId}")
	public GrupoDTO atualizar(@PathVariable Long grupoId, @RequestBody @Valid GrupoInputDTO grupoInputDTO) {
		Grupo grupoAtual = grupoService.buscarOuFalhar(grupoId);
		grupoDisassemblerDTO.copyToDomainObject(grupoInputDTO, grupoAtual);
		grupoAtual = grupoService.salvar(grupoAtual);
		return grupoAssemblerDTO.toDto(grupoAtual);
	}
	
	@DeleteMapping("/{grupoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluir(@PathVariable Long grupoId) {
		grupoService.excluir(grupoId);
	}
	
}
