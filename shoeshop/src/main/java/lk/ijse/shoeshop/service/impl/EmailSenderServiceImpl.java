package lk.ijse.shoeshop.service.impl;

import lk.ijse.shoeshop.dto.EmailDTO;
import lk.ijse.shoeshop.entity.CustomerEntity;
import lk.ijse.shoeshop.repo.CustomerRepo;
import lk.ijse.shoeshop.service.EmailSenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailSenderServiceImpl implements EmailSenderService {
    private final JavaMailSender mailSender;
    private final CustomerRepo customerRepo;

    @Override
    public void sendEmail(String email, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
        System.out.println("Email sent successfully to: " + email);
    }

    @Override
    public List<EmailDTO> getMails() {
        List<EmailDTO> mails = new ArrayList<>();

        List<CustomerEntity> customersWithTodayBirthday = customerRepo.findCustomersWithTodayBirthday();
        for (CustomerEntity customerEntity : customersWithTodayBirthday) {
            EmailDTO emailDTO = new EmailDTO();
            emailDTO.setEmail(customerEntity.getEmail());
            emailDTO.setName(customerEntity.getName());
            mails.add(emailDTO);
        }
        return mails;
    }
}