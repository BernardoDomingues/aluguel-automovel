package com.aluguel.repository;

import com.aluguel.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByCpf(String cpf);

    Optional<Cliente> findByRg(String rg);

    Optional<Cliente> findByEmail(String email);

    @Query("SELECT COUNT(c) > 0 FROM Cliente c WHERE c.cpf = :cpf AND c.id != :id")
    boolean existsByCpfAndIdNot(@Param("cpf") String cpf, @Param("id") Long id);

    @Query("SELECT COUNT(c) > 0 FROM Cliente c WHERE c.rg = :rg AND c.id != :id")
    boolean existsByRgAndIdNot(@Param("rg") String rg, @Param("id") Long id);

    @Query("SELECT COUNT(c) > 0 FROM Cliente c WHERE c.email = :email AND c.id != :id")
    boolean existsByEmailAndIdNot(@Param("email") String email, @Param("id") Long id);

}