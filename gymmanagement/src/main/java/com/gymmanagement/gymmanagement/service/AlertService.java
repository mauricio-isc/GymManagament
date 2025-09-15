package com.gymmanagement.gymmanagement.service;

import com.gymmanagement.gymmanagement.model.Membership;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AlertService {

    @Autowired
    private MembershipService membershipService;

    @Autowired
    private EmailService emailService;

    //verificar membresias que experiran en 3 dias
    @Scheduled(cron = "0 0 9 * * ?") //Se ejecutara todos los dias a las 9:00 AM
    public void checkExpiringMemberships(){
        LocalDate threeDaysFromNow = LocalDate.now().plusDays(3);
        List<Membership> expiringMemberships = membershipService.getExpiringMemberships(3);

        for (Membership membership : expiringMemberships){
            if (membership.getStatus().equals("ACTIVATE")){
                sendExpirationAlert(membership);
            }
        }
    }

    //Verificar membresias que ya expiraron
    @Scheduled(cron = "0 0 8 * * ?")    //Se ejecuta todos los dias alas 9:00 AM
    public void chechExpiredMembreships(){
        membershipService.checkAndUpdateExpiredMemberships();
    }

    private void sendExpirationAlert(Membership membership){
        String subject = "Tu membresia esta por vencer";
        String message = String.format(
                "Hola %s, \n\nTu membresia %s venvera el %s. Por favor, renueva tu membresia para continuar disfrutando de nuestros servicios. \n\nSaludos,\nEl equipo de gimnacio",
                        membership.getMember().getName(),
                        membership.getPlanType(),
                        membership.getEndDate()
        );

        emailService.sendEmail(membership.getMember().getEmail(), subject, message);
    }
}
