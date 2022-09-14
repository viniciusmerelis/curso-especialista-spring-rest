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

import com.algaworks.algafood.api.assembler.EstadoAssemblerDTO;
import com.algaworks.algafood.api.assembler.disassembler.EstadoInputDisassemblerDTO;
import com.algaworks.algafood.api.model.EstadoDTO;
import com.algaworks.algafood.api.model.input.EstadoInputDTO;
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
	private EstadoAssemblerDTO estadoAssemblerDTO;
	
	private EstadoInputDisassemblerDTO estadoDisassemblerDTO;

	@GetMapping
	public List<EstadoDTO> listar() {
		List<Estado> todosEstados = estadoRepository.findAll();
		return estadoAssemblerDTO.toCollectionDto(todosEstados);
	}

	@GetMapping("/{estadoId}")
	public EstadoDTO buscar(@PathVariable Long estadoId) {
		Estado estado = estadoService.buscarOuFalhar(estadoId);
		return estadoAssemblerDTO.toDto(estado);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EstadoDTO adicionar(@RequestBody @Valid EstadoInputDTO estadoInputDTO) {
		Estado estado = estadoDisassemblerDTO.toDomainObject(estadoInputDTO);
		estado = estadoService.salvar(estado);
		return estadoAssemblerDTO.toDto(estado);
	}

	@PutMapping("/{estadoId}")
	public EstadoDTO atualizar(@PathVariable Long estadoId, @RequestBody @Valid EstadoInputDTO estadoInputDTO) {
		Estado estadoAtual = estadoService.buscarOuFalhar(estadoId);
		estadoDisassemblerDTO.copyToDomainObject(estadoInputDTO, estadoAtual);
		estadoAtual = estadoService.salvar(estadoAtual);
		return estadoAssemblerDTO.toDto(estadoAtual);
	}

	@DeleteMapping("/{estadoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long estadoId) {
		estadoService.excluir(estadoId);
	}
}
