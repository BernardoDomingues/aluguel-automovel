package com.aluguel.service;

import com.aluguel.model.Contrato;
import com.aluguel.model.Contrato.StatusContrato;
import com.aluguel.model.Contrato.TipoContrato;
import com.aluguel.model.Automovel;
import com.aluguel.model.Usuario;
import com.aluguel.repository.ContratoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ContratoService {

    private final ContratoRepository contratoRepository;
    private final AutomovelService automovelService;
    private final UsuarioService usuarioService;

    public ContratoService(ContratoRepository contratoRepository, AutomovelService automovelService, UsuarioService usuarioService) {
        this.contratoRepository = contratoRepository;
        this.automovelService = automovelService;
        this.usuarioService = usuarioService;
    }

    public List<Contrato> listarTodos() {
        return contratoRepository.findAll();
    }

    public Optional<Contrato> buscarPorId(Long id) {
        return contratoRepository.findById(id);
    }

    public List<Contrato> listarPorStatus(StatusContrato status) {
        return contratoRepository.findByStatus(status);
    }

    public List<Contrato> listarPorTipoContrato(TipoContrato tipoContrato) {
        return contratoRepository.findByTipoContrato(tipoContrato);
    }

    public List<Contrato> listarPorAutomovel(Long automovelId) {
        return contratoRepository.findByAutomovelId(automovelId);
    }

    public List<Contrato> listarPorCliente(Long clienteId) {
        return contratoRepository.findByClienteId(clienteId);
    }

    public List<Contrato> listarPedidosPendentes() {
        return contratoRepository.findPedidosPendentes();
    }

    public List<Contrato> listarContratosAtivosNaData(LocalDate data) {
        return contratoRepository.findContratosAtivosNaData(data);
    }

    public List<Contrato> listarContratosVencidos() {
        return contratoRepository.findContratosVencidos(LocalDate.now());
    }

    public Contrato criarPedidoAluguel(Contrato contrato) {
        // Validar se o automóvel existe
        Automovel automovel = automovelService.buscarPorId(contrato.getAutomovel().getId())
                .orElseThrow(() -> new RuntimeException("Automóvel não encontrado com ID: " + contrato.getAutomovel().getId()));

        // Validar se o usuário existe
        Usuario usuario = usuarioService.buscarPorId(contrato.getUsuario().getId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + contrato.getUsuario().getId()));

        // Verificar se o automóvel está disponível
        if (!automovel.getDisponivel()) {
            throw new RuntimeException("Automóvel não está disponível para locação");
        }

        // Verificar se há conflito de datas com contratos ativos
        List<Contrato> contratosAtivos = contratoRepository.findContratosAtivosPorAutomovel(automovel.getId());
        for (Contrato contratoAtivo : contratosAtivos) {
            if (datasConflitantes(contrato.getDataInicio(), contrato.getDataFim(),
                    contratoAtivo.getDataInicio(), contratoAtivo.getDataFim())) {
                throw new RuntimeException("Já existe um contrato ativo para este automóvel no período especificado");
            }
        }

        // Definir o valor do aluguel baseado no automóvel
        contrato.setValorAluguel(automovel.getValorAluguel());
        contrato.setAutomovel(automovel);
        contrato.setUsuario(usuario);
        contrato.setStatus(StatusContrato.PENDENTE);
        contrato.setTipoContrato(TipoContrato.ALUGUEL);

        return contratoRepository.save(contrato);
    }

    public Contrato atualizarContrato(Long id, Contrato contratoAtualizado) {
        return contratoRepository.findById(id)
                .map(contrato -> {
                    contrato.setDataInicio(contratoAtualizado.getDataInicio());
                    contrato.setDataFim(contratoAtualizado.getDataFim());
                    contrato.setValorAluguel(contratoAtualizado.getValorAluguel());
                    contrato.setObservacoes(contratoAtualizado.getObservacoes());
                    contrato.setStatus(contratoAtualizado.getStatus());
                    contrato.setTipoContrato(contratoAtualizado.getTipoContrato());
                    return contratoRepository.save(contrato);
                })
                .orElseThrow(() -> new RuntimeException("Contrato não encontrado com ID: " + id));
    }

    public void aprovarPedido(Long id) {
        contratoRepository.findById(id)
                .ifPresentOrElse(
                        contrato -> {
                            contrato.setStatus(StatusContrato.APROVADO);
                            contratoRepository.save(contrato);
                        },
                        () -> {
                            throw new RuntimeException("Contrato não encontrado com ID: " + id);
                        }
                );
    }

    public void rejeitarPedido(Long id) {
        contratoRepository.findById(id)
                .ifPresentOrElse(
                        contrato -> {
                            contrato.setStatus(StatusContrato.REJEITADO);
                            contratoRepository.save(contrato);
                        },
                        () -> {
                            throw new RuntimeException("Contrato não encontrado com ID: " + id);
                        }
                );
    }

    public void ativarContrato(Long id) {
        contratoRepository.findById(id)
                .ifPresentOrElse(
                        contrato -> {
                            contrato.setStatus(StatusContrato.ATIVO);
                            contratoRepository.save(contrato);
                            
                            // Marcar automóvel como indisponível
                            automovelService.marcarComoIndisponivel(contrato.getAutomovel().getId());
                        },
                        () -> {
                            throw new RuntimeException("Contrato não encontrado com ID: " + id);
                        }
                );
    }

    public void finalizarContrato(Long id) {
        contratoRepository.findById(id)
                .ifPresentOrElse(
                        contrato -> {
                            contrato.setStatus(StatusContrato.FINALIZADO);
                            contratoRepository.save(contrato);
                            
                            // Marcar automóvel como disponível novamente
                            automovelService.marcarComoDisponivel(contrato.getAutomovel().getId());
                        },
                        () -> {
                            throw new RuntimeException("Contrato não encontrado com ID: " + id);
                        }
                );
    }

    public void cancelarContrato(Long id) {
        contratoRepository.findById(id)
                .ifPresentOrElse(
                        contrato -> {
                            contrato.setStatus(StatusContrato.CANCELADO);
                            contratoRepository.save(contrato);
                            
                            // Marcar automóvel como disponível novamente
                            automovelService.marcarComoDisponivel(contrato.getAutomovel().getId());
                        },
                        () -> {
                            throw new RuntimeException("Contrato não encontrado com ID: " + id);
                        }
                );
    }

    public void excluirContrato(Long id) {
        if (!contratoRepository.existsById(id)) {
            throw new RuntimeException("Contrato não encontrado com ID: " + id);
        }
        contratoRepository.deleteById(id);
    }

    private boolean datasConflitantes(LocalDate inicio1, LocalDate fim1, LocalDate inicio2, LocalDate fim2) {
        return !(fim1.isBefore(inicio2) || inicio1.isAfter(fim2));
    }
}
