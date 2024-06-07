package lk.ijse.shoeshop.service;

import lk.ijse.shoeshop.dto.EmailDTO;

import java.util.List;


public interface EmailSenderService {


    void sendEmail(String email, String subject, String body);
    List<EmailDTO> getMails();
}
