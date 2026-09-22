package br.com.cupcakestore.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import br.com.cupcakestore.entity.Cliente;
import br.com.cupcakestore.entity.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByClienteOrderByDataPedidoDesc(Cliente cliente);
    List<Pedido> findAllByOrderByDataPedidoDesc();
}
