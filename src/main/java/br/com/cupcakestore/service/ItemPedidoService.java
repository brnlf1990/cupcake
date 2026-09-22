package br.com.cupcakestore.service;

import org.springframework.stereotype.Service;
import br.com.cupcakestore.repository.ItemPedidoRepository;

@Service
public class ItemPedidoService {
    private final ItemPedidoRepository itemPedidoRepository;

    public ItemPedidoService(ItemPedidoRepository itemPedidoRepository) {
        this.itemPedidoRepository = itemPedidoRepository;
    }
}
