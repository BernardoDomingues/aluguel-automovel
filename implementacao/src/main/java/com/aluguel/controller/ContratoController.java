package com.aluguel.controller;

import com.aluguel.model.Contrato;
import com.aluguel.model.Contrato.StatusContrato;
import com.aluguel.service.ContratoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contratos")
@Tag(name = "Contratos", description = "API para gerenciamento de contratos de aluguel de automóveis")
public class ContratoController {

    private final ContratoService contratoService;

    public ContratoController(ContratoService contratoService) {
        this.contratoService = contratoService;
    }

    @GetMapping
    @Operation(summary = "Listar todos os contratos", description = "Retorna uma lista com todos os contratos cadastrados")
    @ApiResponse(responseCode = "200", description = "Lista de contratos retornada com sucesso")
    public ResponseEntity<List<Contrato>> listarTodos() {
        List<Contrato> contratos = contratoService.listarTodos();
        return ResponseEntity.ok(contratos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar contrato por ID", description = "Retorna um contrato específico pelo seu ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Contrato encontrado"),
        @ApiResponse(responseCode = "404", description = "Contrato não encontrado")
    })
    public ResponseEntity<Contrato> buscarPorId(
            @Parameter(description = "ID do contrato") @PathVariable Long id) {
        return contratoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Listar contratos por status", description = "Retorna contratos filtrados por status (PENDENTE, APROVADO, REJEITADO, ATIVO, FINALIZADO, CANCELADO)")
    @ApiResponse(responseCode = "200", description = "Lista de contratos com o status especificado")
    public ResponseEntity<List<Contrato>> listarPorStatus(
            @Parameter(description = "Status do contrato") @PathVariable StatusContrato status) {
        List<Contrato> contratos = contratoService.listarPorStatus(status);
        return ResponseEntity.ok(contratos);
    }

    @GetMapping("/pendentes")
    @Operation(summary = "Listar pedidos pendentes", description = "Retorna todos os pedidos de aluguel pendentes de aprovação")
    @ApiResponse(responseCode = "200", description = "Lista de pedidos pendentes")
    public ResponseEntity<List<Contrato>> listarPedidosPendentes() {
        List<Contrato> contratos = contratoService.listarPedidosPendentes();
        return ResponseEntity.ok(contratos);
    }

    @PostMapping("/pedido")
    @Operation(summary = "Criar pedido de aluguel", description = "Cria um novo pedido de aluguel de automóvel")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Pedido criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos ou automóvel indisponível")
    })
    public ResponseEntity<Contrato> criarPedidoAluguel(
            @Parameter(description = "Dados do pedido de aluguel") @Valid @RequestBody Contrato contrato) {
        try {
            Contrato contratoSalvo = contratoService.criarPedidoAluguel(contrato);
            return ResponseEntity.status(HttpStatus.CREATED).body(contratoSalvo);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar contrato", description = "Atualiza os dados de um contrato existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Contrato atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Contrato não encontrado"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public ResponseEntity<Contrato> atualizar(
            @Parameter(description = "ID do contrato") @PathVariable Long id,
            @Parameter(description = "Dados atualizados do contrato") @Valid @RequestBody Contrato contrato) {
        try {
            Contrato contratoAtualizado = contratoService.atualizarContrato(id, contrato);
            return ResponseEntity.ok(contratoAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/aprovar")
    @Operation(summary = "Aprovar pedido", description = "Aprova um pedido de aluguel pendente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Pedido aprovado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Contrato não encontrado")
    })
    public ResponseEntity<Void> aprovarPedido(
            @Parameter(description = "ID do contrato") @PathVariable Long id) {
        try {
            contratoService.aprovarPedido(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/rejeitar")
    @Operation(summary = "Rejeitar pedido", description = "Rejeita um pedido de aluguel pendente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Pedido rejeitado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Contrato não encontrado")
    })
    public ResponseEntity<Void> rejeitarPedido(
            @Parameter(description = "ID do contrato") @PathVariable Long id) {
        try {
            contratoService.rejeitarPedido(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/ativar")
    @Operation(summary = "Ativar contrato", description = "Ativa um contrato aprovado")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Contrato ativado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Contrato não encontrado")
    })
    public ResponseEntity<Void> ativarContrato(
            @Parameter(description = "ID do contrato") @PathVariable Long id) {
        try {
            contratoService.ativarContrato(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/finalizar")
    @Operation(summary = "Finalizar contrato", description = "Finaliza um contrato ativo")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Contrato finalizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Contrato não encontrado")
    })
    public ResponseEntity<Void> finalizarContrato(
            @Parameter(description = "ID do contrato") @PathVariable Long id) {
        try {
            contratoService.finalizarContrato(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/cancelar")
    @Operation(summary = "Cancelar contrato", description = "Cancela um contrato ativo")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Contrato cancelado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Contrato não encontrado")
    })
    public ResponseEntity<Void> cancelarContrato(
            @Parameter(description = "ID do contrato") @PathVariable Long id) {
        try {
            contratoService.cancelarContrato(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir contrato", description = "Remove um contrato do sistema")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Contrato excluído com sucesso"),
        @ApiResponse(responseCode = "404", description = "Contrato não encontrado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID do contrato") @PathVariable Long id) {
        try {
            contratoService.excluirContrato(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
