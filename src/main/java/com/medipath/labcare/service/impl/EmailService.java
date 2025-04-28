package com.medipath.labcare.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendHtmlEmail(String to, String patientName, String subject, String message) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, "UTF-8");

            helper.setTo(to);
            helper.setSubject(subject); // Correctly set the subject
            helper.setFrom("sejalwaman@gmail.com");

            // Create the HTML content
            String htmlContent = "<html>" +
                    "<body style='background-color: #ffffff; font-family: Arial, sans-serif; padding: 20px;'>" +
                    "<h2 style='color: #4CAF50;'>Hello " + (patientName != null ? patientName : "Patient") + ",</h2>" +
                    "<p style='font-size: 16px; color: #333333;'>" + (message != null ? message : "Your appointment has been approved.") + "</p>" +
                    "<br/>" +
                    "<p style='font-size: 14px; color: #333333;'>Thank you,<br/>" +
                    "<span style='font-weight: bold;'>MediPath LabCare Team</span></p>" +  // Bold the team name for visibility
                    "</body>" +
                    "</html>";

            helper.setText(htmlContent, true); // second param 'true' means HTML

            mailSender.send(mimeMessage);

            System.out.println("✅ Email sent successfully to: " + to);

        } catch (MessagingException e) {
            System.err.println("❌ Failed to send email: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
