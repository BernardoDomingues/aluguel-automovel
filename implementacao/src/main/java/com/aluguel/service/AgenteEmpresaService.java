package com.aluguel.service;

import com.aluguel.model.AgenteEmpresa;
import com.aluguel.repository.AgenteEmpresaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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

    public List<AgenteEmpresa> buscarPorPorteEmpresa(String porteEmpresa) {
        return agenteEmpresaRepository.findByPorteEmpresa(porteEmpresa);
    }

    public List<AgenteEmpresa> buscarPorRegimeTributario(String regimeTributario) {
        return agenteEmpresaRepository.findByRegimeTributario(regimeTributario);
    }

    public List<AgenteEmpresa> listarAtivasParaOperacoes() {
        return agenteEmpresaRepository.findByAtivoOperacoesTrue();
    }

    public List<AgenteEmpresa> listarInativasParaOperacoes() {
        return agenteEmpresaRepository.findByAtivoOperacoesFalse();
    }

    public List<AgenteEmpresa> buscarEmpresasAtivasECredenciadas() {
        return agenteEmpresaRepository.findEmpresasAtivasECredenciadas();
    }

    public List<AgenteEmpresa> buscarEmpresasGrandePorte() {
        return agenteEmpresaRepository.findEmpresasGrandePorte();
    }

    public List<AgenteEmpresa> buscarPorFaturamentoEntre(BigDecimal faturamentoMinimo, BigDecimal faturamentoMaximo) {
        return agenteEmpresaRepository.findByFaturamentoAnualBetween(faturamentoMinimo, faturamentoMaximo);
    }

    public List<AgenteEmpresa> buscarPorNumeroFuncionariosEntre(Integer numeroMinimo, Integer numeroMaximo) {
        return agenteEmpresaRepository.findByNumeroFuncionariosBetween(numeroMinimo, numeroMaximo);
    }

    public List<AgenteEmpresa> buscarPorRegiaoAtendimento(String regiao) {
        return agenteEmpresaRepository.findByRegioesAtendimentoContaining(regiao);
    }

    public List<AgenteEmpresa> buscarPorModalidadeServico(String modalidade) {
        return agenteEmpresaRepository.findByModalidadesServicoContaining(modalidade);
    }

    public List<AgenteEmpresa> buscarComCertificacoes() {
        return agenteEmpresaRepository.findWithCertificacoes();
    }

    public List<AgenteEmpresa> buscarComLicencasAmbientais() {
        return agenteEmpresaRepository.findWithLicencasAmbientais();
    }

    public AgenteEmpresa salvar(AgenteEmpresa agenteEmpresa) {
        // Definir data de cadastro se for nova empresa
        if (agenteEmpresa.getId() == null) {
            agenteEmpresa.setDataCadastro(LocalDateTime.now());
        } else {
            agenteEmpresa.setUltimaAtualizacao(LocalDateTime.now());
        }

        return agenteEmpresaRepository.save(agenteEmpresa);
    }

    public AgenteEmpresa atualizar(Long id, AgenteEmpresa agenteEmpresaAtualizado) {
        return agenteEmpresaRepository.findById(id)
                .map(agenteEmpresa -> {
                    agenteEmpresa.setNome(agenteEmpresaAtualizado.getNome());
                    agenteEmpresa.setEmail(agenteEmpresaAtualizado.getEmail());
                    agenteEmpresa.setTelefone(agenteEmpresaAtualizado.getTelefone());
                    agenteEmpresa.setEndereco(agenteEmpresaAtualizado.getEndereco());
                    agenteEmpresa.setCnpj(agenteEmpresaAtualizado.getCnpj());
                    agenteEmpresa.setRazaoSocial(agenteEmpresaAtualizado.getRazaoSocial());
                    agenteEmpresa.setNomeFantasia(agenteEmpresaAtualizado.getNomeFantasia());
                    agenteEmpresa.setResponsavelLegal(agenteEmpresaAtualizado.getResponsavelLegal());
                    agenteEmpresa.setCpfResponsavel(agenteEmpresaAtualizado.getCpfResponsavel());
                    agenteEmpresa.setTelefoneComercial(agenteEmpresaAtualizado.getTelefoneComercial());
                    agenteEmpresa.setEmailComercial(agenteEmpresaAtualizado.getEmailComercial());
                    agenteEmpresa.setAreaAtuacao(agenteEmpresaAtualizado.getAreaAtuacao());
                    agenteEmpresa.setSegmentoAtuacao(agenteEmpresaAtualizado.getSegmentoAtuacao());
                    agenteEmpresa.setNumeroFuncionarios(agenteEmpresaAtualizado.getNumeroFuncionarios());
                    agenteEmpresa.setFaturamentoAnual(agenteEmpresaAtualizado.getFaturamentoAnual());
                    agenteEmpresa.setCapitalSocial(agenteEmpresaAtualizado.getCapitalSocial());
                    agenteEmpresa.setDataFundacao(agenteEmpresaAtualizado.getDataFundacao());
                    agenteEmpresa.setPorteEmpresa(agenteEmpresaAtualizado.getPorteEmpresa());
                    agenteEmpresa.setRegimeTributario(agenteEmpresaAtualizado.getRegimeTributario());
                    agenteEmpresa.setCertificacoes(agenteEmpresaAtualizado.getCertificacoes());
                    agenteEmpresa.setLicencasAmbientais(agenteEmpresaAtualizado.getLicencasAmbientais());
                    agenteEmpresa.setNumeroVeiculosFrota(agenteEmpresaAtualizado.getNumeroVeiculosFrota());
                    agenteEmpresa.setCapacidadeAtendimento(agenteEmpresaAtualizado.getCapacidadeAtendimento());
                    agenteEmpresa.setRegioesAtendimento(agenteEmpresaAtualizado.getRegioesAtendimento());
                    agenteEmpresa.setModalidadesServico(agenteEmpresaAtualizado.getModalidadesServico());
                    agenteEmpresa.setPoliticaCancelamento(agenteEmpresaAtualizado.getPoliticaCancelamento());
                    agenteEmpresa.setGarantiasOferecidas(agenteEmpresaAtualizado.getGarantiasOferecidas());
                    agenteEmpresa.setAtivoOperacoes(agenteEmpresaAtualizado.getAtivoOperacoes());
                    agenteEmpresa.setObservacoes(agenteEmpresaAtualizado.getObservacoes());
                    agenteEmpresa.setUltimaAtualizacao(LocalDateTime.now());
                    return agenteEmpresaRepository.save(agenteEmpresa);
                })
                .orElseThrow(() -> new RuntimeException("Agente Empresa não encontrado com ID: " + id));
    }

    public void ativarOperacoes(Long id) {
        agenteEmpresaRepository.findById(id)
                .ifPresentOrElse(
                        agenteEmpresa -> {
                            agenteEmpresa.setAtivoOperacoes(true);
                            agenteEmpresa.setUltimaAtualizacao(LocalDateTime.now());
                            agenteEmpresaRepository.save(agenteEmpresa);
                        },
                        () -> {
                            throw new RuntimeException("Agente Empresa não encontrado com ID: " + id);
                        }
                );
    }

    public void inativarOperacoes(Long id) {
        agenteEmpresaRepository.findById(id)
                .ifPresentOrElse(
                        agenteEmpresa -> {
                            agenteEmpresa.setAtivoOperacoes(false);
                            agenteEmpresa.setUltimaAtualizacao(LocalDateTime.now());
                            agenteEmpresaRepository.save(agenteEmpresa);
                        },
                        () -> {
                            throw new RuntimeException("Agente Empresa não encontrado com ID: " + id);
                        }
                );
    }

    public void excluir(Long id) {
        if (!agenteEmpresaRepository.existsById(id)) {
            throw new RuntimeException("Agente Empresa não encontrado com ID: " + id);
        }
        agenteEmpresaRepository.deleteById(id);
    }

    public Long contarEmpresasAtivas() {
        return agenteEmpresaRepository.countByAtivoOperacoesTrue();
    }

    public Long contarEmpresasPorSegmento(String segmentoAtuacao) {
        return agenteEmpresaRepository.countBySegmentoAtuacao(segmentoAtuacao);
    }

    public Long contarEmpresasPorPorte(String porteEmpresa) {
        return agenteEmpresaRepository.countByPorteEmpresa(porteEmpresa);
    }

    // Método para verificar se uma empresa pode atender uma demanda
    public boolean podeAtenderDemanda(Long empresaId, Integer numeroVeiculos, String regiao) {
        return agenteEmpresaRepository.findById(empresaId)
                .map(empresa -> empresa.podeAtenderDemanda(numeroVeiculos, regiao))
                .orElse(false);
    }

    // Método para calcular capacidade de utilização de uma empresa
    public double calcularCapacidadeUtilizacao(Long empresaId, Integer veiculosEmUso) {
        return agenteEmpresaRepository.findById(empresaId)
                .map(empresa -> empresa.calcularCapacidadeUtilizacao(veiculosEmUso))
                .orElse(0.0);
    }

    // Método para verificar se uma empresa é de grande porte
    public boolean isGrandePorte(Long empresaId) {
        return agenteEmpresaRepository.findById(empresaId)
                .map(AgenteEmpresa::isGrandePorte)
                .orElse(false);
    }
}
