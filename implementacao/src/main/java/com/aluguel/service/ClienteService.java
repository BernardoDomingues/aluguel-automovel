package com.aluguel.service;

import com.aluguel.model.Cliente;
import com.aluguel.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    public Optional<Cliente> buscarPorId(Long id) {
        return clienteRepository.findById(id);
    }

    public Optional<Cliente> buscarPorCpf(String cpf) {
        return clienteRepository.findByCpf(cpf);
    }

    public Optional<Cliente> buscarPorEmail(String email) {
        return clienteRepository.findByEmail(email);
    }

    public Optional<Cliente> buscarPorRg(String rg) {
        return clienteRepository.findByRg(rg);
    }

    public List<Cliente> buscarPorNome(String nome) {
        return clienteRepository.findByNomeContaining(nome);
    }

    public List<Cliente> buscarPorProfissao(String profissao) {
        return clienteRepository.findByProfissaoContaining(profissao);
    }

    public List<Cliente> buscarPorEndereco(String endereco) {
        return clienteRepository.findByEnderecoContaining(endereco);
    }

    public Cliente salvar(Cliente cliente) {
        // Verificar se CPF já existe
        if (clienteRepository.existsByCpf(cliente.getCpf())) {
            throw new RuntimeException("CPF já cadastrado: " + cliente.getCpf());
        }

        // Verificar se email já existe
        if (clienteRepository.existsByEmail(cliente.getEmail())) {
            throw new RuntimeException("Email já cadastrado: " + cliente.getEmail());
        }

        // Verificar se RG já existe
        if (clienteRepository.existsByRg(cliente.getRg())) {
            throw new RuntimeException("RG já cadastrado: " + cliente.getRg());
        }

        return clienteRepository.save(cliente);
    }

    public Cliente atualizar(Long id, Cliente clienteAtualizado) {
        return clienteRepository.findById(id)
                .map(cliente -> {
                    // Verificar se o novo CPF já existe em outro cliente
                    if (!cliente.getCpf().equals(clienteAtualizado.getCpf()) &&
                        clienteRepository.existsByCpf(clienteAtualizado.getCpf())) {
                        throw new RuntimeException("CPF já cadastrado: " + clienteAtualizado.getCpf());
                    }

                    // Verificar se o novo email já existe em outro cliente
                    if (!cliente.getEmail().equals(clienteAtualizado.getEmail()) &&
                        clienteRepository.existsByEmail(clienteAtualizado.getEmail())) {
                        throw new RuntimeException("Email já cadastrado: " + clienteAtualizado.getEmail());
                    }

                    // Verificar se o novo RG já existe em outro cliente
                    if (!cliente.getRg().equals(clienteAtualizado.getRg()) &&
                        clienteRepository.existsByRg(clienteAtualizado.getRg())) {
                        throw new RuntimeException("RG já cadastrado: " + clienteAtualizado.getRg());
                    }

                    cliente.setNome(clienteAtualizado.getNome());
                    cliente.setCpf(clienteAtualizado.getCpf());
                    cliente.setRg(clienteAtualizado.getRg());
                    cliente.setEndereco(clienteAtualizado.getEndereco());
                    cliente.setEmail(clienteAtualizado.getEmail());
                    cliente.setTelefone(clienteAtualizado.getTelefone());
                    cliente.setProfissao(clienteAtualizado.getProfissao());
                    cliente.setEmpregadores(clienteAtualizado.getEmpregadores());
                    cliente.setRendimentos(clienteAtualizado.getRendimentos());
                    cliente.setObservacoes(clienteAtualizado.getObservacoes());
                    return clienteRepository.save(cliente);
                })
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com ID: " + id));
    }

    public void excluir(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new RuntimeException("Cliente não encontrado com ID: " + id);
        }
        clienteRepository.deleteById(id);
    }
}