package com.aluguel.repository;

import com.aluguel.model.Agente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AgenteRepository extends JpaRepository<Agente, Long> {

    Optional<Agente> findByCnpj(String cnpj);

    List<Agente> findByCredenciadoTrue();

    List<Agente> findByCredenciadoFalse();

    @Query("SELECT COUNT(a) > 0 FROM Agente a WHERE a.cnpj = :cnpj AND a.id != :id")
    boolean existsByCnpjAndIdNot(@Param("cnpj") String cnpj, @Param("id") Long id);

}
