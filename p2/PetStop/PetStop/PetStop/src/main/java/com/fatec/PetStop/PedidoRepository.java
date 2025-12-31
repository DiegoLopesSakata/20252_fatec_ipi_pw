package com.fatec.PetStop;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

    @Query(value = "select * from pedido where codigo_cliente = ?1", 
    nativeQuery = true)
    public List<Pedido> buscarPorCliente(Integer id);
    
    @Query(value = "select * from cliente where codigo = ?1",
    nativeQuery = true)
    public Cliente buscarClienteId(Integer id);
    
    @Query(value = "select * from produto where codigo in ?1",
    nativeQuery = true)
    public List<Produto> buscarProdutosPorIds(List<Integer> codigos);
}