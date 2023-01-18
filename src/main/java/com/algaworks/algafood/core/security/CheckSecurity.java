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
        @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
        @interface PodeConsultar { }

        @Retention(RetentionPolicy.RUNTIME)
        @Target(METHOD)
        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_COZINHAS')")
        @interface PodeEditar { }
    }

    @interface Restaurantes {
        @Retention(RetentionPolicy.RUNTIME)
        @Target(METHOD)
        @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
        @interface PodeConsultar { }

        @Retention(RetentionPolicy.RUNTIME)
        @Target(METHOD)
        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_RESTAURANTES')")
        @interface PodeGerenciarCadastro { }

        @Retention(RetentionPolicy.RUNTIME)
        @Target(METHOD)
        @PreAuthorize("hasAuthority('SCOPE_WRITE') and " +
                "hasAuthority('EDITAR_RESTAURANTES') or " +
                "@algaSecurity.gerenciaRestaurante(#restauranteId)")
        @interface PodeGerenciarFuncionamento { }
    }

    @interface Pedidos {
        @Retention(RetentionPolicy.RUNTIME)
        @Target(METHOD)
        @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
        @PostAuthorize("hasAuthority('CONSULTAR_PEDIDOS') or " +
                "@algaSecurity.getUsuarioId() == returnObject.cliente.id or " +
                "@algaSecurity.gerenciaRestaurante(returnObject.restaurante.id)")
        @interface PodeBuscar { }

        @Retention(RetentionPolicy.RUNTIME)
        @Target(METHOD)
        @PreAuthorize("hasAuthority('SCOPE_READ') and  (hasAuthority('CONSULTAR_PEDIDOS') or " +
                "@algaSecurity.getUsuarioId() == #filtro.clienteId or " +
                "@algaSecurity.gerenciaRestaurante(#filtro.restauranteId))")
        @interface PodePesquisar { }

        @Retention(RetentionPolicy.RUNTIME)
        @Target(METHOD)
        @PreAuthorize("hasAuthority('SCOPE_WRITE') and isAuthenticated()")
        @interface PodeCriar { }

        @Retention(RetentionPolicy.RUNTIME)
        @Target(METHOD)
        @PreAuthorize("hasAuthority('SCOPE_WRITE') and " +
                "(hasAuthority('GERENCIAR_PEDIDOS') or " +
                "@algaSecurity.gerenciaRestauranteDoPedido(#codigoPedido))")
        @interface PodeGerenciarPedidos { }
    }
}
