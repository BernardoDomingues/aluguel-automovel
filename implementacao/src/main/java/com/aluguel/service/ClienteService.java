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


    public Cliente salvar(Cliente cliente) {
        if (cliente.getId() == null) {
            if (clienteRepository.findByCpf(cliente.getCpf()).isPresent()) {
                throw new RuntimeException("CPF já cadastrado: " + cliente.getCpf());
            }
        } else {
            if (clienteRepository.existsByCpfAndIdNot(cliente.getCpf(), cliente.getId())) {
                throw new RuntimeException("CPF já cadastrado: " + cliente.getCpf());
            }
        }

        if (cliente.getId() == null) {
            if (clienteRepository.findByRg(cliente.getRg()).isPresent()) {
                throw new RuntimeException("RG já cadastrado: " + cliente.getRg());
            }
        } else {
            if (clienteRepository.existsByRgAndIdNot(cliente.getRg(), cliente.getId())) {
                throw new RuntimeException("RG já cadastrado: " + cliente.getRg());
            }
        }

        if (cliente.getId() == null) {
            if (clienteRepository.findByEmail(cliente.getEmail()).isPresent()) {
                throw new RuntimeException("Email já cadastrado: " + cliente.getEmail());
            }
        } else {
            if (clienteRepository.existsByEmailAndIdNot(cliente.getEmail(), cliente.getId())) {
                throw new RuntimeException("Email já cadastrado: " + cliente.getEmail());
            }
        }


        return clienteRepository.save(cliente);
    }

    public Cliente atualizar(Long id, Cliente clienteAtualizado) {
        return clienteRepository.findById(id)
                .map(cliente -> {
                    if (!cliente.getCpf().equals(clienteAtualizado.getCpf()) &&
                        clienteRepository.existsByCpfAndIdNot(clienteAtualizado.getCpf(), id)) {
                        throw new RuntimeException("CPF já cadastrado: " + clienteAtualizado.getCpf());
                    }

                    if (!cliente.getEmail().equals(clienteAtualizado.getEmail()) &&
                        clienteRepository.existsByEmailAndIdNot(clienteAtualizado.getEmail(), id)) {
                        throw new RuntimeException("Email já cadastrado: " + clienteAtualizado.getEmail());
                    }

                    if (!cliente.getRg().equals(clienteAtualizado.getRg()) &&
                        clienteRepository.existsByRgAndIdNot(clienteAtualizado.getRg(), id)) {
                        throw new RuntimeException("RG já cadastrado: " + clienteAtualizado.getRg());
                    }

                    cliente.setNome(clienteAtualizado.getNome());
                    cliente.setCpf(clienteAtualizado.getCpf());
                    cliente.setRg(clienteAtualizado.getRg());
                    cliente.setEndereco(clienteAtualizado.getEndereco());
                    cliente.setEmail(clienteAtualizado.getEmail());
                    cliente.setProfissao(clienteAtualizado.getProfissao());
                    cliente.setEmpregadores(clienteAtualizado.getEmpregadores());
                    cliente.setRendimentos(clienteAtualizado.getRendimentos());
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