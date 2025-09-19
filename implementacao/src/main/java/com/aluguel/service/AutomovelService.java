package com.aluguel.service;

import com.aluguel.model.Automovel;
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


    public Automovel salvar(Automovel automovel) {
        if (automovelRepository.existsByPlaca(automovel.getPlaca())) {
            throw new RuntimeException("Placa já cadastrada: " + automovel.getPlaca());
        }

        if (automovelRepository.existsByMatricula(automovel.getMatricula())) {
            throw new RuntimeException("Matrícula já cadastrada: " + automovel.getMatricula());
        }

        return automovelRepository.save(automovel);
    }

    public Automovel atualizar(Long id, Automovel automovelAtualizado) {
        return automovelRepository.findById(id)
                .map(automovel -> {
                    if (!automovel.getPlaca().equals(automovelAtualizado.getPlaca()) &&
                        automovelRepository.existsByPlaca(automovelAtualizado.getPlaca())) {
                        throw new RuntimeException("Placa já cadastrada: " + automovelAtualizado.getPlaca());
                    }

                    if (!automovel.getMatricula().equals(automovelAtualizado.getMatricula()) &&
                        automovelRepository.existsByMatricula(automovelAtualizado.getMatricula())) {
                        throw new RuntimeException("Matrícula já cadastrada: " + automovelAtualizado.getMatricula());
                    }

                    automovel.setMatricula(automovelAtualizado.getMatricula());
                    automovel.setAno(automovelAtualizado.getAno());
                    automovel.setMarca(automovelAtualizado.getMarca());
                    automovel.setModelo(automovelAtualizado.getModelo());
                    automovel.setPlaca(automovelAtualizado.getPlaca());
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
