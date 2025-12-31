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
public class ProdutoController {
    @Autowired
    ProdutoRepository bd;

    @PostMapping("/api/produto")
    public void gravar(@RequestBody Produto obj){
        bd.save(obj);
        System.out.println("Produto gravado com sucesso!");
    }

    @GetMapping("/api/produto/{codigo}")
    public Produto carregar(@PathVariable("codigo") int c){
        Optional<Produto> opt = bd.findById(c);
        if(opt.isPresent()){
            System.out.println("Produto encontrado!");
            return bd.findById(c).get();
        } else {
            System.out.println("Produto nao existe!");
            return new Produto();
        }
    }
    
    @PutMapping("/api/produto")
    public void alterar(@RequestBody Produto obj){
        Optional<Produto> opt = bd.findById(obj.getCodigo());
        if(opt.isPresent()){
            bd.save(obj);
            System.out.println("Produto alterado com sucesso");
        } else {
            System.out.println("Produto nao existe!");
        }
    }

    @DeleteMapping("/api/produto/{codigo}")
    public void remover(@PathVariable("codigo") int codigo){
        Optional<Produto> opt = bd.findById(codigo);
        if(opt.isPresent()){
            bd.deleteById(codigo);
            System.out.println("Produto removido com sucesso!");
        } else {
            System.out.println("Produto nao existe!");
        }
    }

    @GetMapping("/api/produtos")
    public List<Produto> listar(){ return bd.findAll();}

    @GetMapping("/api/produtos/vitrine")
    public List<Produto> carregarVitrine(){
        return bd.listarVitrine();
    }

    @GetMapping("/api/produtos/busca/{termo}")
    public List<Produto> fazerBuscar(@PathVariable("termo") String termo){
        return bd.fazerBusca("%"+ termo + "%");
    }

}
