package com.aluguel.service;

import com.aluguel.model.AgenteBanco;
import com.aluguel.repository.AgenteBancoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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

    public List<AgenteBanco> buscarPorNumeroAgencia(String numeroAgencia) {
        return agenteBancoRepository.findByNumeroAgencia(numeroAgencia);
    }

    public List<AgenteBanco> buscarPorNumeroConta(String numeroConta) {
        return agenteBancoRepository.findByNumeroConta(numeroConta);
    }

    public List<AgenteBanco> listarAtivosParaOperacoes() {
        return agenteBancoRepository.findByAtivoOperacoesTrue();
    }

    public List<AgenteBanco> listarInativosParaOperacoes() {
        return agenteBancoRepository.findByAtivoOperacoesFalse();
    }

    public List<AgenteBanco> buscarPorTipoConta(String tipoConta) {
        return agenteBancoRepository.findByTipoConta(tipoConta);
    }

    public List<AgenteBanco> buscarBancosQuePodemOperarComValor(BigDecimal valor) {
        return agenteBancoRepository.findBancosQuePodemOperarComValor(valor);
    }

    public List<AgenteBanco> buscarBancosAtivosECredenciados() {
        return agenteBancoRepository.findBancosAtivosECredenciados();
    }

    public List<AgenteBanco> buscarPorTaxaJurosEntre(BigDecimal taxaMinima, BigDecimal taxaMaxima) {
        return agenteBancoRepository.findByTaxaJurosBetween(taxaMinima, taxaMaxima);
    }

    public List<AgenteBanco> buscarPorLimiteCreditoEntre(BigDecimal limiteMinimo, BigDecimal limiteMaximo) {
        return agenteBancoRepository.findByLimiteCreditoBetween(limiteMinimo, limiteMaximo);
    }

    public AgenteBanco salvar(AgenteBanco agenteBanco) {
        // Validar se código do banco já existe
        if (agenteBanco.getId() == null) {
            if (agenteBancoRepository.findByCodigoBanco(agenteBanco.getCodigoBanco()).isPresent()) {
                throw new RuntimeException("Código do banco já cadastrado: " + agenteBanco.getCodigoBanco());
            }
        } else {
            if (agenteBancoRepository.existsByCodigoBancoAndIdNot(agenteBanco.getCodigoBanco(), agenteBanco.getId())) {
                throw new RuntimeException("Código do banco já cadastrado: " + agenteBanco.getCodigoBanco());
            }
        }

        // Definir data de cadastro se for novo banco
        if (agenteBanco.getId() == null) {
            agenteBanco.setDataCadastro(LocalDateTime.now());
        } else {
            agenteBanco.setUltimaAtualizacao(LocalDateTime.now());
        }

        return agenteBancoRepository.save(agenteBanco);
    }

    public AgenteBanco atualizar(Long id, AgenteBanco agenteBancoAtualizado) {
        return agenteBancoRepository.findById(id)
                .map(agenteBanco -> {
                    // Verificar se o novo código do banco já existe em outro banco
                    if (!agenteBanco.getCodigoBanco().equals(agenteBancoAtualizado.getCodigoBanco()) &&
                        agenteBancoRepository.existsByCodigoBancoAndIdNot(agenteBancoAtualizado.getCodigoBanco(), id)) {
                        throw new RuntimeException("Código do banco já cadastrado: " + agenteBancoAtualizado.getCodigoBanco());
                    }

                    agenteBanco.setNome(agenteBancoAtualizado.getNome());
                    agenteBanco.setEmail(agenteBancoAtualizado.getEmail());
                    agenteBanco.setTelefone(agenteBancoAtualizado.getTelefone());
                    agenteBanco.setEndereco(agenteBancoAtualizado.getEndereco());
                    agenteBanco.setCnpj(agenteBancoAtualizado.getCnpj());
                    agenteBanco.setRazaoSocial(agenteBancoAtualizado.getRazaoSocial());
                    agenteBanco.setNomeFantasia(agenteBancoAtualizado.getNomeFantasia());
                    agenteBanco.setResponsavelLegal(agenteBancoAtualizado.getResponsavelLegal());
                    agenteBanco.setCpfResponsavel(agenteBancoAtualizado.getCpfResponsavel());
                    agenteBanco.setTelefoneComercial(agenteBancoAtualizado.getTelefoneComercial());
                    agenteBanco.setEmailComercial(agenteBancoAtualizado.getEmailComercial());
                    agenteBanco.setAreaAtuacao(agenteBancoAtualizado.getAreaAtuacao());
                    agenteBanco.setCodigoBanco(agenteBancoAtualizado.getCodigoBanco());
                    agenteBanco.setNumeroAgencia(agenteBancoAtualizado.getNumeroAgencia());
                    agenteBanco.setDigitoAgencia(agenteBancoAtualizado.getDigitoAgencia());
                    agenteBanco.setNumeroConta(agenteBancoAtualizado.getNumeroConta());
                    agenteBanco.setDigitoConta(agenteBancoAtualizado.getDigitoConta());
                    agenteBanco.setTipoConta(agenteBancoAtualizado.getTipoConta());
                    agenteBanco.setLimiteCredito(agenteBancoAtualizado.getLimiteCredito());
                    agenteBanco.setTaxaJuros(agenteBancoAtualizado.getTaxaJuros());
                    agenteBanco.setPrazoMaximoPagamento(agenteBancoAtualizado.getPrazoMaximoPagamento());
                    agenteBanco.setValorMinimoOperacao(agenteBancoAtualizado.getValorMinimoOperacao());
                    agenteBanco.setValorMaximoOperacao(agenteBancoAtualizado.getValorMaximoOperacao());
                    agenteBanco.setAtivoOperacoes(agenteBancoAtualizado.getAtivoOperacoes());
                    agenteBanco.setObservacoes(agenteBancoAtualizado.getObservacoes());
                    agenteBanco.setUltimaAtualizacao(LocalDateTime.now());
                    return agenteBancoRepository.save(agenteBanco);
                })
                .orElseThrow(() -> new RuntimeException("Agente Banco não encontrado com ID: " + id));
    }

    public void ativarOperacoes(Long id) {
        agenteBancoRepository.findById(id)
                .ifPresentOrElse(
                        agenteBanco -> {
                            agenteBanco.setAtivoOperacoes(true);
                            agenteBanco.setUltimaAtualizacao(LocalDateTime.now());
                            agenteBancoRepository.save(agenteBanco);
                        },
                        () -> {
                            throw new RuntimeException("Agente Banco não encontrado com ID: " + id);
                        }
                );
    }

    public void inativarOperacoes(Long id) {
        agenteBancoRepository.findById(id)
                .ifPresentOrElse(
                        agenteBanco -> {
                            agenteBanco.setAtivoOperacoes(false);
                            agenteBanco.setUltimaAtualizacao(LocalDateTime.now());
                            agenteBancoRepository.save(agenteBanco);
                        },
                        () -> {
                            throw new RuntimeException("Agente Banco não encontrado com ID: " + id);
                        }
                );
    }

    public void excluir(Long id) {
        if (!agenteBancoRepository.existsById(id)) {
            throw new RuntimeException("Agente Banco não encontrado com ID: " + id);
        }
        agenteBancoRepository.deleteById(id);
    }

    public Long contarBancosAtivos() {
        return agenteBancoRepository.countByAtivoOperacoesTrue();
    }

    public boolean codigoBancoExiste(String codigoBanco) {
        return agenteBancoRepository.findByCodigoBanco(codigoBanco).isPresent();
    }

    // Método para verificar se um banco pode operar com um valor específico
    public boolean podeOperarComValor(Long bancoId, BigDecimal valor) {
        return agenteBancoRepository.findById(bancoId)
                .map(banco -> banco.podeOperarComValor(valor))
                .orElse(false);
    }

    // Método para calcular juros de um banco
    public BigDecimal calcularJuros(Long bancoId, BigDecimal valor, Integer prazoDias) {
        return agenteBancoRepository.findById(bancoId)
                .map(banco -> banco.calcularJuros(valor, prazoDias))
                .orElse(BigDecimal.ZERO);
    }
}
