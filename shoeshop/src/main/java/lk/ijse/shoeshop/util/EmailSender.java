package lk.ijse.shoeshop.util;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lk.ijse.shoeshop.dto.EmailDTO;
import lk.ijse.shoeshop.service.EmailSenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@RequiredArgsConstructor
@Slf4j
public class EmailSender {
    private final EmailSenderService emailSenderService;

    @Scheduled(cron = "0 10 09 * * ?", zone = "Asia/Colombo")
    public void scheduleDailyEmail() {
        List<EmailDTO> mails = emailSenderService.getMails();
        for (EmailDTO emailDTO : mails){
            String email = emailDTO.getEmail();
            String subject = "Happy Birthday from Hello Shoes!";
            String body = createBirthdayEmailBody(emailDTO.getName());
            emailSenderService.sendEmail(email, subject, body);
            log.info("Email sent successfully at 09:10 AM Sri Lanka time");
        }
    }

    private String createBirthdayEmailBody(String customerName) {
        return String.format(
                "Happy Birthday, %s!\n\n" +
                        "Dear %s,\n\n" +
                        "We at Hello Shoes wish you a very happy birthday! Thank you for being a valued customer. We appreciate your loyalty and support.\n\n" +
                        "As a token of our appreciation, we are pleased to offer you a special discount on your next purchase. Use the code BIRTHDAY20 to get 20%% off.\n\n" +
                        "Enjoy your special day!\n\n" +
                        "Best wishes,\n" +
                        "The Hello Shoes Team",
                customerName, customerName
        );
    }


}
