package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.CidadeDtoAssembler;
import com.algaworks.algafood.api.disassembler.CidadeDtoInputDisassembler;
import com.algaworks.algafood.api.model.CidadeDto;
import com.algaworks.algafood.api.model.input.CidadeDtoInput;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.service.CidadeService;

@RestController
@RequestMapping(value = "/cidades")
public class CidadeController {
	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private CidadeService cidadeService;
	
	@Autowired
	private CidadeDtoAssembler cidadeDtoAssembler;
	
	@Autowired
	private CidadeDtoInputDisassembler cidadeDtoDisassembler;

	@GetMapping
	public List<CidadeDto> listar() {
		List<Cidade> todasCidades = cidadeRepository.findAll();
		return cidadeDtoAssembler.toCollectionDto(todasCidades);
	}

	@GetMapping("/{cidadeId}")
	public CidadeDto buscar(@PathVariable Long cidadeId) {
		Cidade cidade = cidadeService.buscarOuFalhar(cidadeId);
		return cidadeDtoAssembler.toDto(cidade);
	}

	@PostMapping
	public CidadeDto adicionar(@RequestBody @Valid CidadeDtoInput cidadeDtoInput) {
		try {
			Cidade cidade = cidadeDtoDisassembler.toDomainObject(cidadeDtoInput);
			cidade = cidadeService.salvar(cidade);
			return cidadeDtoAssembler.toDto(cidade);
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@PutMapping("/{cidadeId}")
	public CidadeDto atualizar(@PathVariable Long cidadeId, @RequestBody @Valid CidadeDtoInput cidadeDtoInput) {
		try {
			Cidade cidadeAtual = cidadeService.buscarOuFalhar(cidadeId);
			cidadeDtoDisassembler.copyToDomainObject(cidadeDtoInput, cidadeAtual);
			cidadeAtual = cidadeService.salvar(cidadeAtual);
			return cidadeDtoAssembler.toDto(cidadeAtual);
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@DeleteMapping("/{cidadeId}")
	public void remover(@PathVariable Long cidadeId) {
		cidadeService.excluir(cidadeId);
	}
	
}
