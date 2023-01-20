package com.algaworks.algafood.core.security;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;

public @interface CheckSecurity {
    @interface Cozinhas {
        @Retention(RetentionPolicy.RUNTIME)
        @Target(METHOD)
        @PreAuthorize("@algaSecurity.podeConsultarCozinhas()")
        @interface PodeConsultar { }

        @Retention(RetentionPolicy.RUNTIME)
        @Target(METHOD)
        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_COZINHAS')")
        @interface PodeEditar { }
    }

    @interface Restaurantes {
        @Retention(RetentionPolicy.RUNTIME)
        @Target(METHOD)
        @PreAuthorize("@algaSecurity.podeConsultarRestaurantes()")
        @interface PodeConsultar { }

        @Retention(RetentionPolicy.RUNTIME)
        @Target(METHOD)
        @PreAuthorize("@algaSecurity.podeGerenciarCadastroRestaurantes()")
        @interface PodeGerenciarCadastro { }

        @Retention(RetentionPolicy.RUNTIME)
        @Target(METHOD)
        @PreAuthorize("@algaSecurity.podeGerenciarFuncionamentoRestaurantes()")
        @interface PodeGerenciarFuncionamento { }
    }

    @interface Pedidos {
        @Retention(RetentionPolicy.RUNTIME)
        @Target(METHOD)
        @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
        @PostAuthorize("hasAuthority('CONSULTAR_PEDIDOS') or " +
                "@algaSecurity.usuarioAutenticadoIgual(returnObject.cliente.id) or " +
                "@algaSecurity.gerenciaRestaurante(returnObject.restaurante.id)")
        @interface PodeBuscar { }

        @Retention(RetentionPolicy.RUNTIME)
        @Target(METHOD)
        @PreAuthorize("@algaSecurity.podePesquisarPedidos(#filtro.clienteId, #filtro.restauranteId)")
        @interface PodePesquisar { }

        @Retention(RetentionPolicy.RUNTIME)
        @Target(METHOD)
        @PreAuthorize("hasAuthority('SCOPE_WRITE') and isAuthenticated()")
        @interface PodeCriar { }

        @Retention(RetentionPolicy.RUNTIME)
        @Target(METHOD)
        @PreAuthorize("@algaSecurity.podeGerenciarPedidos(#codigoPedido)")
        @interface PodeGerenciarPedidos { }
    }

    @interface FormasPagamento {
        @Retention(RetentionPolicy.RUNTIME)
        @Target(METHOD)
        @PreAuthorize("@algaSecurity.podeConsultarFormasPagamento()")
        @interface PodeConsultar { }

        @Retention(RetentionPolicy.RUNTIME)
        @Target(METHOD)
        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_FORMAS_PAGAMENTO')")
        @interface PodeEditar { }
    }

    @interface Cidades {
        @Retention(RetentionPolicy.RUNTIME)
        @Target(METHOD)
        @PreAuthorize("@algaSecurity.podeConsultarCidades()")
        @interface PodeConsultar { }

        @Retention(RetentionPolicy.RUNTIME)
        @Target(METHOD)
        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_CIDADES')")
        @interface PodeEditar { }
    }

    @interface Estados {
        @Retention(RetentionPolicy.RUNTIME)
        @Target(METHOD)
        @PreAuthorize("@algaSecurity.podeConsultarEstados()")
        @interface PodeConsultar { }

        @Retention(RetentionPolicy.RUNTIME)
        @Target(METHOD)
        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_ESTADOS')")
        @interface PodeEditar { }
    }

    @interface UsuariosGruposPermissoes {
        @Retention(RetentionPolicy.RUNTIME)
        @Target(METHOD)
        @PreAuthorize("hasAuthority(SCOPE_WRITE) and @algaSecurity.usuarioAutenticadoIgual(#usuarioId)")
        @interface PodeAlterarPropriaSenha { }

        @Retention(RetentionPolicy.RUNTIME)
        @Target(METHOD)
        @PreAuthorize("hasAuthority('SCOPE_WRITE') and " +
                "(hasAuthority('EDITAR_USUARIOS_GRUPOS_PERMISSOES') or " +
                "@algaSecurity.usuarioAutenticadoIgual(#usuarioId))")
        @interface PodeAlterarUsuario { }

        @Retention(RetentionPolicy.RUNTIME)
        @Target(METHOD)
        @PreAuthorize("@algaSecurity.podeConsultarUsuariosGruposPermissoes()")
        @interface PodeConsultar { }

        @Retention(RetentionPolicy.RUNTIME)
        @Target(METHOD)
        @PreAuthorize("@algaSecurity.podeEditarUsuariosGruposPermissoes()")
        @interface PodeEditar { }
    }

    @interface Estatisticas {
        @Retention(RetentionPolicy.RUNTIME)
        @Target(METHOD)
        @PreAuthorize("@algaSecurity.podeConsultarEstatisticas()")
        @interface PodeConsultar { }
    }
}
