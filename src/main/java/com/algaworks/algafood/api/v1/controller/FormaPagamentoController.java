package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.assembler.FormaPagamentoAssemblerDTO;
import com.algaworks.algafood.api.v1.assembler.disassembler.FormaPagamentoInputDisassemblerDTO;
import com.algaworks.algafood.api.v1.model.FormaPagamentoDTO;
import com.algaworks.algafood.api.v1.model.input.FormaPagamentoInputDTO;
import com.algaworks.algafood.api.v1.openapi.controller.FormaPagamentoControllerOpenApi;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;
import com.algaworks.algafood.domain.service.FormaPagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(path = "/formas-pagamento", produces = MediaType.APPLICATION_JSON_VALUE)
public class FormaPagamentoController implements FormaPagamentoControllerOpenApi {

	@Autowired
	private FormaPagamentoRepository formaPagamentoRepository;
	
	@Autowired
	private FormaPagamentoService formaPagamentoService;
	
	@Autowired
	private FormaPagamentoAssemblerDTO formaPagamentoAssemblerDTO;
	
	@Autowired
	private FormaPagamentoInputDisassemblerDTO formaPagamentoDisassemblerDTO;
	
	@Override
	@GetMapping
	public ResponseEntity<CollectionModel<FormaPagamentoDTO>> listar(ServletWebRequest request) {
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
		CollectionModel<FormaPagamentoDTO> formaPagamentoDto = formaPagamentoAssemblerDTO.toCollectionModel(todasFormasPagamentos);
		return ResponseEntity.ok()
				.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePrivate())
				.eTag(eTag)
				.body(formaPagamentoDto);
	}
	
	@Override
	@GetMapping("/{formaPagamentoId}")
	public ResponseEntity<FormaPagamentoDTO> buscar(@PathVariable Long formaPagamentoId) {
		FormaPagamento formaPagamento = formaPagamentoService.buscarOuFalhar(formaPagamentoId);
		FormaPagamentoDTO formaPagamentoDto =  formaPagamentoAssemblerDTO.toModel(formaPagamento);
		return ResponseEntity.ok()
				.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
				.body(formaPagamentoDto);
	}
	
	@Override
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public FormaPagamentoDTO adicionar(@RequestBody FormaPagamentoInputDTO formaPagamentoInputDTO) {
		FormaPagamento formaPagamento = formaPagamentoDisassemblerDTO.toDomainObject(formaPagamentoInputDTO);
		formaPagamento = formaPagamentoService.salvar(formaPagamento);
		return formaPagamentoAssemblerDTO.toModel(formaPagamento);
	}
	
	@Override
	@PutMapping("/{formaPagamentoId}")
	public FormaPagamentoDTO atualizar(@PathVariable Long formaPagamentoId,
									   @RequestBody FormaPagamentoInputDTO formaPagamentoInputDTO) {
		FormaPagamento formaPagamentoAtual = formaPagamentoService.buscarOuFalhar(formaPagamentoId);
		formaPagamentoDisassemblerDTO.copyToDomainObject(formaPagamentoInputDTO, formaPagamentoAtual);
		formaPagamentoAtual = formaPagamentoService.salvar(formaPagamentoAtual);
		return formaPagamentoAssemblerDTO.toModel(formaPagamentoAtual);
	}
	
	@Override
	@DeleteMapping("/{formaPagamentoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long formaPagamentoId) {
		formaPagamentoService.excluir(formaPagamentoId);
	}
	
}
