package com.aluguel.repository;

import com.aluguel.model.Contrato;
import com.aluguel.model.Contrato.StatusContrato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContratoRepository extends JpaRepository<Contrato, Long> {

    List<Contrato> findByStatus(StatusContrato status);


    @Query("SELECT c FROM Contrato c WHERE c.status = 'PENDENTE'")
    List<Contrato> findPedidosPendentes();
}
