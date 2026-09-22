package br.com.cupcakestore.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.cupcakestore.entity.Cliente;
import br.com.cupcakestore.entity.ItemPedido;
import br.com.cupcakestore.entity.Pedido;
import br.com.cupcakestore.repository.PedidoRepository;

@Service
public class PedidoService {
    private static final Set<String> STATUS_VALIDOS = Set.of(
        "Recebido", "Em preparo", "Saiu para entrega", "Entregue"
    );

    private final PedidoRepository pedidoRepository;

    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public List<Pedido> listarTodos() {
        return pedidoRepository.findAllByOrderByDataPedidoDesc();
    }

    public List<Pedido> listarPorCliente(Cliente cliente) {
        return pedidoRepository.findByClienteOrderByDataPedidoDesc(cliente);
    }

    @Transactional
    public Pedido criarPedido(Cliente cliente, List<ItemPedido> itens) {
        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setDataPedido(LocalDateTime.now());
        pedido.setStatus("Recebido");

        BigDecimal total = BigDecimal.ZERO;
        for (ItemPedido item : itens) {
            pedido.adicionarItem(item);
            total = total.add(item.getSubtotal());
        }
        pedido.setValorTotal(total);
        return pedidoRepository.save(pedido);
    }

    @Transactional
    public boolean alterarStatus(Long id, String status) {
        if (!STATUS_VALIDOS.contains(status)) return false;
        return pedidoRepository.findById(id).map(pedido -> {
            pedido.setStatus(status);
            pedidoRepository.save(pedido);
            return true;
        }).orElse(false);
    }
}
