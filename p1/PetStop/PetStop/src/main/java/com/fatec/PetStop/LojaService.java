package com.fatec.PetStop;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

@Service
public class LojaService {
    @Autowired
    private JavaMailSender conta;

    public String enviaEmailHTML(String to, String assunto, String html){
        try{
            MimeMessage mimeMessage = conta.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(html, true);
            helper.setTo(to);
            helper.setSubject(assunto);
            helper.setFrom("petstop82@gmail.com");
            conta.send(mimeMessage);
            return "Email enviado com sucesso!";
        }
        catch(Exception err){
            return "Ocorreu um erro durante o envio do email !" + err.getMessage();
        }

    }

    public String md5(String p){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] mensagemDigest = md.digest(p.getBytes());
            BigInteger numero = new BigInteger(1, mensagemDigest);
            String hashTexto = numero.toString(16);

            while (hashTexto.length() < 32) {
                hashTexto = "0" + hashTexto;
            }

            return hashTexto;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

    }

}
