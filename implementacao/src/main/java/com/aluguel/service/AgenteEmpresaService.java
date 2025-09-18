package com.aluguel.service;

import com.aluguel.model.AgenteEmpresa;
import com.aluguel.repository.AgenteEmpresaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AgenteEmpresaService {

    private final AgenteEmpresaRepository agenteEmpresaRepository;

    public AgenteEmpresaService(AgenteEmpresaRepository agenteEmpresaRepository) {
        this.agenteEmpresaRepository = agenteEmpresaRepository;
    }

    public List<AgenteEmpresa> listarTodos() {
        return agenteEmpresaRepository.findAll();
    }

    public Optional<AgenteEmpresa> buscarPorId(Long id) {
        return agenteEmpresaRepository.findById(id);
    }

    public List<AgenteEmpresa> buscarPorSegmentoAtuacao(String segmentoAtuacao) {
        return agenteEmpresaRepository.findBySegmentoAtuacao(segmentoAtuacao);
    }

    public AgenteEmpresa salvar(AgenteEmpresa agenteEmpresa) {
        return agenteEmpresaRepository.save(agenteEmpresa);
    }

    public AgenteEmpresa atualizar(Long id, AgenteEmpresa agenteEmpresaAtualizado) {
        return agenteEmpresaRepository.findById(id)
                .map(agenteEmpresa -> {
                    agenteEmpresa.setNome(agenteEmpresaAtualizado.getNome());
                    agenteEmpresa.setEmail(agenteEmpresaAtualizado.getEmail());
                    agenteEmpresa.setEndereco(agenteEmpresaAtualizado.getEndereco());
                    agenteEmpresa.setCnpj(agenteEmpresaAtualizado.getCnpj());
                    agenteEmpresa.setRazaoSocial(agenteEmpresaAtualizado.getRazaoSocial());
                    agenteEmpresa.setSegmentoAtuacao(agenteEmpresaAtualizado.getSegmentoAtuacao());
                    return agenteEmpresaRepository.save(agenteEmpresa);
                })
                .orElseThrow(() -> new RuntimeException("Agente Empresa não encontrado com ID: " + id));
    }


    public void excluir(Long id) {
        if (!agenteEmpresaRepository.existsById(id)) {
            throw new RuntimeException("Agente Empresa não encontrado com ID: " + id);
        }
        agenteEmpresaRepository.deleteById(id);
    }
}
