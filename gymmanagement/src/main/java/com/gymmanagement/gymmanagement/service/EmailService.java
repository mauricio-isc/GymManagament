package com.gymmanagement.gymmanagement.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    public void sendEmail(String to, String subject, String message){
        //En la implementacion real, aqui ya se estaria integrando un servicio de email con SendGrid o AWS SES
        logger.info("Enviando email a: {}", to);
        logger.info("Asunto: {}", subject);
        logger.info("Mensaje: {}", message);

        //Simulacion de envio de email
        System.out.println("=== EMAIL SIMULADO ===");
        System.out.println("Para: " + to);
        System.out.println("Asunto: " + subject);
        System.out.println("Mensaje: " + message);
        System.out.println("=================");
    }

}
