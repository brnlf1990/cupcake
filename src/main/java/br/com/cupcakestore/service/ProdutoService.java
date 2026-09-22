package br.com.cupcakestore.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import br.com.cupcakestore.entity.Produto;
import br.com.cupcakestore.repository.ProdutoRepository;

@Service
public class ProdutoService {
    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public List<Produto> listarTodos() { return produtoRepository.findAll(); }
    public Optional<Produto> buscarPorId(Long id) { return produtoRepository.findById(id); }
    public Produto salvar(Produto produto) { return produtoRepository.save(produto); }
    public void excluir(Long id) { produtoRepository.deleteById(id); }
}
