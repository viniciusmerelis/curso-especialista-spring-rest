package com.algaworks.algafood.api.controller;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.FormaPagamentoDtoAssembler;
import com.algaworks.algafood.api.assembler.disassembler.FormaPagamentoDtoInputDisassembler;
import com.algaworks.algafood.api.model.FormaPagamentoDto;
import com.algaworks.algafood.api.model.input.FormaPagamentoDtoInput;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;
import com.algaworks.algafood.domain.service.FormaPagamentoService;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

@RestController
@RequestMapping("/formas-pagamento")
public class FormaPagamentoController {

	@Autowired
	private FormaPagamentoRepository formaPagamentoRepository;
	
	@Autowired
	private FormaPagamentoService formaPagamentoService;
	
	@Autowired
	private FormaPagamentoDtoAssembler formaPagamentoDtoAssembler;
	
	@Autowired
	private FormaPagamentoDtoInputDisassembler formaPagamentoDtoDisassembler;
	
	@GetMapping
	public ResponseEntity<List<FormaPagamentoDto>> listar(ServletWebRequest request) {
		ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());
		String eTag = "0";
		OffsetDateTime dataUltimaAtualizacao = formaPagamentoRepository.obterUltimaDataAtualizacao();
		if (dataUltimaAtualizacao != null) {
			eTag = String.valueOf(dataUltimaAtualizacao.toEpochSecond());
		}
		if (request.checkNotModified(eTag)) {
			return null;
		}
		List<FormaPagamento> todasFormasPagamentos = formaPagamentoRepository.findAll();
		List<FormaPagamentoDto> formaPagamentoDto = formaPagamentoDtoAssembler.toCollectionDto(todasFormasPagamentos);
		return ResponseEntity.ok()
				.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePrivate())
				.eTag(eTag)
				.body(formaPagamentoDto);
	}
	
	@GetMapping("/{formaPagamentoId}")
	public ResponseEntity<FormaPagamentoDto> buscar(@PathVariable Long formaPagamentoId) {
		FormaPagamento formaPagamento = formaPagamentoService.buscarOuFalhar(formaPagamentoId);
		FormaPagamentoDto formaPagamentoDto =  formaPagamentoDtoAssembler.toDto(formaPagamento);
		return ResponseEntity.ok()
				.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
				.body(formaPagamentoDto);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public FormaPagamentoDto adicionar(@RequestBody FormaPagamentoDtoInput formaPagamentoDtoInput) {
		FormaPagamento formaPagamento = formaPagamentoDtoDisassembler.toDomainObject(formaPagamentoDtoInput);
		formaPagamento = formaPagamentoService.salvar(formaPagamento);
		return formaPagamentoDtoAssembler.toDto(formaPagamento);
	}
	
	@PutMapping("/{formaPagamentoId}")
	public FormaPagamentoDto atualizar(@PathVariable Long formaPagamentoId,
			@RequestBody FormaPagamentoDtoInput formaPagamentoDtoInput) {
		FormaPagamento formaPagamentoAtual = formaPagamentoService.buscarOuFalhar(formaPagamentoId);
		formaPagamentoDtoDisassembler.copyToDomainObject(formaPagamentoDtoInput, formaPagamentoAtual);
		formaPagamentoAtual = formaPagamentoService.salvar(formaPagamentoAtual);
		return formaPagamentoDtoAssembler.toDto(formaPagamentoAtual);
	}
	
	@DeleteMapping("/{formaPagamentoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long formaPagamentoId) {
		formaPagamentoService.excluir(formaPagamentoId);
	}
	
}
