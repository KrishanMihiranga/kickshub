package lk.ijse.shoeshop.util;

import lk.ijse.shoeshop.service.EmailSenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
@Slf4j
public class EmailSender {
    private final EmailSenderService emailSenderService;

    @Scheduled(cron = "0 23 12 * * ?", zone = "Asia/Colombo")
    public void scheduleDailyEmail() {
        String email = "mkrishan2003@gmail.com";
        String subject = "Happy Birthday from [Company Name]!";
        String body = createBirthdayEmailBody("Krishan");

        emailSenderService.sendEmail(email, subject, body);
        log.info("Email sent successfully at 12:15 PM Sri Lanka time");
    }

    private void getMails(){

    }

    private String createBirthdayEmailBody(String customerName) {
        return String.format(
                "Happy Birthday, %s!\n\n" +
                        "Dear %s,\n\n" +
                        "We at [Company Name] wish you a very happy birthday! Thank you for being a valued customer. We appreciate your loyalty and support.\n\n" +
                        "As a token of our appreciation, we are pleased to offer you a special discount on your next purchase. Use the code BIRTHDAY20 to get 20%% off.\n\n" +
                        "Enjoy your special day!\n\n" +
                        "Best wishes,\n" +
                        "The [Company Name] Team",
                customerName, customerName
        );
    }
}
