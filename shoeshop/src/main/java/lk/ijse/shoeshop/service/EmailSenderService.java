package lk.ijse.shoeshop.service;

public interface EmailSenderService {
    void sendEmail(String email, String subject, String body);
}
