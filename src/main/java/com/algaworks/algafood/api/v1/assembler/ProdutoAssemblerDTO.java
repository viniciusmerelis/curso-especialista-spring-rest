package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.LinkFactory;
import com.algaworks.algafood.api.v1.controller.RestauranteProdutoController;
import com.algaworks.algafood.api.v1.model.ProdutoDTO;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.Produto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class ProdutoAssemblerDTO extends RepresentationModelAssemblerSupport<Produto, ProdutoDTO> {

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private AlgaSecurity algaSecurity;
	
	public ProdutoAssemblerDTO() {
		super(RestauranteProdutoController.class, ProdutoDTO.class);
	}

	@Override
	public ProdutoDTO toModel(Produto produto) {
		ProdutoDTO produtoDTO = createModelWithId(produto.getId(), produto, produto.getRestaurante().getId());
		mapper.map(produto, produtoDTO);
		if (algaSecurity.podeConsultarRestaurantes()) {
			produtoDTO.add(LinkFactory.linkToProdutos(produto.getRestaurante().getId(), "produtos"));
			produtoDTO.add(LinkFactory.linkToProdutoFoto(produto.getRestaurante().getId(), produto.getId(), "foto"));
		}
		return produtoDTO;
	}
}
