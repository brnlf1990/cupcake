package br.com.cupcakestore.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.cupcakestore.entity.Cliente;
import br.com.cupcakestore.entity.ItemPedido;
import br.com.cupcakestore.entity.Pedido;
import br.com.cupcakestore.service.PedidoService;
import jakarta.servlet.http.HttpSession;

@Controller
public class PedidoController {
    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @SuppressWarnings("unchecked")
    private List<ItemPedido> obterCarrinho(HttpSession session) {
        List<ItemPedido> carrinho = (List<ItemPedido>) session.getAttribute("carrinho");
        return carrinho == null ? new ArrayList<>() : carrinho;
    }

    @GetMapping("/pedido")
    public String visualizarPedido(Model model, HttpSession session) {
        List<ItemPedido> carrinho = obterCarrinho(session);
        BigDecimal total = carrinho.stream()
            .map(ItemPedido::getSubtotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        model.addAttribute("itens", carrinho);
        model.addAttribute("total", total);
        return "pedido";
    }

    @PostMapping("/pedido/finalizar")
    public String finalizarPedido(HttpSession session,
                                  RedirectAttributes redirectAttributes) {
        Cliente cliente = (Cliente) session.getAttribute("clienteLogado");
        if (cliente == null) {
            redirectAttributes.addFlashAttribute("erro", "Faça login para finalizar o pedido.");
            return "redirect:/login";
        }

        List<ItemPedido> carrinho = obterCarrinho(session);
        if (carrinho.isEmpty()) {
            redirectAttributes.addFlashAttribute("erro", "O carrinho está vazio.");
            return "redirect:/carrinho";
        }

        Pedido pedido = pedidoService.criarPedido(cliente, carrinho);
        session.removeAttribute("carrinho");
        redirectAttributes.addFlashAttribute("sucesso", "Pagamento simulado com sucesso. Pedido #" + pedido.getId() + " registrado.");
        return "redirect:/pedido";
    }

    @GetMapping("/admin/pedidos")
    public String listarPedidos(Model model) {
        model.addAttribute("pedidos", pedidoService.listarTodos());
        return "admin-pedidos";
    }

    @PostMapping("/admin/pedidos/status/{id}")
    public String alterarStatus(@PathVariable Long id,
                                @RequestParam String status,
                                RedirectAttributes redirectAttributes) {
        if (pedidoService.alterarStatus(id, status)) {
            redirectAttributes.addFlashAttribute("sucesso", "Status atualizado com sucesso.");
        } else {
            redirectAttributes.addFlashAttribute("erro", "Pedido não encontrado ou status inválido.");
        }
        return "redirect:/admin/pedidos";
    }
}
