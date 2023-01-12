package com.algaworks.algafood.core.security;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;

public @interface CheckSecurity {
    public @interface Cozinhas {
        @Retention(RetentionPolicy.RUNTIME)
        @Target(METHOD)
        @PreAuthorize("isAuthenticated()")
        public @interface PodeConsultar { }

        @Retention(RetentionPolicy.RUNTIME)
        @Target(METHOD)
        @PreAuthorize("hasAuthority('EDITAR_COZINHAS')")
        public @interface PodeEditar { }
    }
}
