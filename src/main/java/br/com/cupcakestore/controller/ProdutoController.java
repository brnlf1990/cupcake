package br.com.cupcakestore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.cupcakestore.entity.Produto;
import br.com.cupcakestore.service.ProdutoService;
import jakarta.validation.Valid;

@Controller
public class ProdutoController {
    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping("/produtos")
    public String listarProdutos(Model model) {
        model.addAttribute("produtos", produtoService.listarTodos());
        return "produtos";
    }

    @GetMapping("/admin/produtos")
    public String listarProdutosAdmin(Model model) {
        if (!model.containsAttribute("produto")) {
            model.addAttribute("produto", new Produto());
        }
        model.addAttribute("produtos", produtoService.listarTodos());
        return "admin-produtos";
    }

    @PostMapping("/admin/produtos/salvar")
    public String salvarProduto(@Valid @ModelAttribute("produto") Produto produto,
                                BindingResult result,
                                Model model,
                                RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("produtos", produtoService.listarTodos());
            return "admin-produtos";
        }
        produtoService.salvar(produto);
        redirectAttributes.addFlashAttribute("sucesso", "Produto salvo com sucesso.");
        return "redirect:/admin/produtos";
    }

    @GetMapping("/admin/produtos/editar/{id}")
    public String editarProduto(@PathVariable Long id,
                                RedirectAttributes redirectAttributes) {
        return produtoService.buscarPorId(id)
            .map(produto -> {
                redirectAttributes.addFlashAttribute("produto", produto);
                return "redirect:/admin/produtos";
            })
            .orElseGet(() -> {
                redirectAttributes.addFlashAttribute("erro", "Produto não encontrado.");
                return "redirect:/admin/produtos";
            });
    }

    @GetMapping("/admin/produtos/excluir/{id}")
    public String excluirProduto(@PathVariable Long id,
                                 RedirectAttributes redirectAttributes) {
        if (produtoService.buscarPorId(id).isEmpty()) {
            redirectAttributes.addFlashAttribute("erro", "Produto não encontrado.");
            return "redirect:/admin/produtos";
        }
        produtoService.excluir(id);
        redirectAttributes.addFlashAttribute("sucesso", "Produto excluído com sucesso.");
        return "redirect:/admin/produtos";
    }
}
