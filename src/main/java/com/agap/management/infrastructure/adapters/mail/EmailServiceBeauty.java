package com.agap.management.infrastructure.adapters.mail;

//@Service
//@RequiredArgsConstructor
public class EmailServiceBeauty {

    /*private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Async
    public void send(String to, String username, String templateName, String confirmationUrl) throws MessagingException {
        if ( !StringUtils.hasLength(templateName))  templateName = "confirm-email";

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(
                mimeMessage,
                MimeMessageHelper.MULTIPART_MODE_MIXED,
                StandardCharsets.UTF_8.name()
        );
        Map<String, Object> properties = new HashMap<>();
        properties.put("username", username);
        properties.put("confirmationUrl", confirmationUrl);

        Context context = new Context();
        context.setVariables(properties);

        helper.setFrom("bouali.social@gmail.com");
        helper.setTo(to);
        helper.setSubject("Welcome to our nice platform");

        String template = templateEngine.process(templateName, context);

        helper.setText(template, true);

        mailSender.send(mimeMessage);
    }*/
}
