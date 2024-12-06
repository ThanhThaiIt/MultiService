package com.example.multiservice.consumer;

import com.example.multiservice.configuration.RabbitmqConfig;
import com.example.multiservice.controller.NotificationController;
import com.example.multiservice.service.EmailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConsumerService {
    @Autowired
    private EmailService emailService;

    @Autowired
    private NotificationController notificationController;



    @RabbitListener(queues = RabbitmqConfig.ACTIVATE_ACCOUNT_QUEUE)
    public void handleEmail(String message) {

         emailService.sendConfirmActiveEmail(message);

    }

    @RabbitListener(queues = RabbitmqConfig.NOTIFICATION_QUEUE)
    public void handleNotification(String message) {
//        System.out.println("email  mess la: "+message);
//        String activationLink = "http://localhost:8080/identity/auth/resend-email?email=" + message;
//        notificationController.sendNotification("/notification/activation/"+message, activationLink);

        System.out.println("email message: " + message);
        String activationLink = "http://localhost:8080/identity/auth/resend-email?email=" + message;
        notificationController.sendNotification("/notification/activation/" + message,
                "Your account is not active. Click the link to activate: " + activationLink);

    }

}
