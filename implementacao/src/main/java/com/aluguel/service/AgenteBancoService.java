package com.aluguel.service;

import com.aluguel.model.AgenteBanco;
import com.aluguel.repository.AgenteBancoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AgenteBancoService {

    private final AgenteBancoRepository agenteBancoRepository;

    public AgenteBancoService(AgenteBancoRepository agenteBancoRepository) {
        this.agenteBancoRepository = agenteBancoRepository;
    }

    public List<AgenteBanco> listarTodos() {
        return agenteBancoRepository.findAll();
    }

    public Optional<AgenteBanco> buscarPorId(Long id) {
        return agenteBancoRepository.findById(id);
    }

    public Optional<AgenteBanco> buscarPorCodigoBanco(String codigoBanco) {
        return agenteBancoRepository.findByCodigoBanco(codigoBanco);
    }


    public AgenteBanco salvar(AgenteBanco agenteBanco) {
        if (agenteBanco.getId() == null) {
            if (agenteBancoRepository.findByCodigoBanco(agenteBanco.getCodigoBanco()).isPresent()) {
                throw new RuntimeException("Código do banco já cadastrado: " + agenteBanco.getCodigoBanco());
            }
        } else {
            if (agenteBancoRepository.existsByCodigoBancoAndIdNot(agenteBanco.getCodigoBanco(), agenteBanco.getId())) {
                throw new RuntimeException("Código do banco já cadastrado: " + agenteBanco.getCodigoBanco());
            }
        }

        return agenteBancoRepository.save(agenteBanco);
    }

    public AgenteBanco atualizar(Long id, AgenteBanco agenteBancoAtualizado) {
        return agenteBancoRepository.findById(id)
                .map(agenteBanco -> {
                    if (!agenteBanco.getCodigoBanco().equals(agenteBancoAtualizado.getCodigoBanco()) &&
                        agenteBancoRepository.existsByCodigoBancoAndIdNot(agenteBancoAtualizado.getCodigoBanco(), id)) {
                        throw new RuntimeException("Código do banco já cadastrado: " + agenteBancoAtualizado.getCodigoBanco());
                    }

                    agenteBanco.setNome(agenteBancoAtualizado.getNome());
                    agenteBanco.setEmail(agenteBancoAtualizado.getEmail());
                    agenteBanco.setEndereco(agenteBancoAtualizado.getEndereco());
                    agenteBanco.setCnpj(agenteBancoAtualizado.getCnpj());
                    agenteBanco.setRazaoSocial(agenteBancoAtualizado.getRazaoSocial());
                    agenteBanco.setCodigoBanco(agenteBancoAtualizado.getCodigoBanco());
                    return agenteBancoRepository.save(agenteBanco);
                })
                .orElseThrow(() -> new RuntimeException("Agente Banco não encontrado com ID: " + id));
    }


    public void excluir(Long id) {
        if (!agenteBancoRepository.existsById(id)) {
            throw new RuntimeException("Agente Banco não encontrado com ID: " + id);
        }
        agenteBancoRepository.deleteById(id);
    }
}
