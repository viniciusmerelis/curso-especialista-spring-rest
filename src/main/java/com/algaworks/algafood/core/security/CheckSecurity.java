package com.algaworks.algafood.core.security;

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
}
