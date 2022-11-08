package com.algaworks.algafood.api;

import com.algaworks.algafood.api.controller.CidadeController;
import com.algaworks.algafood.api.controller.CozinhaController;
import com.algaworks.algafood.api.controller.EstadoController;
import com.algaworks.algafood.api.controller.FormaPagamentoController;
import com.algaworks.algafood.api.controller.PedidoController;
import com.algaworks.algafood.api.controller.RestauranteController;
import com.algaworks.algafood.api.controller.RestauranteProdutoController;
import com.algaworks.algafood.api.controller.RestauranteUsuarioResponsavelController;
import com.algaworks.algafood.api.controller.UsuarioController;
import com.algaworks.algafood.api.controller.UsuarioGrupoController;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.TemplateVariable;
import org.springframework.hateoas.TemplateVariables;
import org.springframework.hateoas.UriTemplate;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class LinkFactory {

    public static final TemplateVariables PAGINACAO_VARIABLES = new TemplateVariables(
            new TemplateVariable("page", TemplateVariable.VariableType.REQUEST_PARAM),
            new TemplateVariable("size", TemplateVariable.VariableType.REQUEST_PARAM),
            new TemplateVariable("sort", TemplateVariable.VariableType.REQUEST_PARAM));

    public static Link linkToPedido() {
        TemplateVariables filtroVariables = new TemplateVariables(
                new TemplateVariable("clienteId", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("restauranteId", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("dataCriacaoInicio", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("dataCriacaoFim", TemplateVariable.VariableType.REQUEST_PARAM));
        String pedidosUrl = linkTo(PedidoController.class).toUri().toString();
        return Link.of(UriTemplate.of(pedidosUrl, PAGINACAO_VARIABLES.concat(filtroVariables)), "pedidos");
    }

    public static Link linkToRestaurante(Long id, String rel) {
        return linkTo(methodOn(RestauranteController.class).buscar(id)).withRel(rel);
    }

    public static Link linkToRestaurante(Long id) {
        return linkToRestaurante(id, IanaLinkRelations.SELF.value());
    }

    public static Link linkToUsuario(Long id, String rel) {
        return linkTo(methodOn(UsuarioController.class).buscar(id)).withRel(rel);
    }

    public static Link linkToUsuario(Long id) {
        return linkToUsuario(id, IanaLinkRelations.SELF.value());
    }

    public static Link linkToUsuarios(String rel) {
        return linkTo(UsuarioController.class).withRel(rel);
    }

    public static Link linkToUsuarios() {
        return linkToUsuarios(IanaLinkRelations.SELF.value());
    }

    public static Link linkToGruposUsuario(Long id, String rel) {
        return linkTo(methodOn(UsuarioGrupoController.class).listar(id)).withRel(rel);
    }

    public static Link linkToGruposUsuario(Long id) {
        return linkToGruposUsuario(id, IanaLinkRelations.SELF.value());
    }

    public static Link linkToResponsaveisRestaurante(Long id, String rel) {
        return linkTo(methodOn(RestauranteUsuarioResponsavelController.class).listar(id)).withRel(rel);
    }

    public static Link linkToResponsaveisRestaurante(Long id) {
        return linkToResponsaveisRestaurante(id, IanaLinkRelations.SELF.value());
    }

    public static Link linkToFormaPagamento(Long id, String rel) {
        return linkTo(methodOn(FormaPagamentoController.class).buscar(id)).withRel(rel);
    }

    public static Link linkToFormaPagamento(Long id) {
        return linkToFormaPagamento(id, IanaLinkRelations.SELF.value());
    }

    public static Link linkToCidade(Long id, String rel) {
        return linkTo(methodOn(CidadeController.class).buscar(id)).withRel(rel);
    }

    public static Link linkToCidade(Long id) {
        return linkToCidade(id, IanaLinkRelations.SELF.value());
    }

    public static Link linkToCidades(String rel) {
        return linkTo(CidadeController.class).withRel(rel);
    }

    public static Link linkToCidades() {
        return linkToCidades(IanaLinkRelations.SELF.value());
    }

    public static Link linkToEstado(Long id, String rel) {
        return linkTo(methodOn(EstadoController.class).buscar(id)).withRel(rel);
    }

    public static Link linkToEstado(Long id) {
        return linkToEstado(id, IanaLinkRelations.SELF.value());
    }

    public static Link linkToEstados(String rel) {
        return linkTo(EstadoController.class).withRel(rel);
    }

    public static Link linkToEstados() {
        return linkToEstados(IanaLinkRelations.SELF.value());
    }

    public static Link linkToProduto(Long restauranteId, Long produtoId, String rel) {
        return linkTo(methodOn(RestauranteProdutoController.class).buscar(restauranteId, produtoId)).withRel(rel);
    }

    public static Link linkToProduto(Long restauranteId, Long produtoId) {
        return linkToProduto(restauranteId, produtoId, IanaLinkRelations.SELF.value());
    }

    public static Link linkToCozinhas(String rel) {
        return linkTo(CozinhaController.class).withRel(rel);
    }

    public static Link linkToCozinhas() {
        return linkToCozinhas(IanaLinkRelations.SELF.value());
    }

}
