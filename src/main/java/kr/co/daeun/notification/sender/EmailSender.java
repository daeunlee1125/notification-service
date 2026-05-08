package kr.co.daeun.notification.sender;

import kr.co.daeun.notification.dto.NotificationDTO;
import kr.co.daeun.notification.type.ChannelType;
import kr.co.daeun.notification.type.NotificationSendResult;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailSender implements NotificationSender {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Override
    public ChannelType getChannelType() {
        return ChannelType.EMAIL;
    }

    @Override
    public NotificationSendResult send(NotificationDTO notification) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(notification.getRecipientValue());
            message.setSubject(notification.getTitle());
            message.setText(notification.getBody());

            mailSender.send(message);

            return NotificationSendResult.success();
        } catch (MailAuthenticationException e) {
            return NotificationSendResult.fail("EMAIL_AUTH_FAIL", e.getMessage());
        } catch (MailSendException e) {
            return NotificationSendResult.fail("EMAIL_SEND_FAIL", e.getMessage());
        } catch (MailException e) {
            return NotificationSendResult.fail("EMAIL_ERROR", e.getMessage());
        } catch (Exception e) {
            return NotificationSendResult.fail("EMAIL_EXCEPTION", e.getMessage());
        }
    }
}