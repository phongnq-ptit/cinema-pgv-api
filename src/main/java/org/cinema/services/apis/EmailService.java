package org.cinema.services.apis;

import io.quarkus.mailer.Mailer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Default;
import jakarta.inject.Inject;
import java.util.Map;
import org.cinema.models.enums.MailTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import io.quarkus.mailer.Mail;

@ApplicationScoped
@Default
@Component
public class EmailService {
  @Inject
  private Mailer mailer;
  @Autowired
  private ClassLoaderTemplateResolver resolver;


  private String getMailBodyFromTemplate(MailTemplate templateMail, Map<String, Object> vars) {
    TemplateEngine htmlTemplateEngine = new TemplateEngine();
    htmlTemplateEngine.setTemplateResolver(resolver);

    // create context
    final Context ctx = new Context();
    for (String key : vars.keySet()) {
      ctx.setVariable(key, vars.get(key));
    }

    return htmlTemplateEngine.process(templateMail.getTemplateName(), ctx);
  }

  public void sendMail(MailTemplate templateMail, Map<String, Object> mailInfo) {
    String content = this.getMailBodyFromTemplate(templateMail, mailInfo);
    String subject = this.getSubjectMail(templateMail);

    // send mail by MailHog
    Mail mailContent = Mail.withHtml(mailInfo.get("recipient").toString(), subject, content);
    mailer.send(mailContent);
  }

  private String getSubjectMail(MailTemplate mailTemplate) {
    String subject;
    switch (mailTemplate) {
      case MAIL_VERIFY_ACCOUNT:
        subject = "Xác thực tài khoản hệ thống Cinema PGV";
        break;
      default:
        subject = "Email được gửi bởi Cinema PGV";
    }

    return subject;
  }
}
