package com.aluguel.controller;

import com.aluguel.model.Agente;
import com.aluguel.service.AgenteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    @GetMapping("/razao-social")
    @Operation(summary = "Buscar agentes por razão social", description = "Retorna agentes que contenham a razão social especificada")
    @ApiResponse(responseCode = "200", description = "Lista de agentes encontrados")
    public ResponseEntity<List<Agente>> buscarPorRazaoSocial(
            @Parameter(description = "Razão social para busca") @RequestParam String razaoSocial) {
        List<Agente> agentes = agenteService.buscarPorRazaoSocial(razaoSocial);
        return ResponseEntity.ok(agentes);
    }

    @GetMapping("/nome-fantasia")
    @Operation(summary = "Buscar agentes por nome fantasia", description = "Retorna agentes que contenham o nome fantasia especificado")
    @ApiResponse(responseCode = "200", description = "Lista de agentes encontrados")
    public ResponseEntity<List<Agente>> buscarPorNomeFantasia(
            @Parameter(description = "Nome fantasia para busca") @RequestParam String nomeFantasia) {
        List<Agente> agentes = agenteService.buscarPorNomeFantasia(nomeFantasia);
        return ResponseEntity.ok(agentes);
    }

    @GetMapping("/responsavel-legal")
    @Operation(summary = "Buscar agentes por responsável legal", description = "Retorna agentes que contenham o responsável legal especificado")
    @ApiResponse(responseCode = "200", description = "Lista de agentes encontrados")
    public ResponseEntity<List<Agente>> buscarPorResponsavelLegal(
            @Parameter(description = "Responsável legal para busca") @RequestParam String responsavelLegal) {
        List<Agente> agentes = agenteService.buscarPorResponsavelLegal(responsavelLegal);
        return ResponseEntity.ok(agentes);
    }

    @GetMapping("/cpf-responsavel/{cpfResponsavel}")
    @Operation(summary = "Buscar agente por CPF do responsável", description = "Retorna um agente específico pelo CPF do responsável")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Agente encontrado"),
        @ApiResponse(responseCode = "404", description = "Agente não encontrado")
    })
    public ResponseEntity<Agente> buscarPorCpfResponsavel(
            @Parameter(description = "CPF do responsável") @PathVariable String cpfResponsavel) {
        return agenteService.buscarPorCpfResponsavel(cpfResponsavel)
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

    @GetMapping("/credenciamento-valido")
    @Operation(summary = "Listar agentes com credenciamento válido", description = "Retorna agentes com credenciamento válido")
    @ApiResponse(responseCode = "200", description = "Lista de agentes com credenciamento válido")
    public ResponseEntity<List<Agente>> listarComCredenciamentoValido() {
        List<Agente> agentes = agenteService.listarComCredenciamentoValido();
        return ResponseEntity.ok(agentes);
    }

    @GetMapping("/credenciamento-vencido")
    @Operation(summary = "Listar agentes com credenciamento vencido", description = "Retorna agentes com credenciamento vencido")
    @ApiResponse(responseCode = "200", description = "Lista de agentes com credenciamento vencido")
    public ResponseEntity<List<Agente>> listarComCredenciamentoVencido() {
        List<Agente> agentes = agenteService.listarComCredenciamentoVencido();
        return ResponseEntity.ok(agentes);
    }

    @GetMapping("/area-atuacao")
    @Operation(summary = "Buscar agentes por área de atuação", description = "Retorna agentes que contenham a área de atuação especificada")
    @ApiResponse(responseCode = "200", description = "Lista de agentes encontrados")
    public ResponseEntity<List<Agente>> buscarPorAreaAtuacao(
            @Parameter(description = "Área de atuação para busca") @RequestParam String areaAtuacao) {
        List<Agente> agentes = agenteService.buscarPorAreaAtuacao(areaAtuacao);
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
    @Operation(summary = "Credenciar agente", description = "Credencia um agente com data de vencimento")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Agente credenciado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Agente não encontrado")
    })
    public ResponseEntity<Void> credenciar(
            @Parameter(description = "ID do agente") @PathVariable Long id,
            @Parameter(description = "Data de vencimento do credenciamento") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataVencimento) {
        try {
            agenteService.credenciar(id, dataVencimento);
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

    @GetMapping("/periodo-cadastro")
    @Operation(summary = "Buscar agentes por período de cadastro", description = "Retorna agentes cadastrados em um período específico")
    @ApiResponse(responseCode = "200", description = "Lista de agentes encontrados")
    public ResponseEntity<List<Agente>> buscarPorPeriodoCadastro(
            @Parameter(description = "Data de início") @RequestParam LocalDateTime dataInicio,
            @Parameter(description = "Data de fim") @RequestParam LocalDateTime dataFim) {
        List<Agente> agentes = agenteService.buscarPorPeriodoCadastro(dataInicio, dataFim);
        return ResponseEntity.ok(agentes);
    }

    @GetMapping("/periodo-credenciamento")
    @Operation(summary = "Buscar agentes por período de credenciamento", description = "Retorna agentes credenciados em um período específico")
    @ApiResponse(responseCode = "200", description = "Lista de agentes encontrados")
    public ResponseEntity<List<Agente>> buscarPorPeriodoCredenciamento(
            @Parameter(description = "Data de início") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @Parameter(description = "Data de fim") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {
        List<Agente> agentes = agenteService.buscarPorPeriodoCredenciamento(dataInicio, dataFim);
        return ResponseEntity.ok(agentes);
    }

    @GetMapping("/estatisticas/contar-credenciados")
    @Operation(summary = "Contar agentes credenciados", description = "Retorna o número total de agentes credenciados")
    @ApiResponse(responseCode = "200", description = "Contagem realizada com sucesso")
    public ResponseEntity<Long> contarAgentesCredenciados() {
        Long total = agenteService.contarAgentesCredenciados();
        return ResponseEntity.ok(total);
    }

    @GetMapping("/estatisticas/contar-ativos")
    @Operation(summary = "Contar agentes ativos", description = "Retorna o número total de agentes ativos")
    @ApiResponse(responseCode = "200", description = "Contagem realizada com sucesso")
    public ResponseEntity<Long> contarAgentesAtivos() {
        Long total = agenteService.contarAgentesAtivos();
        return ResponseEntity.ok(total);
    }

    @GetMapping("/verificar-cnpj/{cnpj}")
    @Operation(summary = "Verificar se CNPJ existe", description = "Verifica se um CNPJ já está cadastrado")
    @ApiResponse(responseCode = "200", description = "Verificação realizada com sucesso")
    public ResponseEntity<Boolean> verificarCnpj(
            @Parameter(description = "CNPJ para verificar") @PathVariable String cnpj) {
        boolean existe = agenteService.cnpjExiste(cnpj);
        return ResponseEntity.ok(existe);
    }

    @GetMapping("/verificar-cpf-responsavel/{cpfResponsavel}")
    @Operation(summary = "Verificar se CPF do responsável existe", description = "Verifica se um CPF do responsável já está cadastrado")
    @ApiResponse(responseCode = "200", description = "Verificação realizada com sucesso")
    public ResponseEntity<Boolean> verificarCpfResponsavel(
            @Parameter(description = "CPF do responsável para verificar") @PathVariable String cpfResponsavel) {
        boolean existe = agenteService.cpfResponsavelExiste(cpfResponsavel);
        return ResponseEntity.ok(existe);
    }
}
