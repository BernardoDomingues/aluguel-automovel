package com.aluguel.repository;

import com.aluguel.model.Contrato;
import com.aluguel.model.Contrato.StatusContrato;
import com.aluguel.model.Contrato.TipoContrato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ContratoRepository extends JpaRepository<Contrato, Long> {

    List<Contrato> findByStatus(StatusContrato status);

    List<Contrato> findByTipoContrato(TipoContrato tipoContrato);

    List<Contrato> findByAutomovelId(Long automovelId);

    List<Contrato> findByUsuarioId(Long usuarioId);

    @Query("SELECT c FROM Contrato c WHERE c.dataInicio <= :data AND c.dataFim >= :data AND c.status = 'ATIVO'")
    List<Contrato> findContratosAtivosNaData(@Param("data") LocalDate data);

    @Query("SELECT c FROM Contrato c WHERE c.dataFim < :data AND c.status = 'ATIVO'")
    List<Contrato> findContratosVencidos(@Param("data") LocalDate data);

    @Query("SELECT c FROM Contrato c WHERE c.automovel.id = :automovelId AND c.status = 'ATIVO'")
    List<Contrato> findContratosAtivosPorAutomovel(@Param("automovelId") Long automovelId);

    @Query("SELECT c FROM Contrato c WHERE c.usuario.id = :usuarioId AND c.status IN ('PENDENTE', 'APROVADO', 'ATIVO')")
    List<Contrato> findContratosAtivosPorUsuario(@Param("usuarioId") Long usuarioId);

    @Query("SELECT c FROM Contrato c WHERE c.status = 'PENDENTE'")
    List<Contrato> findPedidosPendentes();
}
