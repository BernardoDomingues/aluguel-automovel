package com.aluguel.service;

import com.aluguel.model.Automovel;
import com.aluguel.model.Automovel.TipoProprietario;
import com.aluguel.repository.AutomovelRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AutomovelService {

    private final AutomovelRepository automovelRepository;

    public AutomovelService(AutomovelRepository automovelRepository) {
        this.automovelRepository = automovelRepository;
    }

    public List<Automovel> listarTodos() {
        return automovelRepository.findAll();
    }

    public Optional<Automovel> buscarPorId(Long id) {
        return automovelRepository.findById(id);
    }

    public List<Automovel> listarDisponiveis() {
        return automovelRepository.findByDisponivelTrue();
    }

    public List<Automovel> buscarPorMarca(String marca) {
        return automovelRepository.findByMarca(marca);
    }

    public List<Automovel> buscarPorModelo(String modelo) {
        return automovelRepository.findByModelo(modelo);
    }

    public List<Automovel> buscarPorAno(Integer ano) {
        return automovelRepository.findByAno(ano);
    }

    public List<Automovel> buscarPorProprietario(TipoProprietario proprietario) {
        return automovelRepository.findByProprietario(proprietario);
    }

    public List<Automovel> buscarPorFaixaPreco(Double valorMinimo, Double valorMaximo) {
        return automovelRepository.findByValorAluguelBetween(valorMinimo, valorMaximo);
    }

    public List<Automovel> buscarPorMarcaEModelo(String marca, String modelo) {
        return automovelRepository.findByMarcaAndModeloContaining(marca, modelo);
    }

    public List<Automovel> buscarPorFaixaAno(Integer anoInicio, Integer anoFim) {
        return automovelRepository.findByAnoBetween(anoInicio, anoFim);
    }

    public Optional<Automovel> buscarPorPlaca(String placa) {
        return automovelRepository.findByPlaca(placa);
    }

    public Optional<Automovel> buscarPorMatricula(String matricula) {
        return automovelRepository.findByMatricula(matricula);
    }

    public Automovel salvar(Automovel automovel) {
        // Verificar se placa já existe
        if (automovelRepository.existsByPlaca(automovel.getPlaca())) {
            throw new RuntimeException("Placa já cadastrada: " + automovel.getPlaca());
        }

        // Verificar se matrícula já existe
        if (automovelRepository.existsByMatricula(automovel.getMatricula())) {
            throw new RuntimeException("Matrícula já cadastrada: " + automovel.getMatricula());
        }

        return automovelRepository.save(automovel);
    }

    public Automovel atualizar(Long id, Automovel automovelAtualizado) {
        return automovelRepository.findById(id)
                .map(automovel -> {
                    // Verificar se a nova placa já existe em outro automóvel
                    if (!automovel.getPlaca().equals(automovelAtualizado.getPlaca()) &&
                        automovelRepository.existsByPlaca(automovelAtualizado.getPlaca())) {
                        throw new RuntimeException("Placa já cadastrada: " + automovelAtualizado.getPlaca());
                    }

                    // Verificar se a nova matrícula já existe em outro automóvel
                    if (!automovel.getMatricula().equals(automovelAtualizado.getMatricula()) &&
                        automovelRepository.existsByMatricula(automovelAtualizado.getMatricula())) {
                        throw new RuntimeException("Matrícula já cadastrada: " + automovelAtualizado.getMatricula());
                    }

                    automovel.setMatricula(automovelAtualizado.getMatricula());
                    automovel.setAno(automovelAtualizado.getAno());
                    automovel.setMarca(automovelAtualizado.getMarca());
                    automovel.setModelo(automovelAtualizado.getModelo());
                    automovel.setPlaca(automovelAtualizado.getPlaca());
                    automovel.setValorAluguel(automovelAtualizado.getValorAluguel());
                    automovel.setDescricao(automovelAtualizado.getDescricao());
                    automovel.setDisponivel(automovelAtualizado.getDisponivel());
                    automovel.setProprietario(automovelAtualizado.getProprietario());
                    return automovelRepository.save(automovel);
                })
                .orElseThrow(() -> new RuntimeException("Automóvel não encontrado com ID: " + id));
    }

    public void excluir(Long id) {
        if (!automovelRepository.existsById(id)) {
            throw new RuntimeException("Automóvel não encontrado com ID: " + id);
        }
        automovelRepository.deleteById(id);
    }

    public void marcarComoIndisponivel(Long id) {
        automovelRepository.findById(id)
                .ifPresentOrElse(
                        automovel -> {
                            automovel.setDisponivel(false);
                            automovelRepository.save(automovel);
                        },
                        () -> {
                            throw new RuntimeException("Automóvel não encontrado com ID: " + id);
                        }
                );
    }

    public void marcarComoDisponivel(Long id) {
        automovelRepository.findById(id)
                .ifPresentOrElse(
                        automovel -> {
                            automovel.setDisponivel(true);
                            automovelRepository.save(automovel);
                        },
                        () -> {
                            throw new RuntimeException("Automóvel não encontrado com ID: " + id);
                        }
                );
    }
}
