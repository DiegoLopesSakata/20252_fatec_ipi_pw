package com.fatec.PetStop;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int codigo;

    @ManyToOne
    @JoinColumn(name = "codigo_cliente", referencedColumnName = "codigo")
    private Cliente cliente;
    
    @ManyToMany
    @JoinTable(
        name = "pedido_produto",
        joinColumns = @JoinColumn(name = "codigo_pedido"),
        inverseJoinColumns = @JoinColumn(name = "codigo_produto")
    )
    private List<Produto> produtos;
    
    private LocalDateTime dataPedido;
    private String enderecoEntrega;
    private double valorTotal;
    private String status; // Ex.: "Em processamento", "Enviado", "Entregue"
    
    public Pedido() {
        this.dataPedido = LocalDateTime.now();
        this.status = "Em processamento";
    }
}