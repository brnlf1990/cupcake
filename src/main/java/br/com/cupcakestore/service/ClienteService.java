package br.com.cupcakestore.service;

import java.util.Optional;
import org.springframework.stereotype.Service;
import br.com.cupcakestore.entity.Cliente;
import br.com.cupcakestore.repository.ClienteRepository;

@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public boolean emailJaCadastrado(String email) {
        return clienteRepository.existsByEmail(email);
    }

    public Cliente salvar(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public Optional<Cliente> realizarLogin(String email, String senha) {
        return clienteRepository.findByEmailAndSenha(email, senha);
    }
}
