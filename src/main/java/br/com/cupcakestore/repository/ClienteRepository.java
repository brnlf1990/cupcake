package br.com.cupcakestore.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import br.com.cupcakestore.entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByEmail(String email);
    Optional<Cliente> findByEmailAndSenha(String email, String senha);
    boolean existsByEmail(String email);
}
