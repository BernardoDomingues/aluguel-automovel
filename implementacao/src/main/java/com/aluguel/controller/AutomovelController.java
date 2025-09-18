package com.aluguel.controller;

import com.aluguel.model.Automovel;
import com.aluguel.service.AutomovelService;
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
@RequestMapping("/api/automoveis")
@Tag(name = "Automóveis", description = "API para gerenciamento de automóveis")
public class AutomovelController {

    private final AutomovelService automovelService;

    public AutomovelController(AutomovelService automovelService) {
        this.automovelService = automovelService;
    }

    @GetMapping
    @Operation(summary = "Listar todos os automóveis", description = "Retorna uma lista com todos os automóveis cadastrados")
    @ApiResponse(responseCode = "200", description = "Lista de automóveis retornada com sucesso")
    public ResponseEntity<List<Automovel>> listarTodos() {
        List<Automovel> automoveis = automovelService.listarTodos();
        return ResponseEntity.ok(automoveis);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar automóvel por ID", description = "Retorna um automóvel específico pelo seu ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Automóvel encontrado"),
        @ApiResponse(responseCode = "404", description = "Automóvel não encontrado")
    })
    public ResponseEntity<Automovel> buscarPorId(
            @Parameter(description = "ID do automóvel") @PathVariable Long id) {
        return automovelService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/disponiveis")
    @Operation(summary = "Listar automóveis disponíveis", description = "Retorna uma lista com todos os automóveis disponíveis para locação")
    @ApiResponse(responseCode = "200", description = "Lista de automóveis disponíveis retornada com sucesso")
    public ResponseEntity<List<Automovel>> listarDisponiveis() {
        List<Automovel> automoveis = automovelService.listarDisponiveis();
        return ResponseEntity.ok(automoveis);
    }


    @PostMapping
    @Operation(summary = "Criar novo automóvel", description = "Cadastra um novo automóvel no sistema")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Automóvel criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos ou placa/matrícula já cadastrada")
    })
    public ResponseEntity<Automovel> criar(
            @Parameter(description = "Dados do automóvel") @Valid @RequestBody Automovel automovel) {
        try {
            Automovel automovelSalvo = automovelService.salvar(automovel);
            return ResponseEntity.status(HttpStatus.CREATED).body(automovelSalvo);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar automóvel", description = "Atualiza os dados de um automóvel existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Automóvel atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Automóvel não encontrado"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos ou placa/matrícula já cadastrada")
    })
    public ResponseEntity<Automovel> atualizar(
            @Parameter(description = "ID do automóvel") @PathVariable Long id,
            @Parameter(description = "Dados atualizados do automóvel") @Valid @RequestBody Automovel automovel) {
        try {
            Automovel automovelAtualizado = automovelService.atualizar(id, automovel);
            return ResponseEntity.ok(automovelAtualizado);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("não encontrado")) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir automóvel", description = "Remove um automóvel do sistema")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Automóvel excluído com sucesso"),
        @ApiResponse(responseCode = "404", description = "Automóvel não encontrado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID do automóvel") @PathVariable Long id) {
        try {
            automovelService.excluir(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/disponivel")
    @Operation(summary = "Marcar automóvel como disponível", description = "Altera o status de um automóvel para disponível")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Status alterado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Automóvel não encontrado")
    })
    public ResponseEntity<Void> marcarComoDisponivel(
            @Parameter(description = "ID do automóvel") @PathVariable Long id) {
        try {
            automovelService.marcarComoDisponivel(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/indisponivel")
    @Operation(summary = "Marcar automóvel como indisponível", description = "Altera o status de um automóvel para indisponível")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Status alterado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Automóvel não encontrado")
    })
    public ResponseEntity<Void> marcarComoIndisponivel(
            @Parameter(description = "ID do automóvel") @PathVariable Long id) {
        try {
            automovelService.marcarComoIndisponivel(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
