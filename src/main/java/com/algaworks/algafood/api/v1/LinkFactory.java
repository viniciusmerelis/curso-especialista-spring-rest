package com.algaworks.algafood.api.v1;

import com.algaworks.algafood.api.v1.controller.CidadeController;
import com.algaworks.algafood.api.v1.controller.CozinhaController;
import com.algaworks.algafood.api.v1.controller.EstadoController;
import com.algaworks.algafood.api.v1.controller.EstatisticaController;
import com.algaworks.algafood.api.v1.controller.FluxoPedidoController;
import com.algaworks.algafood.api.v1.controller.FormaPagamentoController;
import com.algaworks.algafood.api.v1.controller.GrupoController;
import com.algaworks.algafood.api.v1.controller.GrupoPermissaoController;
import com.algaworks.algafood.api.v1.controller.PedidoController;
import com.algaworks.algafood.api.v1.controller.PermissaoController;
import com.algaworks.algafood.api.v1.controller.RestauranteController;
import com.algaworks.algafood.api.v1.controller.RestauranteFormaPagamentoController;
import com.algaworks.algafood.api.v1.controller.RestauranteProdutoController;
import com.algaworks.algafood.api.v1.controller.RestauranteProdutoFotoController;
import com.algaworks.algafood.api.v1.controller.RestauranteUsuarioResponsavelController;
import com.algaworks.algafood.api.v1.controller.UsuarioController;
import com.algaworks.algafood.api.v1.controller.UsuarioGrupoController;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.StatusPedido;
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
            new TemplateVariable("sort", TemplateVariable.VariableType.REQUEST_PARAM)
    );

    public static final TemplateVariables PROJECAO_VARIABLES = new TemplateVariables(
            new TemplateVariable("projecao", TemplateVariable.VariableType.REQUEST_PARAM)
    );

    // Pedido
    public static Link linkToPedidos(String rel) {
        TemplateVariables filtroVariables = new TemplateVariables(
                new TemplateVariable("clienteId", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("restauranteId", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("dataCriacaoInicio", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("dataCriacaoFim", TemplateVariable.VariableType.REQUEST_PARAM));
        String pedidosUrl = linkTo(PedidoController.class).toUri().toString();
        return Link.of(UriTemplate.of(pedidosUrl, PAGINACAO_VARIABLES.concat(filtroVariables)), rel);
    }

    public static Link linkToStatusPedido(StatusPedido status, String codigoPedido) {
        switch (status) {
            case CONFIRMADO:
                return linkTo(methodOn(FluxoPedidoController.class).confirmar(codigoPedido)).withRel("confirmar");
            case ENTREGUE:
                return linkTo(methodOn(FluxoPedidoController.class).entregar(codigoPedido)).withRel("entregar");
            case CANCELADO:
                return linkTo(methodOn(FluxoPedidoController.class).cancelar(codigoPedido)).withRel("cancelar");
            default: throw new NegocioException("Status não previsto: " + status.name());
        }
    }

    // Restaurante
    public static Link linkToRestaurante(Long id, String rel) {
        return linkTo(methodOn(RestauranteController.class).buscar(id)).withRel(rel);
    }

    public static Link linkToRestaurante(Long id) {
        return linkToRestaurante(id, IanaLinkRelations.SELF.value());
    }

    public static Link linkToRestaurantes(String rel) {
        String restaurantesUrl = linkTo(RestauranteController.class).toUri().toString();
        return Link.of(UriTemplate.of(restaurantesUrl, PROJECAO_VARIABLES), rel);
    }

    public static Link linkToRestaurantes() {
        return linkToRestaurantes(IanaLinkRelations.SELF.value());
    }

    public static Link linkToRestauranteResponsaveis(Long restauranteId, String rel) {
        return linkTo(methodOn(RestauranteUsuarioResponsavelController.class).listar(restauranteId)).withRel(rel);
    }

    public static Link linkToRestauranteResponsaveis(Long id) {
        return linkToRestauranteResponsaveis(id, IanaLinkRelations.SELF.value());
    }

    public static Link linkToRestauranteResponsavelAssociar(Long restauranteId, String rel) {
        return linkTo(methodOn(RestauranteUsuarioResponsavelController.class).associar(restauranteId, null)).withRel(rel);
    }

    public static Link linkToRestauranteResponsavelDesassociar(Long restauranteId, Long usuarioId, String rel) {
        return linkTo(methodOn(RestauranteUsuarioResponsavelController.class).desassociar(restauranteId, usuarioId)).withRel(rel);
    }

    public static Link linkToRestauranteFormasPagamento(Long restauranteId, String rel) {
        return linkTo(methodOn(RestauranteFormaPagamentoController.class).listar(restauranteId)).withRel(rel);
    }

    public static Link linkToRestauranteFormasPagamento(Long restauranteId) {
        return linkToRestauranteFormasPagamento(restauranteId, IanaLinkRelations.SELF.value());
    }

    public static Link linkToRestauranteFormaPagamentoDesassociar(Long restauranteId, Long formaPagamentoId, String rel) {
        return linkTo(methodOn(RestauranteFormaPagamentoController.class).desassociar(restauranteId, formaPagamentoId)).withRel(rel);
    }

    public static Link linkToRestauranteFormaPagamentoAssociar(Long restauranteId, String rel) {
        return linkTo(methodOn(RestauranteFormaPagamentoController.class).associar(restauranteId, null)).withRel(rel);
    }

    public static Link linkToRestauranteAbertura(Long restauranteId, String rel) {
        return linkTo(methodOn(RestauranteController.class).abrir(restauranteId)).withRel(rel);
    }

    public static Link linkToRestauranteFechamento(Long restauranteId, String rel) {
        return linkTo(methodOn(RestauranteController.class).fechar(restauranteId)).withRel(rel);
    }

    public static Link linkToRestauranteAtivacao(Long restauranteId, String rel) {
        return linkTo(methodOn(RestauranteController.class).ativar(restauranteId)).withRel(rel);
    }

    public static Link linkToRestauranteInativacao(Long restauranteId, String rel) {
        return linkTo(methodOn(RestauranteController.class).inativar(restauranteId)).withRel(rel);
    }

    // Formas Pagamento
    public static Link linkToFormaPagamento(Long id, String rel) {
        return linkTo(methodOn(FormaPagamentoController.class).buscar(id)).withRel(rel);
    }

    public static Link linkToFormaPagamento(Long id) {
        return linkToFormaPagamento(id, IanaLinkRelations.SELF.value());
    }

    public static Link linkToFormasPagamento(String rel) {
        return linkTo(FormaPagamentoController.class).withRel(rel);
    }

    public static Link linkToFormasPagamento() {
        return linkToFormasPagamento(IanaLinkRelations.SELF.value());
    }

    // Cozinha
    public static Link linkToCozinhas(String rel) {
        return linkTo(CozinhaController.class).withRel(rel);
    }

    public static Link linkToCozinhas() {
        return linkToCozinhas(IanaLinkRelations.SELF.value());
    }

    public static Link linkToCozinha(Long cozinhaId, String rel) {
        return linkTo(methodOn(CozinhaController.class)
                .buscar(cozinhaId)).withRel(rel);
    }

    public static Link linkToCozinha(Long cozinhaId) {
        return linkToCozinha(cozinhaId, IanaLinkRelations.SELF.value());
    }

    // Usuario
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

    // Grupo
    public static Link linkToGrupos(String rel) {
        return linkTo(GrupoController.class).withRel(rel);
    }

    public static Link linkToGrupos() {
        return linkToGrupos(IanaLinkRelations.SELF.value());
    }

    // Usuario Grupo
    public static Link linkToUsuarioGrupos(Long id, String rel) {
        return linkTo(methodOn(UsuarioGrupoController.class).listar(id)).withRel(rel);
    }

    public static Link linkToUsuarioGrupos(Long id) {
        return linkToUsuarioGrupos(id, IanaLinkRelations.SELF.value());
    }

    public static Link linkToUsuarioGrupoAssociar(Long usuarioId, String rel) {
        return linkTo(methodOn(UsuarioGrupoController.class).associar(usuarioId, null)).withRel(rel);
    }

    public static Link linkToUsuarioGrupoDesassociar(Long usuarioId, Long grupoId, String rel) {
        return linkTo(methodOn(UsuarioGrupoController.class).desassociar(usuarioId, grupoId)).withRel(rel);
    }

    // Permissao
    public static Link linkToPermissoes(String rel) {
        return linkTo(PermissaoController.class).withRel(rel);
    }

    public static Link linkToPermissoes() {
        return linkToPermissoes(IanaLinkRelations.SELF.value());
    }

    // Grupo Permissao
    public static Link linkToGrupoPermissoes(Long grupoId, String rel) {
        return linkTo(methodOn(GrupoPermissaoController.class).listar(grupoId)).withRel(rel);
    }

    public static Link linkToGrupoPermissoes(Long grupoId) {
        return linkToGrupoPermissoes(grupoId, IanaLinkRelations.SELF.value());
    }

    public static Link linkToGrupoPermissaoAssociar(Long grupoId, String rel) {
        return linkTo(methodOn(GrupoPermissaoController.class).associar(grupoId, null)).withRel(rel);
    }

    public static Link linkToGrupoPermissaoDesassociar(Long grupoId, Long permissaoId, String rel) {
        return linkTo(methodOn(GrupoPermissaoController.class).desassociar(grupoId, permissaoId)).withRel(rel);
    }

    // Cidade
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

    // Estado
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

    // Produto
    public static Link linkToProduto(Long restauranteId, Long produtoId, String rel) {
        return linkTo(methodOn(RestauranteProdutoController.class).buscar(restauranteId, produtoId)).withRel(rel);
    }

    public static Link linkToProduto(Long restauranteId, Long produtoId) {
        return linkToProduto(restauranteId, produtoId, IanaLinkRelations.SELF.value());
    }

    public static Link linkToProdutos(Long restauranteId, String rel) {
        return linkTo(methodOn(RestauranteProdutoController.class).listar(restauranteId, null)).withRel(rel);
    }

    public static Link linkToProdutos(Long restauranteId) {
        return linkToProdutos(restauranteId, IanaLinkRelations.SELF.value());
    }

    public static Link linkToProdutoFoto(Long restauranteId, Long produtoId, String rel) {
        return linkTo(methodOn(RestauranteProdutoFotoController.class).buscar(restauranteId, produtoId)).withRel(rel);
    }

    public static Link linkToProdutoFoto(Long restauranteId, Long produtoId) {
        return linkToProdutoFoto(restauranteId, produtoId, IanaLinkRelations.SELF.value());
    }

    // Estatistica
    public static Link linkToEstatisticas(String rel) {
        return linkTo(EstatisticaController.class).withRel(rel);
    }

    public static Link linkToEstatisticasVendasDiarias(String rel) {
        TemplateVariables filtroVariables = new TemplateVariables(
                new TemplateVariable("restauranteId", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("dataCriacaoInicio", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("dataCriacaoFim", TemplateVariable.VariableType.REQUEST_PARAM),
                new TemplateVariable("timeOffset", TemplateVariable.VariableType.REQUEST_PARAM)
        );
        String pedidosUrl = linkTo(methodOn(EstatisticaController.class)
                .consultarVendasDiarias(null, null)).toUri().toString();
        return Link.of(UriTemplate.of(pedidosUrl, filtroVariables), rel);
    }
}
