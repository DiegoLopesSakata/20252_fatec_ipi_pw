package com.fatec.PetStop;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class ClienteController {
    @Autowired
    private ClienteRepository bd;
    @Autowired
    LojaService service;

    @PostMapping("/api/cliente") 
    public void gravar(@RequestBody Cliente obj){
        bd.save(obj);
        System.out.println("Cliente gravado com sucesso!");
        String msg = service.enviarEmail(obj.getEmail(), "Cadastro no PetStop!", "Cadastro efetuado com sucesso no PetStop!\n Seja bem vindo(a)!");
    }

    @PutMapping("/api/cliente")
    public void alterar(@RequestBody Cliente obj){
        if(bd.existsById(obj.getCodigo())){
            bd.save(obj);
            System.out.println("Cliente alterado com sucesso!");
        }
        else{
            System.out.println("Nao existe um cliente com esse codigo!");
        }

    }

    @GetMapping("/api/cliente/{codigo}")
    public Cliente carregar(@PathVariable("codigo") int id){
        if(bd.existsById(id)){
            System.out.println("Cliente encontrado!");
            return bd.findById(id).get();
        } else {
            return new Cliente();
        }
    }

    @DeleteMapping("/api/cliente/{codigo}")
    public void remover(@PathVariable("codigo") int id){
        if(bd.existsById(id)){
            bd.deleteById(id);
            System.out.println("cliente removido com sucesso!");
        } else {
            System.out.println("Cliente nao encontrado!");
        }
    }

    @GetMapping("/api/clientes")
    public List<Cliente> listar(){ return bd.findAll();}

    @PostMapping("/api/cliente/login")
    public Cliente fazerLogin(@RequestBody Cliente obj){
       Optional<Cliente> retorno 
       =  bd.fazerLogin(obj.getEmail(), obj.getSenha());
       if(retorno.isPresent()){
            System.out.println("Login efetuado com sucesso!");
            String msg = service.enviarEmail(obj.getEmail(), "Login no petshop!", "Login efetuado com sucesso!");
            return retorno.get();
       } else {
            return new Cliente();
       }
    }

    

}
