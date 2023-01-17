package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.LinkFactory;
import com.algaworks.algafood.api.v1.assembler.ProdutoAssemblerDTO;
import com.algaworks.algafood.api.v1.assembler.disassembler.ProdutoInputDisassemblerDTO;
import com.algaworks.algafood.api.v1.model.ProdutoDTO;
import com.algaworks.algafood.api.v1.model.input.ProdutoInputDTO;
import com.algaworks.algafood.api.v1.openapi.controller.RestauranteProdutoControllerOpenApi;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import com.algaworks.algafood.domain.service.ProdutoService;
import com.algaworks.algafood.domain.service.RestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/v1/restaurantes/{restauranteId}/produtos", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteProdutoController implements RestauranteProdutoControllerOpenApi {

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private ProdutoService produtoService;

	@Autowired
	private RestauranteService restauranteService;

	@Autowired
	private ProdutoAssemblerDTO produtoAssemblerDTO;

	@Autowired
	private ProdutoInputDisassemblerDTO produtoInputDisassemblerDTO;

	@Override
	@CheckSecurity.Restaurantes.PodeConsultar
	@GetMapping
	public CollectionModel<ProdutoDTO> listar(@PathVariable Long restauranteId, @RequestParam(required = false, defaultValue = "false") Boolean incluirInativos) {
		Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);
		List<Produto> todosProdutos;
		if (incluirInativos) {
			todosProdutos = produtoRepository.findTodosByRestaurante(restaurante);
		} else {
			todosProdutos = produtoRepository.findAtivosByRestaurante(restaurante);
		} 
		return produtoAssemblerDTO.toCollectionModel(todosProdutos).add(LinkFactory.linkToProdutos(restauranteId));
	}

	@Override
	@CheckSecurity.Restaurantes.PodeConsultar
	@GetMapping("/{produtoId}")
	public ProdutoDTO buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
		Produto produto = produtoService.buscarOuFalhar(restauranteId, produtoId);
		return produtoAssemblerDTO.toModel(produto);
	}

	@Override
	@CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ProdutoDTO adicionar(@PathVariable Long restauranteId, @RequestBody @Valid ProdutoInputDTO produtoInputDTO) {
		Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);
		Produto produto = produtoInputDisassemblerDTO.toDomainObject(produtoInputDTO);
		produto.setRestaurante(restaurante);
		produto = produtoService.salvar(produto);
		return produtoAssemblerDTO.toModel(produto);
	}

	@Override
	@CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
	@PutMapping("/{produtoId}")
	public ProdutoDTO atualizar(@PathVariable Long restauranteId, @PathVariable Long produtoId,
								@RequestBody @Valid ProdutoInputDTO produtoInputDTO) {
		Produto produtoAtual = produtoService.buscarOuFalhar(restauranteId, produtoId);
		produtoInputDisassemblerDTO.copyToDomainObject(produtoInputDTO, produtoAtual);
		produtoAtual = produtoService.salvar(produtoAtual);
		return produtoAssemblerDTO.toModel(produtoAtual);
	}

}
