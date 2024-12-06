package com.example.multiservice.service.impl;

import com.example.multiservice.service.EmailService;
import com.example.multiservice.utils.StringUtils;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.from}")
    private String emailFrom;

    @Override
    public String sendConfirmActiveEmail(String data) {
        String[] datas = StringUtils.splitStringBySpace(data);
        if (datas != null && datas.length > 1) {
            String emailTo = datas[0];
            String token = datas[1];

            //String activationLink = "https://example.com/activate?token=" + token; // Thay thế bằng link thực tế
            String activationLink = "http://localhost:8080/identity/auth/" + token;
            // HTML email content
            String htmlContent = """
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <style>
                    body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; background-color: #f9f9f9; }
                    .email-container { max-width: 600px; margin: 20px auto; background: #fff; padding: 20px; border-radius: 10px; box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1); }
                    .email-header { text-align: center; color: #555; }
                    .email-body { margin: 20px 0; text-align: center; }
                    .email-button { display: inline-block; padding: 10px 20px; color: #fff; background: #007BFF; text-decoration: none; border-radius: 5px; }
                    .email-footer { text-align: center; margin-top: 20px; font-size: 0.9em; color: #888; }
                </style>
            </head>
            <body>
                <div class="email-container">
                    <h2 class="email-header">Kích Hoạt Tài Khoản</h2>
                    <div class="email-body">
                        <p>Chào bạn,</p>
                        <p>Cảm ơn bạn đã đăng ký tài khoản. Hãy nhấn nút bên dưới để kích hoạt tài khoản của bạn:</p>
                        <a href="%s" class="email-button">Kích Hoạt Tài Khoản</a>
                    </div>
                    <div class="email-footer">
                        <p>Nếu bạn không thực hiện yêu cầu này, vui lòng bỏ qua email này.</p>
                        <p>Trân trọng,</p>
                        <p>Đội ngũ của chúng tôi</p>
                    </div>
                </div>
            </body>
            </html>
        """.formatted(activationLink);

            try {
                MimeMessage mimeMessage = mailSender.createMimeMessage();
                mimeMessage.setFrom(new InternetAddress(emailFrom));
                mimeMessage.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(emailTo));
                mimeMessage.setSubject("Kích Hoạt Tài Khoản");
                mimeMessage.setContent(htmlContent, "text/html; charset=utf-8");

                mailSender.send(mimeMessage);
                return "Email đã được gửi!";
            } catch (Exception e) {
                e.printStackTrace();
                return "Gửi email thất bại!";
            }
        }
        return "Dữ liệu không hợp lệ!";
    }
}
