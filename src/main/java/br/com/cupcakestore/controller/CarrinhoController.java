package br.com.cupcakestore.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.cupcakestore.entity.ItemPedido;
import br.com.cupcakestore.entity.Produto;
import br.com.cupcakestore.service.ProdutoService;
import jakarta.servlet.http.HttpSession;

@Controller
public class CarrinhoController {
    private final ProdutoService produtoService;

    public CarrinhoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @SuppressWarnings("unchecked")
    private List<ItemPedido> obterCarrinho(HttpSession session) {
        List<ItemPedido> carrinho = (List<ItemPedido>) session.getAttribute("carrinho");
        if (carrinho == null) {
            carrinho = new ArrayList<>();
            session.setAttribute("carrinho", carrinho);
        }
        return carrinho;
    }

    @GetMapping("/carrinho")
    public String visualizarCarrinho(Model model, HttpSession session) {
        List<ItemPedido> carrinho = obterCarrinho(session);
        BigDecimal total = carrinho.stream()
            .map(ItemPedido::getSubtotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        model.addAttribute("itens", carrinho);
        model.addAttribute("total", total);
        return "carrinho";
    }

    @GetMapping("/carrinho/adicionar/{id}")
    public String adicionarProduto(@PathVariable Long id,
                                   HttpSession session,
                                   RedirectAttributes redirectAttributes) {
        Produto produto = produtoService.buscarPorId(id).orElse(null);
        if (produto == null) {
            redirectAttributes.addFlashAttribute("erro", "Produto não encontrado.");
            return "redirect:/produtos";
        }
        if (produto.getEstoque() == null || produto.getEstoque() <= 0) {
            redirectAttributes.addFlashAttribute("erro", "Produto sem estoque.");
            return "redirect:/produtos";
        }

        List<ItemPedido> carrinho = obterCarrinho(session);
        for (ItemPedido item : carrinho) {
            if (item.getProduto().getId().equals(produto.getId())) {
                if (item.getQuantidade() < produto.getEstoque()) {
                    item.setQuantidade(item.getQuantidade() + 1);
                    redirectAttributes.addFlashAttribute("sucesso", "Quantidade atualizada no carrinho.");
                } else {
                    redirectAttributes.addFlashAttribute("erro", "Quantidade máxima em estoque atingida.");
                }
                return "redirect:/carrinho";
            }
        }

        ItemPedido item = new ItemPedido();
        item.setProduto(produto);
        item.setQuantidade(1);
        item.setPrecoUnitario(produto.getPreco());
        carrinho.add(item);
        redirectAttributes.addFlashAttribute("sucesso", "Produto adicionado ao carrinho.");
        return "redirect:/carrinho";
    }

    @GetMapping("/carrinho/remover/{indice}")
    public String removerProduto(@PathVariable int indice,
                                 HttpSession session,
                                 RedirectAttributes redirectAttributes) {
        List<ItemPedido> carrinho = obterCarrinho(session);
        if (indice >= 0 && indice < carrinho.size()) {
            carrinho.remove(indice);
            redirectAttributes.addFlashAttribute("sucesso", "Produto removido do carrinho.");
        }
        return "redirect:/carrinho";
    }
}
