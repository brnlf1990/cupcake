package br.com.cupcakestore.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import br.com.cupcakestore.entity.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    List<Produto> findByNomeContainingIgnoreCase(String nome);
    List<Produto> findByAtivoTrue();
}
