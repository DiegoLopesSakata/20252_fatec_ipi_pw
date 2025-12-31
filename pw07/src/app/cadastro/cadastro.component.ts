import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
 
// [Opcional] Interface para definir a estrutura do cliente
interface Cliente {
  id: number;
  nome: string;
  email: string;
  telefone: string;
}

@Component({
  selector: 'app-contato', // Note que o seletor continua 'app-contato'
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './cadastro.html',
  styleUrl: './cadastro.css'
})
export class CadastroComponent {
    // CORREÇÃO 1: Definição do objeto 'cliente' para vincular o formulário
    cliente: Cliente = {
        id: 0,
        nome: '',
        email: '',
        telefone: ''
    };
    
    msg:string = ""; // Variável para feedback

    enviar(){
        // Verifica se os campos obrigatórios estão preenchidos
        if (!this.cliente.nome || !this.cliente.email || !this.cliente.telefone) {
            this.msg = "Por favor, preencha todos os campos obrigatórios.";
            return;
        }

        // Lógica para salvar no localStorage
        
        // 1. Tenta buscar a lista de clientes salva (chave 'clientes')
        const clientesJSON = localStorage.getItem('clientes');
        let clientes: Cliente[] = clientesJSON ? JSON.parse(clientesJSON) : [];

        // 2. Gera um ID simples
        const proximoId = clientes.length > 0 ? clientes[clientes.length - 1].id + 1 : 1;
        this.cliente.id = proximoId;

        // 3. Adiciona o novo cliente (usando uma cópia para garantir a integridade)
        clientes.push({ ...this.cliente }); 

        // 4. Salva a lista completa no localStorage
        localStorage.setItem('clientes', JSON.stringify(clientes));

        // 5. Feedback e limpeza
        this.msg = `Cliente ${this.cliente.nome} cadastrado com sucesso!`;
        
        // Reseta o objeto para limpar os campos da tela
        this.cliente = {
            id: 0,
            nome: '',
            email: '',
            telefone: ''
        };
    }
}