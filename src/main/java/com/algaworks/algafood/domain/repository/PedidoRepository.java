package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.Pedido;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PedidoRepository extends CustomJpaRepository<Pedido, Long>, JpaSpecificationExecutor<Pedido> {

    Optional<Pedido> findByCodigo(String codigo);

    @Query("from Pedido p join fetch p.cliente join fetch p.restaurante r join fetch r.cozinha")
    List<Pedido> findAll();

    @Query("select case when count(1) > 0 then true else false end " +
            "from Pedido ped " +
            "join ped.restaurante rest " +
            "join rest.responsaveis resp " +
            "where ped.codigo = :codigoPedido and resp.id = :usuarioId")
    boolean isPedidoGerenciadoPor(@Param("codigoPedido") String codigoPedido, @Param("usuarioId") Long usuarioId);
}
