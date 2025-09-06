package com.fatec.loja;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
// import org.springframework.web.bind.annotation.RequestParam;



@RestController
@CrossOrigin(origins = "*")
public class ProdutoController {
    @Autowired
    private ProdutoRepository bd;
    @Autowired
    LojaService service;

    @PostMapping("/api/produto")
    public void gravar(@RequestBody Produto obj) {
        bd.save(obj);
        System.out.println("Produto gravado com sucesso!");
    }

    @PutMapping("/api/produto")
    public void alterar(@RequestBody Produto obj) {
        bd.save(obj);
        System.out.println("Produto gravado com sucesso!");
    }
    
    @GetMapping("/api/produto/{codigo}")
    public Produto carregar(@PathVariable("codigo") int codigo){
        if(bd.existsById(codigo)) return bd.findById(codigo).get();
        else return new Produto();
    }
    @DeleteMapping("/api/produto/{codigo}")
    public void apagar(@PathVariable("codigo") int codigo){
        bd.deleteById(codigo);
        System.out.println("Produto removido com sucesso!");
    }

    @GetMapping("/api/produto/vitrine")
    public List<Produto> carregarVitrine(){
        return bd.listarVitrine();
    }

    @GetMapping("/api/produto/busca/{termo}")
    public List<Produto> fazerBusca(@PathVariable("termo") String termo) {
        return bd.fazerBusca("%" + termo + "%");
    }

    @PutMapping("/api/produto/testeEmail")
    public void enviarEmail() {
        String msg = service.enviarEmail(" ", " ", " ");
        System.out.println(msg);
    }
}
