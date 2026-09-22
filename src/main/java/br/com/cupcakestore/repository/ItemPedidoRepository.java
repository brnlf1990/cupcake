package br.com.cupcakestore.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import br.com.cupcakestore.entity.ItemPedido;
import br.com.cupcakestore.entity.Pedido;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {
    List<ItemPedido> findByPedido(Pedido pedido);
}
