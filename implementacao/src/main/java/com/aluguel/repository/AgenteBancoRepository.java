package com.aluguel.repository;

import com.aluguel.model.AgenteBanco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AgenteBancoRepository extends JpaRepository<AgenteBanco, Long> {

    Optional<AgenteBanco> findByCodigoBanco(String codigoBanco);

    @Query("SELECT COUNT(ab) > 0 FROM AgenteBanco ab WHERE ab.codigoBanco = :codigoBanco AND ab.id != :id")
    boolean existsByCodigoBancoAndIdNot(@Param("codigoBanco") String codigoBanco, @Param("id") Long id);

}
