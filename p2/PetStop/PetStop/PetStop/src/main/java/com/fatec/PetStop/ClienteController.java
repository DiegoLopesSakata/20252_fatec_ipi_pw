package com.fatec.PetStop;

import java.util.List;
import java.util.Optional;
import java.util.Map;

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
    private LojaService util;

    @PostMapping("/api/cliente") 
    public void gravar(@RequestBody Cliente obj){
        obj.setSenha(util.md5(obj.getSenha()));
        bd.save(obj);
        String email = "<b>Email de confirmação de cadastro</b><br><br>" +
                    "seja bem vindo, "+ obj.getNome() + ", clique no link abaixo para "+
                    "confirmar o seu cadastro.<br><br>"+
                    "<a href='http://localhost:8080/api/cliente/efetivar/"+ obj.getCodigo() +"'>Clique aqui</a>";
        String retorno = util.enviaEmailHTML(obj.getEmail(), "Confirmação de novo cadastro", email);
        System.out.println("Cliente gravado com sucesso! "+ retorno);
    }

    @PutMapping("/api/cliente")
    public void alterar(@RequestBody Cliente obj){
        Optional<Cliente> opt = bd.findById(obj.getCodigo());
        if(opt.isPresent()){
            obj.setSenha(util.md5(obj.getSenha()));
            bd.save(obj);
            System.out.println("Cliente alterado com sucesso!");
        } else {
            System.out.println("Cliente não encontrado!");
        }
    }

    @GetMapping("/api/cliente/{codigo}")
    public Cliente carregar(@PathVariable("codigo") int id){
        Optional<Cliente> opt = bd.findById(id);
        if(opt.isPresent()){
            System.out.println("Cliente encontrado!");
            return bd.findById(id).get();
        } else {
            System.out.println("Cliente não encontrado!");
            return new Cliente();
        }
    }

    @DeleteMapping("/api/cliente/{codigo}")
    public void remover(@PathVariable("codigo") int id){
        Optional<Cliente> opt = bd.findById(id);
        if(opt.isPresent()){
            bd.deleteById(id);
            System.out.println("cliente removido com sucesso!");
        } else {
            System.out.println("Cliente não encontrado!");
        }
    }

    @GetMapping("/api/clientes")
    public List<Cliente> listar(){ return bd.findAll();}

    @PostMapping("/api/cliente/login")
    public Cliente fazerLogin(@RequestBody Cliente obj){
        obj.setSenha(util.md5(obj.getSenha()));
        Optional<Cliente> retorno =  bd.fazerLogin(obj.getEmail(), obj.getSenha());
        if(retorno.isPresent()){
             System.out.println("Login efetuado com sucesso!");
            return retorno.get();
        } else {
            System.out.println("Email ou senha incorretos!");
            return new Cliente();
       }
    }

    @GetMapping("/api/cliente/efetivar/{codigo}")
    public void efetivar(@PathVariable("codigo") int codigo){
        Optional<Cliente> opt = bd.findById(codigo);
        if(opt.isPresent()){
            Cliente obj = opt.get();
            obj.setAtivo(1);
            bd.save(obj);
            System.out.println("Cliente liberado com sucesso!");
        } else {
            System.out.println("Cliente não encontrado!");
        }
    }

    @PostMapping("/api/cliente/alterarSenha/{codigo}")
    public void alterarSenha(@PathVariable("codigo") int codigo, @RequestBody Map<String, String> body){
        String senha = body.get("senha");

        Optional<Cliente> opt = bd.findById(codigo);
        if(opt.isPresent()){
            Cliente obj = bd.findById(codigo).get();
            String email = "<b>Email de alteração de senha</b><br><br>" +
                    "Olá, "+ obj.getNome() + ", clique no link abaixo para "+
                    "confirmar a alteração da sua senha.<br><br>"+
                    "<a href='http://localhost:8080/api/cliente/setSenha/"+ obj.getCodigo() +"/" + senha + "'>Clique aqui</a>";
            String retorno = util.enviaEmailHTML(obj.getEmail(), "Confirmação de alteração de senha", email);
            System.out.println("senha alterada com sucesso! "+ retorno);
        } else {
            System.out.println("Cliente não encontrado!");
        }
    }

    @GetMapping("/api/cliente/setSenha/{codigo}/{senha}")
    public void setSenha(@PathVariable("codigo") int codigo, @PathVariable("senha") String senha){
         Cliente obj = bd.findById(codigo).get();
         obj.setSenha(util.md5(senha));
         bd.save(obj);
    }
}
