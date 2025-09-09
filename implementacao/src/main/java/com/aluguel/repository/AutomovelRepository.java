package com.aluguel.repository;

import com.aluguel.model.Automovel;
import com.aluguel.model.Automovel.TipoProprietario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AutomovelRepository extends JpaRepository<Automovel, Long> {

    List<Automovel> findByDisponivelTrue();

    List<Automovel> findByMarca(String marca);

    List<Automovel> findByModelo(String modelo);

    List<Automovel> findByAno(Integer ano);

    List<Automovel> findByProprietario(TipoProprietario proprietario);

    @Query("SELECT a FROM Automovel a WHERE a.valorAluguel BETWEEN :valorMinimo AND :valorMaximo")
    List<Automovel> findByValorAluguelBetween(@Param("valorMinimo") Double valorMinimo, @Param("valorMaximo") Double valorMaximo);

    @Query("SELECT a FROM Automovel a WHERE a.marca LIKE %:marca% AND a.modelo LIKE %:modelo%")
    List<Automovel> findByMarcaAndModeloContaining(@Param("marca") String marca, @Param("modelo") String modelo);

    @Query("SELECT a FROM Automovel a WHERE a.ano BETWEEN :anoInicio AND :anoFim")
    List<Automovel> findByAnoBetween(@Param("anoInicio") Integer anoInicio, @Param("anoFim") Integer anoFim);

    Optional<Automovel> findByPlaca(String placa);

    Optional<Automovel> findByMatricula(String matricula);

    boolean existsByPlaca(String placa);

    boolean existsByMatricula(String matricula);
}
