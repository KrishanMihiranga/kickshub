package lk.ijse.shoeshop.api;

import lk.ijse.shoeshop.service.EmailSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("api/v1/email")
@RequiredArgsConstructor
public class EmailSender {
    private final EmailSenderService emailSenderService;

    @PostMapping
    public void sendMail(){
        emailSenderService.sendEmail("mkrishan2003@gmail.com","This is subject", "This is body of the email");
    }
}
