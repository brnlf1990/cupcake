package br.com.cupcakestore.entity;

import java.math.BigDecimal;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Entity
@Table(name = "produto")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do cupcake é obrigatório.")
    @Column(nullable = false, length = 100)
    private String nome;

    @Column(length = 255)
    private String descricao;

    @NotNull(message = "O preço é obrigatório.")
    @DecimalMin(value = "0.01", message = "O preço deve ser maior que zero.")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal preco;

    @NotNull(message = "O estoque é obrigatório.")
    @PositiveOrZero(message = "O estoque não pode ser negativo.")
    @Column(nullable = false)
    private Integer estoque;

    @Column(length = 255)
    private String imagem;

    public Produto() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public BigDecimal getPreco() { return preco; }
    public void setPreco(BigDecimal preco) { this.preco = preco; }
    public Integer getEstoque() { return estoque; }
    public void setEstoque(Integer estoque) { this.estoque = estoque; }
    public String getImagem() { return imagem; }
    public void setImagem(String imagem) { this.imagem = imagem; }
}
