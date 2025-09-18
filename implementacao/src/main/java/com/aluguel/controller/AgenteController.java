package com.aluguel.controller;

import com.aluguel.model.Agente;
import com.aluguel.service.AgenteService;
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
@RequestMapping("/api/agentes")
@Tag(name = "Agentes", description = "API para gerenciamento de agentes (empresas e bancos)")
public class AgenteController {

    private final AgenteService agenteService;

    public AgenteController(AgenteService agenteService) {
        this.agenteService = agenteService;
    }

    @GetMapping
    @Operation(summary = "Listar todos os agentes", description = "Retorna uma lista com todos os agentes cadastrados")
    @ApiResponse(responseCode = "200", description = "Lista de agentes retornada com sucesso")
    public ResponseEntity<List<Agente>> listarTodos() {
        List<Agente> agentes = agenteService.listarTodos();
        return ResponseEntity.ok(agentes);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar agente por ID", description = "Retorna um agente específico pelo seu ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Agente encontrado"),
        @ApiResponse(responseCode = "404", description = "Agente não encontrado")
    })
    public ResponseEntity<Agente> buscarPorId(
            @Parameter(description = "ID do agente") @PathVariable Long id) {
        return agenteService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/cnpj/{cnpj}")
    @Operation(summary = "Buscar agente por CNPJ", description = "Retorna um agente específico pelo seu CNPJ")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Agente encontrado"),
        @ApiResponse(responseCode = "404", description = "Agente não encontrado")
    })
    public ResponseEntity<Agente> buscarPorCnpj(
            @Parameter(description = "CNPJ do agente") @PathVariable String cnpj) {
        return agenteService.buscarPorCnpj(cnpj)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @GetMapping("/credenciados")
    @Operation(summary = "Listar agentes credenciados", description = "Retorna uma lista com todos os agentes credenciados")
    @ApiResponse(responseCode = "200", description = "Lista de agentes credenciados retornada com sucesso")
    public ResponseEntity<List<Agente>> listarCredenciados() {
        List<Agente> agentes = agenteService.listarCredenciados();
        return ResponseEntity.ok(agentes);
    }

    @GetMapping("/nao-credenciados")
    @Operation(summary = "Listar agentes não credenciados", description = "Retorna uma lista com todos os agentes não credenciados")
    @ApiResponse(responseCode = "200", description = "Lista de agentes não credenciados retornada com sucesso")
    public ResponseEntity<List<Agente>> listarNaoCredenciados() {
        List<Agente> agentes = agenteService.listarNaoCredenciados();
        return ResponseEntity.ok(agentes);
    }


    @PostMapping
    @Operation(summary = "Criar novo agente", description = "Cadastra um novo agente no sistema")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Agente criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos ou CNPJ/CPF já cadastrado")
    })
    public ResponseEntity<Agente> criar(
            @Parameter(description = "Dados do agente") @Valid @RequestBody Agente agente) {
        try {
            Agente agenteSalvo = agenteService.salvar(agente);
            return ResponseEntity.status(HttpStatus.CREATED).body(agenteSalvo);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar agente", description = "Atualiza os dados de um agente existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Agente atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Agente não encontrado"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos ou CNPJ/CPF já cadastrado")
    })
    public ResponseEntity<Agente> atualizar(
            @Parameter(description = "ID do agente") @PathVariable Long id,
            @Parameter(description = "Dados atualizados do agente") @Valid @RequestBody Agente agente) {
        try {
            Agente agenteAtualizado = agenteService.atualizar(id, agente);
            return ResponseEntity.ok(agenteAtualizado);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("não encontrado")) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping("/{id}/credenciar")
    @Operation(summary = "Credenciar agente", description = "Credencia um agente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Agente credenciado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Agente não encontrado")
    })
    public ResponseEntity<Void> credenciar(
            @Parameter(description = "ID do agente") @PathVariable Long id) {
        try {
            agenteService.credenciar(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/revogar-credenciamento")
    @Operation(summary = "Revogar credenciamento", description = "Revoga o credenciamento de um agente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Credenciamento revogado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Agente não encontrado")
    })
    public ResponseEntity<Void> revogarCredenciamento(
            @Parameter(description = "ID do agente") @PathVariable Long id) {
        try {
            agenteService.revogarCredenciamento(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/ativar")
    @Operation(summary = "Ativar agente", description = "Ativa um agente inativo")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Agente ativado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Agente não encontrado")
    })
    public ResponseEntity<Void> ativar(
            @Parameter(description = "ID do agente") @PathVariable Long id) {
        try {
            agenteService.ativar(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/inativar")
    @Operation(summary = "Inativar agente", description = "Inativa um agente ativo")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Agente inativado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Agente não encontrado")
    })
    public ResponseEntity<Void> inativar(
            @Parameter(description = "ID do agente") @PathVariable Long id) {
        try {
            agenteService.inativar(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir agente", description = "Remove um agente do sistema")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Agente excluído com sucesso"),
        @ApiResponse(responseCode = "404", description = "Agente não encontrado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID do agente") @PathVariable Long id) {
        try {
            agenteService.excluir(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
