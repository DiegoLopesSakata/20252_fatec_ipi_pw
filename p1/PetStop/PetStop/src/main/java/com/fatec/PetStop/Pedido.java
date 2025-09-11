package com.fatec.PetStop;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Pedido {
    @Id
    private int codigo;
    private int codigoCliente;
    private String cep;
    private String endereco;


}
