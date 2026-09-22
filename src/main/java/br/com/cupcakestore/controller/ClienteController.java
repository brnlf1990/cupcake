package br.com.cupcakestore.controller;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import br.com.cupcakestore.entity.Cliente;
import br.com.cupcakestore.service.ClienteService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class ClienteController {
    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping("/cadastro")
    public String exibirCadastro(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "cadastro";
    }

    @PostMapping("/cadastro")
    public String cadastrar(@Valid @ModelAttribute("cliente") Cliente cliente,
                            BindingResult result,
                            Model model) {
        if (result.hasErrors()) return "cadastro";
        if (clienteService.emailJaCadastrado(cliente.getEmail())) {
            model.addAttribute("erro", "Já existe um cliente cadastrado com este e-mail.");
            return "cadastro";
        }
        clienteService.salvar(cliente);
        model.addAttribute("sucesso", "Cadastro realizado com sucesso!");
        model.addAttribute("cliente", new Cliente());
        return "cadastro";
    }

    @GetMapping("/login")
    public String exibirLogin(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "login";
    }

    @PostMapping("/login")
    public String realizarLogin(@ModelAttribute Cliente cliente,
                                HttpSession session,
                                Model model) {
        Optional<Cliente> clienteLogado = clienteService.realizarLogin(cliente.getEmail(), cliente.getSenha());
        if (clienteLogado.isPresent()) {
            session.setAttribute("clienteLogado", clienteLogado.get());
            return "redirect:/";
        }
        model.addAttribute("erro", "E-mail ou senha inválidos.");
        model.addAttribute("cliente", new Cliente());
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
