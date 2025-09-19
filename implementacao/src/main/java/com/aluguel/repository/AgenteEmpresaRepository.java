package com.aluguel.repository;

import com.aluguel.model.AgenteEmpresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgenteEmpresaRepository extends JpaRepository<AgenteEmpresa, Long> {

    List<AgenteEmpresa> findBySegmentoAtuacao(String segmentoAtuacao);

}
