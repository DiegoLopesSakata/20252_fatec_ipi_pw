package com.fatec.PetStop;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int codigo;
    private String nome;
    private String email;
    private String senha;
    private String documento;
    private String telefone;
    private String logradouro;
    private String cidade;
    private String cep;
    private String complemento;
    private int ativo;
    
}
