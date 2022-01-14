package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.EstadoDtoAssembler;
import com.algaworks.algafood.api.disassembler.EstadoDtoInputDisassembler;
import com.algaworks.algafood.api.model.EstadoDto;
import com.algaworks.algafood.api.model.input.EstadoDtoInput;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import com.algaworks.algafood.domain.service.EstadoService;

@RestController
@RequestMapping(value = "/estados", produces = MediaType.APPLICATION_JSON_VALUE)
public class EstadoController {

	@Autowired
	private EstadoRepository estadoRepository;

	@Autowired
	private EstadoService estadoService;
	
	@Autowired
	private EstadoDtoAssembler estadoDtoAssembler;
	
	private EstadoDtoInputDisassembler estadoDtoDisassembler;

	@GetMapping
	public List<EstadoDto> listar() {
		List<Estado> todosEstados = estadoRepository.findAll();
		return estadoDtoAssembler.toCollectionDto(todosEstados);
	}

	@GetMapping("/{estadoId}")
	public EstadoDto buscar(@PathVariable Long estadoId) {
		Estado estado = estadoService.buscarOuFalhar(estadoId);
		return estadoDtoAssembler.toDto(estado);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EstadoDto adicionar(@RequestBody @Valid EstadoDtoInput estadoDtoInput) {
		Estado estado = estadoDtoDisassembler.toDomainObject(estadoDtoInput);
		estado = estadoService.salvar(estado);
		return estadoDtoAssembler.toDto(estado);
	}

	@PutMapping("/{estadoId}")
	public EstadoDto atualizar(@PathVariable Long estadoId, @RequestBody @Valid EstadoDtoInput estadoDtoInput) {
		Estado estadoAtual = estadoService.buscarOuFalhar(estadoId);
		estadoDtoDisassembler.copyToDomainObject(estadoDtoInput, estadoAtual);
		estadoAtual = estadoService.salvar(estadoAtual);
		return estadoDtoAssembler.toDto(estadoAtual);
	}

	@DeleteMapping("/{estadoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long estadoId) {
		estadoService.excluir(estadoId);
	}
}
