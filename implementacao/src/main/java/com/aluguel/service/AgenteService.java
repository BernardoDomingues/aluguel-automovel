package com.aluguel.service;

import com.aluguel.model.Agente;
import com.aluguel.repository.AgenteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AgenteService {

    private final AgenteRepository agenteRepository;

    public AgenteService(AgenteRepository agenteRepository) {
        this.agenteRepository = agenteRepository;
    }

    public List<Agente> listarTodos() {
        return agenteRepository.findAll();
    }

    public Optional<Agente> buscarPorId(Long id) {
        return agenteRepository.findById(id);
    }

    public Optional<Agente> buscarPorCnpj(String cnpj) {
        return agenteRepository.findByCnpj(cnpj);
    }


    public List<Agente> listarCredenciados() {
        return agenteRepository.findByCredenciadoTrue();
    }

    public List<Agente> listarNaoCredenciados() {
        return agenteRepository.findByCredenciadoFalse();
    }


    public Agente salvar(Agente agente) {
        if (agente.getId() == null) {
            if (agenteRepository.findByCnpj(agente.getCnpj()).isPresent()) {
                throw new RuntimeException("CNPJ já cadastrado: " + agente.getCnpj());
            }
        } else {
            if (agenteRepository.existsByCnpjAndIdNot(agente.getCnpj(), agente.getId())) {
                throw new RuntimeException("CNPJ já cadastrado: " + agente.getCnpj());
            }
        }


        return agenteRepository.save(agente);
    }

    public Agente atualizar(Long id, Agente agenteAtualizado) {
        return agenteRepository.findById(id)
                .map(agente -> {
                    if (!agente.getCnpj().equals(agenteAtualizado.getCnpj()) &&
                        agenteRepository.existsByCnpjAndIdNot(agenteAtualizado.getCnpj(), id)) {
                        throw new RuntimeException("CNPJ já cadastrado: " + agenteAtualizado.getCnpj());
                    }

                    agente.setNome(agenteAtualizado.getNome());
                    agente.setEmail(agenteAtualizado.getEmail());
                    agente.setEndereco(agenteAtualizado.getEndereco());
                    agente.setCnpj(agenteAtualizado.getCnpj());
                    agente.setRazaoSocial(agenteAtualizado.getRazaoSocial());
                    return agenteRepository.save(agente);
                })
                .orElseThrow(() -> new RuntimeException("Agente não encontrado com ID: " + id));
    }

    public void credenciar(Long id) {
        agenteRepository.findById(id)
                .ifPresentOrElse(
                        agente -> {
                            agente.setCredenciado(true);
                            agenteRepository.save(agente);
                        },
                        () -> {
                            throw new RuntimeException("Agente não encontrado com ID: " + id);
                        }
                );
    }

    public void revogarCredenciamento(Long id) {
        agenteRepository.findById(id)
                .ifPresentOrElse(
                        agente -> {
                            agente.setCredenciado(false);
                            agenteRepository.save(agente);
                        },
                        () -> {
                            throw new RuntimeException("Agente não encontrado com ID: " + id);
                        }
                );
    }

    public void ativar(Long id) {
        agenteRepository.findById(id)
                .ifPresentOrElse(
                        agente -> {
                            agente.setAtivo(true);
                            agenteRepository.save(agente);
                        },
                        () -> {
                            throw new RuntimeException("Agente não encontrado com ID: " + id);
                        }
                );
    }

    public void inativar(Long id) {
        agenteRepository.findById(id)
                .ifPresentOrElse(
                        agente -> {
                            agente.setAtivo(false);
                            agenteRepository.save(agente);
                        },
                        () -> {
                            throw new RuntimeException("Agente não encontrado com ID: " + id);
                        }
                );
    }

    public void excluir(Long id) {
        if (!agenteRepository.existsById(id)) {
            throw new RuntimeException("Agente não encontrado com ID: " + id);
        }
        agenteRepository.deleteById(id);
    }

}
