package com.aluguel.controller;

import com.aluguel.model.Usuario;
import com.aluguel.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@Tag(name = "Usuários", description = "API para gerenciamento de usuários")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    @Operation(summary = "Listar todos os usuários", description = "Retorna uma lista com todos os usuários cadastrados")
    @ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso")
    public ResponseEntity<List<Usuario>> listarTodos() {
        List<Usuario> usuarios = usuarioService.listarTodos();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuário por ID", description = "Retorna um usuário específico pelo seu ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<Usuario> buscarPorId(
            @Parameter(description = "ID do usuário") @PathVariable Long id) {
        return usuarioService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    @Operation(summary = "Buscar usuário por email", description = "Retorna um usuário específico pelo seu email")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<Usuario> buscarPorEmail(
            @Parameter(description = "Email do usuário") @PathVariable String email) {
        return usuarioService.buscarPorEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/nome")
    @Operation(summary = "Buscar usuários por nome", description = "Retorna usuários que contenham o nome especificado")
    @ApiResponse(responseCode = "200", description = "Lista de usuários encontrados")
    public ResponseEntity<List<Usuario>> buscarPorNome(
            @Parameter(description = "Nome para busca") @RequestParam String nome) {
        List<Usuario> usuarios = usuarioService.buscarPorNome(nome);
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/endereco")
    @Operation(summary = "Buscar usuários por endereço", description = "Retorna usuários que contenham o endereço especificado")
    @ApiResponse(responseCode = "200", description = "Lista de usuários encontrados")
    public ResponseEntity<List<Usuario>> buscarPorEndereco(
            @Parameter(description = "Endereço para busca") @RequestParam String endereco) {
        List<Usuario> usuarios = usuarioService.buscarPorEndereco(endereco);
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/ativos")
    @Operation(summary = "Listar usuários ativos", description = "Retorna uma lista com todos os usuários ativos")
    @ApiResponse(responseCode = "200", description = "Lista de usuários ativos retornada com sucesso")
    public ResponseEntity<List<Usuario>> listarAtivos() {
        List<Usuario> usuarios = usuarioService.listarAtivos();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/inativos")
    @Operation(summary = "Listar usuários inativos", description = "Retorna uma lista com todos os usuários inativos")
    @ApiResponse(responseCode = "200", description = "Lista de usuários inativos retornada com sucesso")
    public ResponseEntity<List<Usuario>> listarInativos() {
        List<Usuario> usuarios = usuarioService.listarInativos();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/periodo-cadastro")
    @Operation(summary = "Buscar usuários por período de cadastro", description = "Retorna usuários cadastrados em um período específico")
    @ApiResponse(responseCode = "200", description = "Lista de usuários encontrados")
    public ResponseEntity<List<Usuario>> buscarPorPeriodoCadastro(
            @Parameter(description = "Data de início") @RequestParam LocalDateTime dataInicio,
            @Parameter(description = "Data de fim") @RequestParam LocalDateTime dataFim) {
        List<Usuario> usuarios = usuarioService.buscarPorPeriodoCadastro(dataInicio, dataFim);
        return ResponseEntity.ok(usuarios);
    }

    @PostMapping
    @Operation(summary = "Criar novo usuário", description = "Cadastra um novo usuário no sistema")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos ou email/telefone já cadastrado")
    })
    public ResponseEntity<Usuario> criar(
            @Parameter(description = "Dados do usuário") @Valid @RequestBody Usuario usuario) {
        try {
            Usuario usuarioSalvo = usuarioService.salvar(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioSalvo);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar usuário", description = "Atualiza os dados de um usuário existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos ou email/telefone já cadastrado")
    })
    public ResponseEntity<Usuario> atualizar(
            @Parameter(description = "ID do usuário") @PathVariable Long id,
            @Parameter(description = "Dados atualizados do usuário") @Valid @RequestBody Usuario usuario) {
        try {
            Usuario usuarioAtualizado = usuarioService.atualizar(id, usuario);
            return ResponseEntity.ok(usuarioAtualizado);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("não encontrado")) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping("/{id}/ativar")
    @Operation(summary = "Ativar usuário", description = "Ativa um usuário inativo")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuário ativado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<Void> ativar(
            @Parameter(description = "ID do usuário") @PathVariable Long id) {
        try {
            usuarioService.ativar(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/inativar")
    @Operation(summary = "Inativar usuário", description = "Inativa um usuário ativo")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuário inativado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<Void> inativar(
            @Parameter(description = "ID do usuário") @PathVariable Long id) {
        try {
            usuarioService.inativar(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir usuário", description = "Remove um usuário do sistema")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Usuário excluído com sucesso"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID do usuário") @PathVariable Long id) {
        try {
            usuarioService.excluir(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/estatisticas/contar-ativos")
    @Operation(summary = "Contar usuários ativos", description = "Retorna o número total de usuários ativos")
    @ApiResponse(responseCode = "200", description = "Contagem realizada com sucesso")
    public ResponseEntity<Long> contarUsuariosAtivos() {
        Long total = usuarioService.contarUsuariosAtivos();
        return ResponseEntity.ok(total);
    }

    @GetMapping("/verificar-email/{email}")
    @Operation(summary = "Verificar se email existe", description = "Verifica se um email já está cadastrado")
    @ApiResponse(responseCode = "200", description = "Verificação realizada com sucesso")
    public ResponseEntity<Boolean> verificarEmail(
            @Parameter(description = "Email para verificar") @PathVariable String email) {
        boolean existe = usuarioService.emailExiste(email);
        return ResponseEntity.ok(existe);
    }

    @GetMapping("/verificar-telefone/{telefone}")
    @Operation(summary = "Verificar se telefone existe", description = "Verifica se um telefone já está cadastrado")
    @ApiResponse(responseCode = "200", description = "Verificação realizada com sucesso")
    public ResponseEntity<Boolean> verificarTelefone(
            @Parameter(description = "Telefone para verificar") @PathVariable String telefone) {
        boolean existe = usuarioService.telefoneExiste(telefone);
        return ResponseEntity.ok(existe);
    }
}
