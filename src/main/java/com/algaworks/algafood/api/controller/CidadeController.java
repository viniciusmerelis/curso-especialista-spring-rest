package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import com.algaworks.algafood.api.model.input.CidadeInputDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.CidadeAssemblerDTO;
import com.algaworks.algafood.api.assembler.disassembler.CidadeInputDisassemblerDTO;
import com.algaworks.algafood.api.model.CidadeDTO;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.service.CidadeService;

@Api(tags = "Cidades")
@RestController
@RequestMapping(value = "/cidades")
public class CidadeController {
	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private CidadeService cidadeService;
	
	@Autowired
	private CidadeAssemblerDTO cidadeAssemblerDTO;
	
	@Autowired
	private CidadeInputDisassemblerDTO cidadeDisassemblerDTO;

	@ApiOperation("Lista todas as cidades")
	@GetMapping
	public List<CidadeDTO> listar() {
		List<Cidade> todasCidades = cidadeRepository.findAll();
		return cidadeAssemblerDTO.toCollectionDto(todasCidades);
	}

	@ApiOperation("Busca uma cidade por ID")
	@GetMapping("/{cidadeId}")
	public CidadeDTO buscar(@ApiParam(value = "ID de uma cidade", example = "1") @PathVariable Long cidadeId) {
		Cidade cidade = cidadeService.buscarOuFalhar(cidadeId);
		return cidadeAssemblerDTO.toDto(cidade);
	}

	@ApiOperation("Cadastra uma cidade")
	@PostMapping
	public CidadeDTO adicionar(
			@ApiParam(name = "corpo", value = "Representação de uma nova cidade")
			@RequestBody @Valid CidadeInputDTO cidadeInputDTO) {
		try {
			Cidade cidade = cidadeDisassemblerDTO.toDomainObject(cidadeInputDTO);
			cidade = cidadeService.salvar(cidade);
			return cidadeAssemblerDTO.toDto(cidade);
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@ApiOperation("Atualiza uma cidade por ID")
	@PutMapping("/{cidadeId}")
	public CidadeDTO atualizar(
			@ApiParam(value = "ID de uma cidade", example = "1") @PathVariable Long cidadeId,
			@ApiParam(name = "corpo", value = "Representação de uma cidade como novos dados")
			@RequestBody @Valid CidadeInputDTO cidadeInputDTO) {
		try {
			Cidade cidadeAtual = cidadeService.buscarOuFalhar(cidadeId);
			cidadeDisassemblerDTO.copyToDomainObject(cidadeInputDTO, cidadeAtual);
			cidadeAtual = cidadeService.salvar(cidadeAtual);
			return cidadeAssemblerDTO.toDto(cidadeAtual);
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@ApiOperation("Exclui uma cidade por ID")
	@DeleteMapping("/{cidadeId}")
	public void remover(@ApiParam(value = "ID de uma cidade", example = "1") @PathVariable Long cidadeId) {
		cidadeService.excluir(cidadeId);
	}
}
