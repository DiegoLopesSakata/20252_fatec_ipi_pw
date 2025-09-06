package com.fatec.loja;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class LojaService {
    @Autowired
    private JavaMailSender conta;

    public String enviarEmail(String to, String assunto, String corpo){
        try{
            SimpleMailMessage mensagem = new SimpleMailMessage();
            mensagem.setFrom("dihsakata@gmail.com");
            mensagem.setTo(to);
            mensagem.setSubject(assunto);
            mensagem.setText(corpo);
            conta.send(mensagem);
            return "Email enviado com sucesso!";
        }
        catch(Exception err){
            err.printStackTrace();
            return "Ocorreu um erro durante o envio do email !" + err.getMessage();
        }

    }



}
