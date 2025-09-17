package com.aluguel.service;

import com.aluguel.model.Agente;
import com.aluguel.repository.AgenteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    public List<Agente> buscarPorRazaoSocial(String razaoSocial) {
        return agenteRepository.findByRazaoSocialContainingIgnoreCase(razaoSocial);
    }

    public List<Agente> buscarPorNomeFantasia(String nomeFantasia) {
        return agenteRepository.findByNomeFantasiaContainingIgnoreCase(nomeFantasia);
    }

    public List<Agente> buscarPorResponsavelLegal(String responsavelLegal) {
        return agenteRepository.findByResponsavelLegalContainingIgnoreCase(responsavelLegal);
    }

    public Optional<Agente> buscarPorCpfResponsavel(String cpfResponsavel) {
        return agenteRepository.findByCpfResponsavel(cpfResponsavel);
    }

    public List<Agente> listarCredenciados() {
        return agenteRepository.findByCredenciadoTrue();
    }

    public List<Agente> listarNaoCredenciados() {
        return agenteRepository.findByCredenciadoFalse();
    }

    public List<Agente> listarComCredenciamentoValido() {
        return agenteRepository.findWithCredenciamentoValido(LocalDate.now());
    }

    public List<Agente> listarComCredenciamentoVencido() {
        return agenteRepository.findWithCredenciamentoVencido(LocalDate.now());
    }

    public List<Agente> buscarPorAreaAtuacao(String areaAtuacao) {
        return agenteRepository.findByAreaAtuacaoContainingIgnoreCase(areaAtuacao);
    }

    public Agente salvar(Agente agente) {
        // Validar se CNPJ já existe
        if (agente.getId() == null) {
            if (agenteRepository.findByCnpj(agente.getCnpj()).isPresent()) {
                throw new RuntimeException("CNPJ já cadastrado: " + agente.getCnpj());
            }
        } else {
            if (agenteRepository.existsByCnpjAndIdNot(agente.getCnpj(), agente.getId())) {
                throw new RuntimeException("CNPJ já cadastrado: " + agente.getCnpj());
            }
        }

        // Validar se CPF do responsável já existe
        if (agente.getCpfResponsavel() != null) {
            if (agente.getId() == null) {
                if (agenteRepository.findByCpfResponsavel(agente.getCpfResponsavel()).isPresent()) {
                    throw new RuntimeException("CPF do responsável já cadastrado: " + agente.getCpfResponsavel());
                }
            } else {
                if (agenteRepository.existsByCpfResponsavelAndIdNot(agente.getCpfResponsavel(), agente.getId())) {
                    throw new RuntimeException("CPF do responsável já cadastrado: " + agente.getCpfResponsavel());
                }
            }
        }

        // Definir data de cadastro se for novo agente
        if (agente.getId() == null) {
            agente.setDataCadastro(LocalDateTime.now());
        } else {
            agente.setUltimaAtualizacao(LocalDateTime.now());
        }

        return agenteRepository.save(agente);
    }

    public Agente atualizar(Long id, Agente agenteAtualizado) {
        return agenteRepository.findById(id)
                .map(agente -> {
                    // Verificar se o novo CNPJ já existe em outro agente
                    if (!agente.getCnpj().equals(agenteAtualizado.getCnpj()) &&
                        agenteRepository.existsByCnpjAndIdNot(agenteAtualizado.getCnpj(), id)) {
                        throw new RuntimeException("CNPJ já cadastrado: " + agenteAtualizado.getCnpj());
                    }

                    // Verificar se o novo CPF do responsável já existe em outro agente
                    if (agenteAtualizado.getCpfResponsavel() != null &&
                        !agenteAtualizado.getCpfResponsavel().equals(agente.getCpfResponsavel()) &&
                        agenteRepository.existsByCpfResponsavelAndIdNot(agenteAtualizado.getCpfResponsavel(), id)) {
                        throw new RuntimeException("CPF do responsável já cadastrado: " + agenteAtualizado.getCpfResponsavel());
                    }

                    agente.setNome(agenteAtualizado.getNome());
                    agente.setEmail(agenteAtualizado.getEmail());
                    agente.setTelefone(agenteAtualizado.getTelefone());
                    agente.setEndereco(agenteAtualizado.getEndereco());
                    agente.setCnpj(agenteAtualizado.getCnpj());
                    agente.setRazaoSocial(agenteAtualizado.getRazaoSocial());
                    agente.setNomeFantasia(agenteAtualizado.getNomeFantasia());
                    agente.setInscricaoEstadual(agenteAtualizado.getInscricaoEstadual());
                    agente.setInscricaoMunicipal(agenteAtualizado.getInscricaoMunicipal());
                    agente.setResponsavelLegal(agenteAtualizado.getResponsavelLegal());
                    agente.setCpfResponsavel(agenteAtualizado.getCpfResponsavel());
                    agente.setTelefoneComercial(agenteAtualizado.getTelefoneComercial());
                    agente.setEmailComercial(agenteAtualizado.getEmailComercial());
                    agente.setSite(agenteAtualizado.getSite());
                    agente.setAreaAtuacao(agenteAtualizado.getAreaAtuacao());
                    agente.setObservacoes(agenteAtualizado.getObservacoes());
                    agente.setUltimaAtualizacao(LocalDateTime.now());
                    return agenteRepository.save(agente);
                })
                .orElseThrow(() -> new RuntimeException("Agente não encontrado com ID: " + id));
    }

    public void credenciar(Long id, LocalDate dataVencimento) {
        agenteRepository.findById(id)
                .ifPresentOrElse(
                        agente -> {
                            agente.credenciar(dataVencimento);
                            agente.setUltimaAtualizacao(LocalDateTime.now());
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
                            agente.setDataCredenciamento(null);
                            agente.setDataVencimentoCredenciamento(null);
                            agente.setUltimaAtualizacao(LocalDateTime.now());
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
                            agente.setUltimaAtualizacao(LocalDateTime.now());
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
                            agente.setUltimaAtualizacao(LocalDateTime.now());
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

    public List<Agente> buscarPorPeriodoCadastro(LocalDateTime dataInicio, LocalDateTime dataFim) {
        return agenteRepository.findByDataCadastroBetween(dataInicio, dataFim);
    }

    public List<Agente> buscarPorPeriodoCredenciamento(LocalDate dataInicio, LocalDate dataFim) {
        return agenteRepository.findByDataCredenciamentoBetween(dataInicio, dataFim);
    }

    public Long contarAgentesCredenciados() {
        return agenteRepository.countByCredenciadoTrue();
    }

    public Long contarAgentesAtivos() {
        return agenteRepository.countByAtivoTrue();
    }

    public boolean cnpjExiste(String cnpj) {
        return agenteRepository.findByCnpj(cnpj).isPresent();
    }

    public boolean cpfResponsavelExiste(String cpfResponsavel) {
        return agenteRepository.findByCpfResponsavel(cpfResponsavel).isPresent();
    }
}
