package com.fatec.PetStop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.ArrayList;

@RestController
@CrossOrigin(origins = "*")
public class PedidoController {
    @Autowired
    private PedidoRepository bd;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private LojaService util;
    
    @PostMapping("/api/pedido")
    public Pedido gravar(@RequestBody Map<String, Object> pedidoData){
        Integer codigoCliente = (Integer) pedidoData.get("codigoCliente");
        List<Integer> codigosProdutos = (List<Integer>) pedidoData.get("codigosProdutos");
        String enderecoEntrega = (String) pedidoData.get("enderecoEntrega");
        
        Optional<Cliente> clienteOpt = clienteRepository.findById(codigoCliente);
        if(!clienteOpt.isPresent()){
            System.out.println("Cliente não encontrado!");
            return new Pedido();
        }
        Cliente cliente = clienteOpt.get();
        
        List<Produto> produtos = new ArrayList<>();
        if(codigosProdutos != null && !codigosProdutos.isEmpty()){
            produtos = bd.buscarProdutosPorIds(codigosProdutos);
        }
        
        double valorTotal = 0;
        for(Produto produto : produtos){
            valorTotal += produto.getValor();
        }
        
        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setProdutos(produtos);
        pedido.setEnderecoEntrega(enderecoEntrega);
        pedido.setValorTotal(valorTotal);
        
        Pedido pedidoSalvo = bd.save(pedido);
        
        String email = "<b>Confirmação de Pedido</b><br><br>" +
                "Olá, " + cliente.getNome() + ", seu pedido foi registrado com sucesso!<br><br>" +
                "Número do pedido: " + pedidoSalvo.getCodigo() + "<br>" +
                "Valor total: R$ " + String.format("%.2f", valorTotal) + "<br><br>" +
                "Obrigado por comprar em nossa loja!";
        String retorno = util.enviaEmailHTML(cliente.getEmail(), "Confirmação do Pedido #" + pedidoSalvo.getCodigo(), email);
        
        System.out.println("Pedido gravado com sucesso! " + retorno);
        return pedidoSalvo;
    }

    @PutMapping("/api/pedido")
    public void alterar(@RequestBody Pedido obj){
        Optional<Pedido> opt = bd.findById(obj.getCodigo());
        if(opt.isPresent()){
            bd.save(obj);
            System.out.println("Pedido alterado com sucesso!");
            
        } else {
            System.out.println("Pedido não encontrado!");
        }
    }

    @DeleteMapping("/api/pedido/{codigo}")
    public void remover(@PathVariable int codigo){
        Optional<Pedido> opt = bd.findById(codigo);
        if(opt.isPresent()){
            bd.deleteById(codigo);
            System.out.println("Pedido removido com sucesso!");
        } else {
            System.out.println("Pedido não encontrado!");
        }
    }

    @GetMapping("/api/pedido/{codigo}")
    public Pedido buscarPorCodigo(@PathVariable int codigo){
        Optional<Pedido> opt = bd.findById(codigo);
        if(opt.isPresent()){
            System.out.println("Pedido encontrado!");
            return opt.get();
        } else {
            System.out.println("Pedido não encontrado!");
            return new Pedido();
        }
    }
    
    @GetMapping("/api/pedido/cliente/{codigoCliente}")
    public List<Pedido> listarPorCliente(@PathVariable int codigoCliente){
        return bd.buscarPorCliente(codigoCliente);
    }

    @GetMapping("/api/pedidos")
    public List<Pedido> listar(){ 
        return bd.findAll();
    }
}